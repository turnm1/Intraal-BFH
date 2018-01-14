/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intraal.bt.sensor.room.eingang;

import intraal.bt.config.connection.ConnectionParameters;
import intraal.bt.sensor.room.AmbientLightSensor;
import intraal.bt.sensor.room.MotionSensor;
import intraal.bt.sensor.room.PassageSensor;
import intraal.bt.sensor.room.TemperaturSensor;

/**
 *
 * @author turna
 */
public class startEingangSensors {
    
    ConnectionParameters cp = new ConnectionParameters();
    
    private final String tinkerforgeIP = cp.getTINKERFORGE_IP_EINGANG();
    private final String room = "Eingang";
    
    public void startEingangModul(){
        
    PassageSensor ps = new PassageSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_EINGANG_PASSAGE(), room);
    AmbientLightSensor als = new AmbientLightSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_EINGANG_AMBIENTELIGHT(), room);
    MotionSensor ms = new MotionSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_EINGANG_MOTION(), room);
    TemperaturSensor ts = new TemperaturSensor(tinkerforgeIP, cp.getTINKERFORGE_SENSOR_UID_EINGANG_TEMPERATUR(), room);
    
    ps.doPassage();
    als.getLight();
    ms.doMotion();
    ts.getTemp();    
    }
}
