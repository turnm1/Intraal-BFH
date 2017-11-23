/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.wohnzimmer;

import intraal.bt.sensor.room.schlafzimmer.*;
import intraal.bt.sensor.room.eingang.*;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Turna
 */
public class wohnzimmerPassage implements MqttCallback {

    BrickletDistanceIR tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;

    private int flag = 0;

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "tJ9";
    private final String ROOM = "Wohnzimmer";
    private final String MODUL = "Passage";
    /////////////////////////////////////////////////////

    public wohnzimmerPassage() {

    }

    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectHost() throws Exception {
        con = new ConnectionParameters();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletDistanceIR(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgEingang2IP(), con.getTgPort());
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
    Infrarotsensor
     */
    public void doPassage() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        tinkerforg.addDistanceListener((int distance) -> {
            message.setRetained(true);
            message.setQos(0);
            
            if (distance <= 350 && flag != 0) {
                flag = 0;
                message.setPayload("Passage Detected".getBytes());
                c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                
            } else if (distance > 350 && flag != 1) {
                flag = 1;
                message.setPayload("No Passage".getBytes());
                c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                
            }
        });
        // Set period for distance callback to 0.5s (500ms)
        // Note: The distance callback is only called every 0.5 seconds
        //       if the distance has changed since the last call!
        tinkerforg.setDistanceCallbackPeriod(200);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Message Arrived! =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Disconnected! =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ",  OK! =========== ");
    }

}
