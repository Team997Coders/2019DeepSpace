package frc.robot.vision.cameramountserver;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class processes one socket accept at a time in order
 * to provide streams for the command processor.
 */
public class CameraMountServer implements Runnable, Closeable {
  private final ServerSocket serverSocket;
  private Socket socket;
  private volatile boolean accepting;
  private volatile boolean socketAvailable;

  public CameraMountServer(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    accepting = false;
    socketAvailable = false;
  }

  public boolean isSocketAvailable() {
    return socketAvailable;
  }

  public void acceptWhenAvailable() {
    accepting = true;
  }

  /**
   * Get a socket based on a connection attempt from the CameraVision application.
   * @return  A socket ready for IO.
   */
  public Socket getSocket() {
    if (socketAvailable) {
      return socket;
    } else {
      throw new SocketUnavailableException();
    }
  }

  public void close() {
    if (socket != null) {
      if (!socket.isClosed() && socket.isConnected()) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
          System.out.println("Error closing socket.");
        }
      }
    }
  }

  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      // Get a connection socket so we can get the input stream
      // Only one accept will be allowed at a time...

      try {
        if (accepting) {
          accepting = false;
          System.out.println("CameraMountServer awaiting command connection...");
          socket = serverSocket.accept();
          // Disable NAGLE algo so that reply packets are not held up.
          socket.setTcpNoDelay(true);
          System.out.println("CameraMountServer command connection established...");
          socketAvailable = true;
        }
        // Don't spin the CPUs
        Thread.sleep(20);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("CameraMountServer command processor halted.");
        // Exit the thread
        break;          
      } catch (InterruptedException e) {
        Thread.interrupted();
      }
    }
  }
}