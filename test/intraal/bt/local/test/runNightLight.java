/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.algo.uc1.NightLightUC1;

/**
 *
 * @author turna
 */
public class runNightLight {
    
    NightLightUC1 nl = new NightLightUC1();
    
     public void runNightLight()throws Exception {
         nl.nightLight();
    }
    

    public static void main(String[] args) throws Exception {
        new runNightLight().runNightLight();
    }

}
