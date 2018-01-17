/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room;

import com.tinkerforge.BrickletAmbientLightV2;
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
public class AmbientLightSensor {

    BrickletAmbientLightV2 tinkerforge;
    ConnectionParameters cp;

    private final String UID;
    private final String ROOM;
    private final String MODUL = "AmbienteLight";
    private final String TINKERFORGE_IP;

    
    public AmbientLightSensor(String tinkerforgeIP, String uid, String room) {
        this.TINKERFORGE_IP = tinkerforgeIP;
        this.UID = uid;
        this.ROOM = room;
    }

    private void getTinkerforgeConnection() throws Exception {
        IPConnection ipcon = new IPConnection();
        cp = new ConnectionParameters();
        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
        tinkerforge = new BrickletAmbientLightV2(UID, ipcon);
    }

    public void getLight() {
        Connections con = new Connections();
        IntraalEinstellungen s = new IntraalEinstellungen();
        try {
            getTinkerforgeConnection();
            con.getMQTTconnection(MODUL, ROOM, UID);
            
            tinkerforge.addIlluminanceReachedListener(new BrickletAmbientLightV2.IlluminanceReachedListener() {
                @Override
                public void illuminanceReached(long illuminance) {
                    
                    if (illuminance < s.getAmbientLightOff()) {
                        String nachricht = illuminance / 10.0 + " lux => Licht aus";
                        try {
                            con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                        } catch (Exception ex) {
                            Logger.getLogger(AmbientLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } else if (illuminance >= s.getAmbientLightOn()) {
                        String nachricht = illuminance / 10.0 + " lux => Licht ein";
                        try {
                            con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                        } catch (Exception ex) {
                            Logger.getLogger(AmbientLightSensor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            
            tinkerforge.setDebouncePeriod(10000);
            tinkerforge.setIlluminanceCallbackThreshold('o', s.getAmbientLightOff(), s.getAmbientLightOn());
            
        } catch (Exception ex) {
            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
        }
    }
}
