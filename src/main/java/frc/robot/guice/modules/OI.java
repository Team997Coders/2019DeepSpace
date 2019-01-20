package frc.robot.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.guice.annotations.OI.*;
import frc.robot.guice.providers.OI.*;

/**
 * Define bindings for operator interface hardware
 */
public class OI extends AbstractModule{
  @Override
  protected void configure(){
    bind(Joystick.class).annotatedWith(Gamepad1.class).toProvider(Gamepad1Provider.class).in(Singleton.class);
  }
}