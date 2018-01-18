/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.system.execution;

import intraal.bt.config.connection.ConnectionChecker;

/**
 *
 * @author turna
 */
public class TinkerforgeModulStatusCheckExecutor {
    
    public static void main(String[] args) {
        Thread connectionCheckerThread = new Thread(new ConnectionChecker());
        connectionCheckerThread.start();
        System.out.println("Tinkerforge Module werden getestet ...");
    }
    
}
