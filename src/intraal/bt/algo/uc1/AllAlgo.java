/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.rest.SmartMeService;
import intraal.bt.config.connection.siot.SiotDashboardInput;
import intraal.bt.config.mqtt.MQTTCommunication;
import intraal.bt.config.mqtt.MQTTParameters;
import intraal.bt.system.settings.IntraalEinstellungen;
import intraal.bt.system.settings.KontaktInformationen;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class AllAlgo implements MqttCallback {

    ConnectionParameters con;
    IPConnection ipcon;
    MqttMessage message;
    MQTTParameters p;
    MQTTCommunication c;
    WarningTimer w = new WarningTimer(999999);
    IntraalEinstellungen s;
    CallAndSendNotification callSendMessage;
    List<KontaktInformationen> kontaktinformationen;

    private final String MODUL = "Case";
    private final String TYP = "Algo";
    private final String CASE = "INTRAAL";

    private static boolean isNigh;
    private int flag = 0;
    private String passageDetected = "0";
    private static int errorOne = 0;
    private static boolean onBed;
    private static int LoadCell1, LoadCell2, LoadCell3, LoadCell4 = 0;
    private static int isMotionEnded1, isMotionEnded2, isMotionEnded3, isMotionEnded4, isMotionEnded5;
    private static int isPassage1, isPassage2, isPassage3, isPassage4, isPassage5;

    private void connectMQTT() throws Exception {
        con = new ConnectionParameters();
        c = new MQTTCommunication();
        p = new MQTTParameters();
        p.setClientID(con.getRASPBERRY_PI_MQTT_BROKER_TOPIC(MODUL));
        p.setUserName(con.getRASPBERRY_PI_BENUTZER());
        p.setPassword(con.getRASPBERRY_PI_PW());
        p.setIsCleanSession(true);
        p.setIsLastWillRetained(true);
        p.setLastWillMessage("offline".getBytes());
        p.setLastWillQoS(1);
        p.setServerURIs(URI.create(con.getRASPBERRY_BROKER_CONNECTION()));
        p.setWillTopic(con.getRASPBERRY_PI_MQTT_BROKER_LASTWILL(CASE, TYP, MODUL));
        p.setMqttCallback(this);
        c.connect(p);
        c.publishActualWill("online".getBytes());
        p.getLastWillMessage();
    }

    public void runIntraalAlgo() {
        try {
            connectMQTT();
            c.subscribe("Gateway/10.0.233.51/#", 0);
        } catch (Exception ex) {
            Logger.getLogger(BedActivityTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pushMQTTmessage(String usecase, String room, String modul, String pushMessage) throws Exception {
        message = new MqttMessage();
        message.setRetained(true);
        message.setQos(0);
        message.setPayload((pushMessage).getBytes());
        c.publish(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(usecase, room, modul), message);
        System.out.println(con.getRASPBERRY_PI_MQTT_BROKER_CLIENT(usecase, room, modul) + ": " + message);
    }

    // Message
    public void sendSIOTmessage(String siotKey, String messageText) throws Exception {
        SiotDashboardInput sdi = new SiotDashboardInput();
        sdi.setInputKey(siotKey);
        sdi.setInputMessage(messageText);
        sdi.sendInput();
    }

    private void pushLight(String plugUID, String Switch) throws Exception {
        String location = plugUID;
        if (plugUID.equals(con.getSMART_ME_PLUG_KEY_WOHNZIMMER())) {
            location = "Wohnzimmer";
        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER())) {
            location = "Schlafzimmer";
        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_WOHNZIMMER())) {
            location = "Eingang";
        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_BAD())) {
            location = "Bad";
        } else if (plugUID.equals(con.getSMART_ME_PLUG_KEY_EINGANG())) {
            location = "Küche";
        }
        //String nachricht = location + "=" + Switch;
        String nachricht = Switch;
        pushMQTTmessage("Helper", "Light", location, nachricht);
    }

    private void pushLocation(String location) throws Exception {
        pushMQTTmessage("Helper", "Location1", location, location);
        sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
        if (location.equals("Haus verlassen")) {
            sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
        } else if (location.equals("Eingang")) {
            location = "Zu Hause";
            sendSIOTmessage(con.getSIOT_SERVICE_INPUT_LOCATION_KEY(), location);
        }
    }

    private void pushBedActivity() throws Exception {
      //  System.out.println(LoadCell1 + LoadCell2 + LoadCell3 + LoadCell4);
        if (LoadCell1 + LoadCell2 + LoadCell3 + LoadCell4 == 4) {
            String nachricht = "On the bed";
            onBed = true;
            pushMQTTmessage("Helper", "BedActivity", "Schlafzimmer", nachricht);
        } else if (LoadCell1 + LoadCell2 + LoadCell3 + LoadCell4 == 0) {
            String nachricht = "Not on the bed";
            onBed = false;
            pushMQTTmessage("Helper", "BedActivity", "Schlafzimmer", nachricht);
        }
    }

    private void pushHouseActivity(int warningTime) throws Exception {
      //  System.out.println(isMotionEnded1 + isMotionEnded2 + isMotionEnded3 + isMotionEnded4 + isMotionEnded5);

            if (isMotionEnded1 + isMotionEnded2 + isMotionEnded3 + isMotionEnded4 + isMotionEnded5 == 3 && errorOne >= 5) {
               int warningTimeInMin = warningTime * 60; 
               w = new WarningTimer(warningTimeInMin);
               pushMQTTmessage("Helper", "HouseActivity", "WarningTimerStarted", warningTime + "");
            } else {
                errorOne++;
        }
    }

    // helper classes
    private Date parseDate(String date) {
        final String inputFormat = "HH:mm";
        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    private boolean isNight() {
        IntraalEinstellungen ie = new IntraalEinstellungen();
        Calendar now = Calendar.getInstance();
        isNigh = false;

        int hour = now.get(Calendar.HOUR_OF_DAY); // Get hour in 24 hour format
        int minute = now.get(Calendar.MINUTE);
        Date date = parseDate(hour + ":" + minute);
        Date startTime = parseDate(ie.getStartNightPhase());
        Date endTime = parseDate(ie.getEndNightPhase());
        if (date.after(startTime) || date.before(endTime)) {
            isNigh = true;
        }
        return isNigh;
    }
    

    // Algos
    private void nightLightAlgo(String algoTyp, String algoCase, String messageVal) throws Exception {
        con = new ConnectionParameters();
        SmartMeService sme = new SmartMeService();
        if (isNight() == true && flag >= 0) {
            flag = 0;
            
            if (messageVal.equals("On the bed")) {
                sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
                pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
                sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
            } else 
            
            if (algoTyp.equals("Helper")) {
                if (algoCase.equals("BedActivity")) {
                 if (messageVal.equals("Not on the bed")) {
                        // light on 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        // light off
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
                    }
                } else if (algoCase.equals("Location1")) {

                    if (messageVal.equals("Schlafzimmer")) {
                        // light on 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        // light off
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
                    } else if (messageVal.equals("Wohnzimmer")) {
                        // light on 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                    } else if (messageVal.equals("Eingang")) {
                        // light on 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        // light off 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                    } else if (messageVal.equals("Bad")) {
                        // light on 

                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "true");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        // light off 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");

                    } else if (messageVal.equals("Küche")) {
                        // light on 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "true");
                        // light off 
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                        pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");

//                        } else if (messageVal.equals("On the bed")) {
//                            // light on 
//                            // light off 
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_BAD(), "false");
//                            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
//                            publishLightLokationSwitch(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                    }
                }
            }

        } else if (isNight() == false && flag == 0) {
            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_BAD(), "false");
            pushLight(con.getSMART_ME_PLUG_KEY_BAD(), "false");
            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
            pushLight(con.getSMART_ME_PLUG_KEY_EINGANG(), "false");
            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
            pushLight(con.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
            sme.switchLightStatus(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
            pushLight(con.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
            flag++;
        }
    }

    private void personLocation(String algoTyp, String algoCase, String messageVal) throws Exception {
        if (algoCase.equals("Schlafzimmer")) {
            if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = algoCase;
                pushLocation(passageDetected);
            } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)) {
                passageDetected = algoCase;
               // pushLocation(algoCase);
            } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = "Wohnzimmer";
                pushLocation(passageDetected);
            }
        } else if (algoCase.equals("Küche")) {
            if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = algoCase;
                pushLocation(passageDetected);
            } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)) {
                passageDetected = algoCase;
                pushLocation(algoCase);
            } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = "Eingang";
                pushLocation(passageDetected);
            }
        } else if (algoCase.equals("Wohnzimmer")) {
            if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = algoCase;
                pushLocation(passageDetected);
            } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)) {
                passageDetected = algoCase;
                pushLocation(algoCase);
            } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = "Eingang";
                pushLocation(passageDetected);
            }
        } else if (algoCase.equals("Eingang")) {
            if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = algoCase;
                pushLocation(passageDetected);
            } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)) {
                passageDetected = algoCase;
                pushLocation(algoCase);
            } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = "Haus verlassen";
                pushLocation(passageDetected);
            }
        } else if (algoCase.equals("Bad")) {
            if ((messageVal).equals("Passage Detected") && !passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = algoCase;
                pushLocation(passageDetected);
            } else if (messageVal.equals("Motion Detected") && passageDetected.equals(algoCase)) {
                passageDetected = algoCase;
                pushLocation(algoCase);
            } else if ((messageVal).equals("Passage Detected") && passageDetected.equals(algoCase) || passageDetected.equals("0")) {
                passageDetected = "Eingang";
                pushLocation(passageDetected);
            }
        } // Oben ist alles gut, ab hier muss man noch entwickeln!
        else if (messageVal.equals("Motion Ended")) {
            if (algoCase.equals("Schlafzimmer")) {
                if (algoCase.equals(passageDetected)) {
                    passageDetected = "Wohnzimmer";
                    pushLocation("MEnde" + algoCase);
                }
            } else if (algoCase.equals("Wohnzimmer")) {
                if (!algoCase.equals(passageDetected)) {
                    passageDetected = "Wohn";
                    pushLocation("MEnde" + algoCase);
                }
            }
        }
    }

    private void bedActivity(String algoTyp, String algoCase, String messageVal) throws Exception {
        if (algoCase.equals("Schlafzimmer")) {
            if (messageVal.equals("On the bed")) {
                if (algoTyp.equals("Load Cell1")) {                   
                    LoadCell1 = 1;
                    pushBedActivity();
                } else if (algoTyp.equals("Load Cell2")) {
                    LoadCell2 = 1;
                    pushBedActivity();
                } else if (algoTyp.equals("Load Cell3")) {
                    LoadCell3 = 1;
                    pushBedActivity();
                } else if (algoTyp.equals("Load Cell4")) {
                    LoadCell4 = 1;
                    pushBedActivity();
                }

            } else if (messageVal.equals("Not on the bed")) {
                if (algoTyp.equals("Load Cell1")) {
                    LoadCell1 = 0;
                    pushBedActivity();
                } else if (algoTyp.equals("Load Cell2")) {
                    LoadCell2 = 0;
                    pushBedActivity();
                } else if (algoTyp.equals("Load Cell3")) {
                    LoadCell3 = 0;
                    pushBedActivity();
                } else if (algoTyp.equals("Load Cell4")) {
                    LoadCell4 = 0;
                    pushBedActivity();
                }
            }
        }
    }

    private void personActivity(String algoTyp, String algoCase, String messageVal) throws Exception {
        s = new IntraalEinstellungen();
        if (isNight() == true) {
            if (onBed == false) {
                if (algoTyp.equals("Motion")){
                    
                    if (messageVal.equals("Motion Ended")) {
                        if (algoCase.equals("Schlafzimmer")) {
                            isMotionEnded1 = 1;
                        } else if (algoCase.equals("Wohnzimmer")) {
                            isMotionEnded2 = 1;
                        } else if (algoCase.equals("Eingang")) {
                            isMotionEnded3 = 1;
                        } else if (algoCase.equals("Bad")) {
                            isMotionEnded4 = 1;
                        } else if (algoCase.equals("Kuche")) {
                            isMotionEnded5 = 1;
                        }
                        
                        pushHouseActivity(s.getWarningTime());
                            
                    } else if (messageVal.equals("Motion Detected")) { 
                            if (algoCase.equals("Schlafzimmer")) {
                            isMotionEnded1 = -1;
                            w.stopWarningTimer();
                        } else if (algoCase.equals("Wohnzimmer")) {
                            isMotionEnded2 = -1;
                            w.stopWarningTimer();
                        } else if (algoCase.equals("Eingang")) {
                            isMotionEnded3 = -1;
                            w.stopWarningTimer();
                        } else if (algoCase.equals("Kuche")) {
                            isMotionEnded4 = -1;
                            w.stopWarningTimer();
                        } else if (algoCase.equals("Bad")) {
                            isMotionEnded5 = -1;
                            w.stopWarningTimer();
                        }
                    }
                }
            } else if (onBed == true) {
                
        }
        else if (onBed == true) {
            
        }
        } 
                    
        
//                else if (messageVal.equals("No Passage") && flag == 0) {
//                    w = new WarningTimer(15);
//                    flag = 1;
//                } else if (messageVal.equals("Passage Detected") && flag == 1) {
//                    w.stopWarningTimer();
//                    flag = 0;
//                }
    }
    
    // excution
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        if (topic.endsWith("value")) {
            try {
                String messageVal = new String(message.getPayload());
                String[] res = topic.split("\\/", -1);
                String algoTyp = res[2];
                String algoCase = res[3];

                personLocation(algoTyp, algoCase, messageVal);
                bedActivity(algoTyp, algoCase, messageVal);
                personActivity(algoTyp, algoCase, messageVal);
                nightLightAlgo(algoTyp, algoCase, messageVal);

            } catch (Exception ex) {
                Logger.getLogger(AllAlgo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.print("x DISC: "); // System.out.println(" ===== MQTT VERBINDUNG UNTERBROCKEN! ===== ");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.print("+ PUSH: "); //  System.out.println(" ===== MQTT MESSAGE GESENDET! ===== ");
    }
 
}
