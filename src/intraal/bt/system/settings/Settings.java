/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.system.settings;

import java.sql.Time;

/**
 *
 * @author turna
 */
public class Settings {
    
    private int ambientLightOff = 200;
    private int ambientLightOn = 300;
    private int temperaturToHigh = 2500;
    private int temperaturToLow = 2100;
    private Time startNightPhase;
    private Time endNightPhase;
    private boolean demoModus = false;

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

    public Time getEndNightPhase() {
        return endNightPhase;
    }

    public Time getStartNightPhase() {
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

    public void setEndNightPhase(Time endNightPhase) {
        this.endNightPhase = endNightPhase;
    }

    public void setStartNightPhase(Time startNightPhase) {
        this.startNightPhase = startNightPhase;
    }
}
