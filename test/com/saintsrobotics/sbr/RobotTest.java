package com.saintsrobotics.sbr;

import com.saintsrobotics.sbr.input.OI;
import com.saintsrobotics.sbr.output.Motors;
import com.saintsrobotics.sbr.util.Constants;
import com.saintsrobotics.sbr.util.Log;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NetworkTable.class, DriverStation.class, Motors.Motor.class, OI.Input.class})
@SuppressStaticInitializationFor("edu.wpi.first.wpilibj.DriverStation")
public class RobotTest {
    
    private Robot robot;
    private Talon leftDrive1 = mock(Talon.class);
    private Talon leftDrive2 = mock(Talon.class);
    private Talon leftDrive3 = mock(Talon.class);
    private Talon rightDrive1 = mock(Talon.class);
    private Talon rightDrive2 = mock(Talon.class);
    private Talon rightDrive3 = mock(Talon.class);
    private Joystick driveStick = mock(Joystick.class);
    private Joystick operatorStick = mock(Joystick.class);
    
    @Before
    public void setupRobot() throws Exception {
        Log.inUnitTesting = true;
        Constants.MOTOR_RAMPING = 0.1;
        
        PowerMockito.mockStatic(NetworkTable.class);
        ITable mockSubTable = mock(ITable.class);
        NetworkTable mockNetworkTable = mock(NetworkTable.class);
        when(mockNetworkTable.getSubTable("~STATUS~")).thenReturn(mockSubTable);
        PowerMockito.when(NetworkTable.class, "getTable", "LiveWindow").thenReturn(mockNetworkTable);
        
        DriverStation ds = mock(DriverStation.class);
        when(ds.isEnabled()).thenReturn(true);
        
        Field dsInstance = DriverStation.class.getDeclaredField("instance");
        dsInstance.setAccessible(true);
        dsInstance.set(null, ds);
        
        PowerMockito.whenNew(Talon.class).withArguments(1).thenReturn(leftDrive1);
        PowerMockito.whenNew(Talon.class).withArguments(2).thenReturn(leftDrive2);
        PowerMockito.whenNew(Talon.class).withArguments(3).thenReturn(leftDrive3);
        PowerMockito.whenNew(Talon.class).withArguments(4).thenReturn(rightDrive1);
        PowerMockito.whenNew(Talon.class).withArguments(5).thenReturn(rightDrive2);
        PowerMockito.whenNew(Talon.class).withArguments(6).thenReturn(rightDrive3);
        PowerMockito.whenNew(Joystick.class).withArguments(0).thenReturn(driveStick);
        PowerMockito.whenNew(Joystick.class).withArguments(1).thenReturn(operatorStick);
    
        when(driveStick.getRawAxis(anyInt())).thenReturn(-0.5);
        
        robot = new Robot();
        robot.onRobotCodeBoot();
    }
    
    @Test
    public void testDrive() {
        robot.onEnterOperator();
        for (int i = 0; i < 100; i++) {
            robot.operatorTick();
        }
        verify(leftDrive1, times(100)).set(anyDouble());
        verify(leftDrive2, times(100)).set(anyDouble());
        verify(leftDrive3, times(100)).set(anyDouble());
        verify(rightDrive1, times(100)).set(anyDouble());
        verify(rightDrive2, times(100)).set(anyDouble());
        verify(rightDrive3, times(100)).set(anyDouble());
        
        verify(leftDrive1, atLeastOnce()).set(0.0);
        verify(leftDrive2, atLeastOnce()).set(0.0);
        verify(leftDrive3, atLeastOnce()).set(0.0);
        verify(rightDrive1, atLeastOnce()).set(1.0);
        verify(rightDrive2, atLeastOnce()).set(1.0);
        verify(rightDrive3, atLeastOnce()).set(1.0);
    }
    
    @Test
    public void testAutonomous() throws InterruptedException {
        robot.onEnterAutonomous();
        for (int i = 0; i < 200; i++) {
            robot.autonomousTick();
            Thread.sleep(20); // to test the timing aspect
        }
        verify(leftDrive1, atLeastOnce()).set(0.3);
        verify(leftDrive2, atLeastOnce()).set(0.3);
        verify(leftDrive3, atLeastOnce()).set(0.3);
        verify(rightDrive1, atLeastOnce()).set(0.3);
        verify(rightDrive2, atLeastOnce()).set(0.3);
        verify(rightDrive3, atLeastOnce()).set(0.3);
        
        verify(leftDrive1, atLeastOnce()).set(0.0);
        verify(leftDrive2, atLeastOnce()).set(0.0);
        verify(leftDrive3, atLeastOnce()).set(0.0);
        verify(rightDrive1, atLeastOnce()).set(0.0);
        verify(rightDrive2, atLeastOnce()).set(0.0);
        verify(rightDrive3, atLeastOnce()).set(0.0);
    }
    
    @Test
    public void testDriveTest() throws InterruptedException {
        robot.onEnterTest();
    
        for (int i = 0; i < 7; i++) {
            robot.testTick();
            Thread.sleep(210);
        }
        
        verify(leftDrive1, times(5)).set(0.2);
        verify(leftDrive2, times(4)).set(0.2);
        verify(leftDrive3, times(3)).set(0.2);
        verify(rightDrive1, times(2)).set(0.2);
        verify(rightDrive2, times(1)).set(0.2);
        verify(rightDrive3, times(0)).set(0.2);
        verify(leftDrive1).set(0.1);
        verify(leftDrive2).set(0.1);
        verify(leftDrive3).set(0.1);
        verify(rightDrive1).set(0.1);
        verify(rightDrive2).set(0.1);
        verify(rightDrive3).set(0.1);
        
        robot.testTick();
    
        verify(leftDrive1, times(1)).set(0);
        verify(leftDrive2, times(3)).set(0);
        verify(leftDrive3, times(4)).set(0);
        verify(rightDrive1, times(5)).set(0);
        verify(rightDrive2, times(6)).set(0);
        verify(rightDrive3, times(7)).set(0);
    }
}
