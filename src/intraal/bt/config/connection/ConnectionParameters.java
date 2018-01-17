/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection;


/**
 *
 * @author Turna
 */
public class ConnectionParameters {
      
    /*
    RASPBERRY PI, MQTT BROKER & WIFI
     */
    private final String RASPBERRY_PI_IP = "10.0.233.51"; 
    private final String RASPBERRY_PI_BENUTZER = "intraalpi";
    private final String RASPBERRY_PI_PW = "intraal";
    private final String RASPBERRY_PI_MQTT_BROKER_TOPIC = "Gateway/" + RASPBERRY_PI_IP;
    private final int RASPBERRY_PI_MQTT_BROKER_PORT = 1883; 
    
    private final String WIFI_PW = "nespresso";

    /*
    E-MAIL
    */
    private final String EMAIL_BENUTZER = "meldung@intraal.com";
    private final String EMAIL_PW = "GEz8BBh6";
    private final String EMAIL ="meldung@intraal.com";
    private final String EMAIL_SMPT_AUTH = "asmtp.mail.hostpoint.ch"; //mail.smtp.auth
    
    /*
    SIOT DASHBOARD
    */
    private final String SIOT_USER_AGENT = "Mozilla/5.0";
    private final String SIOT_URL = "https://siot.net:12955";
    private final String SIOT_LICENCE = "3DC9-90D5-8150-44DA-32FE-D81F-310D-5614";
    
    private final String SIOT_SERVICE_INPUT_INOROUT_KEY = "0fc55fa4-6b60-dfb3-202e-90f999c249ef";
    private final String SIOT_SERVICE_INPUT_LOCATION_KEY = "a4b74c4f-6352-8524-4909-8670b4aa0d31";
    private final String SIOT_SERVICE_INPUT_MESSAGE = "a09ab795-3451-8198-bab7-66b6013d9b60";
    private final String SIOT_SERVICE_INPUT_ALARM = "81140b62-5560-59db-12b8-a19fbc93fda8";
    
    /*
    SMART-ME
    */
    private final String SMART_ME_WEBSERVICE = "https://smart-me.com:443";
    private final String SMART_ME_BENUTZER = "vnf1";
    private final String SMART_ME_PW = "f6PEz6QsTZnn";
    private final String SMART_ME_AUTH = SMART_ME_BENUTZER + ":" + SMART_ME_PW;
    
    private final String SMART_ME_PLUG_KEY_WOHNZIMMER = "5b9b3fea-cc8d-45ad-92b3-9caf3be725bc";
    private final String SMART_ME_PLUG_KEY_SCHLAFZIMMER = "ca224bf7-bf6c-4d74-93fe-da4de71cdbb6";
    private final String SMART_ME_PLUG_KEY_EINGANG = "30d97038-8857-4df6-b0c3-cf7e27a820c3";
    private final String SMART_ME_PLUG_KEY_BAD = "e3109c4e-b8f5-4687-88f6-38477d84f5ca";
    
    /*
    TWILIO
    */
    //STATIC GEWESEN!
    private final String TWILIO_SID = "ACfdb500575b3af7eb470252c03f511c5d";
    private final String TWILIO_AUTH = "235d5b27262f7fb0f0634bc7a5a810bc";
    private final String TWILIO_VOICE_NUMMER = "41445051051";
    private final String TWILIO_SMS_NUMMER = "41798072618";
    
    /*
    TINKERFORGE
    */
    private final String TINKERFORGE_MODUL_NAME_EINGANG = "Eingang";
    private final String TINKERFORGE_MODUL_NAME_EINGANG2 = "Eingang2";
    private final String TINKERFORGE_MODUL_NAME_BAD = "Bad";
    private final String TINKERFORGE_MODUL_NAME_KÜCHE = "Küche";
    private final String TINKERFORGE_MODUL_NAME_SCHLAFZIMMER = "Schlafzimmer";
    private final String TINKERFORGE_MODUL_NAME_WOHNZIMMER = "Wohnzimmer";
    private final String TINKERFORGE_MODUL_NAME_BETT = "Bett";
    
    private final int TINKERFORGE_PORT = 4223;
    private final String TINKERFORGE_IP_EINGANG = "10.0.233.43";
    private final String TINKERFORGE_IP_EINGANG2 = "10.0.233.49";
    private final String TINKERFORGE_IP_KÜCHE = "10.0.233.44";
    private final String TINKERFORGE_IP_BAD = "10.0.233.45";
    private final String TINKERFORGE_IP_SCHLAFZIMMER = "10.0.233.46";
    private final String TINKERFORGE_IP_WOHNZIMMER = "10.0.233.47";
    private final String TINKERFORGE_IP_BETT = "10.0.233.48";

