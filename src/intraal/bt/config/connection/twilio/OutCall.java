package intraal.bt.config.connection.twilio;

// Install the Java helper library from twilio.com/docs/java/install


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import intraal.bt.config.connection.ConnectionParameters;
import java.net.URI;



public class OutCall {
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "ACfdb500575b3af7eb470252c03f511c5d";
  public static final String AUTH_TOKEN = "235d5b27262f7fb0f0634bc7a5a810bc";
  private static final ConnectionParameters cp = new ConnectionParameters();

  public static void makeOutCall(String sendTo, String telefon) {
    Twilio.init(cp.getTwilio_sid(), cp.getTwilio_auth_token());
    
    URI uri = URI.create("http://intraal.com/voicemessage/message3.xml");

    Call call = Call.creator(new PhoneNumber(sendTo), new PhoneNumber(telefon), uri).create();

    System.out.println(call.getSid());
  }
}