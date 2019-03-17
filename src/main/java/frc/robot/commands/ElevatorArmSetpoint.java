package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ElevatorArmSetpoint extends Command {
  
  private boolean longWay = false, armMoving = false, elevatorMoving = false, end = false;
  private double angle, height;

  private SetElevatorHeight e = null;
  private SetArmPosition a = null;
  
  //TODO: Make height represent inches.
  //TODO: Do we need angle guards?

  /**
   * Sets the elevator and arm position in one command activity.
   * 
   * @param height
   * @param angle   In degrees with 0 being front horizontal and 180 being back horizontal.
   */
  public ElevatorArmSetpoint(double height, double angle) {
    this.height = height;
    this.angle = (RobotMap.ElevatorHeights.armBackParallel - RobotMap.ElevatorHeights.armFrontParallel) * (angle / 180);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (Math.abs(angle - Robot.arm.readEncoder()) > (RobotMap.ElevatorHeights.armBackParallel - RobotMap.ElevatorHeights.armFrontParallel) / 2) {
      //needsFlip = true;
      if ((Robot.elevator.GetPosition() < RobotMap.ElevatorHeights.elevatorSafeFlipHeight) && (height < RobotMap.ElevatorHeights.elevatorSafeFlipHeight)) {
        longWay = true;
      }
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (longWay) {
      if (e == null) {
        e = new SetElevatorHeight(RobotMap.ElevatorHeights.elevatorSafeFlipHeight, 100);
        Scheduler.getInstance().add(e);
      } else if ((Robot.elevator.GetPosition() >= RobotMap.ElevatorHeights.elevatorSafeFlipHeight - 500) && (a == null)) {
        a = new SetArmPosition(angle, 30);
        Scheduler.getInstance().add(a);
        armMoving = true;
      } else if (a != null && getArmError() < 130) {
        e.cancel();
        e.close();
        e = new SetElevatorHeight(height, 100);
        elevatorMoving = true;
      }
    } else if (height < RobotMap.ElevatorHeights.elevatorSafeFlipHeight) {
      if (a == null) {
        a = new SetArmPosition(angle, 30);
        Scheduler.getInstance().add(a);
        armMoving = true;
      }
      if (e == null) {
        e = new SetElevatorHeight(RobotMap.ElevatorHeights.elevatorSafeFlipHeight, 100);
        Scheduler.getInstance().add(e);
      }
      if (getArmError() < 130) {
        e.cancel();
        e.close();
        e = new SetElevatorHeight(height, 100);
        Scheduler.getInstance().add(e);
        elevatorMoving = true;
      }
    } else {
      if (e == null) {
        e = new SetElevatorHeight(height, 100);
        Scheduler.getInstance().add(e);
        elevatorMoving = true;
      }
      if (a == null && ((Robot.elevator.GetPosition() >= RobotMap.ElevatorHeights.elevatorSafeFlipHeight - 500) || (getArmError() < 130))) {
        a = new SetArmPosition(angle, 30);
        Scheduler.getInstance().add(a);
        armMoving = true;
      }
    }
  }

  public double getArmError() { return Math.abs(angle - Robot.arm.readEncoder()); }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if ((elevatorMoving && armMoving) && !end) {
      end = true;
    }

    if (end) {
      if (e.isCompleted() && a.isCompleted()) {
        return true;
      }
    }

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
    if (a != null) {
      a.cancel();
      a.close();
    }
    if (e != null) {
      e.cancel();
      e.close();
    }
  }
}