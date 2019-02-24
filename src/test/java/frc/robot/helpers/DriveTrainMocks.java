package frc.robot.helpers;

import static org.mockito.Mockito.mock;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import frc.robot.misc.GearBox;

public class DriveTrainMocks {
  static {
    System.loadLibrary("ntcorejni");
  }

  public GearBox leftBoxMock = mock(GearBox.class);
  public GearBox rightBoxMock = mock(GearBox.class);
  public TalonSRX leftTalonMock = mock(TalonSRX.class);
  public TalonSRX rightTalonMock = mock(TalonSRX.class);
  public VictorSPX leftVictor1Mock = mock(VictorSPX.class);
  public VictorSPX leftVictor2Mock = mock(VictorSPX.class);
  public VictorSPX rightVictor1Mock = mock(VictorSPX.class);
  public VictorSPX rightVictor2Mock = mock(VictorSPX.class);
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