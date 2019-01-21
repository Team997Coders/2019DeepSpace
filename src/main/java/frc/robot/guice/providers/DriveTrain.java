package frc.robot.guice.providers;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.google.inject.Inject;
import com.google.inject.Provider;

import frc.robot.RobotMap;
import frc.robot.guice.annotations.DriveTrain.LeftTalon;
import frc.robot.guice.annotations.DriveTrain.RightTalon;

/**
 * Hardware providers for the DriveTrain that could not easily
 * be provided via anonymous functions in the bindings.
 * 
 * @see https://github.com/google/guice/wiki/ProviderBindings
 */
public class DriveTrain {

  public static class LeftTalonProvider implements Provider<TalonSRX> {
    public TalonSRX get() {
      return new TalonSRX(RobotMap.Ports.leftTalon);
    }
  }

  public static class RightTalonProvider implements Provider<TalonSRX> {
    public TalonSRX get() {
      return new TalonSRX(RobotMap.Ports.rightTalon);
    }
  }

  public static class LeftTalonSensorCollectionProvider implements Provider<SensorCollection> {
    private final TalonSRX leftTalon;
  
    @Inject
    public LeftTalonSensorCollectionProvider(@LeftTalon TalonSRX leftTalon) {
      this.leftTalon = leftTalon;
    }
  
    public SensorCollection get() {
      return new SensorCollection(leftTalon);
    }
  }

  public static class RightTalonSensorCollectionProvider implements Provider<SensorCollection> {
    private final TalonSRX rightTalon;

    @Inject
    public RightTalonSensorCollectionProvider(@RightTalon TalonSRX rightTalon) {
      this.rightTalon = rightTalon;
    }

    public SensorCollection get() {
      return new SensorCollection(rightTalon);
    }
  }
}