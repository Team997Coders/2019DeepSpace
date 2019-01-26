/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;

public class AutoCenter extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoCenter() {
    addSequential(new DriveToDistance(10));
    addSequential(new DriveToAngle(90));
  }
}
