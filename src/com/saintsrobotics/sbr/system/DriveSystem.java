package com.saintsrobotics.sbr.system;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.util.Waiter;

public class DriveSystem extends SystemBase {
    
    public DriveSystem(Motors motors, OI oi) {
        super(motors, oi);
        waiter2s = Waiter.forSeconds(2);
    }
    
    @Override
    public void runOperatorTick() {
        double forward = -oi.drive.axis.leftStickY();
        double turn = oi.drive.axis.rightStickX();
        
        setLeftMotors(forward + turn);
        setRightMotors(forward - turn);
    }
    
    private Waiter waiter2s;
    
    @Override
    public void runAutonomousTick() {
        if (waiter2s.untilPassed()) {
        	setLeftMotors(0.3);
        	setRightMotors(0.3);
        } else {
        	setLeftMotors(0);
            setRightMotors(0);
        }
    }
    
    private void setLeftMotors(double val) {
    	motors.leftDrive1.set(val);
        motors.leftDrive2.set(val);
        motors.leftDrive3.set(val);
    }
    
    private void setRightMotors(double val) {
    	motors.rightDrive1.set(val);
        motors.rightDrive2.set(val);
        motors.rightDrive3.set(val);
    }
}
