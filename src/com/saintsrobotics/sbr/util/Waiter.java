package com.saintsrobotics.sbr.util;

public class Waiter {
    
    public static Waiter forSeconds(double seconds) {
        return new Waiter(seconds*1000);
    }
    
    private boolean hasStarted = false;
    private double startTime = 0;
    private double durationMillis;
    
    public Waiter(double durationMillis) {
        this.durationMillis = durationMillis;
    }
    
    public boolean untilPassed() {
        if (!hasStarted) {
            hasStarted = true;
            startTime = System.currentTimeMillis();
        }
        
        return System.currentTimeMillis() < startTime + durationMillis;
    }
}
