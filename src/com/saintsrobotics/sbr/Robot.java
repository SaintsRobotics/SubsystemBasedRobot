package com.saintsrobotics.sbr;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.output.PracticeMotors;
import com.saintsrobotics.sbr.system.DriveSystem;

import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends CustomIterativeRobot {
    
    private final Motors motors = new PracticeMotors();
    private final OI oi = new OI();
    
    private DriveSystem drive;
    
    @Override
    protected void onRobotCodeBoot() {
        motors.init();
        oi.init();
        
        drive = new DriveSystem(motors, oi);
    }
    
    @Override
    protected void onDisable() {
        motors.stopAll();
    }
    
    @Override
    public void operatorTick() {
        drive.runOperatorTick();
        motors.update();
    }
    
    @Override
    public void onEnterAutonomous() {
    	drive = new DriveSystem(motors, oi);
    }
    
    @Override
    public void autonomousTick() {
        drive.runAutonomousTick();
        motors.update();
    }
    
    @Override
    public void testTick() {
        
    }
}
