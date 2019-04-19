/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.RobotMap;

public class FlipArmChain extends Command {

  private Command elevatorCom = null, armCom = null, elevatorTwoCom = null;
  private FlipStep step;

  public FlipArmChain() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    step = FlipStep.elevatorOne;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    switch (step) {
      case elevatorOne:
        if (elevatorCom == null) {
          elevatorCom = new ElevatorToArmHeight();
          Scheduler.getInstance().add(elevatorCom);
        } else if (elevatorCom.isCompleted()) {
          step = FlipStep.arm;
        }
        break;
      case arm:
        if (armCom == null) {
          armCom = new SetArmPosition(RobotMap.Values.armFrontParallel, 10);
          Scheduler.getInstance().add(armCom);
        } else if (armCom.isCompleted()) {
          step = FlipStep.elevatorTwo;
        }
        break;
      case elevatorTwo:
        if (elevatorTwoCom == null) {
          elevatorTwoCom = new SetElevatorHeight(0, 100);
          Scheduler.getInstance().add(elevatorTwoCom);
        } else if (elevatorTwoCom.isCompleted()) {
          step = FlipStep.done;
        }
        break;
      default:
        break;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return step == FlipStep.done;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if (step == FlipStep.done) {
      System.out.println("Finished Flip");
    } else {
      System.out.println("Did not finish");
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    System.out.println("Flip was interrupted at: "+(String) step.name());
  }

  private enum FlipStep {
    elevatorOne(), arm(), elevatorTwo(), done();
  }
}
