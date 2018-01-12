/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.algo.uc1;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author turna
 */
public class WarningTimer {
    
    Timer timer;
    public static String activityStatus = "Activity Status: NORMAL";

    // 300 = 5 min
    public WarningTimer(int seconds) {
        System.out.println("Warning Timer started");
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }
    
    public void stopWarningTimer(){
        System.out.println("Warning Timer reseted");
        timer.cancel();
    }

    class RemindTask extends TimerTask {
        public void run() {
            activityStatus = "Activity Status: WARNING, long time no activity detected!";
            System.err.println(activityStatus);
            timer.cancel(); //Terminate the timer thread
        }
    }
    
    

//    public static void main(String args[]) {
//        System.out.println("About to schedule task.");
//        new WarningTimer(60);
//        System.out.println("Task scheduled.");
//    }
}
