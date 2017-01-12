package com.saintsrobotics.sbr.system;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.input.Sensors;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.util.Log;

abstract class SystemBase {
    
    final Sensors sensors;
    final Motors motors;
    final OI oi;
    private SystemBehavior<OperatorMode> operator;
    private SystemBehavior<AutonomousMode> autonomous;
    private SystemBehavior<TestMode> test;
    
    SystemBase(Sensors sensors, Motors motors, OI oi) {
        this.sensors = sensors;
        this.motors = motors;
        this.oi = oi;
    }
    
    void setBehaviors(
            SystemBehavior<OperatorMode> operator,
            SystemBehavior<AutonomousMode> autonomous,
            SystemBehavior<TestMode> test) {
        if (operator == null) {
            Log.e(this.getClass().getSimpleName() + " operator behavior not defined");
        }
        if (autonomous == null) {
            Log.e(this.getClass().getSimpleName() + " autonomous behavior not defined");
        }
        if (test == null) {
            Log.e(this.getClass().getSimpleName() + " test behavior not defined");
        }
            
        this.operator = operator;
        this.autonomous = autonomous;
        this.test = test;
    }
    
    public void onEnterOperator() { onEnter(operator); }
    public void onEnterAutonomous() { onEnter(autonomous); }
    public void onEnterTest() { onEnter(test); }
    
    public void runOperatorTick() { runTick(operator); }
    public void runAutonomousTick() { runTick(autonomous); }
    public void runTestTick() { runTick(test); }
    
    private void onEnter(SystemBehavior behavior) {
        if (behavior != null) {
            behavior.onEnterMode();
        }
    }
    
    private void runTick(SystemBehavior behavior) {
        if (behavior != null) {
            behavior.runModeTick();
        }
    }
    
    @SuppressWarnings("unused")
    static abstract class SystemBehavior<T extends Mode> {
        public void onEnterMode() { }
        public abstract void runModeTick();
    }
    
    static class Mode {}
    static class OperatorMode extends Mode {}
    static class AutonomousMode extends Mode {}
    static class TestMode extends Mode {}
}
