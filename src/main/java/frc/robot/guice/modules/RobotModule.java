/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.guice.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;

/*
 * Simulate a static class (since Java does not support static outer classes)
 * that gives back an injector for the robot program
 */
public final class RobotModule {
  /**
   * A private constructor makes it so this class cannot be instantiated
   */
  private RobotModule() {}

  /**
   * Create a guice injector that wires up all dependencies
   * for the robot program.
   * 
   * @return  The master injector
   */
  public static Injector createInjector() {
    return Guice.createInjector(new LineFollowing(), new DriveTrain());
  }
}
