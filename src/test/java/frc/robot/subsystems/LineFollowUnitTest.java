/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.junit.Test;

import edu.wpi.first.wpilibj.DigitalInput;
import static org.mockito.Mockito.*;

/**
 * Add your docs here.
 */
public class LineFollowUnitTest {
    @Test
    public void itSeesLeftLine(){
        // Assemble
        DigitalInput left = mock(DigitalInput.class);
        DigitalInput center = mock(DigitalInput.class);
        DigitalInput right = mock(DigitalInput.class);
        when(left.get()).thenReturn(true);
        LineFollowing lineFollowing = new LineFollowing(left, center, right);

        // Act

        // Assert
        assertTrue(lineFollowing.leftLineSeen());
    }
}
