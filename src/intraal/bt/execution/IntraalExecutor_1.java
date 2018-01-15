/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.execution;

import intraal.bt.config.connection.ConnectionChecker;
import intraal.bt.algo.uc1.ActivityCheck;
import intraal.bt.algo.uc1.BedActivity;
import intraal.bt.algo.uc1.NightLightUC1;
import intraal.bt.algo.uc1.OnePersonLokation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author turna
 */
public class IntraalExecutor_1 implements Runnable{
    
    // java -jar "C:\Users\turna\Documents\NetBeansProjects\Intraal-BT\dist\Intraal-BT.jar"
    
   
    
    private void startAlgo() throws Exception {
        Thread connectionCheckerThread = new Thread(new ConnectionChecker());
        OnePersonLokation opl = new OnePersonLokation();
     //   NightLightUC1 nl = new NightLightUC1();
    //    BedActivity ba = new BedActivity();
    //    ActivityCheck ac = new ActivityCheck();
        
     //   connectionCheckerThread.start();
       opl.locationOfPerson();
    //   nl.runNightLight();
    //    ba.bedActivity();
        
        
    }
    
    @Override
    public void run() {
        try {
            startAlgo();
        } catch (Exception ex) {
            Logger.getLogger(IntraalExecutor_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        IntraalExecutor_1 IntraalSoftware = new IntraalExecutor_1();
        IntraalSoftware.run();
        System.out.println("INTRAAL Software Läuft jetzt!");
    }
}
