package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;

import frc.robot.misc.GearBox;
import frc.robot.subsystems.DriveTrain;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class DriveTrainUnitTest {
  // This is needed to load the ntcore jni since we have a NetworkTable dependency.
  // Note that was are not actually calling into network tables in the test, but
  // the jni has to at least be loaded for the test to run. Also note that 
  // java.test.config must be set to point to the build/tmp/jniExtractDir directory
  // in order to find the jnis.
  static {
		System.loadLibrary("ntcorejni");
  }

  @Test
  public void itSetsPIDValuesCorrectly() {
    // Assemble
    double p = 1;
    double i = 0.1;
    double d = 0.01;
    GearBox leftBoxMock = mock(GearBox.class);
    GearBox rightBoxMock = mock(GearBox.class);
    TalonSRX leftTalonMock = mock(TalonSRX.class);
    TalonSRX rightTalonMock = mock(TalonSRX.class);
    VictorSPX leftVictor1Mock = mock(VictorSPX.class);
    VictorSPX leftVictor2Mock = mock(VictorSPX.class);
    VictorSPX rightVictor1Mock = mock(VictorSPX.class);
    VictorSPX rightVictor2Mock = mock(VictorSPX.class);
    leftBoxMock.talon = leftTalonMock;
    leftBoxMock.victor1 = leftVictor1Mock;
    leftBoxMock.victor2 = leftVictor2Mock;
    rightBoxMock.talon = rightTalonMock;
    rightBoxMock.victor1 = rightVictor1Mock;
    rightBoxMock.victor2 = rightVictor2Mock;
    AHRS gyroMock = mock(AHRS.class);
    NetworkTable smartDashboardMock = mock(NetworkTable.class);
    DriveTrain driveTrain = new DriveTrain(leftBoxMock, rightBoxMock, gyroMock, smartDashboardMock);

    // Act
    driveTrain.setPIDValues(p, i, d);

    // Assert
    verify(leftTalonMock, times(1)).config_kP(0, p, 0);
    verify(rightTalonMock, times(1)).config_kP(0, p, 0);
    verify(leftTalonMock, times(1)).config_kI(0, i, 0);
    verify(rightTalonMock, times(1)).config_kI(0, i, 0);
    verify(leftTalonMock, times(1)).config_kD(0, d, 0);
    verify(rightTalonMock, times(1)).config_kD(0, d, 0);
  }
}