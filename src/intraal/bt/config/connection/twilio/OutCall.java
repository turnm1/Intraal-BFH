package intraal.bt.config.connection.twilio;

// Install the Java helper library from twilio.com/docs/java/install


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import intraal.bt.config.connection.ConnectionParameters;
import java.net.URI;



public class OutCall {
  ConnectionParameters cp = new ConnectionParameters();

  public void makeOutCall(String sendTo, String telefon) {
    Twilio.init(cp.getTWILIO_SID(), cp.getTWILIO_AUTH());
    
    URI uri = URI.create("http://intraal.com/voicemessage/message3.xml");

    Call.creator(new PhoneNumber(sendTo), new PhoneNumber(telefon), uri).create();

    System.out.println("@ CALL: started");
  }
}