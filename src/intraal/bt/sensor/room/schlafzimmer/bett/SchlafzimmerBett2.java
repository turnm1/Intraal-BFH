/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.schlafzimmer.bett;

import com.tinkerforge.BrickletLoadCell;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.siot.SiotDashboardInput;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import intraal.bt.system.settings.Settings;
import java.net.URI;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class SchlafzimmerBett2 implements MqttCallback {

    BrickletLoadCell tinkerforg;
    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    Settings s;
    SiotDashboardInput sdi = new SiotDashboardInput();

    /////////////////// EDIT HERE ///////////////////////
    private final String UID = "vcQ";
    private final String ROOM = "Schlafzimmer";
    private final String MODUL = "Load Cell2";
    /////////////////////////////////////////////////////

    private static int flag;

    public SchlafzimmerBett2() {

    }

    /*
    Connection with WLAN & MQTT Raspberry Pi Broker
     */
    private void connectHost() throws Exception {
        con = new ConnectionParameters();
        ipcon = new IPConnection();
        p = new MQTTParameters();
        tinkerforg = new BrickletLoadCell(UID, ipcon);
        ////////////////// EDIT HERE > IP ///////////////////
        ipcon.connect(con.getTgBettIP(), con.getTgPort());
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

    public void getPersonOnBed() {
        try {
            connectHost();
            connectMQTT();
            message = new MqttMessage();
            // Add temperature reached listener (parameter has unit °C/100)

            tinkerforg.addWeightListener(new BrickletLoadCell.WeightListener() {
                public void weight(int weight) {

                    if (weight >= 20000 && flag != 0) {
                        flag = 0;
                        message.setPayload(("On the bed").getBytes());
                        message.setRetained(true);
                        message.setQos(0);
                        c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                        System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);

//                        sdi.setInputKey(con.getTgBettIP());       // inputKey
//                        sdi.setInputMessage(message.toString());
                        try {
                            sdi.sendInput();
                        } catch (Exception ex) {
                            System.out.print("Fehler");
                        }

                    }

                    if (weight < 15000 && flag != 1) {
                        flag = 1;
                        message.setPayload(("Not on the bed").getBytes());
                        message.setRetained(true);
                        message.setQos(0);
                        c.publish(con.getClientIDValueTopic(MODUL, ROOM, UID), message);
                        System.out.println(con.getClientIDValueTopic(MODUL, ROOM, UID) + ": " + message);

//                        sdi.setInputKey(con.getTgBettIP());       // inputKey
//                        sdi.setInputMessage(message.toString());
                        try {
                            sdi.sendInput();
                        } catch (Exception ex) {
                            System.out.print("Fehler");
                        }
                    }

                }
            });

            tinkerforg.setWeightCallbackPeriod(1000);

        } catch (Exception ex) {
            System.out.println("WIFI-Verbindung unterbrochen: " + MODUL + "/" + ROOM + " IP: " + con.getTgSchlafzimmerIP());
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Message Arrived! =========== ");
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ", Disconnected! =========== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(" =========== Room: " + ROOM + ", Typ: " + MODUL + ",  OK! =========== ");
    }

}
