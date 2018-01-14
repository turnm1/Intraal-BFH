/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.execution;

import intraal.bt.algo.uc1.ActivityCheck;
import intraal.bt.algo.uc1.BedActivity;
import intraal.bt.algo.uc1.NightLightUC1;
import intraal.bt.algo.uc1.OnePersonLokation;
import intraal.bt.sensor.room.bad.startBadSensors;
import intraal.bt.sensor.room.eingang.startEingangSensors;
import intraal.bt.sensor.room.kueche.startKücheSensors;
import intraal.bt.sensor.room.schlafzimmer.bett.startBettSensors;
import intraal.bt.sensor.room.schlafzimmer.startSchlafzimmerSensors;
import intraal.bt.sensor.room.wohnzimmer.startWohnzimmerModul;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author turna
 */
public class IntraalExecutor implements Runnable{
    
    // java -jar "C:\Users\turna\Documents\NetBeansProjects\Intraal-BT\dist\Intraal-BT.jar"
    
   
    private void startSensors(){
        startBettSensors bett = new startBettSensors();
        startBadSensors bad = new startBadSensors();
        startWohnzimmerModul wohnzimmer = new startWohnzimmerModul();
        startEingangSensors eingang = new startEingangSensors();
        startKücheSensors küche = new startKücheSensors();
        startSchlafzimmerSensors schlafzimmer = new startSchlafzimmerSensors();
        
        wohnzimmer.startWohnzimmerModul();
        eingang.startEingangModul();
        wohnzimmer.startWohnzimmerModul();
        schlafzimmer.startSchlafzimmerModul();
        küche.startKücheModul();
        bad.startBadModul();
        bett.startBettModul();
    }
    
    private void startAlgo() throws Exception{
        Thread connectionCheckerThread = new Thread(new ConnectionChecker());
        OnePersonLokation opl = new OnePersonLokation();
        NightLightUC1 nl = new NightLightUC1();
        BedActivity ba = new BedActivity();
    //    ActivityCheck ac = new ActivityCheck();
        
        connectionCheckerThread.start();
        opl.locationOfPerson();
        nl.runNightLight();
        ba.bedActivity();
        
        
    }
    
    @Override
    public void run() {
        startSensors();
        try {
            startAlgo();
        } catch (Exception ex) {
            Logger.getLogger(IntraalExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        IntraalExecutor IntraalSoftware = new IntraalExecutor();
        IntraalSoftware.run();
        System.out.println("INTRAAL Software Läuft jetzt!");
    }
}
