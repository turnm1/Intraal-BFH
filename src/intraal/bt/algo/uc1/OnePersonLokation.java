/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.Connections;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class OnePersonLokation implements MqttCallback {

    ConnectionParameters cp;
    Connections con;

    private String passageDetected = "0";
    
    private final String UID = "OnePerson";
    private final String USECASENR = "Location";
    private final String USECASE = "Helper";


    public void locationOfPerson() throws Exception {
        con = new Connections();
        con.getMQTTconnection(USECASE, USECASENR, UID);
        con.subscribeMQTT();
    }

    private void pushLocation(String location) throws Exception{
        con = new Connections();
        con.getMQTTconnection(USECASE, USECASENR, UID);
        con.sendMQTTmessage(USECASE, USECASENR, UID, location);
        con.sendSIOTmessage(cp.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
            if (location.equals("Haus verlassen")){
                con.sendSIOTmessage(UID, location);
            } else if (location.equals("Eingang")){
                location = "Zu Hause";
                con.sendSIOTmessage(cp.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
            }
        System.out.println("Position of Person: " + location);
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
        System.out.println(" ===== MQTT VERBINDUNG UNTERBROCKEN! ===== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" ===== MQTT MESSAGE GESENDET! ===== ");
    }
}
