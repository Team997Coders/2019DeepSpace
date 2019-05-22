/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import frc.robot.MotionProfile;
import frc.robot.RobotMap;
import frc.robot.Robot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class MotionProfile {

  public String name;

	private Notifier m_follower_notifier;
	private EncoderFollower m_left_follower;
	private EncoderFollower m_right_follower;
	private Trajectory left_trajectory;
	private Trajectory right_trajectory;

	public double left_speed;
	public double right_speed;
	public double heading;
	public double desired_heading;
	public double turn;
	public double right_drive;
	public double left_drive;

	public boolean stopTrigger = true;

  public boolean forwards = true;

  /**
   * Create a MotionProfile in one init.
   * 
   * @param pathname the name of the path file (exclude the file extensions and sub-dir as long as its in ~/deploy/paths/)
   */
  public MotionProfile(String pathname) {
    this.name = pathname;

    try {
      pfInit(pathname);
    } catch (Exception e) {
      System.out.println("Exception when initalizing path '" + pathname + "'");
      e.printStackTrace();
    }
  }

  /**
   * Sets the follow direction.
   * 
   * @param forwards True if going forwards, false if backwards.
   */
  public void setFollowDirection(boolean forwards) {
    this.forwards = forwards;
  }

  /**
   * Toggle the follow direction.
   * 
   * @return Updated follow direction.
   */
  public boolean toggleFollowDirection() {
    forwards = !forwards;
    return forwards;
  }

  /**
   * Initalize this object with a pathname
   * 
   * @param Pathname the name of the path file (exclude the file extensions and sub-dir as long as its in ~/deploy/paths/)
   */
	public void pfInit(String Pathname) throws IOException {

		// https://wpilib.screenstepslive.com/s/currentCS/m/84338/l/1021631-integrating-path-following-into-a-robot-program
		/*left_trajectory = PathfinderFRC.getTrajectory(Pathname + ".left"); // FIX:  Know bug in Pathweaver paths
		right_trajectory = PathfinderFRC.getTrajectory(Pathname + ".right"); // FIX:  See screensteps documentation*/

    // MARK: Screensteps fix
		//left_trajectory = PathfinderFRC.getTrajectory(Pathname + ".right");
    //right_trajectory = PathfinderFRC.getTrajectory(Pathname + ".left");

    left_trajectory = Pathfinder.readFromCSV(new File("/home/lvuser/deploy/paths/" + Pathname + ".left.pf1.csv"));
    right_trajectory = Pathfinder.readFromCSV(new File("/home/lvuser/deploy/paths/" + Pathname + ".right.pf1.csv"));

		m_left_follower = new EncoderFollower(left_trajectory);
		m_right_follower = new EncoderFollower(right_trajectory);

		m_left_follower.configureEncoder((int) Robot.driveTrain.leftEncoderTicks(), RobotMap.Values.ticksPerRev,
				RobotMap.Values.robotWheelDia);
		// You must tune the PID values on the following line!
		m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.Values.pf_max_vel, 0);

		m_right_follower.configureEncoder((int) Robot.driveTrain.rightEncoderTicks(), RobotMap.Values.ticksPerRev,
				RobotMap.Values.robotWheelDia);
		// You must tune the PID values on the following line!
		m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.Values.pf_max_vel, 0);
	}

	public void startPath() {
		stopTrigger = false;
		Robot.driveTrain.resetGyro();
		m_follower_notifier = new Notifier(this::followPath);
		m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
	}

	public boolean isFinished() {

		if (stopTrigger) { return true; }

		return m_left_follower.isFinished() || m_right_follower.isFinished();
	}

	public void followPath() {
		if (this.isFinished()) {
			m_follower_notifier.stop();
		} else {
			left_speed = m_left_follower.calculate((int) Robot.driveTrain.leftEncoderTicks());
			right_speed = m_right_follower.calculate((int) Robot.driveTrain.rightEncoderTicks());
			heading = Robot.driveTrain.getHeading();
			desired_heading = Pathfinder.r2d(m_left_follower.getHeading()); //FIX: Another defect in PathWeaver
			if (!forwards) {
				desired_heading += 180;
			}
			double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading); // You may need to reverse the heading when going backwards. IDK
			//double backwards_heading_difference = Pathfinder.boundHalfDegrees(heading - desired_heading);
			turn = (-1.0 / 80.0) * heading_difference;

			// left_drive = (left_speed  * 12 * 223); //+ turn;
			// right_drive = (right_speed * 12 * 223); //- turn;

			left_drive = left_speed - turn;
			right_drive = right_speed + turn;

			// Robot.driveTrain.setVelocity(left_drive, right_drive);

      if (forwards) {
			  Robot.driveTrain.setVolts(left_drive, right_drive);
      } else {
        Robot.driveTrain.setVolts(-right_drive, -left_drive); // This should make the path go backwards
      }
    }
		
	}

	public void Abort() {
		stopTrigger = true;
	}
}