/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.algorithm.ActivityCheck;

/**
 *
 * @author turna
 */
public class runActivityCheck {
    
    ActivityCheck ac = new ActivityCheck();
    
     public void runA()throws Exception {
         ac.activityCheck();
    }
    

    public static void main(String[] args) throws Exception {
        new runActivityCheck().runA();
    }

}
