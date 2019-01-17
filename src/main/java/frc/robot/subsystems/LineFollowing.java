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
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Add your docs here.
 */
public class LineFollowing extends Subsystem {
  //private DigitalOutput sensorLeftOutput = new DigitalOutput(RobotMap.Ports.linesensorleft);
  //private DigitalOutput sensorRightOutput = new DigitalOutput(RobotMap.Ports.linesensorright);
  //private DigitalOutput sensorCenterOutput = new DigitalOutput(RobotMap.Ports.linesensorcenter);

  //Test
  private DigitalInput m_sensorLeftInput;
  private DigitalInput m_sensorRightInput;
  private DigitalInput m_sensorCenterInput;


  public LineFollowing(DigitalInput sensorLeftInput, DigitalInput sensorCenterInput, DigitalInput sensorRightInput) {
    m_sensorLeftInput = sensorLeftInput;
    m_sensorRightInput = sensorRightInput;
    m_sensorCenterInput = sensorCenterInput;


  }

  public void initDefaultCommand(){}

  public boolean leftLineSeen(){
    return(m_sensorLeftInput.get());
  }

  public boolean rightLineSeen(){
    return(m_sensorRightInput.get());
  }

  public boolean centerLineSeen(){
    return(m_sensorCenterInput.get());
  }

  public boolean anyLineSeen(){
    return(m_sensorCenterInput.get() || m_sensorLeftInput.get() || m_sensorRightInput.get());
  }

  public boolean leftCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorLeftInput.get());
  }

  public boolean rightCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorRightInput.get());
  }


  /*public boolean[] returnOutput() {

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
  }*/
}
