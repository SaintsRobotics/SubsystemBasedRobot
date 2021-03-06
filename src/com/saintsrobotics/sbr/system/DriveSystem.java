package com.saintsrobotics.sbr.system;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.input.Sensors;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.util.Waiter;

public class DriveSystem extends SystemBase {
    
    public DriveSystem(Sensors sensors, Motors motors, OI oi) {
        super(sensors, motors, oi);
        setBehaviors(
                new DriveSystem.ArcadeDrive(),
                new DriveSystem.AutonomousMoveForward(),
                new DriveSystem.TestEachMotor());
    }
    
    private class ArcadeDrive extends SystemBehavior<OperatorMode> {
    
        @Override
        public void runModeTick() {
            double forward = -oi.drive.axis.leftStickY();
            double turn = oi.drive.axis.rightStickX();
    
            setLeftMotors(forward + turn);
            setRightMotors(forward - turn);
        }
    }
    
    private class TankDrive extends SystemBehavior<OperatorMode> {
    
        @Override
        public void runModeTick() {
            double left = -oi.drive.axis.leftStickY();
            double right = -oi.drive.axis.rightStickY();
        
            setLeftMotors(left);
            setRightMotors(right);
        }
    }
    
    private class SensorExample extends SystemBehavior<AutonomousMode> {
    
        @Override
        public void runModeTick() {
            if (sensors.limitSwitches.exampleSwitch.get()) {
                setLeftMotors(0);
                setRightMotors(0);
            } else {
                setLeftMotors(0.3);
                setRightMotors(0.3);
            }
        }
    }
    
    private class AutonomousMoveForward extends SystemBehavior<AutonomousMode> {
        
        private Waiter waiter2s = Waiter.forSeconds(2);
    
        @Override
        public void onEnterMode() {
            waiter2s.reset();
        }
    
        @Override
        public void runModeTick() {
            if (waiter2s.untilPassed()) {
                setLeftMotors(0.3);
                setRightMotors(0.3);
            } else {
                setLeftMotors(0);
                setRightMotors(0);
            }
        }
    }
    
    private class TestEachMotor extends SystemBehavior<TestMode> {
        
        private Waiter[] waiters = new Waiter[6];
    
        @Override
        public void onEnterMode() {
            for (int i = 0; i < waiters.length; i++) {
                waiters[i] = Waiter.forSeconds(0.2);
            }
        }
        
        double speed = 0.2;
    
        @Override
        public void runModeTick() {
            if (waiters[0].untilPassed()) {
                motors.leftDrive1.set(speed);
            } else if (waiters[1].untilPassed()) {
                motors.leftDrive2.set(speed);
            } else if (waiters[2].untilPassed()) {
                motors.leftDrive3.set(speed);
            } else if (waiters[3].untilPassed()) {
                motors.rightDrive1.set(speed);
            } else if (waiters[4].untilPassed()) {
                motors.rightDrive2.set(speed);
            } else if (waiters[5].untilPassed()) {
                motors.rightDrive3.set(speed);
            } else {
                setLeftMotors(0);
                setRightMotors(0);
                for (Waiter waiter : waiters) {
                    waiter.reset();
                }
            }
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
