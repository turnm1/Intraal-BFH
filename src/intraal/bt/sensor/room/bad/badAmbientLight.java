/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.bad;

import com.tinkerforge.BrickletAmbientLightV2;
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
public class badAmbientLight implements MqttCallback {

    BrickletAmbientLightV2 tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    
    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "yiJ";
    private final String ROOM = "Bad";
    private final String MODUL = "AmbienteLight";
    /////////////////////////////////////////////////////
    
    private final static int offValue = 2 * 100;
    private final static int onValue = 3 * 100;

    public badAmbientLight() {

    }

    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectHost() throws Exception {
        con = new ConnectionParameters();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletAmbientLightV2(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgBadIP(), con.getTgPort());
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
    public void getLight() throws Exception {
        connectHost();
        connectMQTT();
        message = new MqttMessage();
        tinkerforg.addIlluminanceReachedListener(new BrickletAmbientLightV2.IlluminanceReachedListener() {
            public void illuminanceReached(long illuminance) {

                message.setRetained(true);
                message.setQos(0);

                if (illuminance < offValue) {
                    message.setPayload((illuminance / 10.0 + " lux => Licht aus").getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                    
                } else if (illuminance >= onValue) {
                    message.setPayload((illuminance / 10.0 + "lux => Licht ein").getBytes());
                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
                }
            }
        });

        // Get threshold callbacks with a debounce time of 10 seconds (10000ms)
        tinkerforg.setDebouncePeriod(10000);
        // Configure threshold for illuminance "greater than 500 Lux" (unit is Lux/100)
        tinkerforg.setIlluminanceCallbackThreshold('o', offValue, onValue);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Message Arrived =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Connection Lost motion_m1 =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Delivery Completed =========== ");
    }
   
}
