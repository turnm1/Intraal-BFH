/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.sensor.room.schlafzimmer.bett.SchlafzimmerBett1;
import intraal.bt.sensor.room.schlafzimmer.bett.SchlafzimmerBett2;
import intraal.bt.sensor.room.schlafzimmer.bett.SchlafzimmerBett3;
import intraal.bt.sensor.room.schlafzimmer.bett.SchlafzimmerBett4;


/**
 *
 * @author Turna
 */
public class runBett {

    SchlafzimmerBett1 sb1 = new SchlafzimmerBett1();
    SchlafzimmerBett2 sb2 = new SchlafzimmerBett2();
    SchlafzimmerBett3 sb3 = new SchlafzimmerBett3();
    SchlafzimmerBett4 sb4 = new SchlafzimmerBett4();

    public void detectionBed() throws Exception {
        sb1.getPersonOnBed();
        sb2.getPersonOnBed();
        sb3.getPersonOnBed();
        sb4.getPersonOnBed();
    }

    public static void main(String[] args) throws Exception {
        new runBett().detectionBed();
    }

}
