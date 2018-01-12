/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.config.connection.rest.SendMail;
import intraal.bt.config.connection.twilio.OutCall;
import intraal.bt.config.connection.twilio.SendSMS;
import intraal.bt.system.settings.BewohnerInformation;
import intraal.bt.system.settings.Kontaktpersonen;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author turna
 */
public class CallAndSendNotification {
    
    SendMail sm = new SendMail();
    SendSMS ssms = new SendSMS();
    OutCall oc = new OutCall();
    BewohnerInformation bi = new BewohnerInformation();
    
    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        String dt = dateFormat.format(date);
        return dt;
    }
    
    private void sendWarning(String email, String sendTo, String sendFrom,  String message){
       // callWarningNotification(sendTo, sendFrom);
        sendTextWarningNotification(email, sendTo, sendFrom, message);
    }
    
    private void callWarningNotification(String sendTo, String sendFrom){
        oc.makeOutCall(sendTo, sendFrom);
    }
    
    public void sendTextWarningNotification(String email, String sendTo, String sendFrom,  String message){
        String subject =    "*** INTRAAL Meldung ***";
           String text =    "Es wurde eine Gefahrensituation erkannt!\n" +
                            "Betroffene Person: "+ bi.getPrename() +" "+ bi.getName() +"\n"+
                            "Standort: "+ bi.getStreetAndNr() + ", " + bi.getPlz() + " " + bi.getCity() +"\n"+
                            "Ursache: " + message +"\n"+
                            "Empfohlene Vorgehensweise:" +"\n" +
                            "1. Rufen Sie die betroffene Person an: +"+bi.getTelefon() +"\n" +
                            "2. Wenn niemand Ihren Anruf entgegen nimmt, besuchen Sie die betroffene Person." +"\n" +
                            "3. Wenn die Türe nicht geöffnet wird oder die betroffene Person am Boden liegend vorfinden, rufen Sie die Ambulanz an: 144" +"\n" +
                            "" +"\n" +
                            "Die Meldung wird in 5 Minuten an die nächte Kontaktperson weitergeleitet";
        
        sm.sendMail(subject, text, email);
      //  ssms.sendSMS(sendTo, text, sendFrom);
    }
    
    public void sendTextWarningSystemOff(String mail, String sendTo, String room, String sendFrom){
        String subject =    "*** INTRAAL Meldung ***";
        String text =       "Das System ist Offline!\n" +
                            "Datum & Uhrzeit: "+ getDate() +"\n" +
                            "Bitte starten Sie das System neu!";
        
        sm.sendMail(subject, text, mail);
        ssms.sendSMS(sendTo, text, sendFrom);
    }
    
        public void sendTextWarningSystemOn(String mail, String sendTo, String room, String sendFrom){
        String subject =    "*** INTRAAL Meldung ***";
        String text =       "Das System ist wieder Online!\n" +
                            "Datum & Uhrzeit: "+ getDate();
        
        sm.sendMail(subject, text, mail);
        ssms.sendSMS(sendTo, text, sendFrom);
    }
    
    // Test = OK!
    public static void main(String[] args) {
        CallAndSendNotification c = new CallAndSendNotification();
        Kontaktpersonen k = new Kontaktpersonen();
        ConnectionParameters cp = new ConnectionParameters();
        c.sendWarning(k.getEmail(), k.getTelefon(), cp.getTwilio_sms_nummer(), "Test Message");
    }
}

           
