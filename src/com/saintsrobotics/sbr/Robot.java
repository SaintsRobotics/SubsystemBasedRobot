package com.saintsrobotics.sbr;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.output.PracticeMotors;
import com.saintsrobotics.sbr.system.DriveSystem;

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
        motors.update();
        drive.runTick();
    }
    
    @Override
    public void autonomousTick() {
        
    }
    
    @Override
    public void testTick() {
        
    }
}
