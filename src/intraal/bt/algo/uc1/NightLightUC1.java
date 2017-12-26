/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.rest.SmartMeService;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import intraal.bt.system.settings.Settings;
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
public class NightLightUC1 implements MqttCallback {

    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    Settings s;
    SmartMeService sme;
    MQTTCommunication c;

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "OnePersonLocation";
    private final String USECASENR = "One";
    private final String USECASE = "Usecase";
    /////////////////////////////////////////////////////

    private static boolean isNigh;
    
    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectMQTT() throws Exception {
        con = new ConnectionParameters();
        sme = new SmartMeService();
        c = new MQTTCommunication();
        s = new Settings();
        p = new MQTTParameters();
        p.setClientID(con.getClientIDTopic(UID));
        p.setUserName(con.getUserName());
        p.setPassword(con.getPassword());
        p.setIsCleanSession(false);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(0);
        p.setServerURIs(URI.create(con.getBrokerConnection()));
        p.setWillTopic(con.getLastWillConnectionTopic(USECASE, USECASENR, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }

    public void nightLight() throws Exception {
        connectMQTT();
        c.subscribe("Gateway/10.0.233.51/Helper/#", 0);
    }

    private void publishLightLokationSwitch(String UID, String Switch) {
        String location = UID;
        if (UID.equals(con.getSmWohnzimmerPlugUID())){
            location = "Wohnzimmer";
        } else if (UID.equals(con.getSmSchlafzimmerPlugUID())){
            location = "Schlafzimmer";
        } else if (UID.equals(con.getSmWohnzimmerPlugUID())){
            location = "Eingang";
        } else if (UID.equals(con.getSmBadPlugUID())){
            location = "Bad";
        } else if (UID.equals(con.getSmKüchePlugUID())){
            location = "Küche";
        }
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((location + " = " + Switch).getBytes());
        c.publish(con.getClientIDValueTopic(USECASE, USECASENR, UID), message);
        System.out.println(con.getClientIDValueTopic(USECASE, USECASENR, UID) + ": " + message);
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

        if (date.before(endTime) && startTime.after(date)) {
            System.out.println("NIGHTPHASE");
            isNigh = true;
        }
        return isNigh;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (isNight() == true){
            if (topic.endsWith("value")) {
                String messageVal = new String(message.getPayload());
                String[] res = topic.split("/", 5);
                String locationAlgo = res[2];
                //      Helper/Location/OnePerson
                //      Motion/Eingang/wtd
                System.err.println(topic);
                System.err.println(locationAlgo);
                System.err.println(messageVal);
                    if(locationAlgo.equals("OnePersonLocation")){ 
                        if(messageVal.equals("Schlafzimmer")){
                            // light on in Wohnzimmer, Schlafzimmer
                            sme.switchLightStatus(con.getSmWohnzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmWohnzimmerPlugUID(), "true");
                            sme.switchLightStatus(con.getSmSchlafzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmSchlafzimmerPlugUID(), "true");
                            // light off in Bad
                            sme.switchLightStatus(con.getSmBadPlugUID(), "false");
                            publishLightLokationSwitch(con.getSmBadPlugUID(), "false");
                            sme.switchLightStatus(con.getSmKüchePlugUID(), "false");
                            publishLightLokationSwitch(con.getSmKüchePlugUID(), "false");
                        } else if (messageVal.equals("Wohnzimmer")){
                            // light on in schlafz, wohnz, eingang
                            sme.switchLightStatus(con.getSmWohnzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmWohnzimmerPlugUID(), "true");
                            sme.switchLightStatus(con.getSmSchlafzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmSchlafzimmerPlugUID(), "true");
                            // light off in bad
                            sme.switchLightStatus(con.getSmBadPlugUID(), "false");
                            publishLightLokationSwitch(con.getSmBadPlugUID(), "false");
                            sme.switchLightStatus(con.getSmKüchePlugUID(), "false");
                            publishLightLokationSwitch(con.getSmKüchePlugUID(), "false");
                        } else if (messageVal.equals("Eingang")){
                            // light on in bad, eingang, wohnz
                            sme.switchLightStatus(con.getSmBadPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmBadPlugUID(), "true");
                            sme.switchLightStatus(con.getSmSchlafzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmSchlafzimmerPlugUID(), "true");
                            sme.switchLightStatus(con.getSmWohnzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmWohnzimmerPlugUID(), "true");
                            sme.switchLightStatus(con.getSmKüchePlugUID(), "true");
                            publishLightLokationSwitch(con.getSmKüchePlugUID(), "true");
                            // light off in keiner
                        } else if (messageVal.equals("Bad")){
                            // light on in Eingang, Bad
                            sme.switchLightStatus(con.getSmKüchePlugUID(), "true");
                            publishLightLokationSwitch(con.getSmKüchePlugUID(), "true");
                            sme.switchLightStatus(con.getSmWohnzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmWohnzimmerPlugUID(), "true");
                            sme.switchLightStatus(con.getSmBadPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmBadPlugUID(), "true");
                            // light off in schlafz, wohnz
                            sme.switchLightStatus(con.getSmSchlafzimmerPlugUID(), "false");
                            publishLightLokationSwitch(con.getSmSchlafzimmerPlugUID(), "false");
                        } else if (messageVal.equals("kueche")){
                            // light on in Eingang, Bad
                            sme.switchLightStatus(con.getSmKüchePlugUID(), "true");
                            publishLightLokationSwitch(con.getSmKüchePlugUID(), "true");
                            sme.switchLightStatus(con.getSmWohnzimmerPlugUID(), "true");
                            publishLightLokationSwitch(con.getSmWohnzimmerPlugUID(), "true");
                            // light off in schlafz, wohnz
                            sme.switchLightStatus(con.getSmBadPlugUID(), "false");
                            publishLightLokationSwitch(con.getSmBadPlugUID(), "false");
                            sme.switchLightStatus(con.getSmSchlafzimmerPlugUID(), "false");
                            publishLightLokationSwitch(con.getSmSchlafzimmerPlugUID(), "false");
                        }
                    } else {
                        sme.switchLightStatus(UID, "false");
                        publishLightLokationSwitch(UID, "false");
                        sme.switchLightStatus(UID, "false");
                        publishLightLokationSwitch(UID, "false");
                        sme.switchLightStatus(UID, "false");
                        publishLightLokationSwitch(UID, "false");
                        sme.switchLightStatus(UID, "false");
                        publishLightLokationSwitch(UID, "false");
                        sme.switchLightStatus(UID, "false");
                        publishLightLokationSwitch(UID, "false");
                    }
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Connection Lost =========== "+ cause);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Delivery Completed =========== ");
    }
    
//    public static void main(String[] args) {
//        NightLightUC1 n = new NightLightUC1();
//        System.err.println(n.isNight());
//    }

}
