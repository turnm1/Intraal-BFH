/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.system.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author turna
 */
public class KontaktInformationen {
    
        public static List<KontaktInformationen> loadKontaktInformationen() throws FileNotFoundException, IOException {
        File propertiesFile = new File("C:\\Users\\turna\\Documents\\NetBeansProjects\\Intraal-BT\\src\\intraal\\bt\\system\\settings\\kontaktinformationen.properties"); // Pfad hier hinterlegen
        FileReader propertiesReader = new FileReader(propertiesFile);
        Properties props = new Properties();
        props.load(propertiesReader);
        
        KontaktInformationen kontaktperson = new KontaktInformationen();
        kontaktperson.setName(props.getProperty("k_Nachname"));
        kontaktperson.setPrename(props.getProperty("k_Vorname"));
        kontaktperson.setStreet(props.getProperty("k_Strasse"));
        kontaktperson.setStreetNumber(props.getProperty("k_Strassennummer"));
        kontaktperson.setPlz(props.getProperty("k_PLZ"));
        kontaktperson.setCity(props.getProperty("k_Stadt"));
        kontaktperson.setEmail(props.getProperty("k_Email"));
        kontaktperson.setTelefon(props.getProperty("k_Telefonnummer"));;
        
        KontaktInformationen bewohner = new KontaktInformationen();
        bewohner.setName(props.getProperty("b_Nachname"));
        bewohner.setPrename(props.getProperty("b_Vorname"));
        bewohner.setStreet(props.getProperty("b_Strasse"));
        bewohner.setStreetNumber(props.getProperty("b_Strassennummer"));
        bewohner.setPlz(props.getProperty("b_PLZ"));
        bewohner.setCity(props.getProperty("b_Stadt"));
        bewohner.setEmail(props.getProperty("b_Email"));
        bewohner.setTelefon(props.getProperty("b_Telefonnummer"));
        List<KontaktInformationen> kontaktInformationen = new ArrayList<>(Arrays.asList(kontaktperson, bewohner));
        return kontaktInformationen;
    }

    
    private String name;
    private String prename;
    private String street;
    private String streetNumber;
    private String plz;
    private String city;
    private String email;
    private String telefon;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    
    public String getStreetAndNr() {
        return street + " " + streetNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

}