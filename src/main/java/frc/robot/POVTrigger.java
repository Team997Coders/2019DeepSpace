/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import frc.robot.RobotMap.POVStates;

/**
 * Treat the POV hat on a XBox controller like an other button
 * Call: if (getPOV() == POVStates.UP) ...
 */
public class POVTrigger extends Button{
	GenericHID m_joystick;
	POVStates m_dir;
	boolean m_chording = false;
	
	public POVTrigger(GenericHID joystick) {
		m_joystick = joystick;
	}

	public POVTrigger(GenericHID joystick, boolean chording) {
		m_joystick = joystick;
		m_chording = chording;
	}

	public POVTrigger(GenericHID joystick, POVStates direction) {
		m_joystick = joystick;
		m_dir = direction;
	}

	public boolean get() {
		int m_val = m_joystick.getPOV(0);
		switch (m_dir) {
			case NONE:
				return m_val < 0;
			case UP:
				return m_val == 0;
			case RIGHT:
				return m_val == 90;
			case DOWN:
				return m_val == 180;
			case LEFT:
				return m_val == 270;
			default:
				break;
		}
		return false;
	}

	public POVStates getPOVstate() {
		int m_val = m_joystick.getPOV(0);
		if (m_val == 0) {
			return POVStates.UP;
		} else if (m_val == 90) {
			return POVStates.RIGHT;
		} else if (m_val == 180) {
			return POVStates.DOWN;
		} else if (m_val == 270) {
			return POVStates.LEFT;
		} else if ((m_val == 1 || m_val == 3 || m_val == 5 || m_val == 7) && m_chording == false) {
			return POVStates.NONE;
		} 
		return POVStates.NONE;
	}
}