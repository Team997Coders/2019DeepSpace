package frc.robot.vision.cameramountserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class CameraMountServerUnitTest {

  @Test(expected = SocketUnavailableException.class)
  public void itComplainsWhenGettingASocketWhenNoneAvailable() {
    // Assemble
    ServerSocket serverSocketMock = mock(ServerSocket.class);
    CameraMountServer cameraMountServer = new CameraMountServer(serverSocketMock);

    // Act
    cameraMountServer.getSocket();

    // Assert
  }

  @Test
  public void thereIsNoSocket() {
    // Assemble
    ServerSocket serverSocketMock = mock(ServerSocket.class);
    CameraMountServer cameraMountServer = new CameraMountServer(serverSocketMock);

    // Act

    // Assert
    assertEquals(false, cameraMountServer.isSocketAvailable());
  }

  @Test
  public void thereIsASocket() throws InterruptedException, IOException {
    // Assemble
    ServerSocket serverSocketMock = mock(ServerSocket.class);
    Socket socketMock = mock(Socket.class);
    CameraMountServer cameraMountServer = new CameraMountServer(serverSocketMock);
    // This makes a mock socket immediately available
    when(serverSocketMock.accept()).thenReturn(socketMock);
    // Without running a real thread, run() will run forever, hanging the test
    Thread thread = new Thread(cameraMountServer);
    thread.start();

    // Act
    // Wait for thread to run a bit
    Thread.sleep(50);
    // Now allow server to accept a socket
    cameraMountServer.acceptWhenAvailable();
    // Wait for thread to digest state change
    Thread.sleep(50);

    // Assert
    assertEquals(true, cameraMountServer.isSocketAvailable());
  }

  @Test
  public void theServerWillStop() throws InterruptedException {
    // Assemble
    ServerSocket serverSocketMock = mock(ServerSocket.class);
    CameraMountServer cameraMountServer = new CameraMountServer(serverSocketMock);
    Thread thread = new Thread(cameraMountServer);
    thread.start();

    // Act
    // Wait for thread to run a bit
    Thread.sleep(50);
    cameraMountServer.stop();
    // Wait for thread to digest state change
    Thread.sleep(50);

    // Assert
    assertEquals(false, thread.isAlive());
  }

  @Test
  public void itServesUpASocket() throws IOException, InterruptedException {
    // Assemble
    ServerSocket serverSocketMock = mock(ServerSocket.class);
    Socket socketMock = mock(Socket.class);
    CameraMountServer cameraMountServer = new CameraMountServer(serverSocketMock);
    // This makes a mock socket immediately available
    when(serverSocketMock.accept()).thenReturn(socketMock);
    // Without running a real thread, run() will run forever, hanging the test
    Thread thread = new Thread(cameraMountServer);
    thread.start();

    // Act
    // Wait for thread to run a bit
    Thread.sleep(50);
    // Now allow server to accept a socket
    cameraMountServer.acceptWhenAvailable();
    // Wait for thread to digest state change
    Thread.sleep(50);

    // Assert
    assertEquals(socketMock, cameraMountServer.getSocket());
  }

  @Test
  public void itCleansUpAfterItself() throws IOException, InterruptedException {
    // Assemble
    ServerSocket serverSocketMock = mock(ServerSocket.class);
    Socket socketMock = mock(Socket.class);
    when(socketMock.isClosed()).thenReturn(false);
    when(socketMock.isConnected()).thenReturn(true);
    CameraMountServer cameraMountServer = new CameraMountServer(serverSocketMock);
    // This makes a mock socket immediately available
    when(serverSocketMock.accept()).thenReturn(socketMock);
    // Without running a real thread, run() will run forever, hanging the test
    Thread thread = new Thread(cameraMountServer);
    thread.start();

    // Act
    // Wait for thread to run a bit
    Thread.sleep(50);
    // Now allow server to accept a socket
    cameraMountServer.acceptWhenAvailable();
    // Wait for thread to digest state change
    Thread.sleep(50);
    // Simulate a try-with-resources close
    cameraMountServer.close();

    // Assert
    verify(socketMock, times(1)).close();
  }
}