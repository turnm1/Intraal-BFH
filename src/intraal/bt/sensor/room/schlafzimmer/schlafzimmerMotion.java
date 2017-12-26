/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.schlafzimmer;

import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.siot.SiotDashboardInput;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Turna
 */
public class schlafzimmerMotion implements MqttCallback {

    BrickletMotionDetector tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    SiotDashboardInput sdi = new SiotDashboardInput();


    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "wt9";
    private final String ROOM = "Schlafzimmer";
    private final String MODUL = "Motion";
    /////////////////////////////////////////////////////

/*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectHost() throws Exception {
        con = new ConnectionParameters();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletMotionDetector(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgSchlafzimmerIP(), con.getTgPort());
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
        p.setWillTopic(con.getLastWillConnectionTopic(MODUL, ROOM, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }

    /*
Motionsensor
     */
    public void doMotion() {
        try {
            connectHost();
            connectMQTT();
            message = new MqttMessage();
            // send (id, value, unit, date, time, status)
            
            // Add motion detected listener
            tinkerforg.addMotionDetectedListener(() -> {
                // publish Value
                message.setPayload("Motion Detected".getBytes());
                message.setRetained(true);
                message.setQos(0);
                c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                
                sdi.setInputKey(con.getM_inputKey_schlafz());
                sdi.setInputMessage(message.toString());
                try {
                    sdi.sendInput();
                } catch (Exception ex) {
                    System.out.print("Fehler");
                    Logger.getLogger(schlafzimmerMotion.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            // Add detection cycle ended listener
            tinkerforg.addDetectionCycleEndedListener(() -> {
                message.setPayload("Motion Ended".getBytes());
                message.setRetained(true);
                message.setQos(0);
                c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                
                sdi.setInputKey(con.getM_inputKey_schlafz());
                sdi.setInputMessage(message.toString());
                try {
                    sdi.sendInput();
                } catch (Exception ex) {
                    System.out.print("Fehler");
                    Logger.getLogger(schlafzimmerMotion.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            System.out.println("WIFI-Verbindung unterbrochen: "+ MODUL +"/"+ROOM+" IP: "+con.getTgSchlafzimmerIP());
        }
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Room: "+ROOM + ", Typ: " + MODUL +", Message Arrived! =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Room: "+ROOM + ", Typ: " + MODUL +", Disconnected! =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
       System.out.println(" =========== Room: "+ROOM + ", Typ: " + MODUL +",  OK! =========== ");
    }
    
}
