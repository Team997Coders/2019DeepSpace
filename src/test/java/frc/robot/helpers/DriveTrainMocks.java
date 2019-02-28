package frc.robot.helpers;

import static org.mockito.Mockito.mock;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import frc.robot.misc.GearBox;

public class DriveTrainMocks {
  static {
    System.loadLibrary("ntcorejni");
  }

  public GearBox leftBoxMock = mock(GearBox.class);
  public GearBox rightBoxMock = mock(GearBox.class);
  public WPI_TalonSRX leftTalonMock = mock(WPI_TalonSRX.class);
  public WPI_TalonSRX rightTalonMock = mock(WPI_TalonSRX.class);
  public WPI_VictorSPX leftVictor1Mock = mock(WPI_VictorSPX.class);
  public WPI_VictorSPX leftVictor2Mock = mock(WPI_VictorSPX.class);
  public WPI_VictorSPX rightVictor1Mock = mock(WPI_VictorSPX.class);
  public WPI_VictorSPX rightVictor2Mock = mock(WPI_VictorSPX.class);
  public AHRS gyroMock = mock(AHRS.class);
  public NetworkTable smartDashboardMock = mock(NetworkTable.class);

  public DriveTrainMocks() {
    leftBoxMock.talon = leftTalonMock;
    leftBoxMock.victor1 = leftVictor1Mock;
    leftBoxMock.victor2 = leftVictor2Mock;
    rightBoxMock.talon = rightTalonMock;
    rightBoxMock.victor1 = rightVictor1Mock;
    rightBoxMock.victor2 = rightVictor2Mock;
  }
} 