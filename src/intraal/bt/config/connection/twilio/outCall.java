package intraal.bt.config.connection.twilio;

// Install the Java helper library from twilio.com/docs/java/install


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.net.URISyntaxException;



public class outCall {
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "ACfdb500575b3af7eb470252c03f511c5d";
  public static final String AUTH_TOKEN = "235d5b27262f7fb0f0634bc7a5a810bc";

  public static void main(String[] args) throws URISyntaxException {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Call call = Call.creator(new PhoneNumber("+41788312364"), new PhoneNumber("+41445051051"),
        new URI("http://demo.twilio.com/docs/voice.xml")).create();

    System.out.println(call.getSid());
  }
}