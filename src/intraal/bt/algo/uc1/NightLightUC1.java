/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.Connections;
import intraal.bt.config.connection.rest.SmartMeService;
import intraal.bt.system.settings.IntraalEinstellungen;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author turna
 */
public class NightLightUC1 implements MqttCallback {

    ConnectionParameters cp;
    Connections con;

    private final String UID = "OnePerson";
    private final String USECASENR = "Location";
    private final String USECASE = "Usecase";

    private static boolean isNigh;

    public void runNightLight() throws Exception {
        con = new Connections();
        con.getMQTTconnection(USECASE, USECASENR, UID);
        con.subscribeMQTT("#/");
    }

    private void publishLightLokationSwitch(String plugUID, String Switch) {
        String location = plugUID;
        if (plugUID.equals(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER())) {
            location = "Wohnzimmer";
        } else if (plugUID.equals(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER())) {
            location = "Schlafzimmer";
        } else if (plugUID.equals(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER())) {
            location = "Eingang";
        } else if (plugUID.equals(cp.getSMART_ME_PLUG_KEY_BAD())) {
            location = "Bad";
        } else if (plugUID.equals(cp.getSMART_ME_PLUG_KEY_KÜCHE())) {
            location = "Küche";
        }
        String nachricht = location + "=" + Switch;
        con.sendMQTTmessage(USECASE, USECASENR, UID, nachricht);
    }

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

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        SmartMeService sme = new SmartMeService();
        if (isNight() == true) {

            if (topic.endsWith("value")) {
                String messageVal = new String(message.getPayload());
                String[] res = topic.split("\\/", -1);
                String locationAlgo = res[2];

                if (locationAlgo.equals("Helper")) {

                    if (messageVal.equals("Schlafzimmer")) {
                        // light on 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        // light off
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                    } else if (messageVal.equals("Wohnzimmer")) {
                        // light on 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        // light off 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                    } else if (messageVal.equals("Eingang")) {
                        // light on 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "true");
                        // light off 
                    } else if (messageVal.equals("Bad")) {
                        // light on 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "true");
                        // light off 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                    } else if (messageVal.equals("Kuche")) {
                        // light on 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "true");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "true");
                        // light off 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                    } else if (messageVal.equals("On the bed")) {
                        // light on 
                        // light off 
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
                        sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                        publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
                    }
                }
            }
        } else if (isNight() == false){
            sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
            publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_BAD(), "false");
            sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
            publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_KÜCHE(), "false");
            sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
            publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_SCHLAFZIMMER(), "false");
            sme.switchLightStatus(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
            publishLightLokationSwitch(cp.getSMART_ME_PLUG_KEY_WOHNZIMMER(), "false");
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
