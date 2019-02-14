/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class JoystickTrigger extends Button{
	GenericHID m_joystick;
	int m_axisNum;
	private double THRESHOLD = 0.5;
	
	public JoystickTrigger(GenericHID joystick, int axisNumber) {
		m_joystick = joystick;
		m_axisNum = axisNumber;
	}
	
	public boolean get() {
		if (THRESHOLD < 0) {
			return m_joystick.getRawAxis(m_axisNum) < THRESHOLD;
		} else {
			return m_joystick.getRawAxis(m_axisNum) > THRESHOLD;
		}
	}
}