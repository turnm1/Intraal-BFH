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
    
    private int ambientLightOff = 200;
    private int ambientLightOn = 300;
    private int temperaturToHigh = 2500;
    private int temperaturToLow = 2100;
    private String startNightPhase = "16:00";
    private String endNightPhase = "10:00";
    private boolean demoModus = false;
    private int warnintTime = 15;
    
    public static IntraalEinstellungen Settings() throws FileNotFoundException, IOException {
        File propertiesFile = new File(""); // Pfad hier hinterlegen
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
        settings.setWarnintTime(Integer.parseInt(props.getProperty("WarningTimer")));;
        return settings;
    }

    public int getWarnintTime() {
        return warnintTime;
    }

    public void setWarnintTime(int warnintTime) {
        this.warnintTime = warnintTime;
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
