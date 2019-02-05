/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This command group is the default command that should run
 * in order to service the camera pan/tilt servos.
 */
public class DefaultPanTiltCommandGroup extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DefaultPanTiltCommandGroup() {
    addParallel(new PanTiltCamera());
    try {
      addParallel(new ProcessCameraMountCommands());
    } catch (IOException e) {
      System.out.println(String.format("Cannot process default command for CameraMount. Pan/tilt will not work. Error=%s", e.toString()));
    }
  }
}
