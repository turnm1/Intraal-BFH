/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.bad;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.sensor.room.AmbientLightSensor;
import intraal.bt.sensor.room.MotionSensor;
import intraal.bt.sensor.room.PassageSensor;
import intraal.bt.sensor.room.TemperaturSensor;

/**
 *
 * @author turna
 */
public class startBadSensors {
    
    ConnectionParameters cp = new ConnectionParameters();
    
    private final String tinkerforgeIP = cp.getTINKERFORGE_IP_BAD();
    private final String room = "Bad";
    
    public void startBadModul(){
        
    PassageSensor ps = new PassageSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_BAD_PASSAGE(), room);
    AmbientLightSensor als = new AmbientLightSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_BAD_AMBIENTELIGHT(), room);
    MotionSensor ms = new MotionSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_BAD_MOTION(), room);
    TemperaturSensor ts = new TemperaturSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_BAD_TEMPERATUR(), room);
    
    ps.doPassage();
    als.getLight();
    ms.doMotion();
    ts.getTemp();    
    }
    
    public static void main(String[] args) {
        startBadSensors s = new startBadSensors();
        s.startBadModul();
    }
}
