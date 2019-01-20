package frc.robot.guice.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.robot.RobotMap;
import frc.robot.commands.FollowLine;

/**
 * Hardware providers for operator interface (joysticks and buttons and such)
 */
public class OI {
  public class Gamepad1Provider implements Provider<Joystick> {
    FollowLine followLine;
    Joystick gamepad1;
    JoystickButton followLineButton;

    @Inject
    public Gamepad1Provider(FollowLine followLine) {
      this.followLine = followLine;
    }

    public Joystick get() {
      gamepad1 = new Joystick(RobotMap.Ports.gamepad1);
      followLineButton = new JoystickButton(gamepad1, RobotMap.Ports.followLinebutton);
      followLineButton.whenPressed(followLine);
      return gamepad1;
    }
  }
}