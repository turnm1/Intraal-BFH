/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.algo.uc1.OnePersonLokation;

/**
 *
 * @author turna
 */
public class SubData {
    
    OnePersonLokation opl = new OnePersonLokation();
    
     public void sub()throws Exception {
         opl.locationOfPerson();
    }
    

    public static void main(String[] args) throws Exception {
        new SubData().sub();
    }

}
