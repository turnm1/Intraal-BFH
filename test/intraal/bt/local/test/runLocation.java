/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.algo.uc1.NightLightUC1;
import intraal.bt.algo.uc1.OnePersonLokation;

/**
 *
 * @author turna
 */
public class runLocation {
    
    OnePersonLokation opl = new OnePersonLokation();
    
    
     public void runLocation()throws Exception {
         opl.locationOfPerson();
    }
    

    public static void main(String[] args) throws Exception {
        new runLocation().runLocation();
    }

}
