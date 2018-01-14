/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room;

import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.Connections;

/**
 *
 * @author Turna
 */
public class PassageSensor {

    BrickletDistanceIR tinkerforge;
    ConnectionParameters cp;
    
    private int flag = 0;

    private final String UID;
    private final String ROOM;
    private final String MODUL = "Passage";
    private final String TINKERFORGE_IP;

    
    public PassageSensor(String tinkerforgeIP, String uid, String room) {
        this.TINKERFORGE_IP = tinkerforgeIP;
        this.UID = uid;
        this.ROOM = room;
    }

    private void getTinkerforgeConnection() throws Exception {
        IPConnection ipcon = new IPConnection();
        cp = new ConnectionParameters();
        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
        tinkerforge = new BrickletDistanceIR(UID, ipcon);
    }

    public void doPassage() {
        Connections con = new Connections();
        try {
            getTinkerforgeConnection();
            con.getMQTTconnection(MODUL, ROOM, UID);
            
            tinkerforge.addDistanceListener((int distance) -> {
                
                if (distance <= 350 && flag != 0) {
                    
                    String nachricht = "Passage Detected";
                    con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                    flag = 0;                        
                    
                } else if (distance > 350 && flag != 1) {
                    String nachricht = "No Passage";
                    con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                    flag = 1;
                }
            });
           
            tinkerforge.setDistanceCallbackPeriod(200);
            
        } catch (Exception ex) {
            System.out.println("WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
        }
    }
}
