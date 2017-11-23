/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.config.connection.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

/**
 *
 * @author turna
 */
public class DeviceRestService {

    String webPage = "https://smart-me.com:443";
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
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        String result = sb.toString();

//      System.out.println(result);
        System.out.println("Secure connection with " + webPage + " successful!");
        System.out.println("*** END ***");
    }

    public String getMethode(String methode) throws MalformedURLException, IOException {
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);

        URL url = new URL(webPage + methode);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);

        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        String result = sb.toString();
        System.out.println("GET METHODE: " + webPage + methode);
        return result;
    }

    public void putMethode(String methode) throws MalformedURLException, IOException {
        URLConnection connection = new URL(webPage + "?" + methode).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        InputStream response = connection.getInputStream();
    }

    public static void main(String[] args) throws IOException {

        DeviceRestService drs = new DeviceRestService();
        //drs.checkConnection();
        Device d = new Device();

//        String JSON = drs.getMethode("/api/Devices/5b9b3fea-cc8d-45ad-92b3-9caf3be725bc");
//        d.parsJsonToObjetc(JSON);
//        System.out.println(d.getValueDate()+" "+d.getName()+" "+d.getSerial());
        drs.putMethode("/api/Devices/5b9b3fea-cc8d-45ad-92b3-9caf3be725bc?switchState=false");

    }
}