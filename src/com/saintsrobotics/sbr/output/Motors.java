package com.saintsrobotics.sbr.output;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Talon;

import java.util.ArrayList;
import java.util.List;

import static com.saintsrobotics.sbr.util.Constants.MOTOR_RAMPING;

public abstract class Motors {
    
    private List<Motor> motors = new ArrayList<>();
    
    public final Motor leftDrive1;
    public final Motor leftDrive2;
    public final Motor leftDrive3;
    public final Motor rightDrive1;
    public final Motor rightDrive2;
    public final Motor rightDrive3;
    
    protected Motors(int leftDrivePin1, int leftDrivePin2, int leftDrivePin3,
    				 int rightDrivePin1, int rightDrivePin2, int rightDrivePin3) {
        leftDrive1 = new Motor(leftDrivePin1);
        leftDrive2 = new Motor(leftDrivePin2);
        leftDrive3 = new Motor(leftDrivePin3);
        rightDrive1 = new Motor(rightDrivePin1);
        rightDrive2 = new Motor(rightDrivePin2);
        rightDrive3 = new Motor(rightDrivePin3);
        motors.add(leftDrive1);
        motors.add(leftDrive2);
        motors.add(leftDrive3);
        motors.add(rightDrive1);
        motors.add(rightDrive2);
        motors.add(rightDrive3);
    }
    
    public void init() {
        motors.forEach(Motor::init);
    }
    
    public void stopAll() {
        motors.forEach(Motor::stop);
    }
    
    public void update() {
        motors.forEach(Motor::update);
    }
    
    public static class Motor {
        
        private final int pin;
        private Talon speedController;
    
        Motor(int pin) {
            this.pin = pin;
        }
        
        private void init() {
            speedController = new Talon(pin);
        }
        
        private double setpoint = 0;
        private double current = 0;
        
        public void set(double speed) {
            setpoint = speed;
        }
    
        void stop() {
            //speedController.stopMotor();
            setpoint = 0;
            current = 0;
        }
    
        void update() {
        	DriverStation.reportError(setpoint + " " + current, false);
            if (Math.abs(setpoint - current) < MOTOR_RAMPING) {
                current = setpoint;
            } else if (setpoint > current) {
                current += MOTOR_RAMPING;
            } else if (setpoint < current) {
                current -= MOTOR_RAMPING;
            }
            speedController.set(current);
        }
    }
}
