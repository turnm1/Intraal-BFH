/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.system.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


/**
 *
 * @author turna
 */
public class IntraalEinstellungen {
    

    
    public static IntraalEinstellungen Settings() throws FileNotFoundException, IOException {
        //File propertiesFile = new File("/home/pi/NetBeansProjects/Intraal-BT/dist/intraal_settings.properties"); // Pfad hier hinterlegen C:\\Users\\turna\\Documents\\NetBeansProjects\\Intraal-BT\\src\\intraal\\bt\\system\\settings\\intraal_settings.properties
        File propertiesFile = new File("C:\\Users\\turna\\Documents\\NetBeansProjects\\Intraal-BT_check\\src\\intraal\\bt\\system\\settings\\intraal_settings.properties"); // Metes Pfad
        FileReader propertiesReader = new FileReader(propertiesFile);
        Properties props = new Properties();
        props.load(propertiesReader);
        
        IntraalEinstellungen settings = new IntraalEinstellungen();
        settings.setAmbientLightOff(Integer.parseInt(props.getProperty("LichtEinschalten")));
        settings.setAmbientLightOn(Integer.parseInt(props.getProperty("LichtAusschalten")));
        settings.setTemperaturToHigh(Integer.parseInt(props.getProperty("TemperaturHoch")));
        settings.setTemperaturToLow(Integer.parseInt(props.getProperty("TmperaturTief")));
        settings.setStartNightPhase(props.getProperty("NachtphaseStart"));
        settings.setEndNightPhase(props.getProperty("NachtphaseEnde"));
        settings.setDemoModus(props.getProperty("DemoModusEinschalten").equalsIgnoreCase("true"));
        settings.setWarningTime(Integer.parseInt(props.getProperty("WarningTimer")));
        settings.setSendSMS(Integer.parseInt(props.getProperty("SendSMS")));
        settings.setSendEMail(Integer.parseInt(props.getProperty("SendEMail")));
        settings.setStartCall(Integer.parseInt(props.getProperty("StartCall")));
        return settings;
    }
    
    
    private int sendSMS;
    private int sendEMail;
    private int startCall;
    private int ambientLightOff;
    private int ambientLightOn;
    private int temperaturToHigh;
    private int temperaturToLow;
    private String startNightPhase;
    private String endNightPhase;
    private boolean demoModus;
    private int warningTimeInMin; // sekunden

    public void setSendEMail(int sendEMail) {
        this.sendEMail = sendEMail;
    }

    public void setSendSMS(int sendSMS) {
        this.sendSMS = sendSMS;
    }

    public void setStartCall(int startCall) {
        this.startCall = startCall;
    }

    public int getSendEMail() {
        return sendEMail;
    }

    public int getSendSMS() {
        return sendSMS;
    }

    public int getStartCall() {
        return startCall;
    }


    

    public int getWarningTime() {
        return warningTimeInMin;
    }

    public void setWarningTime(int warnintTime) {
        this.warningTimeInMin = warnintTime;
    }
    
    public int getTemperaturToLow() {
        return temperaturToLow;
    }

    public int getTemperaturToHigh() {
        return temperaturToHigh;
    }
    
    public int  getAmbientLightOff() {
        return ambientLightOff;
    }

    public int getAmbientLightOn() {
        return ambientLightOn;
    }

    public String getEndNightPhase() {
        return endNightPhase;
    }

    public String getStartNightPhase() {
        return startNightPhase;
    }

    public boolean isDemoModus() {
        return demoModus;
    }

    public void setTemperaturToLow(int temperaturToLow) {
        this.temperaturToLow = temperaturToLow;
    }

    public void setTemperaturToHigh(int temperaturToHigh) {
        this.temperaturToHigh = temperaturToHigh;
    }
   
    public void setAmbientLightOff(int ambientLightOff) {
        this.ambientLightOff = ambientLightOff;
    }

    public void setAmbientLightOn(int ambientLightOn) {
        this.ambientLightOn = ambientLightOn;
    }
    
    public void setDemoModus(boolean demoModus) {
        this.demoModus = demoModus;
    }

    public void setEndNightPhase(String endNightPhase) {
        this.endNightPhase = endNightPhase;
    }

    public void setStartNightPhase(String startNightPhase) {
        this.startNightPhase = startNightPhase;
    }
}
