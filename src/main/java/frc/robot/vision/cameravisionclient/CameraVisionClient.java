package frc.robot.vision.cameravisionclient;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class which sends commands to the CameraVision application over
 * network sockets. All heads up display functionality is handled
 * there and all control of pan/tilt servos are handled by a state
 * machine within that application.
 */
public class CameraVisionClient implements Closeable {
  private Socket socket;
  private PrintWriter client;
  private final String host;
  private final int port;
  private boolean lastPanWasZero;
  private boolean lastTiltWasZero;
  private boolean connected;

  /**
   * Constructor that assumes the client is running on localhost and 
   * the default port (2222).
   * @throws UnknownHostException
   * @throws IOException
   */
  public CameraVisionClient() throws UnknownHostException, IOException {
    this(null);
  }

  /**
   * Constructor requiring a host name running the CameraVision application.
   * Assume that the default port is used (2222).
   * @param host                  The name of the host running the application.
   * @throws UnknownHostException IP address cannot be found.
   * @throws IOException          Thrown if...who knows.
   */
  public CameraVisionClient(String host) throws UnknownHostException, IOException {
    this(host, 2222);
  }

  /**
   * Constructor requiring a host name running the CameraVision application.
   * Assume that the default port is used (2222).
   * @param host                  The name of the host running the application.
   * @param port                  The port that the application is listening for commands.
   * @throws UnknownHostException IP address cannot be found.
   * @throws IOException          Thrown if...who knows.
   */
  public CameraVisionClient(String host, int port) throws UnknownHostException, IOException {
    this.host = host;
    this.port = port;
    connected = false;
    // Socket to talk to the CameraVision application
    this.socket = new Socket(host, port);
    // Disable NAGLE algo so that sent packets are not held up.
    // This seems to be disabled on the Windows loopback, so I do
    // not see performance problems there but do when deploying
    // the CameraVision app across the network.
    this.socket.setTcpNoDelay(true);
    // PrintWriter through which we will write commands to
    this.client = new PrintWriter(socket.getOutputStream(), true);
    lastPanWasZero = true;
    lastTiltWasZero = true;
    System.out.println("CameraVisionClient connected.");
    connected = true;
  }

  /**
   * Close all resources.
   */
  public void close() throws IOException {
    socket.close();
    connected = false;
    System.out.println("CameraVisionClient disconnected.");
  }

  /**
   * Wrap up PrintWriter printf to check for errors and reconnect to the socket.
   * 
   * @param format  The string format.
   * @param args    Variable length argument list.
   */
  private void printf(String format, Object... args) {
    // Try to print the command
    client.printf(format, args);
    // If it fails, then try to reconnect and print again.
    if (client.checkError()) {
      if (connected) {
        System.out.println("CameraVisionClient disconnected.");
        connected = false;
      }
      try {
        lastPanWasZero = true;
        lastTiltWasZero = true;
        client.close();
        socket.close();
        // Socket to talk to the CameraVision application
        socket = new Socket(host, port);
        socket.setTcpNoDelay(true);
        // PrintWriter through which we will write commands to
        client = new PrintWriter(socket.getOutputStream(), true);
        client.printf(format, args);
        if (!client.checkError()) {
          connected = true;
          System.out.println("CameraVisionClient connected.");
        }
      } catch (Exception e) {
        System.out.println("CameraVisionClient not connected.");
      }
    }
  }

  /**
   * Slew (both pan and tilt) the camera with values between -100..100,
   * which represents the percentage rate to slew to maximum.
   * 
   * @param panPct  Percentage of maximum rate to pan
   * @param tiltPct Percentage of maximum rate to tilt
   */
  public void slew(int panPct, int tiltPct) {
    // Keep from flooding pipe with zero pan commands
    // Also treat -1..1 as zero to deal with rounding issues from joystick
    if (panPct >= -3 && panPct <= 3) {
      if (!lastPanWasZero) {
        printf("%dp", 0);
        lastPanWasZero = true;
      }
    } else {
      printf("%dp", panPct);
      lastPanWasZero = false;
    }
    // Keep from flooding pipe with zero tilt commands
    // Also treat -1..1 as zero to deal with rounding issues from joystick
    if (tiltPct >= -3 && tiltPct <= 3) {
      if (!lastTiltWasZero) {
        printf("%dt", 0);
        lastTiltWasZero = true;
      }
    } else {
      printf("%dt", tiltPct);
      lastTiltWasZero = false;
    }
  }

  /**
   * Press left thumbstick button.
   */
  public void pressLeftThumbstick() {
    printf("c");
  }

  /**
   * Press right thumbstick button.
   */
  public void pressRightThumbstick() {
    printf("d");
  }

  /**
   * Press left shoulder button.
   */
  public void pressLeftShoulder() {
    printf("e");
  }

  /**
   * Press right shoulder button.
   */
  public void pressRightShoulder() {
    printf("f");
  }

    /**
   * Press left trigger button.
   */
  public void pressLeftTrigger() {
    printf("g");
  }

  /**
   * Press right trigger button.
   */
  public void pressRightTrigger() {
    printf("h");
  }

  /**
   * Press A.
   */
  public void pressA() {
    printf("A");
  }

  /**
   * Press B.
   */
  public void pressB() {
    printf("B");
  }

  /**
   * Press X.
   */
  public void pressX() {
    printf("X");
  }

  /**
   * Press Y.
   */
  public void pressY() {
    printf("Y");
  }
}