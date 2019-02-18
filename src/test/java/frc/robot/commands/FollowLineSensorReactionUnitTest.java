/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.InfraredRangeFinder;
import frc.robot.subsystems.LineDetector;

import org.junit.Before;
import org.junit.Test;

import frc.robot.RobotMap;
import frc.robot.buttonbox.*;

import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineSensorReactionUnitTest {
/*
	LineDetector frontLineDetector;
	LineDetector backLineDetector;
	InfraredRangeFinder frontInfraredRangeFinder;
	InfraredRangeFinder backInfraredRangeFinder;
	DriveTrain driveTrain;

	@Before
	public void initializeMocks() {
		frontLineDetector = mock(LineDetector.class);
		backLineDetector = mock(LineDetector.class);
		frontInfraredRangeFinder = mock(InfraredRangeFinder.class);
		backInfraredRangeFinder = mock(InfraredRangeFinder.class);
		driveTrain = mock(DriveTrain.class);
	}

	@Test
  public void goStraightIfCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Front, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
		
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(true);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.straight, RobotMap.Values.straight);
  }

	@Test
	public void goStraightIfCenterSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Back, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
		
		//Assemble
		when(backLineDetector.leftLineSeen()).thenReturn(false);
		when(backLineDetector.centerLineSeen()).thenReturn(true);
		when(backLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.straight, -RobotMap.Values.straight);
	}

	@Test
	public void turnRightIfRightSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Front, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.powerMotor, RobotMap.Values.noPowerMotor);
	}

	@Test
	public void turnRightIfRightSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Back, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(backLineDetector.leftLineSeen()).thenReturn(false);
		when(backLineDetector.centerLineSeen()).thenReturn(false);
		when(backLineDetector.rightLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.powerMotor, -RobotMap.Values.noPowerMotor);
	}

	@Test
	public void turnLeftIfLeftSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Front, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(true);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.noPowerMotor, RobotMap.Values.powerMotor);
	}

	@Test
	public void turnLeftIfLeftSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Back, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(backLineDetector.leftLineSeen()).thenReturn(true);
		when(backLineDetector.centerLineSeen()).thenReturn(false);
		when(backLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.noPowerMotor, -RobotMap.Values.powerMotor);
	}

	@Test
	public void turnEasyLeftIfLeftCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Front, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(frontLineDetector.leftCenterLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.normal, RobotMap.Values.powerMotor);
	}

	@Test
	public void turnEasyRightIfRightCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Front, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(frontLineDetector.rightCenterLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.powerMotor, RobotMap.Values.normal);
	}

	@Test
	public void turnEasyLeftIfLeftCenterSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Back, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(backLineDetector.leftCenterLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.normal, -RobotMap.Values.powerMotor);
	}

	@Test
	public void turnEasyRightIfRightCenterSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
			(long) 1000, 
			ButtonBox.ScoringDirectionStates.Back, 
			ButtonBox.ScoringDestinationStates.Rocket, 
			ButtonBox.ScoringArtifactStates.Hatch);
			
		//Assemble
		when(backLineDetector.rightCenterLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.powerMotor, -RobotMap.Values.normal);
	}
	*/
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
    }
*/

    
    // TODO: Now implement this test!
    //@Test
    //public void itStopsWhenCloseToTarget() {
    //
    //}
    