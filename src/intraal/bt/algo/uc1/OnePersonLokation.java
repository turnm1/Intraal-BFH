/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
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
public class OnePersonLokation implements MqttCallback {

    BrickletAmbientLightV2 tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    SiotDashboardInput sdi;

    private String passageDetected = "0"; // war static 16.12.17

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "OnePerson";
    private final String USECASENR = "Location";
    private final String USECASE = "Helper";
    /////////////////////////////////////////////////////

    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectMQTT() throws Exception {
        con = new ConnectionParameters();
        sdi = new SiotDashboardInput();
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
        p.setWillTopic(con.getLastWillConnectionTopic(USECASE, USECASENR, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }

    public void locationOfPerson() throws Exception {
        connectMQTT();
        c.subscribe("Gateway/10.0.233.51/#", 0);
    }

    private void pushLocation(String location) throws Exception{
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((location).getBytes());
            sdi.setInputKey(con.getService_location());
            sdi.setInputMessage(location); 
            sdi.sendInput();
                if (location.equals("Haus verlassen")){
                    sdi.setInputKey(con.getService_inOrOut()); 
                    sdi.setInputMessage(location);
                    sdi.sendInput();
                } else if (location.equals("Eingang")){
                    location = "Zu Hause";
                    sdi.setInputKey(con.getService_inOrOut());   
                    sdi.setInputMessage(location);           
                    sdi.sendInput();
                }
        c.publish(con.getClientIDValueTopic(USECASE, USECASENR, UID), message);
        System.out.println("Location Algo Message: "+message);
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
                       pushLocation(passageDetected);
                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(sendedRoom)){
                       passageDetected = sendedRoom;
                       pushLocation(sendedRoom);
                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(sendedRoom) || passageDetected.equals("0")){
                       passageDetected = "Wohnzimmer";
                       pushLocation(passageDetected);
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
