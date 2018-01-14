/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.sensor.room.AmbientLightSensor;

/**
 *
 * @author Turna
 */
public class runLight {

    AmbientLightSensor al = new AmbientLightSensor("yiJ", "Bad", "AmbientLight");
//     AmbientLightSensor al = new AmbientLightSensor("yiJ", "Bad", "AmbientLight");
//      AmbientLightSensor al = new AmbientLightSensor("yiJ", "Bad", "AmbientLight");
//       AmbientLightSensor al = new AmbientLightSensor("yiJ", "Bad", "AmbientLight");
//        AmbientLightSensor al = new AmbientLightSensor("yiJ", "Bad", "AmbientLight");
    
    
    public void detectLight() throws Exception {
        al.getLight();
    }

    public static void main(String[] args) throws Exception {
        new runLight().detectLight();
    }

}
