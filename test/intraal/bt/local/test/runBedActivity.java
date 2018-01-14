/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.algo.uc1.BedActivity;

/**
 *
 * @author turna
 */
public class runBedActivity {
    
    BedActivity ba = new BedActivity();
    
     public void runBedActivity()throws Exception {
         ba.bedActivity();
    }
    

    public static void main(String[] args) throws Exception {
        new runBedActivity().runBedActivity();
    }

}
