/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.guice.modules;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;

import frc.robot.RobotMap;
import frc.robot.guice.providers.*;

/*
 * Add your docs here.
 */
public class RobotModule extends AbstractModule {
  @Override
  protected void configure(){

    //Talon
    bind(TalonSRX.class).annotatedWith(Names.named("leftTalon")).toProvider(
      LeftTalonProvider.class).in(Singleton.class);
    bind(TalonSRX.class).annotatedWith(Names.named("rightTalon")).toProvider(
      RightTalonProvider.class).in(Singleton.class);

    //VictorSPX
    bind(VictorSPX.class).annotatedWith(Names.named("leftVictor1")).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.leftVictor1)).in(Singleton.class);
    bind(VictorSPX.class).annotatedWith(Names.named("leftVictor2")).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.leftVictor2)).in(Singleton.class);
    bind(VictorSPX.class).annotatedWith(Names.named("rightVictor1")).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.rightVictor1)).in(Singleton.class);
    bind(VictorSPX.class).annotatedWith(Names.named("rightVictor2")).toProvider(() 
      -> new VictorSPX(RobotMap.Ports.rightVictor2)).in(Singleton.class);

    //Digitigal Input
    bind(DigitalInput.class).annotatedWith(Names.named("sensorLeftInput")).toProvider(() 
      -> new DigitalInput(RobotMap.Ports.linesensorleft)).in(Singleton.class);
    bind(DigitalInput.class).annotatedWith(Names.named("sensorCenterInput")).toProvider(() 
      -> new DigitalInput(RobotMap.Ports.linesensorcenter)).in(Singleton.class);
    bind(DigitalInput.class).annotatedWith(Names.named("sensorRightInput")).toProvider(() 
      -> new DigitalInput(RobotMap.Ports.linesensorright)).in(Singleton.class);

    //SensorCollection
    bind(SensorCollection.class).annotatedWith(Names.named("leftTalonSensorCollection")).toProvider(
      LeftTalonSensorCollectionProvider.class).in(Singleton.class);
    bind(SensorCollection.class).annotatedWith(Names.named("rightTalonSensorCollection")).toProvider(
      RightTalonSensorCollectionProvider.class).in(Singleton.class);

    //AnalogInput
    bind(AnalogInput.class).annotatedWith(Names.named("ultrasonicSensorInput")).toProvider(() 
      -> new AnalogInput(RobotMap.Ports.ultrasonicsensor)).in(Singleton.class);
  }
}

