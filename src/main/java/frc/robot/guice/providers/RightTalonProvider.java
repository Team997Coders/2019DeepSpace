package frc.robot.guice.providers;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.google.inject.Provider;

import frc.robot.RobotMap;

public class RightTalonProvider implements Provider<TalonSRX> {
  public TalonSRX get() {
    return new TalonSRX(RobotMap.Ports.rightTalon);
  }
}