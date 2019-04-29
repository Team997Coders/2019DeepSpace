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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Add your docs here.
 */
public class FollowLineGracePeriodUnitTest {
	LineDetector frontLineDetector;
	InfraredRangeFinder frontInfraredRangeFinder;
  	DriveTrain driveTrain;

	@Before
	public void initializeMocks() {
		frontLineDetector = mock(LineDetector.class);
		frontInfraredRangeFinder = mock(InfraredRangeFinder.class);
		driveTrain = mock(DriveTrain.class);

		RobotState.setRobotState(RobotState.ScoringDirectionStates.Front, 
		RobotState.ScoringArtifactStates.Hatch, 
		RobotState.ScoringDestinationStates.Rocket,
		RobotState.LiftPositionStates.Low);
	}

	@Test
	public void itGoesStraightIfNoLineSeenInitiallyFront() {
	// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder,  
			driveTrain, 
      		(long) 1000);
		
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.initialize();
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setVolts(RobotMap.Values.straight, RobotMap.Values.straight);

		//Cleanup
		followLine.close();
  }

	@Test
	public void itContinuesStraightDuringGracePeriodAndNoLineSeen() throws InterruptedException {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder,
			driveTrain, 
			(long) 10 // make grace period short
			);
		
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.initialize();
		followLine.execute();
		Thread.sleep(5);
		followLine.execute();

		//Assert
		verify(driveTrain, times(2)).setVolts(RobotMap.Values.straight, RobotMap.Values.straight);

		//Cleanup
		followLine.close();
  }

	@Test
	public void itFinishesAfterGracePeriodExpiresAndNoLineSeen() throws InterruptedException {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector,  
			frontInfraredRangeFinder,  
			driveTrain, 
      		(long) 10 // make grace period short
      	);
		
    //Assemble
    when(frontLineDetector.noLineSeen()).thenReturn(true);

		//Act
		followLine.initialize();
		followLine.execute();
		Thread.sleep(20);

		//Assert
		assertEquals(true, followLine.isFinished());

		//Cleanup
		followLine.close();
  }

	@Test
	public void itSetsBrakeWhenLineSeenDuringGracePeriod() throws InterruptedException {
		// Assemble
		FollowLine followLine = new FollowLine(
			frontLineDetector, 
			frontInfraredRangeFinder,  
			driveTrain, 
      		(long) 10 // make grace period short
      	);
		
		//Assemble
		when(frontLineDetector.leftLineSeen()).thenReturn(false);
		when(frontLineDetector.centerLineSeen()).thenReturn(false);
		when(frontLineDetector.rightLineSeen()).thenReturn(false);

		//Act
		followLine.initialize();
		followLine.execute();
		Thread.sleep(5);
		when(frontLineDetector.anyLineSeen()).thenReturn(true);
		followLine.execute();

		//Assert
		verify(driveTrain, times(1)).setBrake();

		//Cleanup
		followLine.close();
  }
}