/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.local.test;

import intraal.bt.config.connection.ConnectionTest_orginal;

/**
 *
 * @author turna
 */
public class runTinkerforgeSensors {
    
    public static void main(String[] args) throws Exception {
        ConnectionTest_orginal test = new ConnectionTest_orginal();
        test.connectionTestWithFeedback();
    }
    
}
