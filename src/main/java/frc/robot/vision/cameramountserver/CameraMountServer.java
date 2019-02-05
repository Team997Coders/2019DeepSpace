package frc.robot.vision.cameramountserver;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class processes one socket accept at a time in order
 * to provide streams for the command processor. It presents
 * these sockets to a consumer for their use.
 */
public class CameraMountServer implements Runnable, Closeable {
  private final ServerSocket serverSocket;
  private Socket socket;
  private volatile boolean accepting;
  private volatile boolean socketAvailable;
  private volatile boolean stop;

  public CameraMountServer(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
    accepting = false;
    socketAvailable = false;
    stop = false;
  }

  /**
   * Tests whether a socket is available for use.
   * 
   * @return  True if available.
   */
  public boolean isSocketAvailable() {
    return socketAvailable;
  }

  /**
   * Tells the server that it is ok to accept
   * another socket if one is available to accept.
   * Sockets are only available to accept if a client
   * is requesting to connect.
   */
  public void acceptWhenAvailable() {
    accepting = true;
  }

  /**
   * Signal to stop running the thread processing loop.
   */
  public void stop() {
    stop = true;
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

  /**
   * Closes open resources. Try-with-resources will automatically call this
   * method.
   */
  public void close() {
    stop();
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

  /**
   * Main entry point for a thread running this server.
   */
  public void run() {
    while (!Thread.currentThread().isInterrupted() && !stop) {
      // Get a socket so we can get ultimately get the streams for command processing
      try {
        // Only one accept will be allowed at a time...
        if (accepting) {
          accepting = false;
          System.out.println("CameraMountServer awaiting command connection...");
          socket = serverSocket.accept();
          // Disable NAGLE algo so that reply packets are not held up.
          socket.setTcpNoDelay(true);
          System.out.println("CameraMountServer command connection established.");
          // Set our flag telling the consumer a socket is fired
          // up and ready to go for command processing.
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