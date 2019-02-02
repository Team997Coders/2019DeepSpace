/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LineFollowing;

import org.junit.Before;
import org.junit.Test;
import  static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineUnitTest {
    /*

    @Test
    public void goStraightIfCenterSeen(){
        LineFollowing m_lineFollowing = mock(LineFollowing.class);
        DriveTrain m_driveTrain = mock(DriveTrain.class);
        FollowLine m_followLine = new FollowLine(m_lineFollowing, m_driveTrain);
        
        //Assemble
        when(m_lineFollowing.centerLineSeen()).thenReturn(true);

        //Act
        m_followLine.execute();

        //Assert
        verify(m_driveTrain, times(1)).setVolts(.25, .25);
    }

    // TODO: Implement
    // @Test
    //public void turnRightIfRightSeen() {
    //  
    //}

    // TODO: Now implement this test!
    //@Test
    //public void itStopsWhenCloseToTarget() {
    //
    //}*/
}
