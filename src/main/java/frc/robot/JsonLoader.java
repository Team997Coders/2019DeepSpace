/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Add your docs here.
 */
public class JsonLoader {
  /**
   * Takes hard coded pathnames and loads setpoints from a file.
   */
  public JSONArray cargo_order = new JSONArray();
  public JSONArray hatch_order = new JSONArray();
  public HashMap<String, HashMap> setpoints = new HashMap<String, HashMap>();
  

  public JsonLoader() throws ParseException, FileNotFoundException, IOException {
    load("C:/Users/RED/Local Documents/GitHub/2019DeepSpace/src/main/deploy/ElevatorArmSetpoints.json");
  }

  public void load(String fileName) throws ParseException, FileNotFoundException, IOException {
    JSONParser parser = new JSONParser();

    // TODO: Use "deploy/ElevatorArmSetpoints.json"
    try (FileReader reader = new FileReader( fileName )) {
      // Read JSON file
      JSONObject obj = (JSONObject) parser.parse(reader);
      System.out.println(obj);

      // get the setpoint ordering arrays:
      cargo_order = (JSONArray) obj.get("CargoElevatorHeights");
      hatch_order = (JSONArray) obj.get("HatchElevatorHeights");

      // Iterate over setpoint array
      setpoints = ((HashMap<String, HashMap>) obj.get("Setpoints"));
      cargo_order = (JSONArray) obj.get("CargoElevatorHeights");
      hatch_order = (JSONArray) obj.get("HatchElevatorHeights");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public boolean isValid(String _key) {
    HashMap<String, Boolean> values = (HashMap<String, Boolean>) setpoints.get(_key);
    return values.get("valid");
  }

  public double getHeight(String _key) {
    HashMap<String, Long> values = (HashMap<String, Long>) setpoints.get(_key);
    return (double) values.get("height");
  }

  public double getAngle(String _key) {
    HashMap<String, Long> values = (HashMap<String, Long>) setpoints.get(_key);
    return (double) values.get("angle");
  }

  public String getSide(String _key) {
    HashMap<String, String> values = (HashMap<String, String>) setpoints.get(_key);
    return values.get("side");
  }
            
  public String nextHeight() {
    // loop array
    Iterator<String> iterator = cargo_order.iterator();
    while (iterator.hasNext()) {
      return iterator.next();
    }
    return "";
  }


}
