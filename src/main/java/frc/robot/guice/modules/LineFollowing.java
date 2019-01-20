package frc.robot.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

import frc.robot.RobotMap;
import frc.robot.guice.annotations.LineFollowing.SensorCenterInput;
import frc.robot.guice.annotations.LineFollowing.SensorLeftInput;
import frc.robot.guice.annotations.LineFollowing.SensorRightInput;
import frc.robot.guice.annotations.LineFollowing.UltrasonicSensorInput;

/*
 * Google guice module for LineFollowing subsystem.
 */
public class LineFollowing extends AbstractModule {
  @Override
  protected void configure(){

    //Digital Input
    bind(DigitalInput.class).annotatedWith(SensorLeftInput.class).toProvider(() 
      -> new DigitalInput(RobotMap.Ports.linesensorleft)).in(Singleton.class);
    bind(DigitalInput.class).annotatedWith(SensorCenterInput.class).toProvider(() 
      -> new DigitalInput(RobotMap.Ports.linesensorcenter)).in(Singleton.class);
    bind(DigitalInput.class).annotatedWith(SensorRightInput.class).toProvider(() 
      -> new DigitalInput(RobotMap.Ports.linesensorright)).in(Singleton.class);

    //AnalogInput
    bind(AnalogInput.class).annotatedWith(UltrasonicSensorInput.class).toProvider(() 
      -> new AnalogInput(RobotMap.Ports.ultrasonicsensor)).in(Singleton.class);
  }
}
