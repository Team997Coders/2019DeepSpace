package frc.robot.vision.cameramountserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.team997coders.spartanlib.commands.SlewCamera;
import org.team997coders.spartanlib.subsystems.CameraMount;
import org.team997coders.spartanlib.commands.CenterCamera;

/**
 * This is a state machine to gather input from presumably
 * a joystick and translate that input into commands that can
 * operate the camera mount.
 */
public class CommandProcessor {
  private final InputStream m_input;
  private final PrintStream m_output;
  private final CommandProcessorValueBuilder m_valueBuilder;
  private final JoystickValueProvider m_panValueProvider;
  private final JoystickValueProvider m_tiltValueProvider;
  private final CenterCamera m_centerCamera;
  private final SlewCamera m_slewCamera;
  private final CameraMount m_cameraMount;

  private boolean echo = false;

  /**
   * Constructor for the command processor
   * 
   * @param input               The input stream to read command characters from
   * @param output              The output stream to write responses to
   * @param valueBuilder        The value builder to collect command values
   * @param panValueProvider    The pan value provider to give values to downstream commands
   * @param tiltValueProvider   The tilt value provider to give values to downstream commands
   * @param centerCamera        The center camera command to call when centering
   * @param slewCamera          The slew camera command to call when slewing axis
   */
  public CommandProcessor(InputStream input, 
      PrintStream output, 
      CommandProcessorValueBuilder valueBuilder,
      JoystickValueProvider panValueProvider,
      JoystickValueProvider tiltValueProvider,
      CenterCamera centerCamera,
      SlewCamera slewCamera,
      CameraMount cameraMount) {

    // Keep references to dependencies
    m_input = input;
    m_output = output;
    m_valueBuilder = valueBuilder;
    m_panValueProvider = panValueProvider;
    m_tiltValueProvider = tiltValueProvider;
    m_centerCamera = centerCamera;
    m_slewCamera = slewCamera;
    m_cameraMount = cameraMount;

    // Reset the value builder to make sure we are ready to build
    m_valueBuilder.reset();

    // Tell who ever is listening that we are ready
    m_output.print("Ready");
  }

  /**
   * Provide feedback to the listener that characters are being received
   * and/or commands are being processed. If the echo command has not been
   * received, then CRLF will not be sent between line breaks and input
   * characters will not be echoed back.
   */
  private void acknowledge(char input, boolean commandPerformed) {
    if (echo) {
      m_output.print(input);
      if (commandPerformed) {
        m_output.println("");
      }
    }
    if (commandPerformed) {
      m_output.print("Ok");
      if (echo) {
        m_output.println("");
      }
    }
  }

  /**
   * Reply back to the get angle command with the tilt
   * and the pan angles (in that order). Replied as two
   * hex strings delimited by a colon...yuck. Binary writing not available
   * on PrintStream implementation.
   * 
   * These angles are from 0 to 180 degrees, subject to pan/tilt limitations.
   * Center is 90 degrees.
   */
  private void replytoAngle(char input) throws IOException {
    if (echo) {
      m_output.println(input);
    }
    String reply = toHexStringPadded(m_cameraMount.getRoundedTiltAngleInDegrees()) + ":" + toHexStringPadded(m_cameraMount.getRoundedPanAngleInDegrees()); 
    m_output.print(reply);
    if (echo) {
      m_output.println("");
    }
  }

  private static String toHexStringPadded(int number) {
    String toHex = Integer.toHexString(number);
    if (toHex.length() == 0) {
      return "00";
    } else if (toHex.length() == 1) {
      return "0" + toHex;
    } else {
      return toHex;
    }
  }

  /**
   * Process input characters and perform camera mount movement commands.<p>
   * Commands are as follows:<p>
   * <ul>
   *   <li>e - Turn echo on/off (defaults to off). If on, this will echo all command characters
   *           and send a CRLF once commands are executed</li>
   *   <li>c - Center pan and tilt at 90 degrees
   *   <li>a - Get tilt and pan angles (int that order) in degrees. Center is 90 degrees.
   *   <li>p[-]nnn - Pan in a positive or negative direction at a speed given by the percentage of maximum
   *   <li>t[-]nnn - Tilt in a positive or negative direction at a speed given by the percentage of maximum
   * </ul>
   * Responses are as follows (note no CRLF follows these unless echo is on):<p>
   * <ul>
   *   <li>Ready - Upon first connection to the serial port
   *   <li>Ok - Sent in response to receiving a command
   *   <li>[hex integer]:[hex integer] - Sent in response to the 'a' command
   * </ul>
   */
  public void process() throws IOException {
    char input = (char) m_input.read();
    switch(input) {
      case '0': case '1': case '2': case '3': case '4':
      case '5': case '6': case '7': case '8': case '9':                 // Here comes a numeral for a value-based command
        m_valueBuilder.addNumeral(input);
        acknowledge(input, false);
        break;
      case '-':                                                         // Negative value coming your way
        m_valueBuilder.setNegative();
        acknowledge(input, false);
        break;
      case 'p':                                                         // Pan pan man
        m_panValueProvider.setValue(m_valueBuilder.getValue());
        m_slewCamera.start();
        acknowledge(input, true);
        m_valueBuilder.reset();
        break;
      case 't':                                                         // Tilt away
        m_tiltValueProvider.setValue(m_valueBuilder.getValue());
        m_slewCamera.start();
        acknowledge(input, true);
        m_valueBuilder.reset();
        break;
      case 'c':                                                         // Center yourself
        m_centerCamera.start();
        acknowledge(input, true);
        m_valueBuilder.reset();
        break;
      case 'a':                                                         // Give me my angles
        replytoAngle(input);
        m_valueBuilder.reset();
        break;
      case 'e':                                                         // Echo commands back (for the telnet user)
        echo = !echo;
        acknowledge(input, true);
        m_valueBuilder.reset();
        break;
      case 'r':                                                         // Ready tickler
        acknowledge(input, true);
        m_valueBuilder.reset();
        break;
    }
  }
}