package com.saintsrobotics.sbr.input;

import edu.wpi.first.wpilibj.DigitalInput;

import java.util.ArrayList;
import java.util.List;

public class LimitSwitches {
    
    private List<LimitSwitch> switches = new ArrayList<>();
    
    public final LimitSwitch exampleSwitch;
    
    public LimitSwitches(int exampleSwitchPin) {
        exampleSwitch = new LimitSwitch(exampleSwitchPin);
        switches.add(exampleSwitch);
    }
    
    public void init() {
        switches.forEach(LimitSwitch::init);
    }
    
    public static class LimitSwitch {
        
        private final int pin;
        private DigitalInput switchInput;
    
        public LimitSwitch(int pin) {
            this.pin = pin;
        }
        
        private void init() {
            switchInput = new DigitalInput(pin);
        }
    }
}
