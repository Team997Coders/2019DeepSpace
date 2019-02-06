package frc.robot.commands;

import frc.robot.Robot;

public class SlewCamera extends org.team997coders.spartanlib.commands.SlewCamera {
  public SlewCamera() {
    super(Robot.cameraMount, Robot.panRateProvider, Robot.tiltRateProvider, 2, 20);
  }
}