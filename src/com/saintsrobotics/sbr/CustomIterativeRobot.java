package com.saintsrobotics.sbr;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public abstract class CustomIterativeRobot extends RobotBase {
    
    protected abstract void onRobotCodeBoot();
    protected abstract void onDisable();
    
    protected void onEnterOperator() { }
    protected void onEnterAutonomous() { }
    protected void onEnterTest() { }
    
    protected abstract void operatorTick();
    protected abstract void autonomousTick();
    protected abstract void testTick();
    
    @Override
    public void startCompetition() {
        UsageReporting.report(FRCNetworkCommunicationsLibrary.tResourceType.kResourceType_Framework,
                              FRCNetworkCommunicationsLibrary.tInstances.kFramework_Sample);
    
        onRobotCodeBoot();
    
        // Tell the DS that the robot is ready to be enabled
        FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationObserveUserProgramStarting();
    
        // first and one-time initialization
        LiveWindow.setEnabled(false);
    
        //noinspection InfiniteLoopStatement
        while (true) {
            if (isDisabled()) {
                m_ds.InDisabled(true);
                onDisable();
                m_ds.InDisabled(false);
                while (isDisabled()) {
                    Timer.delay(0.01);
                }
            } else if (isAutonomous()) {
                m_ds.InAutonomous(true);
                onEnterAutonomous();
                while (isAutonomous() && !isDisabled()) {
                    autonomousTick();
                }
                m_ds.InAutonomous(false);
            } else if (isTest()) {
                LiveWindow.setEnabled(true);
                m_ds.InTest(true);
                onEnterTest();
                while (isTest() && isEnabled()) {
                    testTick();
                }
                m_ds.InTest(false);
                LiveWindow.setEnabled(false);
            } else {
                m_ds.InOperatorControl(true);
                onEnterOperator();
                while (isOperatorControl() && !isDisabled()) {
                    operatorTick();
                }
                m_ds.InOperatorControl(false);
            }
        }
    }
}
