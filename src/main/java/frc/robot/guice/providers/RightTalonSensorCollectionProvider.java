package frc.robot.guice.providers;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class RightTalonSensorCollectionProvider implements Provider<SensorCollection> {
  private TalonSRX rightTalon;

  @Inject
  public RightTalonSensorCollectionProvider(@Named("rightTalon") TalonSRX rightTalon) {
    this.rightTalon = rightTalon;
  }

  public SensorCollection get() {
    return new SensorCollection(rightTalon);
  }
}