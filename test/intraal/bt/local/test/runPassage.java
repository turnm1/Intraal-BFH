/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.sensor.room.bad.badPassage;
import intraal.bt.sensor.room.eingang.eingangPassage;
import intraal.bt.sensor.room.kueche.kuechePassage;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerPassage;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerPassage;

/**
 *
 * @author Turna
 */
public class runPassage {

    eingangPassage p1 = new eingangPassage();
    badPassage p2 = new badPassage();
    schlafzimmerPassage p3 = new schlafzimmerPassage();
    wohnzimmerPassage p4 = new wohnzimmerPassage();
    kuechePassage p5 = new kuechePassage();

    public void detectPassage() throws Exception {
        p1.doPassage();
        p2.doPassage();
        p3.doPassage();
        p4.doPassage();
        p5.doPassage();
    }

     public static void main(String[] args) throws Exception {
         new runPassage().detectPassage();
    }
    
}
