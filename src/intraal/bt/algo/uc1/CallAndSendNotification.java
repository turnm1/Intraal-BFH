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
    
    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();
        String dt = dateFormat.format(date);
        return dt;
    }
    
    public void sendWarning(String mail, String telefon, String room){
        try {
            callWarningNotification(telefon, room);
        } catch (Exception e) {
            sendTextWarningNotification(mail, telefon, room);
        }
    }
    
    private void callWarningNotification(String telefon, String room){
        
    }
    
    public void sendTextWarningNotification(String mail, String telefon, String room){
        String subject = "*** INTRAAL Meldung ***";
        String text = "Es wurde ein Gefahrensituation erkannt!\n" +
               "Datum & Uhrzeit: "+ getDate() +"\n" +
               "Im Raum: "+ room +"\n"+
               "Empfohlener Vorgehensweise:" +"\n" +
               "1: Anrufen" +"\n" +
               "2: Vorort gehen" +"\n" +
               "3: Massnahmen einleiten";
        
        sm.sendMail(subject, text, mail);
        ssms.sendSMS(telefon, text, ConnectionParameters.getTwilio_sms_nummer());
        oc.makeOutCall(telefon, ConnectionParameters.getTwilio_sms_nummer());
    }
    
    // Test = OK!
    public static void main(String[] args) {
        CallAndSendNotification c = new CallAndSendNotification();
        Kontaktpersonen k = new Kontaktpersonen();
        c.sendTextWarningNotification(k.getEmail(), k.getMobile(), "Haus1");
    }
}

           
