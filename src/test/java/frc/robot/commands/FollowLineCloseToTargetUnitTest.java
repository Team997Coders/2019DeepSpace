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

import frc.robot.buttonbox.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineCloseToTargetUnitTest {
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
	public void itFinishesWhenCloseToTargetFrontBallRocket() {
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
			ButtonBox.ScoringArtifactStates.Ball);
		
		//Assemble
    when(frontInfraredRangeFinder.getRawValue()).thenReturn(0);

		//Act

    //Assert
    assertEquals(true, followLine.isFinished());
  }

	@Test
	public void itDoesNotFinisheWhenNotCloseToTargetFrontBallRocket() {
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
			ButtonBox.ScoringArtifactStates.Ball);
		
		//Assemble
    when(frontInfraredRangeFinder.getRawValue()).thenReturn(10);

		//Act

    //Assert
    assertEquals(false, followLine.isFinished());
	}
	*/
}