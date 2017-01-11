package com.saintsrobotics.sbr.output;

import edu.wpi.first.wpilibj.Talon;

import java.util.ArrayList;
import java.util.List;

import static com.saintsrobotics.sbr.util.Constants.MOTOR_RAMPING;

public abstract class Motors {
    
    private List<Motor> motors = new ArrayList<>();
    
    public final Motor leftDrive;
    public final Motor rightDrive;
    
    protected Motors(int leftDrivePin, int rightDrivePin) {
        leftDrive = new Motor(leftDrivePin);
        rightDrive = new Motor(rightDrivePin);
        motors.add(leftDrive);
        motors.add(rightDrive);
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
            speedController.stopMotor();
            setpoint = 0;
            current = 0;
        }
    
        void update() {
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
