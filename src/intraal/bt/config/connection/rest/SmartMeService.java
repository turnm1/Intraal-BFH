/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import intraal.bt.config.connection.ConnectionParameters;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author turna
 */
public class SmartMeService {

    ConnectionParameters cp = new ConnectionParameters();

    String webPage = cp.getSMwebPage();
    String name = cp.getSMname();
    String password = cp.getSMPassword();
    String authString = cp.getSMauthString();

    public void checkConnection() throws MalformedURLException, IOException {
        System.out.println("*** BEGIN ***");
        System.out.println("auth string: " + authString);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        System.out.println("Base64 encoded auth string: " + authStringEnc);

        URL url = new URL(webPage);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);

        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuilder sb = new StringBuilder();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }

        System.out.println("Secure connection with " + webPage + " successful!");
        System.out.println("*** END ***");
    }

    public boolean getLightStatus(String ID) throws MalformedURLException, IOException, UnirestException, JSONException {
        System.out.println("*** BEGIN ***");
        System.out.println(webPage + "/api/Devices/" + ID);
        boolean status;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        HttpRequest request = Unirest.get(webPage + "/api/Devices/" + ID)
                .header("accept", "application/json")
                .header("Accept-Charset", "UTF-8")
                .header("Authorization", "Basic " + authStringEnc);
        HttpResponse<String> jsonResponse = request.asString();
        System.out.println("Response Status Code: " + jsonResponse.getStatus());
        System.out.println("Response Status Code: " + jsonResponse.getBody());

        JSONObject object = new JSONObject(jsonResponse.getBody());
        status = object.getBoolean("SwitchOn");
        System.out.println(status);
        System.out.println("*** END ***");
        return status;
    }

    public void switchLightStatus(String ID, String onOffswitch) throws MalformedURLException, IOException, UnirestException {
      //  System.out.println("*** BEGIN ***");
      //  System.out.println(webPage + "/api/Devices/" + ID);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        HttpRequest request = Unirest.put(webPage + "/api/Devices/" + ID)
                .header("accept", "application/json")
                .header("Accept-Charset", "UTF-8")
                .header("Authorization", "Basic " + authStringEnc)
                .queryString("switchState", onOffswitch);
        HttpResponse<String> jsonResponse = request.asString();
      //  System.out.println("Response Status Code: " + jsonResponse.getStatus());
      //  System.out.println("*** END ***");
    }

    
    // general Methodes for get more information, not used in intraal processe!
    public void getMethode(String methode, String ID) throws MalformedURLException, IOException, UnirestException, JSONException {
        System.out.println("*** BEGIN ***");
        System.out.println(webPage + methode + ID);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        HttpRequest request = Unirest.get(webPage + methode + ID)
                .header("accept", "application/json")
                .header("Accept-Charset", "UTF-8")
                .header("Authorization", "Basic " + authStringEnc);
        HttpResponse<String> jsonResponse = request.asString();
        System.out.println("Response Status Code: " + jsonResponse.getStatus());
        System.out.println("Response Status Code: " + jsonResponse.getBody());
        System.out.println("*** END ***");
    }

    public void putMethode(String methode) throws MalformedURLException, IOException, UnirestException {
        System.out.println("*** BEGIN ***");
        System.out.println(webPage + methode);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        HttpRequest request = Unirest.put(webPage + methode)
                .header("accept", "application/json")
                .header("Accept-Charset", "UTF-8")
                .header("Authorization", "Basic " + authStringEnc)
                .queryString("switchState", "false");
        HttpResponse<String> jsonResponse = request.asString();
        System.out.println("Response Status Code: " + jsonResponse.getStatus());
        System.out.println("*** END ***");
    }

//    public static void main(String[] args) throws IOException, UnirestException, MalformedURLException, JSONException {
//        SmartMeService sm = new SmartMeService();
//        // sm.checkConnection();
//        // sm.putMethode("/api/Devices/5b9b3fea-cc8d-45ad-92b3-9caf3be725bc");
//        // sm.getMethode("/api/Devices/", "");
//       // sm.getLightStatus("5b9b3fea-cc8d-45ad-92b3-9caf3be725bc");
//    }
}
