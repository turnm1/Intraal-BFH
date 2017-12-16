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
public class OnePersonLokation implements MqttCallback {

    BrickletAmbientLightV2 tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;

    private String motionLocation;
    private String lastMotionLocation;
    private String passageLocation;
    
    private static String  motionDetected;
    private static String passageDetected = "0";
    private static String motionWas;

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
        this.lastMotionLocation = lokationPosition;
    }

    public void setPassageLocation(String passageLocation) {
        this.passageLocation = passageLocation;
    }

    public String getMotionLocation() {
        return motionLocation;
    }
    
    public String getLastMotionLocation() {
        return lastMotionLocation;
    }

    public String getPassageLocation() {
        return passageLocation;
    }

    
    
//        message = new MqttMessage();
//        message.setRetained(true);
//        message.setQos(0);
//        message.setPayload((" lux => Licht aus").getBytes());
//        c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
//        System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);
    
// if (messageVal.equals("Motion Detected")) {
//            setMotionLocation(sendedRoom);
//        } else if (messageVal.equals("Motion Ended")){
//            setLastMotionLocation(sendedRoom);
//        } else if (messageVal.equals("Passage Detected")) {
//            setPassageLocation(sendedRoom);
//        }
//         if (getMotionLocation().equals(getPassageLocation())){
//                direction = getPassageLocation();
//                System.out.println(direction);
//           } else {
//             direction = getMotionLocation();
//         }
//        }
    
    private void entryRoom(){
        if (motionDetected.equals(passageDetected)){
            System.out.println(passageDetected);
        }
    }
    
    private void pushLocation(String location){
        System.out.println(location);
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.endsWith("value")) {
            String messageVal = new String(message.getPayload());
            String[] res = topic.split("/", 5);
            String sendedRoom = res[3];
           
               
                if (sendedRoom.equals("Schlafzimmer")){
                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = sendedRoom;
                       pushLocation("L1"+passageDetected);
                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(sendedRoom)){
                       passageDetected = sendedRoom;
                       pushLocation("L2"+sendedRoom);
                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = "Wohnzimmer";
                       pushLocation("L3"+passageDetected);
                   } 
                }
                else if (sendedRoom.equals("Kuche")){
                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = sendedRoom;
                       pushLocation(passageDetected);
                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(sendedRoom)){
                       passageDetected = sendedRoom;
                       pushLocation(sendedRoom);
                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = "Eingang";
                       pushLocation(passageDetected);
                   }
                }
                 else if (sendedRoom.equals("Wohnzimmer")){
                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = sendedRoom;
                       pushLocation(passageDetected);
                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(sendedRoom)){
                       passageDetected = sendedRoom;
                       pushLocation(sendedRoom);
                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = "Eingang";
                       pushLocation(passageDetected);
                   } 
                }
                 else if (sendedRoom.equals("Eingang")){
                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = sendedRoom;
                       pushLocation(passageDetected);
                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(sendedRoom)){
                       passageDetected = sendedRoom;
                       pushLocation(sendedRoom);
                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = "Haus verlassen";
                       pushLocation(passageDetected);
                   }
                }
                else if (sendedRoom.equals("Bad")){
                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = sendedRoom;
                       pushLocation(passageDetected);
                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(sendedRoom)){
                       passageDetected = sendedRoom;
                       pushLocation(sendedRoom);
                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = "Eingang";
                       pushLocation(passageDetected);
                   }
                }
                
                // Oben ist alles gut, ab hier muss man noch entwickeln!
                else if (messageVal.equals("Motion Ended")){
                    if (sendedRoom.equals("Schlafzimmer")){
                        if (sendedRoom.equals(passageDetected)){
                            passageDetected = "Wohnzimmer";
                            pushLocation("MEnde"+sendedRoom);
                        }
                    } else if (sendedRoom.equals("Wohnzimmer")){
                        if (!sendedRoom.equals(passageDetected)){
                            passageDetected = "Wohn";
                            pushLocation("MEnde"+sendedRoom);
                        }
                    }
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
