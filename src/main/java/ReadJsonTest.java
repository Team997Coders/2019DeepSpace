
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import frc.robot.JsonLoader;
 
public class ReadJsonTest
{
    public static JsonLoader jl;
    public static void main(String[] args)
    {
        try {
            jl = new JsonLoader();
            System.out.println("FrontBallRocketLow:");
            System.out.println("Side: "+jl.getSide("FrontBallRocketLow"));
            System.out.println("Height: "+jl.getHeight("FrontBallRocketLow"));

            // lets test it first
            Iterator<String> iterator = jl.cargo_order.iterator();
                while (iterator.hasNext()) {
                System.out.println("   "+iterator.next());
            }

            Object low = jl.cargo_order.get(0);
            double height = jl.getHeight((String) low);
            double angle = jl.getAngle((String) low);
            System.out.println("First Element in Cargo Array: "+low+", height="+height+", angle="+angle);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}