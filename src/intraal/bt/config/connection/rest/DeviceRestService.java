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

/**
 *
 * @author turna
 */
public class DeviceRestService {

    String webPage = "https://smart-me.com";
    String name = "vnf1";
    String password = "f6PEz6QsTZnn";
    String authString = name + ":" + password;

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

    public void getMethode(String methode) throws MalformedURLException, IOException, UnirestException {
        System.out.println("*** BEGIN ***");
      	System.out.println(webPage + methode);
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
    	HttpRequest request = Unirest.get(webPage + methode)
    			.header("accept", "application/json")
    			.header("Accept-Charset", "UTF-8")
        		.header("Authorization", "Basic "+authStringEnc);
    	HttpResponse<String> jsonResponse = request.asString();
    	System.out.println("Response Status Code: "+jsonResponse.getStatus());
        System.out.println("Response Status Code: "+jsonResponse.getBody());
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
        		.header("Authorization", "Basic "+authStringEnc)
    			.queryString("switchState", "false");
    	HttpResponse<String> jsonResponse = request.asString();
    	System.out.println("Response Status Code: "+jsonResponse.getStatus());
        System.out.println("*** END ***");
    }

    public static void main(String[] args) throws IOException, UnirestException {

        DeviceRestService drs = new DeviceRestService();
        drs.checkConnection();
   //   drs.putMethode("/api/Devices/5b9b3fea-cc8d-45ad-92b3-9caf3be725bc");
        drs.getMethode("/api/Devices/5b9b3fea-cc8d-45ad-92b3-9caf3be725bc");

    }
}
