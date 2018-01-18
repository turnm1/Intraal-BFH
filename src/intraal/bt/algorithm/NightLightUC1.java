///* 
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.algorithm;
//
//import com.tinkerforge.IPConnection;
//import intraal.bt.config.connection.ConnectionParameters;
//import intraal.bt.config.connection.rest.SmartMeService;
//import intraal.bt.config.connection.siot.SiotDashboardInput;
//import intraal.bt.config.connection.mqtt.MQTTCommunication;
//import intraal.bt.config.connection.mqtt.MQTTParameters;
//import intraal.bt.system.settings.IntraalEinstellungen;
//import java.net.URI;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
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
//public class NightLightUC1 implements MqttCallback {
//
//    ConnectionParameters con;
//    IPConnection ipcon;
//    MqttMessage message;
//    MQTTParameters p;
//    MQTTCommunication c;
//    WarningTimer w;
//    IntraalEinstellungen s;
//
//    private final String UID = "OnePerson";
//    private final String USECASENR = "Location";
//    private final String USECASE = "Usecase";
//
//    private static boolean isNigh;
//    private String passageDetected = "0";
//
//  private void connectMQTT() throws Exception {
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
//    public void runNightLight()  {
//    try {
//        connectMQTT();
//        c.subscribe("Gateway/10.0.233.51/#", 0);
//    } catch (Exception ex) {
//        Logger.getLogger(BedActivityTest.class.getName()).log(Level.SEVERE, null, ex);
//    }
//        ;
//    }
//
//    private void pushMQTTmessage(String onBedOrNot) throws Exception {
//        message = new MqttMessage();
//        message.setRetained(true);
//        message.setQos(0);
//        message.setPayload((onBedOrNot).getBytes());
//        c.publish(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID), message);
//        System.out.println(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(USECASE, USECASENR, UID) + ": " + message);
//    }
//
//    private void publishLightLokationSwitch(String plugUID, String Switch) throws Exception {
//        String location = plugUID;
//        if (plugUID.equals(con.getSMART_ME_PLUG_KEY_WOHNZIMMER())) {
//            location = "Wohnzimmer";
//        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER())) {
//            location = "Schlafzimmer";
//        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_WOHNZIMMER())) {
//            location = "Eingang";
//        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_BAD())) {
//            location = "Bad";
//        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_EINGANG())) {
//            location = "Küche";
//        }
//        String nachricht = location + "=" + Switch;
//        pushMQTTmessage(nachricht);
//    }
//    
//        public void sendSIOTmessage(String siotKey, String messageText) throws Exception {
//        SiotDashboardInput sdi = new SiotDashboardInput();
//        sdi.setInputKey(siotKey);
//        sdi.setInputMessage(messageText);
//        sdi.sendInput();
//    }
//    
//      private void pushLocation(String location) throws Exception{
//        pushMQTTmessage(location);
//        
//        sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
//            if (location.equals("Haus verlassen")){
//                sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
//            } else if (location.equals("Eingang")){
//                location = "Zu Hause";
//                sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
//            }
//        System.out.println("Position of Person: " + location);
//    }
//
//    private Date parseDate(String date) {
//        final String inputFormat = "HH:mm";
//        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
//        try {
//            return inputParser.parse(date);
//        } catch (java.text.ParseException e) {
//            return new Date(0);
//        }
//    }
//
//    private boolean isNight() {
//        IntraalEinstellungen ie = new IntraalEinstellungen();
//        Calendar now = Calendar.getInstance();
//        isNigh = false;
//
//        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
//        int minute = now.get(Calendar.MINUTE);
//        Date date = parseDate(hour + ":" + minute);
//        Date startTime = parseDate(ie.getStartNightPhase());
//        Date endTime = parseDate(ie.getEndNightPhase());
//        if (date.after(startTime) || date.before(endTime)) {
//            isNigh = true;
//        }
//        return isNigh;
//    }
//    
//    private void nightLightAlgo(String algoTyp, String algoCase, String messageVal) throws Exception{
//         con = new ConnectionParameters();
//      SmartMeService sme = new SmartMeService();
//      
//      if (isNight() == true) {
//        if (algoTyp.equals("Helper")) { 
//            if (algoCase.equals("BedActivity")) {
//                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                        publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//                        publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                        publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                        publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                    } else {
//                        
//                        if (messageVal.equals("Schlafzimmer")) {
//                            // light on 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
//                            // light off
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//                        } else if (messageVal.equals("Wohnzimmer")) {
//                            // light on 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            // light off 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                        } else if (messageVal.equals("Eingang")) {
//                            // light on 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "true");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            // light off 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                        } else if (messageVal.equals("Bad")) {
//                            // light on 
//                            
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "true");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            // light off 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                            
//                        } else if (messageVal.equals("Küche")) {
//                            // light on 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
//                            // light off 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                            
////                        } else if (messageVal.equals("On the bed")) {
////                            // light on 
////                            // light off 
////                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
////                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
////                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
////                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
////                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
////                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
////                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
////                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                        }
//                    }
//            }
//        } else if (isNight() == false) {
//            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//        }
//    }
//
//    
//    private void personLocation(String algoTyp, String algoCase, String messageVal) throws Exception{
//         if (algoCase.equals("Schlafzimmer")){
//                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = algoCase;
//                       pushLocation(passageDetected);
//                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)){
//                       passageDetected = algoCase;
//                       pushLocation(algoCase);
//                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = "Wohnzimmer";
//                       pushLocation(passageDetected);
//                   } 
//                }
//                else if (algoCase.equals("Kuche")){
//                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = algoCase;
//                       pushLocation(passageDetected);
//                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)){
//                       passageDetected = algoCase;
//                       pushLocation(algoCase);
//                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = "Eingang";
//                       pushLocation(passageDetected);
//                   }
//                }
//                 else if (algoCase.equals("Wohnzimmer")){
//                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = algoCase;
//                       pushLocation(passageDetected);
//                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)){
//                       passageDetected = algoCase;
//                       pushLocation(algoCase);
//                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = "Eingang";
//                       pushLocation(passageDetected);
//                   } 
//                }
//                 else if (algoCase.equals("Eingang")){
//                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = algoCase;
//                       pushLocation(passageDetected);
//                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)){
//                       passageDetected = algoCase;
//                       pushLocation(algoCase);
//                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = "Haus verlassen";
//                       pushLocation(passageDetected);
//                   }
//                }
//                else if (algoCase.equals("Bad")){
//                   if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = algoCase;
//                       pushLocation(passageDetected);
//                   } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)){
//                       passageDetected = algoCase;
//                       pushLocation(algoCase);
//                   } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")){
//                       passageDetected = "Eingang";
//                       pushLocation(passageDetected);
//                   }
//                }
//                
//                // Oben ist alles gut, ab hier muss man noch entwickeln!
//                else if (messageVal.equals("Motion Ended")){
//                    if (algoCase.equals("Schlafzimmer")){
//                        if (algoCase.equals(passageDetected)){
//                            passageDetected = "Wohnzimmer";
//                            pushLocation("MEnde"+algoCase);
//                        }
//                    } else if (algoCase.equals("Wohnzimmer")){
//                        if (!algoCase.equals(passageDetected)){
//                            passageDetected = "Wohn";
//                            pushLocation("MEnde"+algoCase);
//                        }
//                    }
//                }               
//    }
//    
//    @Override
//    public void messageArrived(String topic, MqttMessage message)  {
//
//
//            if (topic.endsWith("value")) {
//                try {
//                    String messageVal = new String(message.getPayload());
//                    String[] res = topic.split("\\/", -1);
//                    String algoTyp = res[2];
//                    String algoCase = res[3];
//                    
//                    nightLightAlgo(algoTyp, algoCase, messageVal);
//                    personLocation(algoTyp, algoCase, messageVal);
//                    
//                    
//                } catch (Exception ex) {
//                    Logger.getLogger(NightLightUC1.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        System.out.println(" ===== MQTT VERBINDUNG UNTERBROCKEN! ===== ");
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        System.out.println(" ===== MQTT MESSAGE GESENDET! ===== ");
//    }
//    
//    public static void main(String[] args) {
//        NightLightUC1 l = new NightLightUC1();
//        l.runNightLight();
//    }
//}
