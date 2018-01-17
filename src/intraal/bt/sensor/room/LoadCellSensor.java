///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.sensor.room;
//
//import com.tinkerforge.BrickletLoadCell;
//import com.tinkerforge.IPConnection;
//import intraal.bt.config.connection.ConnectionParameters;
//import intraal.bt.config.connection.Connections;
//import intraal.bt.system.settings.IntraalEinstellungen;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author turna
// */
//public class LoadCellSensor {
//
//    BrickletLoadCell tinkerforge;
//    ConnectionParameters cp;
//    
//    private final String UID;
//    private final String ROOM;
//    private final String MODUL;
//    private final String TINKERFORGE_IP;
//    private static int flag;
//    private static int OnOffBed;
//
//    public LoadCellSensor(String tinkerforgeIP, String uid, String room, String modul) {
//        this.TINKERFORGE_IP = tinkerforgeIP;
//        this.UID = uid;
//        this.ROOM = room;
//        this.MODUL = modul;
//    }
//    
//    private int getOnOffWight(String uid){
//        if (uid.equals(cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1())){
//            OnOffBed = 22500;
//        } else if (uid.equals(cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2())){
//            OnOffBed = 22500;
//        } else if (uid.equals(cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3())){
//            OnOffBed = 22500;
//        } else if (uid.equals(cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4())){
//            OnOffBed = 22500;
//        }
//        return OnOffBed;
//    }
//
//    private void getTinkerforgeConnection() throws Exception {
//        IPConnection ipcon = new IPConnection();
//        cp = new ConnectionParameters();
//        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
//        tinkerforge = new BrickletLoadCell(UID, ipcon);
//    }
//
//    public void getPersonOnBed() {
//        Connections con = new Connections();
//        IntraalEinstellungen s = new IntraalEinstellungen();
//   
//        try {
//            getTinkerforgeConnection();
//            con.getMQTTconnection(MODUL, ROOM, UID);
//
//            tinkerforge.addWeightListener(new BrickletLoadCell.WeightListener() {
//                @Override
//                public void weight(int weight) {
//
//                    if (weight >= getOnOffWight(UID) && flag != 0) {
//                        System.out.println(weight+ " = " + getOnOffWight(UID));
//                        String nachricht = "On the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        flag = 0;
//                    }
//                    
//                    if (weight < getOnOffWight(UID) && flag != 1) {
//                        String nachricht = "Not on the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        flag = 1;
//                    }
//
//                }
//            });
//
//            tinkerforge.setWeightCallbackPeriod(1000);
//
//        } catch (Exception ex) {
//            System.out.println("WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
//        }
//    }
//}
