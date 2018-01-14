/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection.siot;


import intraal.bt.config.connection.ConnectionParameters;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Turna
 */
public class SiotDashboardInput {
    
    ConnectionParameters siotCon = new ConnectionParameters();
    public String inputKey;
    public String data;

    public void setInputMessage(String message) {
        data = message;
    }
    
    public void setInputKey(String key) {
        inputKey = key;
    }
    

    // HTTP POST request
    public void sendInput() throws Exception {

        String url = siotCon.getSIOT_URL()+ "/mqtt/request";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", siotCon.getSIOT_USER_AGENT()); //USER_AGENT
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "message= " + data + "&topic=siot/DAT/" + siotCon.getSIOT_LICENCE()+ "/" + inputKey;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("SIOT Dashbaord: " + urlParameters);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }
   
}
