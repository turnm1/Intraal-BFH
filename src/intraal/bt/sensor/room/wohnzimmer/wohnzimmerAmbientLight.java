///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.sensor.room.wohnzimmer;
//
//import com.tinkerforge.BrickletAmbientLightV2;
//import com.tinkerforge.IPConnection;
//import intraal.bt.config.connection.ConnectionParameters;
//import intraal.bt.config.connection.siot.SiotDashboardInput;
//import intraal.bt.config.mqtt.MQTTCommunication;
//import intraal.bt.config.mqtt.MQTTParameters;
//import intraal.bt.sensor.room.schlafzimmer.schlafzimmerMotion;
//import intraal.bt.system.settings.IntraalEinstellungen;
//import java.net.URI;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
///**
// *
// * @author Turna
// */
//public class wohnzimmerAmbientLight implements MqttCallback {
//
//    BrickletAmbientLightV2 tinkerforg;
//    ConnectionParameters con;
//    IPConnection ipcon;
//    MqttMessage message;
//    MQTTParameters p;
//    MQTTCommunication c;
//    IntraalEinstellungen s;
//    SiotDashboardInput sdi = new SiotDashboardInput();
//
//    /////////////////// EDIT HERE ///////////////////////
//    private final String UID = "uQu";
//    private final String ROOM = "Wohnzimmer";
//    private final String MODUL = "AmbienteLight";
//    /////////////////////////////////////////////////////
//
//    private int offValue = s.getAmbientLightOff();
//    private int onValue = s.getAmbientLightOn();
//
//    public wohnzimmerAmbientLight() {
//
//    }
//
//    /*
//    Connection with WLAN & MQTT Raspberry Pi Broker
//     */
//    private void connectHost() throws Exception {
//        con = new ConnectionParameters();
//        ipcon = new IPConnection();
//        p = new MQTTParameters();
//        tinkerforg = new BrickletAmbientLightV2(UID, ipcon);
//        ////////////////// EDIT HERE > IP ///////////////////
//        ipcon.connect(con.getTgWohnzimmerIP(), con.getTgPort());
//        /////////////////////////////////////////////////////
//    }
//
//    private void connectMQTT() throws Exception {
//        c = new MQTTCommunication();
//        p = new MQTTParameters();
//        p.setClientID(con.getClientIDTopic(UID));
//        p.setUserName(con.getUserName());
//        p.setPassword(con.getPassword());
//        p.setIsCleanSession(false);
//        p.setIsLastWillRetained(true);
//        p.setLastWillMessage("offline".getBytes());
//        p.setLastWillQoS(0);
//        p.setServerURIs(URI.create(con.getBrokerConnection()));
//        p.setWillTopic(con.getLastWillConnectionTopic(MODUL, ROOM, UID));
//        p.setMqttCallback(this);
//        c.connect(p);
//        c.publishActualWill("online".getBytes());
//        p.getLastWillMessage();
//    }
//
//    /*
//    Infrarotsensor
//     */
//    public void getLight() {
//        try {
//            connectHost();
//            connectMQTT();
//            message = new MqttMessage();
//            tinkerforg.addIlluminanceReachedListener((long illuminance) -> {
//                message.setRetained(true);
//                message.setQos(0);
//                
//                if (illuminance < offValue) {
//                    message.setPayload((illuminance / 10.0 + " lux => Licht aus").getBytes());
//                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
//                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
//                    
//                    sdi.setInputKey(con.getAl_inputKey_schlafz());
//                    sdi.setInputMessage(message.toString());
//                    try {
//                        sdi.sendInput();
//                    } catch (Exception ex) {
//                        System.out.print("Fehler");
//                        Logger.getLogger(schlafzimmerMotion.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                } else if (illuminance >= onValue) {
//                    message.setPayload((illuminance / 10.0 + "lux => Licht ein").getBytes());
//                    c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
//                    System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
//                    
//                    sdi.setInputKey(con.getAl_inputKey_schlafz());
//                    sdi.setInputMessage(message.toString());
//                    try {
//                        sdi.sendInput();
//                    } catch (Exception ex) {
//                        System.out.print("Fehler");
//                        Logger.getLogger(schlafzimmerMotion.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                }
//            });
//            
//            tinkerforg.setDebouncePeriod(10000);
//            tinkerforg.setIlluminanceCallbackThreshold('o', offValue, onValue);
//        } catch (Exception ex) {
//            System.out.println("WIFI-Verbindung unterbrochen: "+ MODUL +"/"+ROOM+" IP: "+con.getTgWohnzimmerIP());
//        }
//    }
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        System.out.println(" =========== Message Arrived =========== ");
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        System.out.println(" =========== Connection Lost motion_m1 =========== ");
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println(" =========== Delivery Completed =========== ");
//    }
//
//}
