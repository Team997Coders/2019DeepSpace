/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Add your docs here.
 */
public class DeadbandClampUnitTest {

  @Test
  public void TestStuff() {
    double a = Utils.condition_gamepad_axis(0.05, -0.1, -1, 1);
    assertTrue("Output is " + a, a == -0.1);
  }

}