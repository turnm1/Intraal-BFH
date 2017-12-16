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
    
    public ConnectionParameters() {
        
    }

    /*
    Wifi Connections for Tinkerforge Sensor and Internet connection
     */
    private final String piIP = "10.0.233.51";  // BFH Orginal IP: 10.0.233.172 / Passwort: nespresso //// HDI: 10.0.0.5 / PW: w0rkSmart! ******************************************************************************
    private final String wifiPw = "nespresso";

    /*
   MQTT Broker (root) connection
     */
    private final String userName = "intraalpi";
    private final String password = "intraal";
    private final String brokerTopic = "Gateway/" + piIP;
    private final int brokerPort = 1883; 
    
    /*
    SIOT Dashboard connection
    */
    private final String USER_AGENT = "Mozilla/5.0";
    private final String URL = "https://siot.net:12955";
    private final String Licence = "3DC9-90D5-8150-44DA-32FE-D81F-310D-5614";
    
    /*
    Smart-me Plug connection
    */
    private final String SMwebPage = "https://smart-me.com:443";
    private final String SMname = "vnf1";
    private final String SMPassword = "f6PEz6QsTZnn";
    private final String SMauthString = SMname + ":" + SMPassword;
    
    /*
    SIOT Input Key's
    */
    // Ambient Lights Sensors
    private final String al_inputKey_bad = "db187d13-ddff-849a-2ebd-a2ad08521752";
    private final String al_inputKey_eingang = "636e7e8b-7a84-5ee4-f17b-b09118208c7e";
    private final String al_inputKey_küche = "7ed4f12b-9860-3fd9-2fa7-9e67b1af052d";
    private final String al_inputKey_schlafz = "da247c5d-dca1-ead6-4d28-3fd2fedfd8e1";
    private final String al_inputKey_wohnz = "04c88ec4-65b5-dea1-eab1-ec9aba99968f";
    // Motion Sensors
    private final String m_inputKey_bad = "2e973949-329a-b170-ee0b-7d1cac9eb4c6";
    private final String m_inputKey_eingang = "6d8c1115-0ed2-ada1-f446-d252fe6b1057";
    private final String m_inputKey_küche = "ec2a0fe7-f93b-dd81-1362-ac97d9eb945c";
    private final String m_inputKey_schlafz = "73b77f58-efc3-c44f-852d-2a146e03aaa4";
    private final String m_inputKey_wohnz = "be7b666f-ce61-e7b9-117a-33c97a8588f5";
    // Passage Sensors
    private final String p_inputKey_bad = "446d18e6-852e-3a56-e416-47daa946e8a8";
    private final String p_inputKey_eingang = "28791173-524b-b361-03d6-0005da6e3133";
    private final String p_inputKey_küche = "77629175-5904-bfcd-f7ad-6ccb44b0b6b4";
    private final String p_inputKey_schlafz = "065510eb-ffe2-5047-8819-08294ac246f0";
    private final String p_inputKey_wohnz = "abd112ac-03e0-192c-8741-03964eca9686";
    // Temperatur Sensors
    private final String t_inputKey_bad = "05cdd07b-7f8e-fb06-e609-f408ce228ac0";
    private final String t_inputKey_eingang = "50dbf5cb-d43c-2156-2f01-d678f51e102b";
    private final String t_inputKey_küche = "10cb4aac-7f1b-12c0-e974-75a349d2b8af";
    private final String t_inputKey_schlafz = "0bddc948-8b1f-f2ca-0dd8-ad02e2c20a62";
    private final String t_inputKey_wohnz = "1b261905-0494-4ece-b19f-69a747c92c84";
    // CO2 Sensors
    private final String co2_inputKey_schlafz = "452ca9d5-5fae-5ce7-4727-6b8aa0b9fc33";
    private final String co2_inputKey_wohnz = "f7d771ee-0501-0f45-9fab-014b1fd2c2d5";
    // INTRAAL Services
    private final String service_inOrOut = "0fc55fa4-6b60-dfb3-202e-90f999c249ef";
    private final String service_location = "a4b74c4f-6352-8524-4909-8670b4aa0d31";
    private final String service_message = "a09ab795-3451-8198-bab7-66b6013d9b60";
    private final String service_alarm = "81140b62-5560-59db-12b8-a19fbc93fda8";
    // INTRAAL EINSTELLUNGEN
    private final String service_message_onOff = "45b1e09d751de54c88fd5679ffa98c17";

     /*
   Smart-Me Plug
     */
    // Fix static UID's
    private final String smWohnzimmerPlugUID = "5b9b3fea-cc8d-45ad-92b3-9caf3be725bc";
    private final String smSchlafzimmerPlugUID = "";
    private final String smEingangPlugUID = "ca224bf7-bf6c-4d74-93fe-da4de71cdbb6";
    private final String smBadPlugUID = "";
    
    /*
   BFH Sensors
     */
    // Fix static IP's & UID's
    private final int tgPort = 4223;
    private final String tgEingangIP = "10.0.233.43";
    private final String tgKücheIP = "10.0.233.44";  //BFH Orginal IP: 10.0.233.44 //// HDI IP: 192.168.0.181 ******************************************************************************
    private final String tgBadIP = "10.0.233.45";    //BFH Orginal IP: 10.0.233.45 //// HDI IP: 192.168.0.106 or 10.0.0.52 or localhost ******************************************************************************
    private final String tgSchlafzimmerIP = "10.0.233.46";
    private final String tgWohnzimmerIP = "10.0.233.47";
    private final String tgBettIP = "10.0.233.48";
    private final String tgEingang2IP = "10.0.233.49";
    // EINGANG UID's
    private final String modulEingang = "Eingang";
    private final String eingangMotionUID = "wtd";
    private final String eingangPassageUID = "tJ3";
    private final String eingangTemperaturUID = "t73";
    private final String eingangAmbientLightUID = "yg4";
    /// Bad/WC UID's
    private final String modulBad = "Bad";
    private final String badMotionUID = "qtu";
    private final String badPassageUID = "qsE"; // BFH: tHC  /////  HDI: qsE
    private final String badTemperaturUID = "qvy";
    private final String badAmbientLightUID = "yiJ";
    // KÜCHE UID's
    private final String modulKüche = "Küche";
    private final String kücheMotionUID = "wrU";
    private final String küchePassageUID = "tJN";
    private final String kücheTemperaturUID = "t6W";
    private final String kücheAmbientLightUID = "yiz";
    // WOHNZIMMER UID's
    private final String modulWohnzimmer = "Wohnzimmer";
    private final String wohnzMotionUID = "wtF";
    private final String wohnzPassageUID = "tJ9";
    private final String wohnzTemperaturUID = "taL";
    private final String wohnzAmbientLightUID = "yhZ";
    private final String wohnzCO2UID = "x7e";
    // SCHLAFZIMMER UID's
    private final String modulSchlafzimmer = "Schlafzimmer";
    private final String schlafzMotionUID = "wt9";
    private final String schlafzPassageUID = "tJo";
    private final String schlafzTemperaturUID = "tm1";
    private final String schlafzAmbientLightUID = "yh9";
    private final String schlafzCO2UID = "xtg";

