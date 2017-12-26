/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.sensor.room.bad.badAmbientLight;
import intraal.bt.sensor.room.eingang.eingangAmbientLight;
import intraal.bt.sensor.room.kueche.kuecheAmbientLight;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerAmbientLight;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerAmbientLight;

/**
 *
 * @author Turna
 */
public class runLight {

    eingangAmbientLight a1 = new eingangAmbientLight();
    badAmbientLight a2 = new badAmbientLight();
    schlafzimmerAmbientLight a3 = new schlafzimmerAmbientLight();
    wohnzimmerAmbientLight a4 = new wohnzimmerAmbientLight();
    kuecheAmbientLight a5 = new kuecheAmbientLight();

    public void detectLight() throws Exception {
        a1.getLight();
        a2.getLight();
        a3.getLight();
        a4.getLight();
        a5.getLight();
    }

    public static void main(String[] args) throws Exception {
        new runLight().detectLight();
    }

}
