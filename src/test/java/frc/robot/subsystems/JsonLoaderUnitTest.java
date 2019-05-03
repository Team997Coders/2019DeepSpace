package frc.robot.subsystems;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import frc.robot.JsonLoader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonLoaderUnitTest {
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