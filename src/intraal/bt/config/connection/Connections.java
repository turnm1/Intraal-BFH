/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection;

import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.siot.SiotDashboardInput;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class Connections implements MqttCallback{
    
    ConnectionParameters con;
    MQTTCommunication c;
 
    public void getMQTTconnection(String modul, String room, String uid) throws Exception {
        con = new ConnectionParameters();
        c = new MQTTCommunication();
        MQTTParameters p = new MQTTParameters();
        p.setClientID(con.getRASPBERRY_PI_MQTT_BROKER_TOPIC(uid));
        p.setUserName(con.getRASPBERRY_PI_BENUTZER());
        p.setPassword(con.getRASPBERRY_PI_PW());
        p.setIsCleanSession(false);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(0);
        p.setServerURIs(URI.create(con.getRASPBERRY_BROKER_CONNECTION()));
        p.setWillTopic(con.getRASPBERRY_PI_MQTT_BROKER_LASTWILL(modul, room, uid));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }
    
    public void subscribeMQTT(){
        c.subscribe("Gateway/10.0.233.51/#", 0);
    }
    
    public void sendMQTTmessage(String modul, String room, String uid, String messageText){
        MqttMessage message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((messageText).getBytes());

        c.publish(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(modul, room, uid), message);
        System.out.println(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(modul, room, uid) + ": " + message);
    }
    
    public void sendSIOTmessage(String siotKey, String messageText) throws Exception{
            SiotDashboardInput sdi = new SiotDashboardInput();
            sdi.setInputKey(siotKey);
            sdi.setInputMessage(messageText); 
            sdi.sendInput();
    }
    
    
    public void getTinkerforgeConnection(String tinkerforgeIP) throws Exception {
        IPConnection ipcon = new IPConnection();
        ConnectionParameters cp = new ConnectionParameters();
        ipcon.connect(tinkerforgeIP, cp.getTINKERFORGE_PORT());
        System.out.println("VERBINDUNG WURDE MIT TINKERFORGE HERGESTELLT: " + tinkerforgeIP);
    }

        @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.print(""); // System.out.println(" ===== MQTT MESSAGE ERHALTEN! ===== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.print(""); // System.out.println(" ===== MQTT VERBINDUNG UNTERBROCKEN! ===== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
      System.out.print(""); //  System.out.println(" ===== MQTT MESSAGE GESENDET! ===== ");
    }
}
