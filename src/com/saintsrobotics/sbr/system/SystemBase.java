package com.saintsrobotics.sbr.system;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;

abstract class SystemBase {
    
    final Motors motors;
    final OI oi;
    
    SystemBase(Motors motors, OI oi) {
        this.motors = motors;
        this.oi = oi;
    }
    
    public abstract void runOperatorTick();
    public abstract void runAutonomousTick();
}
