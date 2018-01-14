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
        runBed rb = new runBed();
        runLocation rl = new runLocation();
        runNightLight rnl = new runNightLight();
        runBedActivity rba = new runBedActivity();
        
        
        // Start Sensor
        rm.detectMotion();
        rp.detectPassage();
        rb.detectionBed();
        
        // Start Algo
        rl.runLocation();
        rnl.runNightLight();
      //  rba.runBedActivity();
    }
    
    
    
}
