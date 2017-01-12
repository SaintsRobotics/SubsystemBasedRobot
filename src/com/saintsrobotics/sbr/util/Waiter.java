package com.saintsrobotics.sbr.util;

public class Waiter {
    
    public static Waiter forSeconds(double seconds) {
        return new Waiter((int)Math.round(seconds*1000));
    }
    
    private boolean hasStarted = false;
    private double startTime = 0;
    private double durationMillis;
    
    private Waiter(int durationMillis) {
        this.durationMillis = durationMillis;
    }
    
    public boolean untilPassed() {
        if (!hasStarted) {
            hasStarted = true;
            startTime = System.currentTimeMillis();
        }
        
        return System.currentTimeMillis() < startTime + durationMillis;
    }
    
    public void reset() {
        hasStarted = false;
        startTime = 0;
    }
}
