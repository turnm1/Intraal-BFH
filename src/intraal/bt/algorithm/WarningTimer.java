/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algorithm;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.system.settings.IntraalEinstellungen;
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
public class WarningTimer {
    
    Timer timer;
    RemindTask rt;
    CallAndSendNotification message;
    ConnectionParameters cp;
    IntraalEinstellungen s;
    List<KontaktInformationen> kontaktinformationen;
    public static String activityStatus = "Normale Aktivitätsstatus in der Wohnung.";

    // 300 = 5 min
    public WarningTimer(int seconds) {
        System.out.println("Warning Timer started");
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds);
    }    
    
    public void stopWarningTimer(){
        System.out.println("Warning Timer reseted");
        timer.cancel();
        timer.purge();
        //rt.cancel();
    }
    
    private void messageWarning() throws IOException{
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen ki = kontaktinformationen.get(0);
        cp = new ConnectionParameters();
        message = new CallAndSendNotification();
        s = new IntraalEinstellungen();
        message.sendWarning(ki.getEmail(), ki.getTelefon(), cp.getTWILIO_SMS_NUMMER(), activityStatus);
        timer.cancel();
        timer.purge();
    }
        
        

    class RemindTask extends TimerTask  {
        
        public void run() {
            try {
                messageWarning();
            } catch (IOException ex) {
                Logger.getLogger(WarningTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
