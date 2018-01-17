/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import com.tinkerforge.BrickletAmbientLightV2;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.Connections;
import intraal.bt.config.connection.siot.SiotDashboardInput;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import intraal.bt.system.settings.IntraalEinstellungen;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class OnePersonLokation implements MqttCallback {

    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    WarningTimer w;
    IntraalEinstellungen s;

    private String passageDetected = "0";
    
    private final String UID = "OnePerson";
    private final String USECASENR = "Location";
    private final String USECASE = "Helper";

    private void connectMQTT() throws Exception {
        con = new ConnectionParameters();
        c = new MQTTCommunication();
        p = new MQTTParameters();
        p.setClientID(con.getRASPBERRY_PI_MQTT_BROKER_TOPIC(UID));
        p.setUserName(con.getRASPBERRY_PI_BENUTZER());
        p.setPassword(con.getRASPBERRY_PI_PW());
        p.setIsCleanSession(false);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(0);
        p.setServerURIs(URI.create(con.getRASPBERRY_BROKER_CONNECTION()));
        p.setWillTopic(con.getRASPBERRY_PI_MQTT_BROKER_LASTWILL(USECASE, USECASENR, UID));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }   
    
    
    
    public void locationOfPerson() throws Exception {
        c = new MQTTCommunication();
        connectMQTT();
        c.subscribe("Gateway/10.0.233.51/#", 0);
    }
    
    private void pushMQTTmessage(String onBedOrNot) throws Exception {
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((onBedOrNot).getBytes());
        c.publish(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID), message);
        System.out.println(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID) + ": " + message);
    }

    public void sendSIOTmessage(String siotKey, String messageText) throws Exception {
        SiotDashboardInput sdi = new SiotDashboardInput();
        sdi.setInputKey(siotKey);
        sdi.setInputMessage(messageText);
        sdi.sendInput();
    }

    private void pushLocation(String location) throws Exception{
        pushMQTTmessage(location);
        sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
            if (location.equals("Haus verlassen")){
                sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
            } else if (location.equals("Eingang")){
                location = "Zu Hause";
                sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
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
