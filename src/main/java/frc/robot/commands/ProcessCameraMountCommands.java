/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;
import java.net.ServerSocket;

import org.team997coders.spartanlib.commands.CenterCamera;
import org.team997coders.spartanlib.commands.SlewCamera;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.CameraMount;
import frc.robot.vision.cameramountserver.CommandProcessor;
import frc.robot.vision.cameramountserver.CommandProcessorValueBuilder;
import frc.robot.vision.cameramountserver.JoystickValueProvider;

/**
 * This command will set up a socket server to read remote
 * commands for panning/tilting the CameraMount. This is used
 * by the CameraVision application to maintain control of the
 * CameraMount pan/tilt servos.
 */
public class ProcessCameraMountCommands extends Command {
  // Dependencies
  private final CommandProcessor commandProcessor;
  private final Thread commandProcessorThread;
  private final SlewCamera slewCamera;
  private final CenterCamera centerCamera;
  private final JoystickValueProvider panValueProvider;
  private final JoystickValueProvider tiltValueProvider;
  private final CameraMount cameraMount;

  /**
   * This parameterless constructor will start a socket server
   * listening for CameraMount commands on port 2223.
   * 
   * @throws IOException
   */
   public ProcessCameraMountCommands() throws IOException {
    // TODO: Put in guards
    this(2223);
    System.out.println("Init ProcessCameraMountCommands");
  }

  /**
   * Start a socket server listening for CameraMount commands
   * on the specifed port.
   * 
   * @param port          The port to listen for remote client connects.
   * @throws IOException
   */
  public ProcessCameraMountCommands(int port) throws IOException {
    // TODO: Put in guards
    this(new ServerSocket(port));
  }

  /**
   * Start the CameraMountServer with the given ServerSocket.
   * 
   * @param serverSocket  The ServerSocket already bound to a port.
   * @throws IOException
   */
  public ProcessCameraMountCommands(ServerSocket serverSocket) throws IOException {
    // TODO: Put in guards
    this(new CommandProcessor(serverSocket, new CommandProcessorValueBuilder()), new JoystickValueProvider(), new JoystickValueProvider());
  }

  /**
   * Wire up a new thread to process the given CameraMountServer.
   * 
   * @param commandProcessor  The command processor that yields CameraMount commands to execute.
   * @param panValueProvider  A container to transport panning magnitude values from the operator interface.
   * @param tiltValueProvider A container to transport tilting magnitude values from the operator interface.
   * @throws IOException
   */
  public ProcessCameraMountCommands(CommandProcessor commandProcessor, 
      JoystickValueProvider panValueProvider, 
      JoystickValueProvider tiltValueProvider) throws IOException {
    // TODO: Put in guards
    this(commandProcessor, 
      new Thread(commandProcessor), 
      new SlewCamera(Robot.cameraMount, panValueProvider, tiltValueProvider), 
      new CenterCamera(Robot.cameraMount),
      panValueProvider,
      tiltValueProvider,
      Robot.cameraMount);
  }

  /**
   * Wire up defaults to begin the command processing sequence for this command by
   * starting the given execution thread to process socket accept requests for the
   * given CameraMountServer.
   * 
   * @param commandProcessor          The command processor that yields CameraMount commands to execute.
   * @param commandProcessorThread    The thread to execute the CommandProcessor.
   * @param slewCamera                The command to slew the camera when panning or tilting commands are received.
   * @param centerCamera              The command to center the camera when the center command is received.
   * @param panValueProvider          A container to transport panning magnitude values from the operator interface.
   * @param tiltValueProvider         A container to transport tilting magnitude values from the operator interface.
   * @throws IOException
   */
  public ProcessCameraMountCommands(CommandProcessor commandProcessor, 
      Thread commandProcessorThread, 
      SlewCamera slewCamera, 
      CenterCamera centerCamera,
      JoystickValueProvider panValueProvider,
      JoystickValueProvider tiltValueProvider,
      CameraMount cameraMount) throws IOException {

    // TODO: Put in guards

    // requires(cameraMount);

    // Wire up dependencies
    this.commandProcessor = commandProcessor;
    this.commandProcessorThread = commandProcessorThread;
    this.slewCamera = slewCamera;
    this.centerCamera = centerCamera;
    this.panValueProvider = panValueProvider;
    this.tiltValueProvider = tiltValueProvider;
    this.cameraMount = cameraMount;
  }

  @Override
  protected void initialize() {
    // Start up the CameraMountServer thread for providing sockets
    if (!commandProcessorThread.isAlive()) {
      this.commandProcessorThread.start();
    }
  }

  /**
   * Each pump will check to see if a socket is available from a client
   * connection attempt (presumable from the CameraVision application),
   * and if so, will call the process method on the command processor
   * to make the CameraMount servos move, if requested.
   */
  @Override
  protected void execute() {
    try {
      if (commandProcessor.isCommandAvailable()) {
        frc.robot.vision.cameramountserver.Command command = commandProcessor.getCommand();
        switch (command.getCommand()) {
          case 'p':
            panValueProvider.setValue(command.getValue());
            slewCamera.start();
            break;
          case 't':
            tiltValueProvider.setValue(command.getValue());
            slewCamera.start();
            break;
          case 'c':
            centerCamera.start();
            break;
          case 'a':
            commandProcessor.replytoAngle(cameraMount.getRoundedTiltAngleInDegrees(), cameraMount.getRoundedPanAngleInDegrees());
            break;
          default:
            System.out.println("ProcessCameraMountCommands command" + command.getCommand() + " not recognized.");
        }
      }
    } catch (IOException e) {
      System.out.println("ProcessCameraMountCommands encountered an error.");
      e.printStackTrace();
    }
  }

  /**
   * This command never finishes.
   */
  @Override
  protected boolean isFinished() {
    return false;
  }

  /**
   * If this command ever finishes, clean up after ourselves.
   */
  @Override
  protected void end() {
    System.out.println("ProcessCameraMountCommands ends.");
    commandProcessor.stop();
  }

  /**
   * There should not be need for another command to act upon
   * the pan/tilt servos as they are under the exclusive use
   * of the CameraVision application. Note that CameraVision
   * will forward operator control requests when the state of
   * the HUD allows for it.
   */
  @Override
  protected void interrupted() {
  }
}
