/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.remote;

import intraal.bt.sensor.room.bad.badAmbientLight;
import intraal.bt.sensor.room.bad.badMotion;
import intraal.bt.sensor.room.bad.badPassage;
import intraal.bt.sensor.room.bad.badTemperatur;
import intraal.bt.sensor.room.eingang.eingangAmbientLight;
import intraal.bt.sensor.room.eingang.eingangMotion;
import intraal.bt.sensor.room.eingang.eingangPassage;
import intraal.bt.sensor.room.eingang.eingangTemperatur;
import intraal.bt.sensor.room.kueche.kuecheAmbientLight;
import intraal.bt.sensor.room.kueche.kuecheMotion;
import intraal.bt.sensor.room.kueche.kuechePassage;
import intraal.bt.sensor.room.kueche.kuecheTemperatur;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerAmbientLight;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerMotion;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerPassage;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerTemperatur;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerAmbientLight;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerMotion;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerPassage;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerTemperatur;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Turna
 */
public class IntraalBT implements Runnable{
    
    static Thread myThread;

    eingangAmbientLight a1 = new eingangAmbientLight();
    badAmbientLight a2 = new badAmbientLight();
    schlafzimmerAmbientLight a3 = new schlafzimmerAmbientLight();
    wohnzimmerAmbientLight a4 = new wohnzimmerAmbientLight();
    kuecheAmbientLight a5 = new kuecheAmbientLight();
    
    eingangTemperatur b1 = new eingangTemperatur();
    badTemperatur b2 = new badTemperatur();
    schlafzimmerTemperatur b3 = new schlafzimmerTemperatur();
    wohnzimmerTemperatur b4 = new wohnzimmerTemperatur();
    kuecheTemperatur b5 = new kuecheTemperatur();
    
    eingangMotion m1 = new eingangMotion();
    badMotion m2 = new badMotion();
    schlafzimmerMotion m3 = new schlafzimmerMotion();
    wohnzimmerMotion m4 = new wohnzimmerMotion();
    kuecheMotion m5 = new kuecheMotion();
    
    eingangPassage p1 = new eingangPassage();
    badPassage p2 = new badPassage();
    schlafzimmerPassage p3 = new schlafzimmerPassage();
    wohnzimmerPassage p4 = new wohnzimmerPassage();
    kuechePassage p5 = new kuechePassage();
    
     public void detectMotion() throws Exception{
        m1.doMotion();
        m2.doMotion();
        m3.doMotion();
        m4.doMotion();
        m5.doMotion();
     }
      
     public void detectTemperatur() throws Exception{
        b1.getTemp();
        b2.getTemp();
        b3.getTemp();
        b4.getTemp();
        b5.getTemp();
     }

     public void detectLight() throws Exception{
        a1.getLight();
        a2.getLight();
        a3.getLight();
        a4.getLight();
        a5.getLight();
     }
     
     public void detectPassage() throws Exception{
        p1.doPassage();
        p2.doPassage();
        p3.doPassage();
        p4.doPassage();
        p5.doPassage();
     }
    
     public void detectAll() throws Exception{
         detectPassage();
         detectLight();
         detectMotion();
         detectTemperatur();
     }
     
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(IntraalBT.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Hello from a thread!");
        }
    }
     
    /**
     * @param args the command line arguments
     */
      public static void main(String[] args) throws Exception {
        myThread = new Thread(new IntraalBT());
        myThread.run();
    }
    
}
