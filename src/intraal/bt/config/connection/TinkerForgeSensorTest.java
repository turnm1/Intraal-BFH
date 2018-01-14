///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package intraal.bt.config.connection;
//
//import com.tinkerforge.IPConnection;
//import intraal.bt.config.connection.ConnectionParameters;
//import intraal.bt.config.connection.siot.SiotDashboardInput;
//
///**
// *
// * @author turna
// */
//public class TinkerForgeSensorTest {
//
//    SiotDashboardInput sdi = new SiotDashboardInput();
//
//    private String status;
//    private String modulName;
//    private String flag;
//    
//    private String getSensorModulStatus(String exceptedMasterUID, String connectedUID, String UID){
//        
//        if (exceptedMasterUID.equals(connectedUID) || exceptedMasterUID.equals(UID)){
//            switch (exceptedMasterUID) {
//                case "6e7NrQ":
//                    flag = "6e7NrQ";
//                    modulName = "Eingang-Modul";
//                    break;
//                case "6jDUPU":
//                    flag = "6jDUPU";
//                    modulName = "Eingang2-Modul";
//                    break;
//                case "6e88VL":
//                    flag = "6e88VL";
//                    modulName = "Küche-Modul";
//                    break;
//                case "6QFxcy":
//                    flag = "6QFxcy";
//                    modulName = "Bad-Modul";
//                    break;
//                case "62B7TB":
//                    flag = "62B7TB";
//                    modulName = "Schlafzimmer-Modul";
//                    break;
//                case "6CtMfr":
//                    flag = "6CtMfr";
//                    modulName = "Bett-Modul";
//                    break;
//                case "5W5jVE":
//                    flag = "5W5jVE";
//                    modulName = "Wohnzimmer-Modul";
//                    break;
//                default:
//                    break;
//            }
//            status = "online";
//        } else {
//            status = "offline";
//        }
//        System.out.println(modulName + " ist " + status);
//        return exceptedMasterUID + " ist " + status;
//    }
//    
//    public void testTinkerforgeSensorModul(String IP, String exceptedMasterUID) throws Exception {
//        IPConnection ipcon = new IPConnection();
//        ConnectionParameters con = new ConnectionParameters();
//        ipcon.connect(IP, con.getTgPort());
//        
//		ipcon.addEnumerateListener(new IPConnection.EnumerateListener() {
//			public void enumerate(String uid, String connectedUid, char position,
//			                      short[] hardwareVersion, short[] firmwareVersion,
//			                      int deviceIdentifier, short enumerationType) {
//				                        getSensorModulStatus(exceptedMasterUID, connectedUid, uid);
//
//				if(enumerationType == IPConnection.ENUMERATION_TYPE_DISCONNECTED) {
//					System.out.println("Disconnected");
//				}
//			}
//		});
//                Thread.sleep(1000);
//		ipcon.enumerate();
//                System.out.println(ipcon.getConnectionState());
//	}
//
//    
//    public void runSensorTest() throws Exception {
//        ConnectionParameters cp = new ConnectionParameters();
//        testTinkerforgeSensorModul(cp.getTgBettIP(), cp.getmUIDBett());
//        testTinkerforgeSensorModul(cp.getTgBadIP(), cp.getmUIDBad());
//        testTinkerforgeSensorModul(cp.getTgEingang2IP(), cp.getmUIDEingang2());
//        testTinkerforgeSensorModul(cp.getTgEingangIP(), cp.getmUIDEingang());
//        testTinkerforgeSensorModul(cp.getTgKücheIP(), cp.getmUIDKüche());
//        testTinkerforgeSensorModul(cp.getTgSchlafzimmerIP(), cp.getmUIDSchalfzimmer());
//        testTinkerforgeSensorModul(cp.getTgWohnzimmerIP(), cp.getmUIDWohnzimmer());
//    }
//    
//    
//}
