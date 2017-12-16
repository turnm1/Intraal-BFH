/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import com.tinkerforge.BrickletAmbientLightV2;
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

    BrickletAmbientLightV2 tinkerforg;
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
    private void connectHost() throws Exception {
        con = new ConnectionParameters();
        //    ipcon = new IPConnection();
        p = new MQTTParameters();
        ////////////////// EDIT HERE > IP ///////////////////
        //      ipcon.connect(con.getTgEingang2IP(), con.getTgPort());
        /////////////////////////////////////////////////////
    }

    private void connectMQTT() throws Exception {
        c = new MQTTCommunication();
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

    public void locationOfPerson() throws Exception {
        connectHost();
        connectMQTT();
        //     c.subscribe("Gateway/10.0.233.51/#", 0);
        c.subscribe("Gateway/10.0.233.51/Helper/Location/OnePerson/#", 0); // Präziser subscribe 16.12.17
    }

    private void publishLightLokationSwitch(String UID, String Switch) {
        String location = UID;
        if (UID.equals("")){
            location = "Wohnzimmer";
        } else if (UID.equals("")){
            location = "Schlafzimmer";
        } else if (UID.equals("")){
            location = "Eingang";
        } else if (UID.equals("")){
            location = "Bad";
        } else if (UID.equals("")){
            location = "Küche";
        }
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((location + ":" + Switch).getBytes());
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

        if (startTime.after(date) && endTime.before(date)) {
            System.out.println("NIGHTPHASE");
            isNigh = true;
        }
        return isNigh;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.endsWith("value")) {
            String messageVal = new String(message.getPayload());
                                                                                    ////// Hier noch die UIDs einsetzen! 16.12.17
            if (isNight() == true){
                if(messageVal.equals("Schlafzimmer")){
                    // light on in Wohnzimmer, Schlafzimmer
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    // light off in Bad, eingang
                    sme.switchLightStatus(UID, "false");
                    publishLightLokationSwitch(UID, "false");
                    sme.switchLightStatus(UID, "false");
                    publishLightLokationSwitch(UID, "false");
                } else if (messageVal.equals("Wohnzimmer")){
                    // light on in schlafz, wohnz, eingang
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    // light off in bad
                    sme.switchLightStatus(UID, "false");
                    publishLightLokationSwitch(UID, "false");
                } else if (messageVal.equals("Eingang")){
                    // light on in bad, eingang, wohnz
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    // light off in schlafz
                    sme.switchLightStatus(UID, "false");
                    publishLightLokationSwitch(UID, "false");
                } else if (messageVal.equals("Bad")){
                    // light on in Eingang, Bad
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    sme.switchLightStatus(UID, "true");
                    publishLightLokationSwitch(UID, "true");
                    // light off in schlafz, wohnz
                    sme.switchLightStatus(UID, "false");
                    publishLightLokationSwitch(UID, "false");
                    sme.switchLightStatus(UID, "false");
                    publishLightLokationSwitch(UID, "false");
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

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Connection Lost =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Delivery Completed =========== ");
    }

}
