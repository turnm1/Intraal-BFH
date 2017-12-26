/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.sensor.room.bad.badTemperatur;
import intraal.bt.sensor.room.eingang.eingangTemperatur;
import intraal.bt.sensor.room.kueche.kuecheTemperatur;
import intraal.bt.sensor.room.schlafzimmer.schlafzimmerTemperatur;
import intraal.bt.sensor.room.wohnzimmer.wohnzimmerTemperatur;

/**
 *
 * @author Turna
 */
public class runTemperatur {

    eingangTemperatur b1 = new eingangTemperatur();
    badTemperatur b2 = new badTemperatur();
    schlafzimmerTemperatur b3 = new schlafzimmerTemperatur();
    wohnzimmerTemperatur b4 = new wohnzimmerTemperatur();
    kuecheTemperatur b5 = new kuecheTemperatur();

    public void detectTemperatur() throws Exception {
        b1.getTemp();
        b2.getTemp();
        b3.getTemp();
        b4.getTemp();
        b5.getTemp();
    }

    public static void main(String[] args) throws Exception {
        new runTemperatur().detectTemperatur();
    }

}
