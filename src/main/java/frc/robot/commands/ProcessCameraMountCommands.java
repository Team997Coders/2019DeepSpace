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
import frc.robot.vision.cameramountserver.CameraMountServer;
import frc.robot.vision.cameramountserver.CommandProcessor;
import frc.robot.vision.cameramountserver.CommandProcessorValueBuilder;
import frc.robot.vision.cameramountserver.JoystickValueProvider;

public class ProcessCameraMountCommands extends Command {
  private final CameraMountServer cameraMountServer;
  private Socket socket;
  private boolean socketHasError;
  private CommandProcessor commandProcessor;
  private JoystickValueProvider panValueProvider;
  private JoystickValueProvider tiltValueProvider;

  public ProcessCameraMountCommands(CameraMountServer cameraMountServer) throws IOException {
    requires(Robot.cameraMount);
    this.cameraMountServer = cameraMountServer;
    // This socket will be listening for CameraMount commands
    // from the CameraVision application.
    socket = null;
    socketHasError = false;
    commandProcessor = null;
    panValueProvider = null;
    tiltValueProvider = null;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    cameraMountServer.acceptWhenAvailable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    try {
      if (socket != null && socketHasError == false) {
        commandProcessor.process();
      } else if (cameraMountServer.isSocketAvailable()) {
        socket = cameraMountServer.getSocket();
        socketHasError = false;
        panValueProvider = new JoystickValueProvider();
        tiltValueProvider = new JoystickValueProvider();
        commandProcessor = new CommandProcessor(
          socket.getInputStream(), 
          new PrintStream(socket.getOutputStream()), 
          new CommandProcessorValueBuilder(), 
          panValueProvider, 
          tiltValueProvider, 
          new CenterCamera(Robot.cameraMount), 
          new SlewCamera(Robot.cameraMount, panValueProvider, tiltValueProvider),
          Robot.cameraMount);
      }
    } catch (IOException e) {
      socketHasError= true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return socketHasError;
  }

  // Called once after isFinished returns true
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

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    throw new RuntimeException("Only one command should be acting on this subsystem.");
  }
}
