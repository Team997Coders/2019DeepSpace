package frc.robot.guice.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.robot.RobotMap;
import frc.robot.commands.FollowLine;

/**
 * Define hardware providers for operator interface (joysticks and buttons and such)
 * Note that since we don't need access to the buttons anywhere (we just need
 * to hook them to a command), there is no need to expose the button to the robot
 * program. Providers can do basic work, but keep them simple.
 * 
 * @see https://github.com/google/guice/wiki/ProviderBindings
 */
public class OI {
  public static class Gamepad1Provider implements Provider<Joystick> {
    private final Joystick gamepad1;
    private final JoystickButton followLineButton;

    /**
     * The gamepad needs the commands that will execute when actions
     * are initiated (buttons are pressed, for example)
     * 
     * @param followLine  The FollowLine command to execute
     */
    @Inject
    public Gamepad1Provider(FollowLine followLine) {
      this.gamepad1 = new Joystick(RobotMap.Ports.gamepad1);
      this.followLineButton = new JoystickButton(gamepad1, RobotMap.Ports.followLinebutton);
      this.followLineButton.whenPressed(followLine);
    }

    public Joystick get() {
      return gamepad1;
    }
  }
}
