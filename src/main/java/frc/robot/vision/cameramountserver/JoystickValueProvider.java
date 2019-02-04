package frc.robot.vision.cameramountserver;

import org.team997coders.spartanlib.interfaces.IJoystickValueProvider;

/**
 * Implementation of the IJoystickValueProvider interface to simply
 * provide a stored double value.
 */
public class JoystickValueProvider implements IJoystickValueProvider {
  private double m_value = 0;

  public JoystickValueProvider() {
  }

  public double getValue() {
    return m_value;
  }

  public void setValue(double value) {
    m_value = value;
  }
}