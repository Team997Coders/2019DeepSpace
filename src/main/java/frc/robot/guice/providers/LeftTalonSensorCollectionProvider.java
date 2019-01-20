package frc.robot.guice.providers;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class LeftTalonSensorCollectionProvider implements Provider<SensorCollection> {
  private TalonSRX leftTalon;

  @Inject
  public LeftTalonSensorCollectionProvider(@Named("leftTalon") TalonSRX leftTalon) {
    this.leftTalon = leftTalon;
  }

  public SensorCollection get() {
    return new SensorCollection(leftTalon);
  }
}