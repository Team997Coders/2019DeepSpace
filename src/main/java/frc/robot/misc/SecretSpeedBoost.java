package frc.robot.misc;

import frc.robot.Robot;

public class SecretSpeedBoost {

  public static boolean checkA = false, checkB = false;
  public static boolean speedBoostActive = false;

  public static void toggleSpeedBoost() {

    if (!(checkA && checkB)) {
      return;
    }

    checkA = false;
    checkB = false;

    if (speedBoostActive) {
      Robot.driveTrain.ramp = 0.25;
      Robot.driveTrain.maxSpeed = 0.5;
    } else {
      Robot.driveTrain.ramp = 4.0;
      Robot.driveTrain.maxSpeed = 1.0;
    }

    speedBoostActive = !speedBoostActive;
  }

}