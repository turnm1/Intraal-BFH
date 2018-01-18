package intraal.bt.config.connection;

import com.tinkerforge.IPConnection;
import intraal.bt.algorithm.CallAndSendNotification;
import intraal.bt.system.settings.KontaktInformationen;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author turna
 */
public class ConnectionTest {

    ConnectionParameters cp;
    Connections con;
    Timer timer;
    CallAndSendNotification sendtext;
    List<KontaktInformationen> kontaktinformationen;

    private final String UID = "Status";
    private final String USECASENR = "ConnectionTest";
    private final String USECASE = "Helper";
    private String modulName;
    private static boolean isRunning;
    private static int count;
    private static boolean isConnected = false;

    private void sendConnectionStatus(String modulName, String status) throws Exception {
        con = new Connections();
        con.getMQTTconnection(USECASE, USECASENR, UID);
        String nachricht = modulName + " = " + status;
        con.sendMQTTmessage(USECASE, USECASENR, UID, nachricht);
    }

    private String getOfflineStatusViaIP(String IP) throws Exception {
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
        }
        return modulName;
    }

    public void testTinkerforgeModuls(int seconds) {
        System.out.println("Testprogramm wird gestartet ...:");
        isRunning = true;
        timer = new Timer();
        if (isRunning = true) {
            timer.schedule(new testModul(), seconds);
        }
    }

    private void sendStatusMessage(int number, boolean isConnected, String problemMessage) throws IOException {
        sendtext = new CallAndSendNotification();
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen ki = kontaktinformationen.get(0);
        if (number == 7 && isConnected == true) {
            sendtext.sendSystemOnline(ki.getEmail(), ki.getTelefon(), cp.getTWILIO_SMS_NUMMER());
        } else if (isConnected == false) {
            // Stop Running!!!!
            sendtext.sendSystemOffline(ki.getEmail(), ki.getTelefon(), cp.getTWILIO_SMS_NUMMER(), problemMessage);
            stopRunning();
        }
    }

    private void testerMethode(String ip) {
        cp = new ConnectionParameters();
        try {
            System.out.println("1. Verbindung mit " + getOfflineStatusViaIP(ip) + " wird hergestellt . . .");
            testTinkerforgeModul(ip);
            sendConnectionStatus(getOfflineStatusViaIP(ip), "online");
            count++;
            isConnected = true;
            sendStatusMessage(count, isConnected, "Alles online");
        } catch (Exception ex) {
            try {
                String problem = "2. Verbindung mit " + getOfflineStatusViaIP(ip) + " konnte nicht hergestellt werden!";
                System.out.println(problem);
                sendConnectionStatus(getOfflineStatusViaIP(ip), "offline");
                count = 0;
                isConnected = false;
                sendStatusMessage(count, isConnected, problem);
            } catch (Exception ex1) {
                Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public void stopRunning() {
        isRunning = false;
        timer.cancel();
        timer.purge();
    }

    class testModul extends TimerTask {

        @Override
        public void run() {
            while (isRunning == true) {
                cp = new ConnectionParameters();
                testerMethode(cp.getTINKERFORGE_IP_EINGANG());
                testerMethode(cp.getTINKERFORGE_IP_EINGANG2());
                testerMethode(cp.getTINKERFORGE_IP_KÜCHE());
                testerMethode(cp.getTINKERFORGE_IP_WOHNZIMMER());
                testerMethode(cp.getTINKERFORGE_IP_SCHLAFZIMMER());
                testerMethode(cp.getTINKERFORGE_IP_BETT());
                testerMethode(cp.getTINKERFORGE_IP_BAD());
                stopRunning();
            }
        }
    }

    private void testTinkerforgeModul(String IP) throws Exception {
        IPConnection ipcon = new IPConnection();
        cp = new ConnectionParameters();
        ipcon.connect(IP, cp.getTINKERFORGE_PORT());

        ipcon.addEnumerateListener(new IPConnection.EnumerateListener() {
            @Override
            public void enumerate(String uid, String connectedUid, char position,
                    short[] hardwareVersion, short[] firmwareVersion,
                    int deviceIdentifier, short enumerationType) {

            }
        });
        System.out.println("2. Verbindung konnte hergestellt werden!");
        ipcon.enumerate();
        ipcon.disconnect();
    }

    public static void main(String[] args) throws Exception {
        ConnectionTest ct = new ConnectionTest();
        ct.testTinkerforgeModuls(100);

    }
}
