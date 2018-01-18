///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.algorithm;
//
//import com.tinkerforge.IPConnection;
//import intraal.bt.config.connection.ConnectionParameters;
//import intraal.bt.config.connection.Connections;
//import intraal.bt.config.connection.siot.SiotDashboardInput;
//import intraal.bt.config.connection.mqtt.MQTTCommunication;
//import intraal.bt.config.connection.mqtt.MQTTParameters;
//import java.net.URI;
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
//public class BedActivityTest implements MqttCallback{
//
//ConnectionParameters con;
//    IPConnection ipcon;
//    MqttMessage message;
//    MQTTParameters p;
//    MQTTCommunication c;
//    SiotDashboardInput sdi;
// 
//
// 
//    private static int LoadCell1 = 0;
//    private static int LoadCell2 = 0;
//    private static int LoadCell3 = 0;
//    private static int LoadCell4 = 0;
// 
//
// 
//    /////////////////// EDIT HERE ///////////////////////
// 
//    private final String UID = "OnePerson";
//    private final String USECASENR = "BedActivity";
//    private final String USECASE = "Helper";
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
//        p.setWillTopic(con.getRASPBERRY_PI_MQTT_BROKER_LASTWILL(USECASE, USECASENR, UID));
//        p.setMqttCallback(this);
//        c.connect(p);
//        c.publishActualWill("online".getBytes());
//        p.getLastWillMessage();
//    }
// 
//    public void bedActivity()  {
//    try {
//        connectMQTT();
//        c.subscribe("Gateway/10.0.233.51/#", 0);
//    } catch (Exception ex) {
//        Logger.getLogger(BedActivityTest.class.getName()).log(Level.SEVERE, null, ex);
//    }
//        ;
//    }
// 
//
// 
//    private void OnBedOrNot() throws Exception{
// 
//        System.out.println(LoadCell1);
//        if (LoadCell1 == 99){
//            pushLocation("On the bed");
//        } else {
//            pushLocation("Not on the bed");
//        }
// 
//    }
// 
//    private void pushLocation(String onBedOrNot) throws Exception{
//        message = new MqttMessage();
//        message.setRetained(true);
//        message.setQos(0);
//        message.setPayload((onBedOrNot).getBytes());
//        c.publish(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID), message);
//        System.out.println("Bed Activity Helper Message: "+message);
//    }
// 
//    
// 
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
// 
//        if (topic.endsWith("value")) {
//            String messageVal = new String(message.getPayload());
//            String[] res = topic.split("\\/", -1);
//            String activityType = res[2];
//            String sendedRoom = res[3];
// 
//               // Methode
//               
//                   }
//           }         
//    
// 
//    @Override
//    public void connectionLost(Throwable cause) {
//        System.out.println(" =========== Connection Lost =========== ");
//    }
// 
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println(" =========== Delivery Completed =========== ");
//    }
// 
//    public static void main(String[] args) throws Exception {
//        BedActivityTest b = new BedActivityTest();
//        b.bedActivity();
//    }
//
// 
//}