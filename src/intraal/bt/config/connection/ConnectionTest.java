///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.config.connection;
//
//import com.tinkerforge.AlreadyConnectedException;
//import com.tinkerforge.BrickletMotionDetector;
//import com.tinkerforge.IPConnection;
//import com.tinkerforge.NotConnectedException;
//import intraal.bt.config.connection.siot.SiotDashboardInput;
//import intraal.bt.config.mqtt.MQTTCommunication;
//import intraal.bt.config.mqtt.MQTTParameters;
//import java.io.IOException;
//import java.net.URI;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
///**
// *
// * @author turna
// */
//public class ConnectionTest {
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
//    /*
//    Tinkerforge Room Moduls with Sensor Testing
//     */
//    private void connectHost() {
//        con = new ConnectionParameters();
//        ipcon = new IPConnection();
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
//        c.connect(p);
//        c.publishActualWill("online".getBytes());
//        p.getLastWillMessage();
//    }
//
//    private void sendConnectionStatus(String modulName, boolean status) throws Exception {
//        connectMQTT();
//        message = new MqttMessage();
//        message.setPayload(modulName.getBytes());
//        message.setRetained(true);
//        message.setQos(0);
//
//        if (status == true) {
//            c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
//            System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
//
//            sdi.setInputKey(con.getM_inputKey_bad());
//            sdi.setInputMessage(message.toString());
//            try {
//                sdi.sendInput();
//            } catch (Exception ex) {
//                System.out.print("Fehler: " + ex);
//            }
//        }
//    }
//
//    private boolean testTinkgerforge(String host, int port, String modulName) {
//        try {
//            System.out.println("Testing: "+modulName);
//            ipcon.setTimeout(100);
//            ipcon.connect(host, port);
//            //    ipcon.enumerate();
//            ipcon.disconnect();
//            return true;
//        } catch (IOException ex) {
//            Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (AlreadyConnectedException ex) {
//            Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NotConnectedException ex) {
//            Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
//
//    public void testEingang2Modul() {
//        String host = con.getTgEingang2IP();
//        int PORT = con.getTgPort();
//        if (testTinkgerforge(host, PORT, "Eingang2") == true){
//            System.out.println("Success");
//        } else {
//          System.out.println("NOT Success");
//        }
//    }
//
//    public void testEingang1Modul() {
//        String host = con.getTgEingangIP();
//        int PORT = con.getTgPort();
//        if (testTinkgerforge(host, PORT, "Eingang1")  == true){
//            System.out.println("Success");
//        } else {
//          System.out.println("NOT Success");
//        }
//    }
//
//    public void testSchlafzimmerModul() {
//        String host = con.getTgSchlafzimmerIP();
//        int PORT = con.getTgPort();
//        testTinkgerforge(host, PORT, "Schlafzimmer");
//    }
//
//    public void testWohnzimmerModul() {
//        String host = con.getTgWohnzimmerIP();
//        int PORT = con.getTgPort();
//        testTinkgerforge(host, PORT, "Wohnzimmer");
//    }
//
//    public void testKücheModul() throws Exception {
//        String host = con.getTgKücheIP();
//        int PORT = con.getTgPort();
//        testTinkgerforge(host, PORT, "Küche");
//    }
//
//    public void testBadModul() {
//        String host = con.getTgBadIP();
//        int PORT = con.getTgPort();
//        if (testTinkgerforge(host, PORT, "Bad")  == true){
//            System.out.println("Success");
//        } else {
//          System.out.println("NOT Success");
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        ConnectionTest ct = new ConnectionTest();
//        ct.connectHost();
//        ct.testBadModul();
//        ct.testEingang1Modul();
//        ct.testEingang2Modul();
//        ct.testSchlafzimmerModul();
//        ct.testWohnzimmerModul();
//        ct.testKücheModul();
//    }
//
//}
