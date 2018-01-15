/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.execution;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.sensor.room.LoadCellSensor_1;
import intraal.bt.sensor.room.LoadCellSensor_2;
import intraal.bt.sensor.room.LoadCellSensor_3;
import intraal.bt.sensor.room.LoadCellSensor_4;

/**
 *
 * @author turna
 */
public class StartBettModul implements Runnable {
    
    ConnectionParameters cp = new ConnectionParameters();
    
    private final String tinkerforgeIP = cp.getTINKERFORGE_IP_BETT();
    private final String room = "Schlafzimmer";
    
    public void startBettModul(){
        LoadCellSensor_1 lc1 = new LoadCellSensor_1(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1(), room, "Load Cell1");
        LoadCellSensor_2 lc2 = new LoadCellSensor_2(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2(), room, "Load Cell2");
        LoadCellSensor_3 lc3 = new LoadCellSensor_3(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3(), room, "Load Cell3");
        LoadCellSensor_4 lc4 = new LoadCellSensor_4(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4(), room, "Load Cell4");

        lc1.getPersonOnBed();
        lc2.getPersonOnBed();
        lc3.getPersonOnBed();
        lc4.getPersonOnBed();
    }

    @Override
    public void run() {
        startBettModul();
    }
    
}
