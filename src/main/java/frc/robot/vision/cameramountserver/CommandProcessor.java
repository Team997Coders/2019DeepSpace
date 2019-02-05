package frc.robot.vision.cameramountserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandProcessor implements Runnable {
  private final ServerSocket serverSocket;
  private final CommandProcessorValueBuilder valueBuilder;
  private volatile boolean commandAvailable;
  private Command command;
  private PrintStream output;
  private boolean echo = false;
  private volatile boolean stopped = false;


  public CommandProcessor(ServerSocket serverSocket, CommandProcessorValueBuilder valueBuider) {
    this.serverSocket = serverSocket;
    this.valueBuilder = valueBuider;
    // Reset the value builder to make sure we are ready to build
    valueBuilder.reset();
    commandAvailable = false;
  }

  public boolean isCommandAvailable() {
    return commandAvailable;
  }

  public void stop() {
    stopped = true;
  }

  public Command getCommand() {
    Command bufferedCommand = command;
    commandAvailable = false;
    return bufferedCommand;
  }

  /**
   * Provide feedback to the listener that characters are being received
   * and/or commands are being processed. If the echo command has not been
   * received, then CRLF will not be sent between line breaks and input
   * characters will not be echoed back.
   */
  private void acknowledge(char input, boolean commandPerformed) {
    if (echo) {
      output.print(input);
      if (commandPerformed) {
        output.println("");
      }
    }
    if (commandPerformed) {
      output.print("Ok");
      if (echo) {
        output.println("");
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
  public void replytoAngle(int roundedTiltAngleInDegrees, int roundedPanAngleInDegrees) throws IOException {
    String reply = toHexStringPadded(roundedTiltAngleInDegrees) + ":" + toHexStringPadded(roundedPanAngleInDegrees); 
    if (output != null) {
      output.print(reply);
      if (echo) {
        output.println("");
      }
    } else {
      throw new IOException("Not connected.");
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

  public void run() {
    while (!Thread.currentThread().isInterrupted() && !stopped) {
      // Get a connection socket so we can get the input stream
      // Only one accept will be allowed at a time...
      System.out.println("Awaiting connection from Pi for Camera pan/tilt commands...");
      try (Socket socket = serverSocket.accept()) {
        // Disable NAGLE algo so that reply packets are not held up.
        socket.setTcpNoDelay(true);
        System.out.println("Command connection with Pi established.");
        try (InputStream inputStream = socket.getInputStream()) {
          output = new PrintStream(socket.getOutputStream());
          // Tell who ever is listening that we are ready
          output.print("Ready");
          while (!Thread.currentThread().isInterrupted() && !stopped) {
            if (!commandAvailable) {
              try {  
                int input = inputStream.read();
                // If the input stream ends, exit the loop
                if (input == -1) {
                  System.out.println("Command connection with Pi closed.");
                  break;
                }
                char inputChar = (char) input;
                // What character did we get?
                switch(inputChar) {
                  case '0': case '1': case '2': case '3': case '4':
                  case '5': case '6': case '7': case '8': case '9':
                    valueBuilder.addNumeral(inputChar);
                    acknowledge(inputChar, false);
                    break;
                  case '-':
                    valueBuilder.setNegative();
                    acknowledge(inputChar, false);
                    break;
                  case 'c': // center command
                    // We got a terminating command so make it ready
                    command = new Command(inputChar);
                    acknowledge(inputChar, true);
                    valueBuilder.reset();
                    commandAvailable = true;
                    break;
                  case 'a': // get angles command
                    command = new Command(inputChar);
                    valueBuilder.reset();
                    commandAvailable = true;
                    break;
                  case 'r': // ready tickler command
                    acknowledge(inputChar, true);
                    valueBuilder.reset();
                    break;
                  case 'e':
                    // Echo commands back (for the telnet user)
                    echo = !echo;
                    acknowledge(inputChar, true);
                    valueBuilder.reset();
                    break;
                  case 'p': // Left X joystick value
                    command = new Command(inputChar, valueBuilder.getValue());
                    acknowledge(inputChar, true);
                    valueBuilder.reset();
                    commandAvailable = true;
                    break;
                  case 't': // Left Y joystick value
                    command = new Command(inputChar, valueBuilder.getValue());
                    acknowledge(inputChar, true);
                    valueBuilder.reset();
                    commandAvailable = true;
                    break;
                }
              } catch (IOException e) {
                // TODO: Under what conditions will this happen?
                // Close the stream and listen for another connection...
                System.err.println(e.toString());
                System.out.println("Pan/tilt command processor socket closing...");
                break;
              }
            }
          }
          System.out.println("Pan/tilt command processor continuing to accept connections...");
        } catch (IOException e) {
          System.err.println(e.toString());
          System.out.println("Pan/tilt command processor socket closing...");
        }
      } catch (IOException e) {
        System.err.println(e.toString());
        System.out.println("Command processor halted.");
        break;          
      }
    }
    System.out.println("Exiting pan/tilt command processor.");
  }
}