/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author turna 
 */
public class Device {

    public String id;
    public String name;
    public int serial;
    public int deviceEnergyType;
    public int familyType;
    public double counterReading;
    public String counterReadingUnit;
    public double counterReadingTl;
    public boolean switchOn;
    public String valueDate;

    public void parsJsonToObjetc(String jsonString) {
        String formatetJSON = formatJSON(jsonString);
        try {
            JSONObject obj = new JSONObject(formatetJSON);
            setId(obj.getString("Id"));
            setName(obj.getString("Name"));
            setSerial(obj.getInt("Serial"));
            setDeviceEnergyType(obj.getInt("DeviceEnergyType"));
            setSwitchOn(obj.getBoolean("SwitchOn"));
            setValueDate(obj.getString("ValueDate"));
            
        } catch (JSONException ex) {
            System.out.println("Fehler");
            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private String formatJSON(String jsonString) {
        String format1 = jsonString.replace("[", "");
        String format2 = format1.replace("]", "");
        return format2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getDeviceEnergyType() {
        return deviceEnergyType;
    }

    public void setDeviceEnergyType(int deviceEnergyType) {
        this.deviceEnergyType = deviceEnergyType;
    }

    public int getFamilyType() {
        return familyType;
    }

    public void setFamilyType(int familyType) {
        this.familyType = familyType;
    }

    public double getCounterReading() {
        return counterReading;
    }

    public void setCounterReading(double counterReading) {
        this.counterReading = counterReading;
    }

    public String getCounterReadingUnit() {
        return counterReadingUnit;
    }

    public void setCounterReadingUnit(String counterReadingUnit) {
        this.counterReadingUnit = counterReadingUnit;
    }

    public double getCounterReadingTl() {
        return counterReadingTl;
    }

    public void setCounterReadingTl(double counterReadingTl) {
        this.counterReadingTl = counterReadingTl;
    }

    public boolean isSwitchOn() {
        return switchOn;
    }

    public void setSwitchOn(boolean switchOn) {
        this.switchOn = switchOn;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
