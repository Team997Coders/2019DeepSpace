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


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Add your docs here.
 */
public class JsonLoader {
  /**
   * Takes hard coded pathnames and loads setpoints from
   * a file.
   */
  private HashMap<String, HashMap> setpoints = new HashMap<String, HashMap>();

  public JsonLoader() throws ParseException, FileNotFoundException, IOException {
      JSONParser parser = new JSONParser();
      
      try (FileReader reader = new FileReader("C:/Users/RED/Local Documents/GitHub/2019DeepSpace/src/main/deploy/ElevatorArmSetpoints.json"))
      {
          // Read JSON file
          JSONObject obj = (JSONObject) parser.parse(reader);

          //Iterate over setpoint array
          setpoints = ((HashMap<String, HashMap>) obj.get("Setpoints"));
          
          // iterating address Map 
          for (String name : setpoints.keySet()){ 
            System.out.println("Name: "+name);
            HashMap<String, Integer> values = (HashMap<String, Integer>) setpoints.get(name);
            /*
            System.out.println("   Scoring Side: "+values.get("side"));
            System.out.println("   Height: "+values.get("height"));
            System.out.println("   Angle: "+values.get("angle"));
            System.out.println("   Valid?: "+values.get("valid"));
            System.out.println("    .... side: "+getSide(name));
            System.out.println("    .... height: "+getHeight(name));
            System.out.println("    .... angle: "+getAngle(name));
            System.out.println("    .... valid: "+isValid(name)); 
            */
          }          
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
}
