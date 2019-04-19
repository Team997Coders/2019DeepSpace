package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;

import org.junit.Test;

import frc.robot.RobotMap;
import frc.robot.helpers.DriveTrainMocks;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class DriveToDistanceIntegrationTest {
  @Test
  public void itInitializesDriveTrainPIDFromRobotMap() {
    // Assemble
    DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    DriveTrain driveTrain = new DriveTrain(driveTrainMocks.leftBoxMock, driveTrainMocks.rightBoxMock, driveTrainMocks.gyroMock, driveTrainMocks.smartDashboardMock);
    DriveToDistance driveToDistance = new DriveToDistance(driveTrain, 0, 0, 0);

    // Act
    driveToDistance.initialize();

    // Assert
    verify(driveTrainMocks.leftTalonMock, times(1)).config_kP(0, RobotMap.Values.driveDistanceP, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).config_kP(0, RobotMap.Values.driveDistanceP, 0);
    verify(driveTrainMocks.leftTalonMock, times(1)).config_kI(0, RobotMap.Values.driveDistanceI, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).config_kI(0, RobotMap.Values.driveDistanceI, 0);
    verify(driveTrainMocks.leftTalonMock, times(1)).config_kD(0, RobotMap.Values.driveDistanceD, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).config_kD(0, RobotMap.Values.driveDistanceD, 0);

    // Cleanup
    driveToDistance.close();
  }

  @Test
  public void itSetsDriveTrainPositionToError() {
    // Assemble
    double leftTarget = 10;
    double rightTarget = 100;
    DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    DriveTrain driveTrain = new DriveTrain(driveTrainMocks.leftBoxMock, driveTrainMocks.rightBoxMock, driveTrainMocks.gyroMock, driveTrainMocks.smartDashboardMock);
    DriveToDistance driveToDistance = new DriveToDistance(driveTrain, 0, leftTarget, rightTarget);
    when(driveTrainMocks.leftTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)leftTarget);
    when(driveTrainMocks.rightTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)rightTarget);

    // Act
    driveToDistance.execute();

    // Assert
    verify(driveTrainMocks.leftTalonMock, times(1)).set(ControlMode.Position, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).set(ControlMode.Position, 0);

    // Cleanup
    driveToDistance.close();
  }

  @Test
  public void itFinishesWhenWithinMarginOfError() {
    // Assemble
    double leftTarget = 10;
    double rightTarget = 100;
    double errorMargin = 5;
    DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    DriveTrain driveTrain = new DriveTrain(driveTrainMocks.leftBoxMock, driveTrainMocks.rightBoxMock, driveTrainMocks.gyroMock, driveTrainMocks.smartDashboardMock);
    DriveToDistance driveToDistance = new DriveToDistance(driveTrain, errorMargin, leftTarget, rightTarget);
    when(driveTrainMocks.leftTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)leftTarget - (int)errorMargin + 1);
    when(driveTrainMocks.rightTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)rightTarget - (int)errorMargin + 1);

    // Assert
    assertEquals(true, driveToDistance.isFinished());

    // Cleanup
    driveToDistance.close();
  }

  @Test
  public void itDoesNotFinishWhenRightTargetNotWithinMarginOfError() {
    // Assemble
    double leftTarget = 10;
    double rightTarget = 100;
    double errorMargin = 5;
    DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    DriveTrain driveTrain = new DriveTrain(driveTrainMocks.leftBoxMock, driveTrainMocks.rightBoxMock, driveTrainMocks.gyroMock, driveTrainMocks.smartDashboardMock);
    DriveToDistance driveToDistance = new DriveToDistance(driveTrain, errorMargin, leftTarget, rightTarget);
    when(driveTrainMocks.leftTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)leftTarget - (int)errorMargin + 1);
    when(driveTrainMocks.rightTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)rightTarget - (int)errorMargin);

    // Assert
    assertEquals(false, driveToDistance.isFinished());

    // Cleanup
    driveToDistance.close();
  }

  @Test
  public void itDoesNotFinishWhenLeftTargetNotWithinMarginOfError() {
    // Assemble
    double leftTarget = 10;
    double rightTarget = 100;
    double errorMargin = 5;
    DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    DriveTrain driveTrain = new DriveTrain(driveTrainMocks.leftBoxMock, driveTrainMocks.rightBoxMock, driveTrainMocks.gyroMock, driveTrainMocks.smartDashboardMock);
    DriveToDistance driveToDistance = new DriveToDistance(driveTrain, errorMargin, leftTarget, rightTarget);
    when(driveTrainMocks.leftTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)leftTarget - (int)errorMargin);
    when(driveTrainMocks.rightTalonMock.getSelectedSensorPosition(anyInt())).thenReturn((int)rightTarget - (int)errorMargin + 1);

    // Assert
    assertEquals(false, driveToDistance.isFinished());

    // Cleanup
    driveToDistance.close();
  }
}
