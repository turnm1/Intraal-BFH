/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

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
public class BedActivity implements MqttCallback {

    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    SiotDashboardInput sdi;

    private static int LoadCell1 = 0;
    private static int LoadCell2 = 0;
    private static int LoadCell3 = 0;
    private static int LoadCell4 = 0;

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "OnePerson";
    private final String USECASENR = "BedActivity";
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

    public void bedActivity() throws Exception {
        connectMQTT();
        c.subscribe("Gateway/10.0.233.51/#", 0);
    }

    private void OnBedOrNot() throws Exception{
        System.out.println(LoadCell1+LoadCell2+LoadCell3+LoadCell4);
        if (LoadCell1+LoadCell2+LoadCell3+LoadCell4 == 4){
            pushLocation("On the bed");
        } else {
            pushLocation("Not on the bed");

        }
    }
    
    private void pushLocation(String onBedOrNot) throws Exception{
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((onBedOrNot).getBytes());
//            sdi.setInputKey(con.getService_location());
//            sdi.setInputMessage(location); 
//            sdi.sendInput();
        c.publish(con.getClientIDValueTopic(USECASE, USECASENR, UID), message);
        System.out.println("Bed Activity Helper Message: "+message);
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
        System.out.println(" =========== Connection Lost =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Delivery Completed =========== ");
    }

}