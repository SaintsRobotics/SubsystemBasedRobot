package com.saintsrobotics.sbr.system;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.util.Waiter;

public class DriveSystem extends SystemBase {
    
    public DriveSystem(Motors motors, OI oi) {
        super(motors, oi);
    }
    
    @Override
    public void runOperatorTick() {
        double forward = oi.drive.axis.leftStickY();
        double turn = oi.drive.axis.rightStickX();
        
        motors.leftDrive.set(forward + turn);
        motors.rightDrive.set(forward - turn);
    }
    
    private Waiter waiter = Waiter.forSeconds(2);
    
    @Override
    public void runAutonomousTick() {
        if (waiter.untilPassed()) {
            motors.leftDrive.set(0.5);
            motors.rightDrive.set(0.5);
        } else {
            motors.leftDrive.set(0);
            motors.rightDrive.set(0);
        }
    }
}
