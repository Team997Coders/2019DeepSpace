package frc.robot.guice.modules;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;
import frc.robot.guice.annotations.DriveTrain.*;
import frc.robot.guice.providers.DriveTrain.*;

/*
 * DriveTrain hardware and default command bindings
 */
public class DriveTrain extends AbstractModule {
  @Override
  protected void configure(){

    //Talon
    bind(TalonSRX.class).annotatedWith(LeftTalon.class).toProvider(
      LeftTalonProvider.class).in(Singleton.class);
    bind(TalonSRX.class).annotatedWith(RightTalon.class).toProvider(
      RightTalonProvider.class).in(Singleton.class);

    //VictorSPX
    bind(VictorSPX.class).annotatedWith(LeftVictor1.class).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.leftVictor1)).in(Singleton.class);
    bind(VictorSPX.class).annotatedWith(LeftVictor2.class).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.leftVictor2)).in(Singleton.class);
    bind(VictorSPX.class).annotatedWith(RightVictor1.class).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.rightVictor1)).in(Singleton.class);
    bind(VictorSPX.class).annotatedWith(RightVictor2.class).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.rightVictor2)).in(Singleton.class);

    //SensorCollection
    bind(SensorCollection.class).annotatedWith(LeftTalonSensorCollection.class).toProvider(
      LeftTalonSensorCollectionProvider.class).in(Singleton.class);
    bind(SensorCollection.class).annotatedWith(RightTalonSensorCollection.class).toProvider(
      RightTalonSensorCollectionProvider.class).in(Singleton.class);

    // Default DriveTrain style
    // This is a really slick way to change out the drive train style without
    // touching the DriveTrain class
    bind(Command.class).annotatedWith(DriveTrainStyle.class).to(ArcadeDrive.class);
  }
}
