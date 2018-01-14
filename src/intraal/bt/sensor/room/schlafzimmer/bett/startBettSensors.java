/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.schlafzimmer.bett;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.sensor.room.LoadCellSensor;

/**
 *
 * @author turna
 */
public class startBettSensors {
    
    ConnectionParameters cp = new ConnectionParameters();
    
    private final String tinkerforgeIP = cp.getTINKERFORGE_IP_BETT();
    private final String room = "Schlafzimmer";
    
    public void startBettModul(){
        LoadCellSensor lc1 = new LoadCellSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_1(), room, "Load Cell1");
        LoadCellSensor lc2 = new LoadCellSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_2(), room, "Load Cell2");
        LoadCellSensor lc3 = new LoadCellSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_3(), room, "Load Cell3");
        LoadCellSensor lc4 = new LoadCellSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_SCHLAFZIMMER_LOADCELL_4(), room, "Load Cell4");

        lc1.getPersonOnBed();
        lc2.getPersonOnBed();
        lc3.getPersonOnBed();
        lc4.getPersonOnBed();
    }
}
