/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import frc.robot.data.RobotState;
import frc.robot.data.RobotState.ScoringArtifactStates;
import frc.robot.data.RobotState.ScoringDestinationStates;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.InfraredRangeFinder;
import frc.robot.subsystems.LineDetector;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineCloseToTargetUnitTest {
	LineDetector frontLineDetector;
	InfraredRangeFinder frontInfraredRangeFinder;
	DriveTrain driveTrain;

	@Before
	public void initializeMocks() {
		frontLineDetector = mock(LineDetector.class);
		frontInfraredRangeFinder = mock(InfraredRangeFinder.class);
		driveTrain = mock(DriveTrain.class);

		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Ball, 
		RobotState.ScoringDestinationStates.Rocket,
		RobotState.PositionStates.Low);
	}

	@Test
	public void itFinishesWhenCloseToTargetFrontBallRocket() {
	  // Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector,
			frontInfraredRangeFinder, 
			driveTrain, 
			(long) 1000);
    when(frontInfraredRangeFinder.getRawValue()).thenReturn(10);
    followLine.initialize();

		//Act

    //Assert
		assertEquals(true, followLine.isFinished());
		
		//Cleanup
		followLine.close();
  }

	@Test
	public void itDoesNotFinishWhenNotCloseToTargetFrontBallRocket() {
	// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder, 
			driveTrain, 
			(long) 1000);
		when(frontInfraredRangeFinder.getRawValue()).thenReturn(0);
    followLine.initialize();

		//Act

    //Assert
		assertEquals(false, followLine.isFinished());
		
		// cleanup
		followLine.close();
	}
}