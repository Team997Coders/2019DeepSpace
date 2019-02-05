package frc.robot.vision.cameramountserver;

/**
 * Custom exception to indicate that a target is not expected
 * where it should be.
 */
public class SocketUnavailableException extends RuntimeException {
  public SocketUnavailableException () {}
  public SocketUnavailableException (String message) {
    super (message);
  }
  public SocketUnavailableException (Throwable cause) {
    super (cause);
  }
  public SocketUnavailableException (String message, Throwable cause) {
    super (message, cause);
  }
}
