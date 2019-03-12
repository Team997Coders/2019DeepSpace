/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.buttonbox.ButtonBox;

/**
 * Subsystem for line detection, consisting of three infrared sensors.
 */
public class LineDetector extends Subsystem {
  private final DigitalInput m_sensorLeftInput;
  private final DigitalInput m_sensorRightInput;
  private final DigitalInput m_sensorCenterInput;
  //private final ButtonBox.ScoringDirectionStates scoringDirection;

  public LineDetector(int leftPort, int centerPort, int rightPort
  // ButtonBox.ScoringDirectionStates scoringDirection
   ) {
    //this.scoringDirection = scoringDirection;
    m_sensorLeftInput = new DigitalInput(leftPort);
    m_sensorRightInput = new DigitalInput(rightPort);
    m_sensorCenterInput = new DigitalInput(centerPort);
  }

   /**
   * Returns a boolean representing whether or not the left linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean leftLineSeen() {
    return(m_sensorLeftInput.get());
  }

  /**
   * Returns a boolean representing whether or not the right linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean rightLineSeen() {
    return(m_sensorRightInput.get());
  }

  /**
   * Returns a boolean representing whether or not the middle linesensor is seeing white
   * 
   * @return True if white is seen. False if not
   */
  public boolean centerLineSeen() {
    return(m_sensorCenterInput.get());
  }

    /**
   * Returns a boolean representing whether or not any of the linesensors see any white
   * 
   * @return True if white is seen. False if not
   */
  public boolean anyLineSeen() {
    return(m_sensorCenterInput.get() || m_sensorLeftInput.get() || m_sensorRightInput.get());
  }

    /**
   * Returns boolean representing no white is seen by any line sensor.
   * This function is !anyLineSeen();
   * 
   * @return True if not white is seen. False if there is
   */
  public boolean noLineSeen() { //TODO: Make static before testing code in Auto Alignment
    return !anyLineSeen();
  }

   /**
   * Returns boolean dependent on the middle and left sensors see white
   * 
   * @return True if middle and left sensors see white. False if only one or neither see white
   */
  public boolean leftCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorLeftInput.get());
  }

    /**
   * Returns boolean dependent on the middle and right sensors see white
   * 
   * @return True if middle and right sensors see white. False if only on or neither see white
   */
  public boolean rightCenterLineSeen(){
    return(m_sensorCenterInput.get() && m_sensorRightInput.get());
  }

  public void updateSmartDashboard(){
    SmartDashboard.putBoolean(String.format("%s left line seen", 
    //scoringDirection.toString()
    ), leftLineSeen());
    SmartDashboard.putBoolean(String.format("%s right line seen",
    // scoringDirection.toString()
     ), rightLineSeen());
    SmartDashboard.putBoolean(String.format("%s center line seen", 
   // scoringDirection.toString()
    ), centerLineSeen());
  }

  @Override
  public void initDefaultCommand() {
    // none
  }
}
