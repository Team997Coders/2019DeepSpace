/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subsystems.DriveTrain;

import org.junit.Before;
import org.junit.Test;
import  static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
// Almost ready...
public class FollowLineIntegrationTest {/*

  private DigitalInput m_center;
  private DigitalInput m_left;
  private DigitalInput m_right;
  private LineFollowing m_lineFollowing;


  private DriveTrain m_driveTrain;
  private FollowLine m_followLine;

  @Before
    public void initMocks() {
        m_left = mock(DigitalInput.class);
        m_center = mock(DigitalInput.class);
        m_right = mock(DigitalInput.class);
        m_lineFollowing = new LineFollowing(m_left, m_center, m_right);

        m_driveTrain = new DriveTrain();
        m_followLine = new FollowLine(m_lineFollowing, m_driveTrain);
    }


    @Test
    public void goStraightIfCenterSeen(){
      
        
        //Assemble
        when(m_center.get()).thenReturn(true);

        //Act

        
        m_followLine.execute();



        //Assert
        
        verify(m_driveTrain, times(1)).setVolts(.25, .25);

    }


  */}
