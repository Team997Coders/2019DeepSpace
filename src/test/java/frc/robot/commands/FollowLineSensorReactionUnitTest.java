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
import frc.robot.data.RobotState;

import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineSensorReactionUnitTest {
	LineDetector frontLineDetector;
	InfraredRangeFinder frontInfraredRangeFinder;
  DriveTrain driveTrain;

	@Before
	public void initializeMocks() {
		frontLineDetector = mock(LineDetector.class);
		frontInfraredRangeFinder = mock(InfraredRangeFinder.class);
    driveTrain = mock(DriveTrain.class);
	}

	@Test
  public void goStraightIfCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder, 
			driveTrain, 
      (long) 1000);
		
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(true);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);

		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Hatch, 
		RobotState.ScoringDestinationStates.Rocket, null);
    
    //Act
    followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.straight, RobotMap.Values.straight);

		//Cleanup
		followLine.close();
  }

	@Test
	public void turnRightIfRightSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder, 
			driveTrain, 
      (long) 1000);
			
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(true);
		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Hatch, 
		RobotState.ScoringDestinationStates.Rocket, null);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.noPowerMotor, RobotMap.Values.powerMotor);

		//Cleanup
		followLine.close();
	}


	@Test
	public void turnLeftIfLeftSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector,
			frontInfraredRangeFinder, 
			driveTrain, 
      (long) 1000);
			
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(true);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);
		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Hatch, 
		RobotState.ScoringDestinationStates.Rocket, null);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.powerMotor, RobotMap.Values.noPowerMotor);

		// Cleanup
		followLine.close();
	}

	
	@Test
	public void turnEasyLeftIfLeftCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder, 
			driveTrain, 
      (long) 1000);
			
		//Assemble
		when(frontLineDetector.leftCenterLineSeen()).thenReturn(true);
		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Hatch, 
		RobotState.ScoringDestinationStates.Rocket, null);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.powerMotor, RobotMap.Values.normal);

		//Cleanup
		followLine.close();
	}

	@Test
	public void turnEasyRightIfRightCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder, 
			driveTrain, 
      (long) 1000);
			
		//Assemble
		when(frontLineDetector.rightCenterLineSeen()).thenReturn(true);
		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Hatch, 
		RobotState.ScoringDestinationStates.Rocket, null);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.normal, RobotMap.Values.powerMotor);

		//Cleanup
		followLine.close();
	}
}
