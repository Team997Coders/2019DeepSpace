package frc.robot.subsystems;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import frc.robot.JsonLoader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonLoaderUnitTest {
  // This is needed to load the ntcore jni since we have a NetworkTable
  // dependency.
  // Note that was are not actually calling into network tables in the test, but
  // the jni has to at least be loaded for the test to run. Also note that
  // java.test.config must be set to point to the build/tmp/jniExtractDir
  // directory
  // in order to find the jnis.

  @Test
  public void TestInstanciation() {
    // Assemble
    JsonLoader jl = new JsonLoader();

    // Act
    // jl.load("C:/home/red/deploy/ElevatorArmSetpoints.json");
    // Object low = jl.cargo_order.get(0);

    // Assert
    // verify(jl, times(1)).getAngle((String) low);
    assertTrue(true);
  }

  @Test
  public void TestFileNotFound() {
    // Assemble
    JsonLoader jlmock = mock(JsonLoader.class);

    // Act
    try{
    doThrow(FileNotFoundException.class)
      .when(jlmock)
      .load("missing_file.json");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}