////////////////////////////////////////////////////////////////////////////////
//////////////////////////////// NO EDIT ! /////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    /*
    MQTT Username & Password
    */
    public String getUserName(){
        return userName;
    }
    
    public char[] getPassword() {
        char[] pw = password.toCharArray();
        return pw;
    }
    
    /*
    Wifi & Internet getter
    */
    public String getPiIp() {
        return piIP;
    }

    public String getBrokerTopic() {
        return brokerTopic;
    }

    public int getBrokerPort() {
        return brokerPort;
    }

    public String getBrokerConnection() {
        return "tcp://" + piIP + ":" + brokerPort;
    }

////////////////////////////////////////////////////////////////////////////////
    /*
    SIOT Dashboard getter
    */

    // INPUT
    public String getSIOTUSER_AGENT() {
        return USER_AGENT;
    }

    public String getSIOTURL() {
        return URL;
    }

    public String getSIOTLicence() {
        return Licence;
    }
    
    // OUTPUT
     public String getMessageOnOff() {
        // https://siot.net:12935/getdata?centerUID=A751-6354-E157-4A05-BDAB-77BB-35E5-2657&sensorUID=65206DBD5B5B465EACBE8BFD8DE92285
        String onOff = URL+"/getdata?centerUID="+Licence+"&sensorUID="+service_message_onOff;
        return onOff;
    }
    
    /*
    SIOT Key getter's
    */

    public String getAl_inputKey_bad() {
        return al_inputKey_bad;
    }

    public String getAl_inputKey_eingang() {
        return al_inputKey_eingang;
    }

    public String getAl_inputKey_küche() {
        return al_inputKey_küche;
    }

    public String getAl_inputKey_schlafz() {
        return al_inputKey_schlafz;
    }

    public String getAl_inputKey_wohnz() {
        return al_inputKey_wohnz;
    }

    public String getM_inputKey_bad() {
        return m_inputKey_bad;
    }

    public String getM_inputKey_eingang() {
        return m_inputKey_eingang;
    }

    public String getM_inputKey_küche() {
        return m_inputKey_küche;
    }

    public String getM_inputKey_schlafz() {
        return m_inputKey_schlafz;
    }

    public String getM_inputKey_wohnz() {
        return m_inputKey_wohnz;
    }

    public String getP_inputKey_bad() {
        return p_inputKey_bad;
    }

    public String getP_inputKey_eingang() {
        return p_inputKey_eingang;
    }

    public String getP_inputKey_küche() {
        return p_inputKey_küche;
    }

    public String getP_inputKey_schlafz() {
        return p_inputKey_schlafz;
    }

    public String getP_inputKey_wohnz() {
        return p_inputKey_wohnz;
    }

    public String getService_alarm() {
        return service_alarm;
    }

    public String getService_inOrOut() {
        return service_inOrOut;
    }

    public String getService_location() {
        return service_location;
    }

    public String getService_message() {
        return service_message;
    }

    public String getT_inputKey_bad() {
        return t_inputKey_bad;
    }

    public String getT_inputKey_eingang() {
        return t_inputKey_eingang;
    }

    public String getT_inputKey_küche() {
        return t_inputKey_küche;
    }

    public String getT_inputKey_schlafz() {
        return t_inputKey_schlafz;
    }

    public String getT_inputKey_wohnz() {
        return t_inputKey_wohnz;
    }

    public String getCo2_inputKey_schlafz() {
        return co2_inputKey_schlafz;
    }

    public String getCo2_inputKey_wohnz() {
        return co2_inputKey_wohnz;
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    /*
    Smart-Me getters 
     */

    public String getSMPassword() {
        return SMPassword;
    }

    public String getSMauthString() {
        return SMauthString;
    }

    public String getSMwebPage() {
        return SMwebPage;
    }

    public String getSMname() {
        return SMname;
    } 
    
    
 
////////////////////////////////////////////////////////////////////////////////
    /*
    BFH getter Tinkerforge Sensors
     */
// get Modul name's
    public String getModulBad() {    
        return modulBad;
    }

    public String getModulSchlafzimmer() {
        return modulSchlafzimmer;
    }

    public String getModulWohnzimmer() {
        return modulWohnzimmer;
    }

    public String getModulEingang() {
        return modulEingang;
    }

    public String getModulKüche() {
        return modulKüche;
    }

    
// get IP's
    public String getTgBadIP() {
        return tgBadIP;
    }

    public String getTgEingangIP() {
        return tgEingangIP;
    }

    public String getTgBettIP() {
        return tgBettIP;
    }

    public String getTgWohnzimmerIP() {
        return tgWohnzimmerIP;
    }

    public String getTgSchlafzimmerIP() {
        return tgSchlafzimmerIP;
    }

    public String getTgEingang2IP() {
        return tgEingang2IP;
    }

    public String getTgKücheIP() {
        return tgKücheIP;
    }
    
    
// get UID's
    public String getBadAmbientLightUID() {
        return badAmbientLightUID;
    }

    public String getBadMotionUID() {
        return badMotionUID;
    }

    public String getBadPassageUID() {
        return badPassageUID;
    }

    public String getBadTemperaturUID() {
        return badTemperaturUID;
    }

    public String getEingangAmbientLightUID() {
        return eingangAmbientLightUID;
    }

    public String getEingangMotionUID() {
        return eingangMotionUID;
    }

    public String getEingangPassageUID() {
        return eingangPassageUID;
    }

    public String getEingangTemperaturUID() {
        return eingangTemperaturUID;
    }

    public String getKücheAmbientLightUID() {
        return kücheAmbientLightUID;
    }

    public String getKücheMotionUID() {
        return kücheMotionUID;
    }

    public String getKüchePassageUID() {
        return küchePassageUID;
    }

    public String getKücheTemperaturUID() {
        return kücheTemperaturUID;
    }

    public String getSchlafzAmbientLightUID() {
        return schlafzAmbientLightUID;
    }

    public String getSchlafzCO2UID() {
        return schlafzCO2UID;
    }

    public String getSchlafzMotionUID() {
        return schlafzMotionUID;
    }

    public String getSchlafzPassageUID() {
        return schlafzPassageUID;
    }

    public String getSchlafzTemperaturUID() {
        return schlafzTemperaturUID;
    }

    public String getWohnzAmbientLightUID() {
        return wohnzAmbientLightUID;
    }

    public String getWohnzCO2UID() {
        return wohnzCO2UID;
    }

    public String getWohnzMotionUID() {
        return wohnzMotionUID;
    }

    public String getWohnzPassageUID() {
        return wohnzPassageUID;
    }

    public String getWohnzTemperaturUID() {
        return wohnzTemperaturUID;
    }
   
    
    /*
    Sensor Topics
    */
    public int getTgPort() {
        return tgPort;
    }
    
    public String getClientIDTopic(String modul){
        return brokerTopic + "/"+ modul;
    }
    
    public String getClientIDValueTopic(String modul,String room,String uid){
        return brokerTopic + "/"+ modul + "/" + room + "/" + uid + "/value";
    }
    
    public String getLastWillConnectionTopic(String modul,String room,String uid){
        return brokerTopic + "/"+ modul + "/" + room + "/" + uid + "/connection";
    }
    
}
