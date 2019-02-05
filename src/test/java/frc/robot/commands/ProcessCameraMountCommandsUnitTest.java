package frc.robot.commands;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Test;
import org.team997coders.spartanlib.commands.CenterCamera;
import org.team997coders.spartanlib.commands.SlewCamera;

import frc.robot.subsystems.CameraMount;
import frc.robot.vision.cameramountserver.CameraMountServer;
import frc.robot.vision.cameramountserver.JoystickValueProvider;

public class ProcessCameraMountCommandsUnitTest {
  @Test
  public void itProcessesCommands() throws IOException, InterruptedException {
    // Assemble (and mock the world!)
    CameraMountServer cameraMountServerMock = mock(CameraMountServer.class);
    Socket socketMock = mock(Socket.class);
    InputStream inputStreamMock = mock(InputStream.class);
    OutputStream outputStreamMock = mock(OutputStream.class);
    when(socketMock.getInputStream()).thenReturn(inputStreamMock);
    when(socketMock.getOutputStream()).thenReturn(outputStreamMock);
    when(cameraMountServerMock.getSocket()).thenReturn(socketMock);
    when(cameraMountServerMock.isSocketAvailable()).thenReturn(true);
    SlewCamera slewCameraMock = mock(SlewCamera.class);
    CenterCamera centerCameraMock = mock(CenterCamera.class);
    JoystickValueProvider panValueProviderMock = mock(JoystickValueProvider.class);
    JoystickValueProvider tiltValueProviderMock = mock(JoystickValueProvider.class);
    CameraMount cameraMountMock = mock(CameraMount.class);
    Thread cameraMountServerThread = new Thread(cameraMountServerMock);

    ProcessCameraMountCommands processCameraMountCommands = new ProcessCameraMountCommands(cameraMountServerMock, 
      cameraMountServerThread, 
      slewCameraMock, 
      centerCameraMock, 
      panValueProviderMock, 
      tiltValueProviderMock, 
      cameraMountMock);

    // Act
    // Give CameraMountServer a moment to get its ducks in a row
    Thread.sleep(50);
    processCameraMountCommands.execute();
    // Pump twice as first iteration gets command processor wired up
    processCameraMountCommands.execute();

    // Assert
    // Eventually, the input stream should have been read
    // for commands being sent from a client
    verify(inputStreamMock, atLeast(1)).read();
  }

  @Test
  public void itRequestsANewSocketWhenExistingSocketGoesBellyUp() throws IOException, InterruptedException {
    // Assemble (and mock the world!)
    CameraMountServer cameraMountServerMock = mock(CameraMountServer.class);
    Socket socketMock = mock(Socket.class);
    OutputStream outputStreamMock = mock(OutputStream.class);
    // Make inputStream barf
    when(socketMock.getInputStream()).thenThrow(IOException.class);
    when(socketMock.getOutputStream()).thenReturn(outputStreamMock);
    when(cameraMountServerMock.getSocket()).thenReturn(socketMock);
    when(cameraMountServerMock.isSocketAvailable()).thenReturn(true);
    SlewCamera slewCameraMock = mock(SlewCamera.class);
    CenterCamera centerCameraMock = mock(CenterCamera.class);
    JoystickValueProvider panValueProviderMock = mock(JoystickValueProvider.class);
    JoystickValueProvider tiltValueProviderMock = mock(JoystickValueProvider.class);
    CameraMount cameraMountMock = mock(CameraMount.class);
    Thread cameraMountServerThread = new Thread(cameraMountServerMock);

    ProcessCameraMountCommands processCameraMountCommands = new ProcessCameraMountCommands(cameraMountServerMock, 
      cameraMountServerThread, 
      slewCameraMock, 
      centerCameraMock, 
      panValueProviderMock, 
      tiltValueProviderMock, 
      cameraMountMock);

    // Act
    // Give CameraMountServer a moment to get its ducks in a row
    Thread.sleep(50);
    processCameraMountCommands.execute();

    // Assert
    verify(cameraMountServerMock, times(1)).acceptWhenAvailable();
  }

  @Test
  public void itInitializesTheServerToAcceptSockets() throws IOException, InterruptedException {
    // Assemble (and mock the world!)
    CameraMountServer cameraMountServerMock = mock(CameraMountServer.class);
    Socket socketMock = mock(Socket.class);
    SlewCamera slewCameraMock = mock(SlewCamera.class);
    CenterCamera centerCameraMock = mock(CenterCamera.class);
    JoystickValueProvider panValueProviderMock = mock(JoystickValueProvider.class);
    JoystickValueProvider tiltValueProviderMock = mock(JoystickValueProvider.class);
    CameraMount cameraMountMock = mock(CameraMount.class);
    Thread cameraMountServerThread = new Thread(cameraMountServerMock);

    ProcessCameraMountCommands processCameraMountCommands = new ProcessCameraMountCommands(cameraMountServerMock, 
      cameraMountServerThread, 
      slewCameraMock, 
      centerCameraMock, 
      panValueProviderMock, 
      tiltValueProviderMock, 
      cameraMountMock);

    // Act
    processCameraMountCommands.initialize();

    // Assert
    // Eventually, the input stream should have been read
    // for commands being sent from a client
    verify(cameraMountServerMock, atLeast(1)).acceptWhenAvailable();
  }
}