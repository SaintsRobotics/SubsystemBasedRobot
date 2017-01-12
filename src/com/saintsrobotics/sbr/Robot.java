package com.saintsrobotics.sbr;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.output.PracticeMotors;
import com.saintsrobotics.sbr.system.DriveSystem;
import com.saintsrobotics.sbr.util.Log;

public class Robot extends CustomIterativeRobot {
    
    private final Motors motors = new PracticeMotors();
    private final OI oi = new OI();
    
    private DriveSystem drive;
    
    @Override
    protected void onRobotCodeBoot() {
        Log.init();
        motors.init();
        oi.init();
        
        drive = new DriveSystem(motors, oi);
    }
    
    @Override
    protected void onDisable() {
        motors.stopAll();
    }
    
    @Override
    protected void onEnterOperator() {
        drive.onEnterOperator();
    }
    
    @Override
    public void operatorTick() {
        drive.runOperatorTick();
        motors.update();
    }
    
    @Override
    public void onEnterAutonomous() {
    	drive.onEnterAutonomous();
    }
    
    @Override
    public void autonomousTick() {
        drive.runAutonomousTick();
        motors.update();
    }
    
    @Override
    protected void onEnterTest() {
        drive.onEnterTest();
    }
    
    @Override
    public void testTick() {
        drive.runTestTick();
        motors.update();
    }
}
