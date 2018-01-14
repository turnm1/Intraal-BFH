///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.config.connection;
//
//import com.tinkerforge.AlreadyConnectedException;
//import com.tinkerforge.IPConnection;
//import com.tinkerforge.NotConnectedException;
//import intraal.bt.config.connection.siot.SiotDashboardInput;
//import intraal.bt.config.mqtt.MQTTCommunication;
//import intraal.bt.config.mqtt.MQTTParameters;
//import java.io.IOException;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
///**
// *
// * @author turna
// */
//public class ConnectionTest_orginal implements MqttCallback {
//
//    ConnectionParameters con;
//    IPConnection ipcon;
//    MqttMessage message;
//    MQTTParameters p;
//    MQTTCommunication c;
//    SiotDashboardInput sdi = new SiotDashboardInput();
//
//    /////////////////// EDIT HERE ///////////////////////
//    private final String UID = "Status";
//    private final String ROOM = "Tinkerforge";
//    private final String MODUL = "Connection";
//    /////////////////////////////////////////////////////
//
//    private String modulName;
//
//
//    private void connectMQTT() throws Exception {
//        con = new ConnectionParameters();
//        c = new MQTTCommunication();
//        p = new MQTTParameters();
//        p.setClientID(con.getRASPBERRY_PI_MQTT_BROKER_TOPIC(UID));
//        p.setUserName(con.getRASPBERRY_PI_BENUTZER());
//        p.setPassword(con.getRASPBERRY_PI_PW());
//        p.setIsCleanSession(false);
//        p.setIsLastWillRetained(true);
//        p.setLastWillMessage("offline".getBytes());
//        p.setLastWillQoS(0);
//        p.setServerURIs(URI.create(con.getRASPBERRY_BROKER_CONNECTION()));
//        p.setWillTopic(con.getRASPBERRY_PI_MQTT_BROKER_LASTWILL(MODUL, ROOM, UID));
//        p.setMqttCallback(this);
//        c.connect(p);
//        c.publishActualWill("online".getBytes());
//        p.getLastWillMessage();
//    }
//
//    private void sendConnectionStatus(String modulName, String status) {
//        try {
//            connectMQTT();
//        } catch (Exception ex) {
//            Logger.getLogger(ConnectionTest_orginal.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String nachricht = modulName + " = " + status;
//        
//        message = new MqttMessage();
//        message.setRetained(true);
//        message.setQos(0);
//
//        message.setPayload(nachricht.getBytes());
//
//        c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
//        System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
//        
////      sdi.setInputKey(con.getM_inputKey_bad());
////      sdi.setInputMessage(message.toString());
////      sdi.sendInput();
//    }
//    
//    private void getSensorModulStatus(String UID, short status) {
//        String statusStr;
//        System.out.println(status);
//        switch (UID) {
//            case "6e7NrQ":
//                modulName = "Eingang-Modul";
//                break;
//            case "6jDUPU":
//                modulName = "Eingang2-Modul";
//                break;
//            case "6e88VL":
//                modulName = "Küche-Modul";
//                break;
//            case "6QFxcy":
//                modulName = "Bad-Modul";
//                break;
//            case "62B7TB":
//                modulName = "Schlafzimmer-Modul";
//                break;
//            case "6CtMfr":
//                modulName = "Bett-Modul";
//                break;
//            case "5W5jVE":
//                modulName = "Wohnzimmer-Modul";
//                break;
//            default:
//                break;
//        }
//        if (status == 1) {
//            statusStr = "online";
//        } else {
//            statusStr = "offline";
//        }
//        sendConnectionStatus(modulName, statusStr);
//    }
//
//    private void getOfflineStatusViaIP(String IP) {
//        switch (IP) {
//            case "10.0.233.43":
//                modulName = "Eingang-Modul";
//                break;
//            case "10.0.233.49":
//                modulName = "Eingang2-Modul";
//                break;
//            case "10.0.233.44":
//                modulName = "Küche-Modul";
//                break;
//            case "10.0.233.45":
//                modulName = "Bad-Modul";
//                break;
//            case "10.0.233.46":
//                modulName = "Schlafzimmer-Modul";
//                break;
//            case "10.0.233.48":
//                modulName = "Bett-Modul";
//                break;
//            case "10.0.233.47":
//                modulName = "Wohnzimmer-Modul";
//                break;
//            default:
//                break;
//        }
//        String off = "offline";
//        sendConnectionStatus(modulName, off);
//    }
//
//    private void testModul(String IP, int Port) {
//        
//        try {
//            IPConnection ipcon = new IPConnection();
//            ipcon.connect(IP, Port);
//            
//            List<String> connectedDevices = new ArrayList<>();
//
//            ipcon.addEnumerateListener(new IPConnection.EnumerateListener() {
//                public void enumerate(String uid, String connectedUid, char position,
//                        short[] hardwareVersion, short[] firmwareVersion,
//                        int deviceIdentifier, short enumerationType) {
//                    
//                    // einmaliger checker
//                    if(connectedDevices.contains(uid)) {
//                        getSensorModulStatus(connectedUid, ipcon.getConnectionState());
//                        connectedDevices.add(uid);
//                    }
//                    // Bei offline von sensoren wird nichts angezeigt!
//                    if (connectedUid.equals(" ")){
//                        getOfflineStatusViaIP(IP);
//                    }
//                    
//                }
//            });
//
//            ipcon.enumerate();
//            Thread.sleep(2000);
//            ipcon.disconnect();
//            
//        } catch (IOException | AlreadyConnectedException | NotConnectedException | InterruptedException ex) {
//            getOfflineStatusViaIP(IP);
//        }
//    }
//    
//        @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Message Arrived! =========== ");
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Disconnected! =========== ");
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ",  OK! =========== ");
//    }
//        
//    public void connectionTestWithFeedback(){
//        ConnectionParameters cp = new ConnectionParameters();
//        int port = cp.getTgPort();
//   //     testModul(cp.getTgBadIP(), port);
//        testModul(cp.getTgBettIP(), port);
//   //     testModul(cp.getTgEingang2IP(), port);
//  //      testModul(cp.getTgEingangIP(), port);
// //       testModul(cp.getTgKücheIP(), port);
//  //      testModul(cp.getTgSchlafzimmerIP(), port);
//  //      testModul(cp.getTgWohnzimmerIP(), port);
//    }
//}
