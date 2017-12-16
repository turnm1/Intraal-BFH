/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

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
 * @author turna
 */
public class xxxLocation implements MqttCallback {

    BrickletAmbientLightV2 tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;

    private String motionLocation;
    private String passageLocation;
    private String direction;

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "UseCase";
    private final String ROOM = "One";
    private final String MODUL = "OnePersonLocation";
    /////////////////////////////////////////////////////


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
        p.setWillTopic(con.getLastWillConnectionTopic(MODUL, ROOM, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }

    public void locationOfPerson() throws Exception {
        connectHost();
        connectMQTT();
        c.subscribe("Gateway/10.0.233.51/#", 0);
    }

    public void setMotionLocation(String lokationPosition) {
        this.motionLocation = lokationPosition;
    }
        public void setLastMotionLocation(String lokationPosition) {
        this.motionLocation = lokationPosition;
    }

    public void setPassageLocation(String passageLocation) {
        this.passageLocation = passageLocation;
    }

    public String getMotionLocation() {
        return motionLocation;
    }
    
    public String getLastMotionLocation() {
        return motionLocation;
    }

    public String getPassageLocation() {
        return passageLocation;
    }

    private void setMotions(String room, String motionOrNot) throws Exception {
        if (motionOrNot.equals("Motion Detected")) {
            setMotionLocation(room);
            //  System.out.println("Standort_Motion: " + getMotionLocation());
        } else if (motionOrNot.equals("Motion Detected")){
            setLastMotionLocation(room);
        }
    }
//        message = new MqttMessage();
//        message.setRetained(true);
//        message.setQos(0);
//        message.setPayload((" lux => Licht aus").getBytes());
//        c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
//        System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);

    private void setPassages(String between, String passageText) throws Exception {
        if (passageText.equals("Passage Detected")) {
            setPassageLocation(between);
            setDirektion();
            //    System.out.println("Standort_Passage: " + getPassageLocation() + " | " + getPassageLocation());
//                System.out.println("P: Lokation = " +getPassageLocation());
//                System.out.println( getDirection());
//            if (getPassageLocation().equals(getMotionLocation())){
//                System.out.println("P & M: Lokation = " +getPassageLocation());
//            }
        }
    }

    public String getDirection() {
        return direction;
    }
    
    private void setDirektion(){
        System.out.println("Motion: "+getMotionLocation() + " - Passage: "+getPassageLocation());
        if (getMotionLocation().equals(getPassageLocation())){
           // if (NewPassageRoom.equals("Wohnzimmer")){
                direction = getPassageLocation();
                System.out.println(direction);
            //}
        } else {
            if (getPassageLocation().equals("Wohnzimmer")){ // || getLastMotionLocation().equals("Wohnzimmer")){
                direction = "Eingang";
                 System.out.println(direction);
            } else if (getPassageLocation().equals("Schlafzimmer")){ // || getLastMotionLocation().equals("Schlafzimmer")){
                direction = "Wohnzimmer";
                 System.out.println(direction);
            } else if (getPassageLocation().equals("Eingang")){ //|| getLastMotionLocation().equals("Eingang")){
                direction = "Aus dem Haus";
                 System.out.println(direction);
            } else if (getPassageLocation().equals("Bad")){ //|| getLastMotionLocation().equals("Bad")){
                direction = "Eingang";
                 System.out.println(direction);
            } else if (getPassageLocation().equals("Küche")){ //|| getLastMotionLocation().equals("Küche")){
                direction = "Eingang";
                 System.out.println(direction);
            }
        }
//        direction = getPassageLocation();
//        System.out.println(getDirection());
    }
    
    // Algo Use Case 1
//    public String getPersonLocation(){
//        String personIs = "Nicht zu Hause";
//        int count = 0;
//        if (!getPassageLocation().equals(personIs)){
//            if (getPassageLocation().equals("Eingang") && getMotionLocation().equals("Eingang")){
//                personIs = "Eingang";
//                return personIs;
//            } else if (getPassageLocation().equals("Wohnzimmer") && getMotionLocation().equals("Wohnzimmer")){
//                personIs = "Wohnzimmer";
//                return personIs;
//            }
//            personIs = getPassageLocation();
//        }
//        return personIs;
//    }
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.endsWith("value")) {
            String messageVal = new String(message.getPayload());
            String[] res = topic.split("/", 5);
            String sendedRoom = res[3];
            switch (messageVal) {
                case "Motion Detected":
                    setMotions(sendedRoom, messageVal);
                    break;
                case "Motion Ended":
                    setMotions(sendedRoom, messageVal);
                    break;
                case "Passage Detected":
                    setPassages(sendedRoom, messageVal);
                    break;
                case "No Passage":
                   // setPassages(sendedRoom, messageVal);
                    break;
                default:
                    break;
            }
        }
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
