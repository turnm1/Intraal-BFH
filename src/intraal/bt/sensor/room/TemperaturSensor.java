/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room;

import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.Connections;
import intraal.bt.system.settings.IntraalEinstellungen;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Turna
 */
public class TemperaturSensor {

    BrickletTemperature tinkerforge;
    ConnectionParameters cp;

    private final String UID;
    private final String ROOM;
    private final String MODUL = "Temperatur";
    private final String TINKERFORGE_IP;

    
    public TemperaturSensor(String tinkerforgeIP, String uid, String room) {
        this.TINKERFORGE_IP = tinkerforgeIP;
        this.UID = uid;
        this.ROOM = room;
    }

    private void getTinkerforgeConnection() throws Exception {
        IPConnection ipcon = new IPConnection();
        cp = new ConnectionParameters();
        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
        tinkerforge = new BrickletTemperature(UID, ipcon);
    }

    public void getTemp()  {
        Connections con = new Connections();
        IntraalEinstellungen s = new IntraalEinstellungen();
        try {
            getTinkerforgeConnection();
            con.getMQTTconnection(MODUL, ROOM, UID);
            
            tinkerforge.addTemperatureListener((short temperature) -> {
                if (temperature > s.getTemperaturToHigh()) {
                  String nachricht = temperature / 100.0 + " Grad => Hoch";
                    try {
                        con.sendMQTTSilenceMessage(MODUL, ROOM, UID, nachricht);
                    } catch (Exception ex) {
                        Logger.getLogger(TemperaturSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                    
                } else if (temperature <= s.getTemperaturToLow()) {
                     String nachricht = temperature / 100.0 + " Grad => Tief";
                    try {
                        con.sendMQTTSilenceMessage(MODUL, ROOM, UID, nachricht);
                    } catch (Exception ex) {
                        Logger.getLogger(TemperaturSensor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            tinkerforge.setTemperatureCallbackPeriod(10000);
            
        } catch (Exception ex) {
            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
        }
    }
}
