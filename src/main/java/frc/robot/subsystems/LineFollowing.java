/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * Add your docs here.
 */
public class LineFollowing extends Subsystem {
  private DigitalOutput sensorLeftOutput = new DigitalOutput(RobotMap.Ports.sensorleft);
  private DigitalOutput sensorRightOutput = new DigitalOutput(RobotMap.Ports.sensorright);
  private DigitalOutput sensorCenterOutput = new DigitalOutput(RobotMap.Ports.sensorcenter);

  public boolean[] returnOutput() {

    boolean[] Output = new boolean[3];

    Output[0] =  sensorLeftOutput.get();
    Output[1] =  sensorCenterOutput.get();
    Output[2] =  sensorRightOutput.get();

    return Output;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
