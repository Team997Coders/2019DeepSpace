/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Sensors;

import org.junit.Before;
import org.junit.Test;
import  static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineUnitTest {
    private double powerMotor = 0.8;
    private double noPowerMotor = -.5;
    private double normal = .1; //for double line seen
    private double straight = .35;

/*    @Test
  public void goStraightIfCenterSeen(){
        Sensors m_sensors = mock(Sensors.class);
        DriveTrain m_driveTrain = mock(DriveTrain.class);
        FollowLine m_followLine = new FollowLine(m_sensors, m_driveTrain, (long) 1000);
        
        //Assemble
        when(m_sensors.lineSensorLeft()).thenReturn(false);
        when(m_sensors.lineSensorCenter()).thenReturn(true);
        when(m_sensors.lineSensorRight()).thenReturn(false);

        //Act
        m_followLine.execute();

        //Assert
        verify(m_driveTrain, times(1)).setVolts(straight, straight);
    }

    @Test
    public void turnRightIfRightSeen() {
        Sensors m_sensors = mock(Sensors.class);
        DriveTrain m_driveTrain = mock(DriveTrain.class);
        FollowLine m_followLine = new FollowLine(m_sensors, m_driveTrain, (long) 1000);
        
        //Assemble
        when(m_sensors.lineSensorLeft()).thenReturn(false);
        when(m_sensors.lineSensorCenter()).thenReturn(false);
        when(m_sensors.lineSensorRight()).thenReturn(true);

        //Act
        m_followLine.execute();

        //Assert
        verify(m_driveTrain, times(1)).setVolts(powerMotor, noPowerMotor);
    }

    /* Can't get the timeout to work right in the test.  
     * I get a: 
     * org.mockito.exceptions.verification.WantedButNotInvoked at FollowLineUnitTest.java:82 the verify step)
     * failure.
     */
    /*
    @Test
    public void continueIfNoLineSeen() {
        Sensors m_sensors = mock(Sensors.class);
        DriveTrain m_driveTrain = mock(DriveTrain.class);
        FollowLine m_followLine = new FollowLine(m_sensors, m_driveTrain, (long) 10);
        
        //Assemble
        when(m_sensors.noLineSeen()).thenReturn(true);

        //Act
        m_followLine.execute();

        //Assert
        verify(m_driveTrain, times(1)).setVolts(straight, straight);
    }*/


    /*
    // TODO: Now implement this test!
    //@Test
    //public void itStopsWhenCloseToTarget() {
    //
    //}
    */}