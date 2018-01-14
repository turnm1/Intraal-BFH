/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import intraal.bt.config.connection.rest.SendMail;
import intraal.bt.config.connection.twilio.OutCall;
import intraal.bt.config.connection.twilio.SendSMS;
import intraal.bt.system.settings.KontaktInformationen;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author turna
 */
public class CallAndSendNotification {

    SendMail sm = new SendMail();
    SendSMS ssms = new SendSMS();
    OutCall oc = new OutCall();
    List<KontaktInformationen> kontaktinformationen;


    public void sendWarning(String email, String sendTo, String sendFrom, String message) throws IOException {
        callWarningNotification(sendTo, sendFrom);
        sendTextWarningNotification(email, sendTo, sendFrom, message);
    }
    
    public void sendSystemOffline(String email, String sendTo, String sendFrom, String offlineModulMessage) throws IOException {
        sendTextSystemOfflineNotification(email, sendTo, sendFrom, offlineModulMessage);
    }
    
        public void sendSystemOffline(String email, String sendTo, String sendFrom) throws IOException {
        sendTextSystemOnlineNotification(email, sendTo, sendFrom);
    }
        
    public void sendMoving(String email, String sendTo, String sendFrom) throws IOException {
        sendTextPersonMovedNotification(email, sendTo, sendFrom);
    }
        
    public void sendVisited(String email, String sendTo, String sendFrom) throws IOException {
        sendTextVisitedNotification(email, sendTo, sendFrom);
    }

    private void callWarningNotification(String sendTo, String sendFrom) {
        oc.makeOutCall(sendTo, sendFrom);
    }

    private void sendTextWarningNotification(String email, String sendTo, String sendFrom, String message) throws IOException {
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen bi = kontaktinformationen.get(0);
        
        String subject = "*** INTRAAL Meldung ***";
        String text = "Es wurde eine Gefahrensituation erkannt!" +"\n"
                + "Betroffene Person: " + bi.getPrename() + " " + bi.getName() + "\n"
                + "Standort: " + bi.getStreetAndNr() + ", " + bi.getPlz() + " " + bi.getCity() + "\n"
                + "Ursache: " + message + "\n"
                + "Empfohlene Vorgehensweise:" + "\n"
                + "1. Rufen Sie die betroffene Person an: +" + bi.getTelefon() + "\n"
                + "2. Wenn niemand Ihren Anruf entgegen nimmt, besuchen Sie die betroffene Person." + "\n"
                + "3. Wenn die Türe Vorort nicht geöffnet werden kann, rufen Sie der Polizei an: 144" + "\n"
                + "4. Wenn Sie die betroffene Person am Boden liegend vorfinden, rufen Sie die Ambulanz an: 117" + "\n"
                + "" + "\n"
                + "Sie werden sofort informiert, wenn sich die betroffene Person bewegt oder jemand vor Ihnen zu besuch gekommen ist.";

        sm.sendMail(subject, text, email);
        ssms.sendSMS(sendTo, text, sendFrom);
    }
    
    private void sendTextSystemOfflineNotification(String email, String sendTo, String sendFrom, String offlineModulMessage) throws IOException {
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen bi = kontaktinformationen.get(0);
        
        String subject = "*** INTRAAL Meldung ***";
        String text = "Das INTRAAL-System ist offline. Die INTRAAL-Service ist ausgeschaltet!" +" \n"
                + "Betroffene Wohnung: " + bi.getStreetAndNr() + ", " + bi.getPlz() + " " + bi.getCity() + "\n"
                + "Ursache: " + offlineModulMessage + "\n"
                + "Dringende Vorgehensweise:" + "\n"
                + "1. Bitte merken Sie sich den Steckplatz befor Sie den Kabel des Offline-Moduls herausziehen." + "\n"
                + "2. Nachdem Sie den Kabel ausgesteckt haben, warten Sie mindestens 5 Sekunden." + "\n"
                + "3. Stecken Sie nun den Kabel wieder an die gleiche Stelle ein" + "\n"
                + "4. Sie müssen zum Schluss das INTRAAL-System über den Desktop neustarten!" + "\n"
                + "\n"
                + "Das INTRAAL-System wird bis dieser Vorgang nicht ausgeführt worden ist offline bleiben!";

        sm.sendMail(subject, text, email);
        ssms.sendSMS(sendTo, text, sendFrom);
    }
        
    private void sendTextSystemOnlineNotification(String email, String sendTo, String sendFrom) throws IOException {
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen bi = kontaktinformationen.get(0);
        
        String subject = "*** INTRAAL Meldung ***";
        String text = "Das System ist wieder Online!";

        sm.sendMail(subject, text, email);
        ssms.sendSMS(sendTo, text, sendFrom);
    }
    
    private void sendTextPersonMovedNotification(String email, String sendTo, String sendFrom) throws IOException {
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen bi = kontaktinformationen.get(0);
        
        String subject = "*** INTRAAL Meldung ***";
        String text = "Es wurde eine Aktivität erkannt!" +"\n"
                + "Betroffene Person: " + bi.getPrename() + " " + bi.getName() + "\n"
                + "Standort: " + bi.getStreetAndNr() + ", " + bi.getPlz() + " " + bi.getCity() + "\n"
                + "Ursache: Es wurde im Raum der Gefahrensituation eine Aktivität beobachtet." + "\n"
                + "Empfohlene Vorgehensweise:" + "\n"
                + "1. Veruschen nochmal die verunfallten Person telefonisch zu erreichen: +" + bi.getTelefon() + "\n"
                + "2. Wenn niemand wieder Ihren Anruf entgegen nimmt, besuchen Sie die betroffene Person." + "\n"
                + "" + "\n"
                + "Sie werden sofort informiert, wenn sich die betroffene Person bewegt oder jemand vor Ihnen zu besuch gekommen ist.";

        sm.sendMail(subject, text, email);
        ssms.sendSMS(sendTo, text, sendFrom);
    }
    
    private void sendTextVisitedNotification(String email, String sendTo, String sendFrom) throws IOException {
        kontaktinformationen = KontaktInformationen.loadKontaktInformationen();
        KontaktInformationen bi = kontaktinformationen.get(0);
        
        String subject = "*** INTRAAL Meldung ***";
        String text = "Ein Besucher/Helfer ist beim betroffenen Person eingetroffen!" +"\n"
                + "Standort: " + bi.getStreetAndNr() + ", " + bi.getPlz() + " " + bi.getCity() + "\n"
                + "Empfohlene Vorgehensweise:" + "\n"
                + "1. Veruschen nochmal die verunfallten Person telefonisch zu erreichen: +" + bi.getTelefon() + "\n"
                + "2. Wenn niemand wieder Ihren Anruf entgegen nimmt, versuchen Sie es später nochmal." + "\n"
                + "" + "\n"
                + "Bitte vergewissern Sie sich, dass die betroffene Person wieder in Sicherheit ist.";

        sm.sendMail(subject, text, email);
        ssms.sendSMS(sendTo, text, sendFrom);
    }

    // Test = OK!
//    public static void main(String[] args) throws IOException {
//        CallAndSendNotification c = new CallAndSendNotification();
//        KontaktInformationen k = new KontaktInformationen();
//        ConnectionParameters cp = new ConnectionParameters();
//        c.sendWarning(k.getEmail(), k.getTelefon(), cp.getTWILIO_SMS_NUMMER(), "Test Message");
//    }
}
