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
	LineDetector frontLineDetector;
	LineDetector backLineDetector;
	InfraredRangeFinder frontInfraredRangeFinder;
	InfraredRangeFinder backInfraredRangeFinder;
  DriveTrain driveTrain;
  ButtonBox buttonBox;

	@Before
	public void initializeMocks() {
		frontLineDetector = mock(LineDetector.class);
		backLineDetector = mock(LineDetector.class);
		frontInfraredRangeFinder = mock(InfraredRangeFinder.class);
		backInfraredRangeFinder = mock(InfraredRangeFinder.class);
    driveTrain = mock(DriveTrain.class);
    buttonBox = mock(ButtonBox.class);
	}

	@Test
  public void goStraightIfCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			//backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
		
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(true);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

    //Act
    followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.straight, RobotMap.Values.straight);
  }

	@Test
	public void goStraightIfCenterSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
		
		//Assemble
		when(backLineDetector.leftLineSeen()).thenReturn(false);
		when(backLineDetector.centerLineSeen()).thenReturn(true);
		when(backLineDetector.rightLineSeen()).thenReturn(false);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

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
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(true);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.noPowerMotor, RobotMap.Values.powerMotor);
	}

	@Test
	public void turnRightIfRightSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			//backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000,
      buttonBox);
			
		//Assemble
		when(backLineDetector.leftLineSeen()).thenReturn(false);
		when(backLineDetector.centerLineSeen()).thenReturn(false);
		when(backLineDetector.rightLineSeen()).thenReturn(true);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.noPowerMotor, -RobotMap.Values.powerMotor);
	}

	@Test
	public void turnLeftIfLeftSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(true);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.powerMotor, RobotMap.Values.noPowerMotor);
	}

	@Test
	public void turnLeftIfLeftSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(backLineDetector.leftLineSeen()).thenReturn(true);
		when(backLineDetector.centerLineSeen()).thenReturn(false);
		when(backLineDetector.rightLineSeen()).thenReturn(false);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.powerMotor, -RobotMap.Values.noPowerMotor);
	}

	@Test
	public void turnEasyLeftIfLeftCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(frontLineDetector.leftCenterLineSeen()).thenReturn(true);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.powerMotor, RobotMap.Values.normal);
	}

	@Test
	public void turnEasyRightIfRightCenterSeenFront() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(frontLineDetector.rightCenterLineSeen()).thenReturn(true);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Front);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.normal, RobotMap.Values.powerMotor);
	}

	@Test
	public void turnEasyLeftIfLeftCenterSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//	backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(backLineDetector.leftCenterLineSeen()).thenReturn(true);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.powerMotor, -RobotMap.Values.normal);
	}

	@Test
	public void turnEasyRightIfRightCenterSeenBack() {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
		//backLineDetector, 
			frontInfraredRangeFinder, 
			backInfraredRangeFinder, 
			driveTrain, 
      (long) 1000, 
      buttonBox);
			
		//Assemble
		when(backLineDetector.rightCenterLineSeen()).thenReturn(true);
    when(buttonBox.getScoringDirectionState()).thenReturn(ButtonBox.ScoringDirectionStates.Back);
    when(buttonBox.getScoringDestinationState()).thenReturn(ButtonBox.ScoringDestinationStates.Rocket);
    when(buttonBox.getScoringArtifactState()).thenReturn(ButtonBox.ScoringArtifactStates.Hatch);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(-RobotMap.Values.normal, -RobotMap.Values.powerMotor);
	}
}
