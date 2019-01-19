/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import org.junit.Before;
import org.junit.Test;
import  static org.junit.Assert.assertTrue;

import edu.wpi.first.wpilibj.DigitalInput;
import static org.mockito.Mockito.*;

/**
 * Add your docs here.
 */
public class LineFollowUnitTest {

    private DigitalInput center;
    private DigitalInput left;
    private DigitalInput right;
    private LineFollowing lineFollowing;

    @Before
    public void initMocks() {
        left = mock(DigitalInput.class);
        center = mock(DigitalInput.class);
        right = mock(DigitalInput.class);
        lineFollowing = new LineFollowing(left, center, right);

    }

    @Test
    public void itSeesLeftLine(){
        // Assemble
    
        
        
        when(left.get()).thenReturn(true);
        
        // Act

        // Assert
        assertTrue(lineFollowing.leftLineSeen());
    }

    @Test 
    public void itSeesRightLine(){
        // Assemble
        when(right.get()).thenReturn(true);
        

        // Act

        // Assert
        assertTrue(lineFollowing.rightLineSeen());

    }
    @Test
    public void itSeesCenterLine(){
        //Assemble
        when(center.get()).thenReturn(true);
        

        //Act

        //Assert
        assertTrue(lineFollowing.centerLineSeen());
    }


    @Test
    public void itSeesCenterAndRightLines(){
        //Assemble
        when(center.get()).thenReturn(true);
        when(right.get()).thenReturn(true);
        
        //Act


        //Assert
        assertTrue( lineFollowing.rightCenterLineSeen());
    }


}
