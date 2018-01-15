/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room;

import com.tinkerforge.BrickletLoadCell;
import com.tinkerforge.IPConnection;
import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.Connections;
import intraal.bt.system.settings.IntraalEinstellungen;

/**
 *
 * @author turna
 */
public class LoadCellSensor_3 {

    BrickletLoadCell tinkerforge;
    ConnectionParameters cp;
    
    private final String UID;
    private final String ROOM;
    private final String MODUL;
    private final String TINKERFORGE_IP;
    private static int flag;
    private static int OnOffBed = 22500;;

    public LoadCellSensor_3(String tinkerforgeIP, String uid, String room, String modul) {
        this.TINKERFORGE_IP = tinkerforgeIP;
        this.UID = uid;
        this.ROOM = room;
        this.MODUL = modul;
    }

    private void getTinkerforgeConnection() throws Exception {
        IPConnection ipcon = new IPConnection();
        cp = new ConnectionParameters();
        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
        tinkerforge = new BrickletLoadCell(UID, ipcon);
    }

    public void getPersonOnBed() {
        Connections con = new Connections();
        IntraalEinstellungen s = new IntraalEinstellungen();
   
        try {
            getTinkerforgeConnection();
            con.getMQTTconnection(MODUL, ROOM, UID);

            tinkerforge.addWeightListener(new BrickletLoadCell.WeightListener() {
                @Override
                public void weight(int weight) {

                    if (weight >= OnOffBed && flag != 0) {
                        System.out.println(weight+ " = " + OnOffBed);
                        String nachricht = "On the bed";
                        con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                        flag = 0;
                    }
                    
                    if (weight < OnOffBed && flag != 1) {
                        String nachricht = "Not on the bed";
                        con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
                        flag = 1;
                    }

                }
            });

            tinkerforge.setWeightCallbackPeriod(1000);

        } catch (Exception ex) {
            System.out.println("WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
        }
    }
}
