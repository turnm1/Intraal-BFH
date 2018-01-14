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
public class BedActivity implements MqttCallback {

    ConnectionParameters cp;
    Connections con;
    
    private static int LoadCell1, LoadCell2, LoadCell3, LoadCell4 = 0;

    private final String UID = "OnePerson";
    private final String USECASENR = "BedActivity";
    private final String USECASE = "Helper";

    public void bedActivity() throws Exception {
        con = new Connections();
        con.getMQTTconnection(USECASE, USECASENR, UID);
        con.subscribeMQTT("#/");
    }

    private void OnBedOrNot() throws Exception{
        System.out.println(LoadCell1+LoadCell2+LoadCell3+LoadCell4);
        if (LoadCell1+LoadCell2+LoadCell3+LoadCell4 == 4){
            String nachricht = "On the bed";
            con.sendMQTTmessage(USECASE, USECASENR, UID, nachricht);
        } else if (LoadCell1+LoadCell2+LoadCell3+LoadCell4 == 0){
            String nachricht = "Not on the bed";
            con.sendMQTTmessage(USECASE, USECASENR, UID, nachricht);
        }
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.endsWith("value")) {
            String messageVal = new String(message.getPayload());
            String[] res = topic.split("\\/", -1);
            String activityType = res[2];
            String sendedRoom = res[3];
            
                if (sendedRoom.equals("Schlafzimmer")){
                   if (messageVal.equals("On the bed")){
                       if (activityType.equals("Load Cell1")){
                           System.out.println("LoadCell 1 Load = 1");
                           LoadCell1 = 1;
                           OnBedOrNot();
                       } else if (activityType.equals("Load Cell2")){
                           LoadCell2 = 1;
                           OnBedOrNot();
                       } else if (activityType.equals("Load Cell3")){
                           LoadCell3 = 1;
                           OnBedOrNot();
                       } else if (activityType.equals("Load Cell4")){
                           LoadCell4 = 1;
                           OnBedOrNot();
                       }
                       
                   } else if (messageVal.equals("Not on the bed")){
                        if (activityType.equals("Load Cell1")){
                           LoadCell1 = 0;
                           OnBedOrNot();
                       } else if (activityType.equals("Load Cell2")){
                           LoadCell2 = 0;
                           OnBedOrNot();
                       } else if (activityType.equals("Load Cell3")){
                           LoadCell3 = 0;
                           OnBedOrNot();
                       } else if (activityType.equals("Load Cell4")){
                           LoadCell4 = 0;
                           OnBedOrNot();
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