    private final String TINKERFORGE_MODUL_UID_Eingang = "6e7NrQ";
    private final String TINKERFORGE_MODUL_UID_EINGANG2 = "6jDUPU";
    private final String TINKERFORGE_MODUL_UID_KÜCHE = "6e88VL";
    private final String TINKERFORGE_MODUL_UID_BAD = "6QFxcy";
    private final String TINKERFORGE_MODUL_UID_SCHLAFZIMMER = "62B7TB";
    private final String TINKERFORGE_MODUL_UID_BETT = "6CtMfr";
    private final String TINKERFORGE_MODUL_UID_WOHNZIMMER = "5W5jVE";
    
    private final String TINKERFORGE_SENSOR_UID_EINGANG_MOTION = "wtd";
    private final String TINKERFORGE_SENSOR_UID_EINGANG_PASSAGE = "tJ3";
    private final String TINKERFORGE_SENSOR_UID_EINGANG_TEMPERATUR = "t73";
    private final String TINKERFORGE_SENSOR_UID_EINGANG_AMBIENTELIGHT = "yg4";

    private final String TINKERFORGE_SENSOR_UID_BAD_MOTION = "qtu";
    private final String TINKERFORGE_SENSOR_UID_BAD_PASSAGE = "tHC";
    private final String TINKERFORGE_SENSOR_UID_BAD_FEUCHTIGKEIT = "dgu";
    private final String TINKERFORGE_SENSOR_UID_BAD_TEMPERATUR = "qvy";
    private final String TINKERFORGE_SENSOR_UID_BAD_AMBIENTELIGHT = "yiJ";

    private final String TINKERFORGE_SENSOR_UID_KÜCHE_MOTION = "wrU";
    private final String TINKERFORGE_SENSOR_UID_KÜCHE_PASSAGE = "tJN";
    private final String TINKERFORGE_SENSOR_UID_KÜCHE_TEMPERATUR = "t6W";
    private final String TINKERFORGE_SENSOR_UID_KÜCHE_AMBIENTELIGHT = "yiz";

    private final String TINKERFORGE_SENSOR_UID_WOHNZIMMER_MOTION = "kgt";
    private final String TINKERFORGE_SENSOR_UID_WOHNZIMMER_PASSAGE = "tJ9";
    private final String TINKERFORGE_SENSOR_UID_WOHNZIMMER_TEMPERATUR = "taL";
    private final String TINKERFORGE_SENSOR_UID_WOHNZIMMER_AMBIENTELIGHT = "uQu";
    private final String TINKERFORGE_SENSOR_UID_WOHNZIMMER_CO2 = "x7e";

    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_MOTION = "wt9";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_PASSAGE = "qsE";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_TEMPERATUR = "tm1";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_AMBIENTELIGHT = "yhZ";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_CO2 = "xtg";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1 = "vdv";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2 = "vcQ";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3 = "vcn";
    private final String TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4 = "vdT";
    

    
    public String getRASPBERRY_BROKER_CONNECTION() {
        return "tcp://" + RASPBERRY_PI_IP + ":" + RASPBERRY_PI_MQTT_BROKER_PORT;
    }
    
    public String getRASPBERRY_PI_MQTT_BROKER_TOPIC(String modul){
        return RASPBERRY_PI_MQTT_BROKER_TOPIC + "/"+ modul;
    }
    
    public String getRASPBERRY_PI_MQTT_BROKER_CLIENT(String modul,String room,String uid){
        return RASPBERRY_PI_MQTT_BROKER_TOPIC + "/"+ modul + "/" + room + "/" + uid + "/value";
    }
    
    public String getRASPBERRY_PI_MQTT_BROKER_LASTWILL(String modul,String room,String uid){
        return RASPBERRY_PI_MQTT_BROKER_TOPIC + "/"+ modul + "/" + room + "/" + uid + "/connection";
    }

    public String getRASPBERRY_PI_IP() {
        return RASPBERRY_PI_IP;
    }

    public String getRASPBERRY_PI_BENUTZER() {
        return RASPBERRY_PI_BENUTZER;
    }

    public char[] getRASPBERRY_PI_PW() {
        char[] pw = RASPBERRY_PI_PW.toCharArray();
        return pw;
    }

    public int getRASPBERRY_PI_MQTT_BROKER_PORT() {
        return RASPBERRY_PI_MQTT_BROKER_PORT;
    }

    public String getWIFI_PW() {
        return WIFI_PW;
    }

    public String getEMAIL_BENUTZER() {
        return EMAIL_BENUTZER;
    }

