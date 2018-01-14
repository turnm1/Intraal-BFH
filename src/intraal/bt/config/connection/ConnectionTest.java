package intraal.bt.config.connection;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author turna
 */
public class ConnectionTest {
    
    ConnectionParameters cp;
    Connections con;

    private final String UID = "Status";
    private final String USECASENR = "ConnectionTest";
    private final String USECASE = "Helper";

    private String modulName;

    private void sendConnectionStatus(String modulName, String status) {
        con = new Connections();
        try {
            con.getMQTTconnection(USECASE, USECASENR, UID);
            String nachricht = modulName + " = " + status;
            con.sendMQTTmessage(USECASE, USECASENR, UID, nachricht);
            
        } catch (Exception ex) {
            System.out.println("WIFI-Verbindung unterbrochen: "+ USECASE + "/" + USECASENR + "/" + UID);
        }
    }
    
    private void getSensorModulStatus(String UID, short status) {
        String statusStr;
        System.out.println(status);
        switch (UID) {
            case "6e7NrQ":
                modulName = "Eingang-Modul";
                break;
            case "6jDUPU":
                modulName = "Eingang2-Modul";
                break;
            case "6e88VL":
                modulName = "Küche-Modul";
                break;
            case "6QFxcy":
                modulName = "Bad-Modul";
                break;
            case "62B7TB":
                modulName = "Schlafzimmer-Modul";
                break;
            case "6CtMfr":
                modulName = "Bett-Modul";
                break;
            case "5W5jVE":
                modulName = "Wohnzimmer-Modul";
                break;
            default:
                break;
        }
        if (status == 1) {
            statusStr = "online";
        } else {
            statusStr = "offline";
        }
        sendConnectionStatus(modulName, statusStr);
    }

    private void getOfflineStatusViaIP(String IP) {
        switch (IP) {
            case "10.0.233.43":
                modulName = "Eingang-Modul";
                break;
            case "10.0.233.49":
                modulName = "Eingang2-Modul";
                break;
            case "10.0.233.44":
                modulName = "Küche-Modul";
                break;
            case "10.0.233.45":
                modulName = "Bad-Modul";
                break;
            case "10.0.233.46":
                modulName = "Schlafzimmer-Modul";
                break;
            case "10.0.233.48":
                modulName = "Bett-Modul";
                break;
            case "10.0.233.47":
                modulName = "Wohnzimmer-Modul";
                break;
            default:
                break;
        }
        String off = "offline";
        sendConnectionStatus(modulName, off);
    }

    private void testModul(String IP) throws Exception {
        IPConnection ipcon = new IPConnection();
        ipcon.connect(IP, cp.getTINKERFORGE_PORT());
        try {
            con.getTinkerforgeConnection(IP);
            
            List<String> connectedDevices = new ArrayList<>();

            ipcon.addEnumerateListener(new IPConnection.EnumerateListener() {
                @Override
                public void enumerate(String uid, String connectedUid, char position,
                        short[] hardwareVersion, short[] firmwareVersion,
                        int deviceIdentifier, short enumerationType) {
                    
                    // einmaliger checker
                    if(connectedDevices.contains(uid)) {
                        getSensorModulStatus(connectedUid, ipcon.getConnectionState());
                        connectedDevices.add(uid);
                    }
                    // Bei offline von sensoren wird nichts angezeigt!
                    if (connectedUid.equals(" ")){
                        getOfflineStatusViaIP(IP);
                    }
                    
                }
            });

            ipcon.enumerate();
            Thread.sleep(2000);
            ipcon.disconnect();
            
      } catch (IOException | AlreadyConnectedException | NotConnectedException | InterruptedException ex) {
            getOfflineStatusViaIP(IP);
        }
    }
        
    public void connectionTestWithFeedback() throws Exception{
        testModul(cp.getTINKERFORGE_IP_BAD());
   //      testModul(cp.getTINKERFORGE_IP_BETT());
   //     testModul(cp.getTINKERFORGE_IP_EINGANG());
  //      testModul(cp.getTINKERFORGE_IP_EINGANG2());
 //       testModul(cp.getTINKERFORGE_IP_KÜCHE());
  //      testModul(cp.getTINKERFORGE_IP_SCHLAFZIMMER());
  //      testModul(cp.getTINKERFORGE_IP_WOHNZIMMER());
    }
    
    public static void main(String[] args) throws Exception {
        ConnectionTest ct = new ConnectionTest();
        ct.connectionTestWithFeedback();
    }
}
