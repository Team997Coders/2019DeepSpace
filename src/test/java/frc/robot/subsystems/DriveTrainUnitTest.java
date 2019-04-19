package frc.robot.subsystems;

import frc.robot.helpers.DriveTrainMocks;
import frc.robot.subsystems.DriveTrain;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class DriveTrainUnitTest {
  // This is needed to load the ntcore jni since we have a NetworkTable dependency.
  // Note that was are not actually calling into network tables in the test, but
  // the jni has to at least be loaded for the test to run. Also note that 
  // java.test.config must be set to point to the build/tmp/jniExtractDir directory
  // in order to find the jnis.

  @Test
  public void itSetsPIDValuesCorrectly() {
    // Assemble
    double p = 1;
    double i = 0.1;
    double d = 0.01;
    DriveTrainMocks driveTrainMocks = new DriveTrainMocks();
    DriveTrain driveTrain = new DriveTrain(driveTrainMocks.leftBoxMock, driveTrainMocks.rightBoxMock, driveTrainMocks.gyroMock, driveTrainMocks.smartDashboardMock);

    // Act
    driveTrain.setPIDValues(p, i, d);

    // Assert
    verify(driveTrainMocks.leftTalonMock, times(1)).config_kP(0, p, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).config_kP(0, p, 0);
    verify(driveTrainMocks.leftTalonMock, times(1)).config_kI(0, i, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).config_kI(0, i, 0);
    verify(driveTrainMocks.leftTalonMock, times(1)).config_kD(0, d, 0);
    verify(driveTrainMocks.rightTalonMock, times(1)).config_kD(0, d, 0);

    //Cleanup
    driveTrain.close();
  }
}