    public String getEMAIL_PW() {
        return EMAIL_PW;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getEMAIL_SMPT_AUTH() {
        return EMAIL_SMPT_AUTH;
    }

    public String getSIOT_USER_AGENT() {
        return SIOT_USER_AGENT;
    }

    public String getSIOT_URL() {
        return SIOT_URL;
    }

    public String getSIOT_LICENCE() {
        return SIOT_LICENCE;
    }

    public String getSIOT_SERVICE_INPUT_INOROUT_KEY() {
        return SIOT_SERVICE_INPUT_INOROUT_KEY;
    }

    public String getSIOT_SERVICE_INPUT_LOCATION_KEY() {
        return SIOT_SERVICE_INPUT_LOCATION_KEY;
    }

    public String getSIOT_SERVICE_INPUT_MESSAGE() {
        return SIOT_SERVICE_INPUT_MESSAGE;
    }

    public String getSIOT_SERVICE_INPUT_ALARM() {
        return SIOT_SERVICE_INPUT_ALARM;
    }

    public String getSMART_ME_WEBSERVICE() {
        return SMART_ME_WEBSERVICE;
    }

    public String getSMART_ME_BENUTZER() {
        return SMART_ME_BENUTZER;
    }

    public String getSMART_ME_PW() {
        return SMART_ME_PW;
    }

    public String getSMART_ME_AUTH() {
        return SMART_ME_AUTH;
    }

    public String getSMART_ME_PLUG_KEY_WOHNZIMMER() {
        return SMART_ME_PLUG_KEY_WOHNZIMMER;
    }

    public String getSMART_ME_PLUG_KEY_SCHLAFZIMMER() {
        return SMART_ME_PLUG_KEY_SCHLAFZIMMER;
    }

    public String getSMART_ME_PLUG_KEY_EINGANG() {
        return SMART_ME_PLUG_KEY_EINGANG;
    }

    public String getSMART_ME_PLUG_KEY_BAD() {
        return SMART_ME_PLUG_KEY_BAD;
    }

    public String getTWILIO_SID() {
        return TWILIO_SID;
    }

    public String getTWILIO_AUTH() {
        return TWILIO_AUTH;
    }

    public String getTWILIO_VOICE_NUMMER() {
        return TWILIO_VOICE_NUMMER;
    }

    public String getTWILIO_SMS_NUMMER() {
        return TWILIO_SMS_NUMMER;
    }

    public String getTINKERFORGE_MODUL_NAME_EINGANG() {
        return TINKERFORGE_MODUL_NAME_EINGANG;
    }

    public String getTINKERFORGE_MODUL_NAME_EINGANG2() {
        return TINKERFORGE_MODUL_NAME_EINGANG2;
    }

    public String getTINKERFORGE_MODUL_NAME_BAD() {
        return TINKERFORGE_MODUL_NAME_BAD;
    }

    public String getTINKERFORGE_MODUL_NAME_KÜCHE() {
        return TINKERFORGE_MODUL_NAME_KÜCHE;
    }

    public String getTINKERFORGE_MODUL_NAME_SCHLAFZIMMER() {
        return TINKERFORGE_MODUL_NAME_SCHLAFZIMMER;
    }

    public String getTINKERFORGE_MODUL_NAME_WOHNZIMMER() {
        return TINKERFORGE_MODUL_NAME_WOHNZIMMER;
    }

    public String getTINKERFORGE_MODUL_NAME_BETT() {
        return TINKERFORGE_MODUL_NAME_BETT;
    }

    public int getTINKERFORGE_PORT() {
        return TINKERFORGE_PORT;
    }

    public String getTINKERFORGE_IP_EINGANG() {
        return TINKERFORGE_IP_EINGANG;
    }

    public String getTINKERFORGE_IP_EINGANG2() {
        return TINKERFORGE_IP_EINGANG2;
    }

    public String getTINKERFORGE_IP_KÜCHE() {
        return TINKERFORGE_IP_KÜCHE;
    }

    public String getTINKERFORGE_IP_BAD() {
        return TINKERFORGE_IP_BAD;
    }

    public String getTINKERFORGE_IP_SCHLAFZIMMER() {
        return TINKERFORGE_IP_SCHLAFZIMMER;
    }

    public String getTINKERFORGE_IP_WOHNZIMMER() {
        return TINKERFORGE_IP_WOHNZIMMER;
    }

    public String getTINKERFORGE_IP_BETT() {
        return TINKERFORGE_IP_BETT;
    }

    public String getTINKERFORGE_MODUL_UID_Eingang() {
        return TINKERFORGE_MODUL_UID_Eingang;
    }

    public String getTINKERFORGE_MODUL_UID_EINGANG2() {
        return TINKERFORGE_MODUL_UID_EINGANG2;
    }

    public String getTINKERFORGE_MODUL_UID_KÜCHE() {
        return TINKERFORGE_MODUL_UID_KÜCHE;
    }

    public String getTINKERFORGE_MODUL_UID_BAD() {
        return TINKERFORGE_MODUL_UID_BAD;
    }

    public String getTINKERFORGE_MODUL_UID_SCHLAFZIMMER() {
        return TINKERFORGE_MODUL_UID_SCHLAFZIMMER;
    }

    public String getTINKERFORGE_MODUL_UID_BETT() {
        return TINKERFORGE_MODUL_UID_BETT;
    }

    public String getTINKERFORGE_MODUL_UID_WOHNZIMMER() {
        return TINKERFORGE_MODUL_UID_WOHNZIMMER;
    }

    public String getTINKERFORGE_SENSOR_UID_EINGANG_MOTION() {
        return TINKERFORGE_SENSOR_UID_EINGANG_MOTION;
    }

    public String getTINKERFORGE_SENSOR_UID_EINGANG_PASSAGE() {
        return TINKERFORGE_SENSOR_UID_EINGANG_PASSAGE;
    }

    public String getTINKERFORGE_SENSOR_UID_EINGANG_TEMPERATUR() {
        return TINKERFORGE_SENSOR_UID_EINGANG_TEMPERATUR;
    }

    public String getTINKERFORGE_SENSOR_UID_EINGANG_AMBIENTELIGHT() {
        return TINKERFORGE_SENSOR_UID_EINGANG_AMBIENTELIGHT;
    }

    public String getTINKERFORGE_SENSOR_UID_BAD_MOTION() {
        return TINKERFORGE_SENSOR_UID_BAD_MOTION;
    }

    public String getTINKERFORGE_SENSOR_UID_BAD_PASSAGE() {
        return TINKERFORGE_SENSOR_UID_BAD_PASSAGE;
    }

    public String getTINKERFORGE_SENSOR_UID_BAD_TEMPERATUR() {
        return TINKERFORGE_SENSOR_UID_BAD_TEMPERATUR;
    }

    public String getTINKERFORGE_SENSOR_UID_BAD_AMBIENTELIGHT() {
        return TINKERFORGE_SENSOR_UID_BAD_AMBIENTELIGHT;
    }

    public String getTINKERFORGE_SENSOR_UID_KÜCHE_MOTION() {
        return TINKERFORGE_SENSOR_UID_KÜCHE_MOTION;
    }

    public String getTINKERFORGE_SENSOR_UID_KÜCHE_PASSAGE() {
        return TINKERFORGE_SENSOR_UID_KÜCHE_PASSAGE;
    }

    public String getTINKERFORGE_SENSOR_UID_KÜCHE_TEMPERATUR() {
        return TINKERFORGE_SENSOR_UID_KÜCHE_TEMPERATUR;
    }

    public String getTINKERFORGE_SENSOR_UID_KÜCHE_AMBIENTELIGHT() {
        return TINKERFORGE_SENSOR_UID_KÜCHE_AMBIENTELIGHT;
    }

    public String getTINKERFORGE_SENSOR_UID_WOHNZIMMER_MOTION() {
        return TINKERFORGE_SENSOR_UID_WOHNZIMMER_MOTION;
    }

    public String getTINKERFORGE_SENSOR_UID_WOHNZIMMER_PASSAGE() {
        return TINKERFORGE_SENSOR_UID_WOHNZIMMER_PASSAGE;
    }

    public String getTINKERFORGE_SENSOR_UID_WOHNZIMMER_TEMPERATUR() {
        return TINKERFORGE_SENSOR_UID_WOHNZIMMER_TEMPERATUR;
    }

    public String getTINKERFORGE_SENSOR_UID_WOHNZIMMER_AMBIENTELIGHT() {
        return TINKERFORGE_SENSOR_UID_WOHNZIMMER_AMBIENTELIGHT;
    }

    public String getTINKERFORGE_SENSOR_UID_WOHNZIMMER_CO2() {
        return TINKERFORGE_SENSOR_UID_WOHNZIMMER_CO2;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_MOTION() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_MOTION;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_PASSAGE() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_PASSAGE;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_TEMPERATUR() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_TEMPERATUR;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_AMBIENTELIGHT() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_AMBIENTELIGHT;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_CO2() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_CO2;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3;
    }

    public String getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4() {
        return TINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4;
    }
    
}
