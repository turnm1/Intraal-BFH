/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.execution;

import intraal.bt.algo.uc1.AllAlgo;
import intraal.bt.config.connection.ConnectionChecker;
import intraal.bt.sensor.room.execution.StartBadModul;
import intraal.bt.sensor.room.execution.StartEingangModul;
import intraal.bt.sensor.room.execution.StartKücheModul;
import intraal.bt.sensor.room.execution.StartBettModul;
import intraal.bt.sensor.room.execution.StartSchlafzimmerModul;
import intraal.bt.sensor.room.execution.StartWohnzimmerModul;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author turna
 */
public class IntraalExecutor implements Runnable{
    
    // java -jar "C:\Users\turna\Documents\NetBeansProjects\Intraal-BT\dist\Intraal-BT.jar"
    
   
    private void startSensors(){
        StartBettModul bett = new StartBettModul();
        StartBadModul bad = new StartBadModul();
        StartWohnzimmerModul wohnzimmer = new StartWohnzimmerModul();
        StartEingangModul eingang = new StartEingangModul();
        StartKücheModul küche = new StartKücheModul();
        StartSchlafzimmerModul schlafzimmer = new StartSchlafzimmerModul();
        
        wohnzimmer.startWohnzimmerModul();
        eingang.startEingangModul();
        schlafzimmer.startSchlafzimmerModul();
        küche.startKücheModul();
        bad.startBadModul();
        bett.startBettModul();
    }
    
    private void startAlgo() throws Exception{
        Thread connectionCheckerThread = new Thread(new ConnectionChecker());
        AllAlgo a = new AllAlgo();
        
        connectionCheckerThread.start();
        a.runIntraalAlgo();
        
        
    }
    
    @Override
    public void run() {
        try {
            startSensors();
            startAlgo();
        } catch (Exception ex) {
            Logger.getLogger(IntraalExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        System.out.println("INTRAAL Software wurde gestartet ...");
        IntraalExecutor IntraalSoftware = new IntraalExecutor();
        IntraalSoftware.run();
        
    }
}
