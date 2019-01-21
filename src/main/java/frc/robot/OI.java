/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Add your docs here.
 */
public class OI {

    private Joystick gamepad1;

    private JoystickButton followLine;

    public OI() {
        gamepad1 = new Joystick(RobotMap.Ports.gamepad1);
    }

    public double getLeftY() {
        return -gamepad1.getRawAxis(1);
    }

    public double getRightX() {
        return gamepad1.getRawAxis(4);
    }

    public double getRightY() {
        return -gamepad1.getRawAxis(5);
    }

}
