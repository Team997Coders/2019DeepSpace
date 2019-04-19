package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LockArm extends Command {

	public double position;
	
    public LockArm() {
    	requires(Robot.arm);
    }
    
    protected void initialize() {
      Robot.arm.engageBrake();
      Robot.arm.resetPID();
      position = Robot.arm.readEncoder();
      //System.out.println("---------------------\ninitted lockArm at " + position);
      //System.out.println("read arm encoder as " + Robot.arm.readEncoder());
      //System.out.println("raw arm encoder is " + Robot.arm.getRawEncoder());
      //Robot.arm.updatePID();
    }

    protected void execute() {
      Robot.arm.SetPostion(position);
      //System.out.println("Locking Arm at position " + position + "\nArm at " + Robot.arm.readEncoder());
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
      Robot.arm.SetPostion(Robot.arm.readEncoder());
      Robot.arm.releaseBrake();
    }

    protected void interrupted() {
      //System.out.println("arm lock interrupted");
      end();
    }
}

