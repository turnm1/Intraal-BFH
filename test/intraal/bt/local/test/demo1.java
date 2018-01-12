/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

/**
 *
 * @author turna
 */
public class demo1 {
    
    
    
    public static void main(String[] args) throws Exception {
        runMotion rm = new runMotion();
        runPassage rp = new runPassage();
        runLocation rl = new runLocation();
        runNightLight rnl = new runNightLight();
        
        // Start Sensor
        rm.detectMotion();
        rp.detectPassage();
        
        // Start Algo
        rl.runLocation();
        rnl.runNightLight();
    }
    
}
