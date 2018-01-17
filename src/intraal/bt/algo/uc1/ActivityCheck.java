/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import intraal.bt.system.settings.IntraalEinstellungen;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class ActivityCheck implements MqttCallback {

    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    WarningTimer w;
    IntraalEinstellungen s;

    private static boolean isNigh;
    private int flag = 0;
    private static int isMotionEnded1, isMotionEnded2, isMotionEnded3, isMotionEnded4, isMotionEnded5;
    private static int isPassage1, isPassage2, isPassage3, isPassage4, isPassage5;

    private final String UID = "OnePersonActivity";
    private final String USECASENR = "Two";
    private final String USECASE = "Usecase";

    private void connectMQTT() throws Exception {
        con = new ConnectionParameters();
        c = new MQTTCommunication();
        s = new IntraalEinstellungen();
        p = new MQTTParameters();
        p.setClientID(con.getRASPBERRY_PI_MQTT_BROKER_TOPIC(UID));
        p.setUserName(con.getRASPBERRY_PI_BENUTZER());
        p.setPassword(con.getRASPBERRY_PI_PW());
        p.setIsCleanSession(false);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(0);
        p.setServerURIs(URI.create(con.getRASPBERRY_BROKER_CONNECTION()));
        p.setWillTopic(con.getRASPBERRY_PI_MQTT_BROKER_LASTWILL(USECASE, USECASENR, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }

    public void activityCheck() throws Exception {
        connectMQTT();
        c.subscribe("Gateway/10.0.233.51/", 0);
    }

    private void pushMQTTmessage(String onBedOrNot) throws Exception {
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((onBedOrNot).getBytes());
        c.publish(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID), message);
        System.out.println(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID) + ": " + message);
    }

    private Date parseDate(String date) {
        final String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    private boolean isNight() {
        Calendar now = Calendar.getInstance();
        isNigh = false;

        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get(Calendar.MINUTE);
        Date date = parseDate(hour + ":" + minute);
        Date startTime = parseDate(s.getStartNightPhase());
        Date endTime = parseDate(s.getEndNightPhase());
        if (date.after(startTime) || date.before(endTime)) {
            isNigh = true;
        }
        return isNigh;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (isNight() == true) {

            if (topic.endsWith("value")) {
                String messageVal = new String(message.getPayload());
                String[] res = topic.split("\\/", -1);
                String activityType = res[2];
                String sendedRoom = res[3];
                
                if (messageVal.equals("Motion Ended")) {
                    if (sendedRoom.equals("Schlafzimmer")) {
                        isMotionEnded1 = 1;
                    } else if (sendedRoom.equals("Wohnzimmer")) {
                        isMotionEnded2 = 1;
                    } else if (sendedRoom.equals("Eingang")) {
                        isMotionEnded3 = 1;
                    } else if (sendedRoom.equals("Bad")) {
                        isMotionEnded4 = 1;
                    } else if (sendedRoom.equals("Kuche")) {
                        isMotionEnded5 = 1;
                    }
                    if (isMotionEnded1 + isMotionEnded2 + isMotionEnded3 + isMotionEnded4 + isMotionEnded5 == 5) {
                        w = new WarningTimer(15);
                    }

                } else if (messageVal.equals("Motion Detected")) {
                    if (sendedRoom.equals("Schlafzimmer")) {
                        isMotionEnded1 = -1;
                        w.stopWarningTimer();
                    } else if (sendedRoom.equals("Wohnzimmer")) {
                        isMotionEnded2 = -1;
                        w.stopWarningTimer();
                    } else if (sendedRoom.equals("Eingang")) {
                        isMotionEnded3 = -1;
                        w.stopWarningTimer();
                    } else if (sendedRoom.equals("Kuche")) {
                        isMotionEnded4 = -1;
                        w.stopWarningTimer();
                    } else if (sendedRoom.equals("Bad")) {
                        isMotionEnded5 = -1;
                        w.stopWarningTimer();
                    }
                } else if (messageVal.equals("No Passage") && flag == 0) {
                    w = new WarningTimer(15);
                    flag = 1;
                } else if (messageVal.equals("Passage Detected") && flag == 1) {
                    w.stopWarningTimer();
                    flag = 0;
                }
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" ===== MQTT VERBINDUNG UNTERBROCKEN! ===== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" ===== MQTT MESSAGE GESENDET! ===== ");
    }
}
