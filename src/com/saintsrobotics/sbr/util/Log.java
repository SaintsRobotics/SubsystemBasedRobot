package com.saintsrobotics.sbr.util;

import edu.wpi.first.wpilibj.DriverStation;

public class Log {
    
    public static boolean inUnitTesting = false;
    
    public static void init() {
//        Socket socket = new Socket();
    }
    
    public static void e(String msg) {
        if (inUnitTesting) {
            System.out.println(msg);
        } else {
            DriverStation.reportError(msg, false);
        }
    }
    
    public static void i(String msg) {
        if (inUnitTesting) {
            System.out.println(msg);
        } else {
            DriverStation.reportError(msg, false);
        }
    }
}