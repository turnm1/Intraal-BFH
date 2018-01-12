/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import intraal.bt.config.connection.ConnectionParameters;
/**
 *
 * @author turna
 */
public class SendSMS {

  ConnectionParameters cp = new ConnectionParameters();
 
  
  public void sendSMS(String sendTo, String text, String sendFrom){
    Twilio.init(cp.getTwilio_sid(), cp.getTwilio_auth_token());
    
    Message message = Message
        .creator(new PhoneNumber(sendTo), // to
                 new PhoneNumber(sendFrom), // from
                 text)
        .create();

    System.out.println(message.getSid());
  }
}
