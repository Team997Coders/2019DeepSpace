/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.team997coders.spartanlib.commands.CenterCamera;
import org.team997coders.spartanlib.commands.SlewCamera;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.CameraMount;
import frc.robot.vision.cameramountserver.CameraMountServer;
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
  private final CameraMountServer cameraMountServer;
  private final Thread cameraMountServerThread;
  private final SlewCamera slewCamera;
  private final CenterCamera centerCamera;
  private final JoystickValueProvider panValueProvider;
  private final JoystickValueProvider tiltValueProvider;
  private final CameraMount cameraMount;
  // This socket will be listening for CameraMount commands
  // from the CameraVision application.
  private Socket socket;
  // Flipped when socket disconnects occur
  private boolean socketHasError;
  private CommandProcessor commandProcessor;

  /**
   * This parameterless constructor will start a socket server
   * listening for CameraMount commands on port 2223.
   * 
   * @throws IOException
   */
   public ProcessCameraMountCommands() throws IOException {
    // TODO: Put in guards
    this(2223);
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
    this(new CameraMountServer(serverSocket), new JoystickValueProvider(), new JoystickValueProvider());
  }

  /**
   * Wire up a new thread to process the given CameraMountServer.
   * 
   * @param cameraMountServer The server providing sockets for connecting to clients.
   * @param panValueProvider  A container to transport panning magnitude values from the operator interface.
   * @param tiltValueProvider A container to transport tilting magnitude values from the operator interface.
   * @throws IOException
   */
  public ProcessCameraMountCommands(CameraMountServer cameraMountServer, 
      JoystickValueProvider panValueProvider, 
      JoystickValueProvider tiltValueProvider) throws IOException {
    // TODO: Put in guards
    this(cameraMountServer, 
      new Thread(cameraMountServer), 
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
   * @param cameraMountServer         The server providing sockets for connecting to clients.
   * @param cameraMountServerThread   The thread to execute the CameraMountServer.
   * @param slewCamera                The command to slew the camera when panning or tilting commands are received.
   * @param centerCamera              The command to center the camera when the center command is received.
   * @param panValueProvider          A container to transport panning magnitude values from the operator interface.
   * @param tiltValueProvider         A container to transport tilting magnitude values from the operator interface.
   * @throws IOException
   */
  public ProcessCameraMountCommands(CameraMountServer cameraMountServer, 
      Thread cameraMountServerThread, 
      SlewCamera slewCamera, 
      CenterCamera centerCamera,
      JoystickValueProvider panValueProvider,
      JoystickValueProvider tiltValueProvider,
      CameraMount cameraMount) throws IOException {

    // TODO: Put in guards

    // The CameraMount subsystem will be used.
    requires(cameraMount);
    // Wire up dependencies.
    this.cameraMountServer = cameraMountServer;
    this.cameraMountServerThread = cameraMountServerThread;
    this.slewCamera = slewCamera;
    this.centerCamera = centerCamera;
    this.panValueProvider = panValueProvider;
    this.tiltValueProvider = tiltValueProvider;
    this.cameraMount = cameraMount;
    // Set default values
    socket = null;
    socketHasError = false;
    commandProcessor = null;
    // Start up the CameraMountServer thread for providing sockets
    this.cameraMountServerThread.start();
  }

  @Override
  protected void initialize() {
    cameraMountServer.acceptWhenAvailable();
  }

  /**
   * Each pump will check to see if a socket is available from a client
   * connection attempt (presumable from the CameraVision application),
   * and if so, will call the process method on the command processor
   * to make the CameraMount servos move, if requested.
   */
  @Override
  protected void execute() {
    // Trap IO errors...socket disconnects and such
    try {

      // If we have a command processor and not encountered a socket error
      if (commandProcessor != null && socketHasError == false) {

        // then proceed to process commands from remote client
        // (typically CameraVision application running on Pi)
        commandProcessor.process();

      // If the socket server detects that a socket is available to read/write
      // from (because a remote client is making a connection attempt...)
      } else if (cameraMountServer.isSocketAvailable()) {

        System.out.println("ProcessCameraMountCommands getting socket...");

        // Get the socket because we need the streams
        socket = cameraMountServer.getSocket();

        // Reset the error flag so processing can take place
        socketHasError = false;

        // Now wire up the command processor
        commandProcessor = new CommandProcessor(
          socket.getInputStream(), 
          new PrintStream(socket.getOutputStream()), 
          new CommandProcessorValueBuilder(), 
          panValueProvider, 
          tiltValueProvider, 
          centerCamera, 
          slewCamera,
          cameraMount);
      }
    } catch (IOException e) {
      System.out.println(String.format("ProcessCameraMountCommands has socket error; reason=%s; continuing...", e));
      // Socket has an error, probably because the client disconnected
      // So set flag so we do not continue processing until another good
      // socket is available.
      socketHasError = true;
      // Clean up but blow off errors. Just be a good citizen of resources.
      try {
        if (socket != null) {
          socket.close();
        }
      } catch(IOException ioe){};
      // Tell the socket server that we are ready for another socket when
      // it has one.
      cameraMountServer.acceptWhenAvailable();
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
    if (socket != null) {
      if (!socket.isClosed() && socket.isConnected()) {
        try {
          socket.close();
        } catch (IOException e) {
          System.out.println(e);
        }
      }
    } 
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
    throw new RuntimeException("Only the ProcessCameraMountCommand should be acting on this subsystem.");
  }
}
