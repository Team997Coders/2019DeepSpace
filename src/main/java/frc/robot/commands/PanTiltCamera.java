/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PanTiltCamera extends Command {
  public PanTiltCamera() {
    // Pan/Tilt servos will be exclusively controlled by a sockets command processor.
  }

  @Override
  protected void initialize() {
  }

  /**
   * Simply forward pan/tilt commands (in otherwords, the current value
   * of the vision operator interface joystick) to the CameraVisionClient.
   * It will decide whether the servos will move and send commands back
   * for the ProcessCameraMountCommands command to actualy move servos.
   */
  @Override
  protected void execute() {
    if (Robot.cameraVisionClient != null) {
      Robot.cameraVisionClient.slew(Robot.oi.getVisionLeftXAxis(), Robot.oi.getVisionLeftYAxis());
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
