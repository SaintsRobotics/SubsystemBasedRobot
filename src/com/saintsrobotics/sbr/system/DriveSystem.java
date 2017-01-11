package com.saintsrobotics.sbr.system;

import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.input.OI;

public class DriveSystem extends SystemBase {
    
    public DriveSystem(Motors motors, OI oi) {
        super(motors, oi);
    }
    
    @Override
    public void runTick() {
        double forward = oi.drive.axis.leftStickY();
        double turn = oi.drive.axis.rightStickX();
        
        motors.leftDrive.set(forward + turn);
        motors.rightDrive.set(forward - turn);
    }
}
