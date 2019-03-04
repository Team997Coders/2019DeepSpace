/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.vision.SelectedTarget;
import frc.robot.vision.TargetNotLockedException;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Utils;
import frc.robot.RobotMap;

/**
 * (note using non-english ascii characters will generate a warning in the tests)
 * Hunter, work your magic...<p>
 * This command will start when the driver pushes the
 * start driving button on the vision HUD.<p>
 * This maybe should be named AutoDriveToTargetUnderVisionControl...because
 * we will probably have a command group called AutoDriveToTarget that combines
 * this command with LineFollowing.<p>
 * I'll keep going...probably another called ScoreNow which moves manipulators
 * and auto drives.
 */
public class AutoDriveToTarget extends Command {
  private final DriveTrain driveTrain;

  private double distSetpoint;
	private double minError = 3;
	public Timer timer = new Timer();
	private double lastTime = 0;
	private double deltaT = 0;
	private double speed = 0.25;
	private double initYaw = -999;
	private double Ktheta = 0.02;
	private static double integral = 0;
	private static double previous_error = 0;
	private double targetAngle;

  public AutoDriveToTarget() {
    this(Robot.driveTrain);
  }

  public AutoDriveToTarget(DriveTrain driveTrain) {
    this.driveTrain = driveTrain;
    requires(driveTrain);
  }
   
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetEncoders();
		Robot.driveTrain.setBrake();
    	initYaw = Robot.driveTrain.getGyroAngle();
    	previous_error = this.piderror();
    	timer.reset();
    	timer.start();
		lastTime = 0;
		
		// for this initial run I only want to grab the data once from the camera.  Later we will
		// want to grab it like once every 100ms (5 iterations)
		try {
			SelectedTarget selectedTarget = Robot.cameraControlStateMachine.getSelectedTarget();
			distSetpoint = (selectedTarget.rangeInInches- 24) * (RobotMap.Values.protobotTickPerFoot/12) ;
			targetAngle = selectedTarget.cameraAngleInDegrees - 90; // 90 degree angle is straight ahead.
			System.out.println("Auto Drive To Target:");
			System.out.println("     Distance setpoint " + distSetpoint);
			System.out.println("     Target angle " + targetAngle);
			System.out.println("     Target lock? " + selectedTarget.active);

			/*
			  selectedTarget contains: 
			  rangeInInches
			  cameraAngleInDegrees - camermount pan angle relative to robot, -90 to 90, with 0 being center
			  angleToTargetInDegrees - robot's angle to target, from target's POV, -90 to 90, with 0 being perpenticular to target
			*/
		  } catch (TargetNotLockedException e) {
			System.out.println(e.getStackTrace());
		  }
    }
	
	/*
    // current algorithm assumes that we are starting
    // from a stop
    private double linearAccel(double input) {
    	double Klin = 0.8;
    	double deltaT = timer.get() - lastTime;
    	lastTime = timer.get();
    	
    	double Volts = lastVoltage + Klin * (deltaT);
    	if (Volts > input) {
    		Volts = input;
    	}
    	lastVoltage = Volts;
    	return Volts;
	}
	*/

    
    public double pFactor() {
		double m_timestep = 0.02;
    	// Calculate full PID
    	// pfactor = (P × error) + (I × ∑error) + (D × δerrorδt)
    	double m_error = this.piderror();
    	// Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
    	integral += (m_error * m_timestep);
    	// Derivative is change in error over time
    	double m_derivative = (m_error - previous_error) / m_timestep;
		previous_error = m_error;
		
		double m_calculated_drive_power = (RobotMap.Values.driveDistanceP * m_error) + 
        	(RobotMap.Values.driveDistanceI * integral) + 
			(RobotMap.Values.driveDistanceD * m_derivative);

		SmartDashboard.putNumber("autodtd/Calculated P error ", (m_error * RobotMap.Values.driveDistanceP));
		SmartDashboard.putNumber("autodtd/Calculated I error ", (integral* RobotMap.Values.driveDistanceI));
		SmartDashboard.putNumber("autodtd/Calculated D error ", (m_derivative * RobotMap.Values.driveDistanceD));
		SmartDashboard.putNumber("autodtd/Calculated PID power ", m_calculated_drive_power);
		
		return m_calculated_drive_power;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double pfactor = speed * Utils.clamp(this.pFactor(), -1, 1);
    	//double pfactor2 = linearAccel(pfactor);
		double deltaTheta = Robot.driveTrain.getGyroAngle() - initYaw;
		
    	deltaT = timer.get() - lastTime;
    	lastTime = timer.get();

    	// calculate yaw correction
    	double yawcorrect = deltaTheta * Ktheta;
    	
    	// set the output voltage
    	Robot.driveTrain.setVolts(-(pfactor + yawcorrect), -(pfactor - yawcorrect));
    	//Robot.driveTrain.SetVoltssetVolts(-pfactor, -pfactor); //without yaw correction, accel

    	// Debug information to be placed on the smart dashboard.
		SmartDashboard.putNumber("autodtd/Distance to Target", distSetpoint);
		SmartDashboard.putNumber("autodtd/Target Angle", targetAngle);
    	SmartDashboard.putNumber("autodtd/Encoder Distance", this.encoderDistance());
    	SmartDashboard.putNumber("autodtd/Distance Error", piderror());
    	SmartDashboard.putNumber("autodtd/K-P factor", pfactor);
    	//SmartDashboard.putNumber("autodtd/K-P factor Accel", pfactor2);
    	SmartDashboard.putNumber("autodtd/deltaT", deltaT);
    	SmartDashboard.putNumber("autodtd/Theta Correction", yawcorrect);
    	SmartDashboard.putBoolean("autodtd/On Target", onTarget());
    	SmartDashboard.putNumber("autodtd/NavX Heading", Robot.driveTrain.getGyroAngle());
		SmartDashboard.putNumber("autodtd/Init Yaw", initYaw);
		interrupted();
    }

    private double piderror() {
    	// shouldn't we average this out between both of the encoders?
    	return distSetpoint - this.encoderDistance();
    }
    
    private double encoderDistance() {
    	return (Robot.driveTrain.leftEncoderTicks() + Robot.driveTrain.rightEncoderTicks()) / 2;
    }
    
    private boolean onTarget() {
    	return piderror() < minError;
	}
	
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.driveTrain.leftEncoderVelocity() <= 0 && 
    			Robot.driveTrain.rightEncoderVelocity() <= 0 &&
    			onTarget()) {
    		System.out.println("(PDTD-ISFINISHED) PDTD ended with isFinished!");
    		 return onTarget();
    	} else {
    		/*if (Robot.collector.getAvgLeftVoltage() > RobotMap.Values.autoIRthreshold ||
    			Robot.collector.getAvgRightVoltage() > RobotMap.Values.autoIRthreshold) {
    			return true;
    		} else {
    			return false;
    		}*/
    		return false;
    	}
    	
    }
    
    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("at end.  Encoder Distance: " + this.encoderDistance());
    	System.out.println("Auto Drive End");
    	timer.stop();
    	Robot.driveTrain.setVolts(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    	System.out.println("(ADTD-INTERRUPTED) I got interrupted!! D:");
    }
  }
  

  