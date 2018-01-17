/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room;

import com.tinkerforge.BrickletMotionDetector;
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
public class MotionSensor {

    BrickletMotionDetector tinkerforge;
    ConnectionParameters cp;

    private final String UID;
    private final String ROOM;
    private final String MODUL = "Motion";
    private final String TINKERFORGE_IP;

    
    public MotionSensor(String tinkerforgeIP, String uid, String room) {
        this.TINKERFORGE_IP = tinkerforgeIP;
        this.UID = uid;
        this.ROOM = room;
    }

    private void getTinkerforgeConnection() throws Exception {
        IPConnection ipcon = new IPConnection();
        cp = new ConnectionParameters();
        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
        tinkerforge = new BrickletMotionDetector(UID, ipcon);
    }

    
    public void doMotion() {
        Connections con = new Connections();
    
        try {
            getTinkerforgeConnection();
            con.getMQTTconnection(MODUL, ROOM, UID);
          
            tinkerforge.addMotionDetectedListener(() -> {
                String nachricht = "Motion Detected";
                try {
                    con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                } catch (Exception ex) {
                    Logger.getLogger(MotionSensor.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            // Add detection cycle ended listener
            tinkerforge.addDetectionCycleEndedListener(() -> {
                String nachricht = "Motion Ended";
                try {
                    con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                } catch (Exception ex) {
                    Logger.getLogger(MotionSensor.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (Exception ex) {
            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
        }
    }
}
