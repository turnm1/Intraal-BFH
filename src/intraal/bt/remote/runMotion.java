/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.remote;

import intraal.bt.sensor.room.bad.badMotion;
import intraal.bt.sensor.room.eingang.eingangMotion;
import intraal.bt.sensor.room.kueche.kuecheMotion;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerMotion;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerMotion;

/**
 *
 * @author Turna
 */
public class runMotion {

    eingangMotion m1 = new eingangMotion();
    badMotion m2 = new badMotion();
    schlafzimmerMotion m3 = new schlafzimmerMotion();
    wohnzimmerMotion m4 = new wohnzimmerMotion();
    kuecheMotion m5 = new kuecheMotion();

    public void detectMotion() throws Exception {
       // m1.doMotion();
      //  m2.doMotion();
     //   m3.doMotion();
        m4.doMotion();
       // m5.doMotion();
    }

    public static void main(String[] args) throws Exception {
        new runMotion().detectMotion();
    }

}
