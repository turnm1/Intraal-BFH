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
//    private final String UID = "LoadCell";
//    private final String UID1 = cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1();
//    private final String UID2 = cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2();
//    private final String UID3 = cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3();
//    private final String UID4 = cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4();
//    private final String ROOM;
//    private final String MODUL;
//    private final String TINKERFORGE_IP;
//    private static int flag = 0;
//    private static final int OnOffBed1 = -10000;
//    private static final int OnOffBed2 = 10;
//    private static final int OnOffBed3 = 10000;
//    private static final int OnOffBed4 = -20000000;
//
//    
//    public LoadCellSensor(String tinkerforgeIP, String room, String modul) {
//        this.TINKERFORGE_IP = tinkerforgeIP;
//        this.MODUL = modul;
//        this.ROOM = room;
//    }
//    
//    private void getTinkerforgeConnection() throws Exception {
//        IPConnection ipcon = new IPConnection();
//        cp = new ConnectionParameters();
//        ipcon.connect(TINKERFORGE_IP, cp.getTINKERFORGE_PORT());
//        tinkerforge = new BrickletLoadCell(UID, ipcon);
//    }
//    
//    public void getPersonOnBed1() {
//        Connections con = new Connections();
//        IntraalEinstellungen s = new IntraalEinstellungen();
//   
//        try {
//            getTinkerforgeConnection();
//            con.getMQTTconnection(MODUL, ROOM, UID1);
//
//            tinkerforge.addWeightListener(new BrickletLoadCell.WeightListener() {
//                @Override
//                public void weight(int weight) {
//
//                    if (weight <= OnOffBed1 && flag != 0) {
//                        String nachricht = "On the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID1, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        flag = 0;
//                    }
//                    
//                    if (weight > OnOffBed1 && flag != 1) {
//                        String nachricht = "Not on the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID1, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
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
//            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
//        }
//    }
//    
//    public void getPersonOnBed2() {
//        Connections con = new Connections();
//        IntraalEinstellungen s = new IntraalEinstellungen();
//   
//        try {
//            getTinkerforgeConnection();
//            con.getMQTTconnection(MODUL, ROOM, UID2);
//
//            tinkerforge.addWeightListener(new BrickletLoadCell.WeightListener() {
//                @Override
//                public void weight(int weight) {
//
//                    if (weight <= OnOffBed2 && flag != 0) {
//                        String nachricht = "On the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID2, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        flag = 0;
//                    }
//                    
//                    if (weight > OnOffBed2 && flag != 1) {
//                        String nachricht = "Not on the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID2, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
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
//            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
//        }
//    }
//
//    public void getPersonOnBed3() {
//        Connections con = new Connections();
//        IntraalEinstellungen s = new IntraalEinstellungen();
//   
//        try {
//            getTinkerforgeConnection();
//            con.getMQTTconnection(MODUL, ROOM, UID3);
//
//            tinkerforge.addWeightListener(new BrickletLoadCell.WeightListener() {
//                @Override
//                public void weight(int weight) {
//
//                    if (weight <= OnOffBed3 && flag != 0) {
//                        String nachricht = "On the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID3, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        flag = 0;
//                    }
//                    
//                    if (weight > OnOffBed3 && flag != 1) {
//                        String nachricht = "Not on the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID3, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
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
//            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
//        }
//    }
//    
//    public void getPersonOnBed4() {
//        Connections con = new Connections();
//        IntraalEinstellungen s = new IntraalEinstellungen();
//   
//        try {
//            getTinkerforgeConnection();
//            con.getMQTTconnection(MODUL, ROOM, UID4);
//
//            tinkerforge.addWeightListener(new BrickletLoadCell.WeightListener() {
//                @Override
//                public void weight(int weight) {
//
//                    if (weight <= OnOffBed4 && flag != 0) {
//                        String nachricht = "On the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID4, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        flag = 0;
//                    }
//                    
//                    if (weight > OnOffBed4 && flag != 1) {
//                        String nachricht = "Not on the bed";
//                        try {
//                            con.sendMQTTmessage(MODUL, ROOM, UID4, nachricht);
//                        } catch (Exception ex) {
//                            Logger.getLogger(LoadCellSensor_1.class.getName()).log(Level.SEVERE, null, ex);
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
//            System.out.println("x DISC: WIFI-Verbindung unterbrochen: "+ MODUL + "/" + ROOM + " IP: " + TINKERFORGE_IP);
//        }
//    }
//}
