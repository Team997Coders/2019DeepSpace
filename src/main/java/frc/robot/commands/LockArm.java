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
      position = Robot.arm.readEncoder();
      //Robot.arm.updatePID();
    }

    protected void execute() {
      Robot.arm.SetPostion(position);
      //System.out.println("Locking Arm at position " + position);
    }

    protected boolean isFinished() {
    	return false;
    }

    protected void end() {
<<<<<<< HEAD
      Robot.arm.SetPostion(Robot.arm.readEncoder());
      Robot.arm.releaseBrake();
=======
      //Robot.arm.SetPostion(Robot.arm.readEncoder());
      //Robot.arm.releaseBrake();
>>>>>>> 1ee790430ce328940005cf310b0f123314d722ad
    }

    protected void interrupted() {
      //System.out.println("arm lock interrupted");
      end();
    }
}

