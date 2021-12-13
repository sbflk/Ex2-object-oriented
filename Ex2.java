import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import api.NodeData;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.util.Arrays;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(json_file))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            HashMap<String, ArrayList<HashMap<String, Double>>> h = new Gson().fromJson(obj.toString(), HashMap.class);

            for(Map.Entry v: h.entrySet()){
                for (int i = 0; i < h.get(v.getKey()).size();i++){
                    h.get(v.getKey()).set(i,new HashMap<String,Double>(h.get(v.getKey()).get(i)));
                }
            }
            ans = new DirectedWeightedGraphClass(h);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        // ****** Add your code here ******
        //
        // ********************************
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }

    public static void main(String[] args) {

        DirectedWeightedGraph ans = getGrapg(args[0]);
        DirectedWeightedGraphAlgorithmsClass g = new DirectedWeightedGraphAlgorithmsClass((DirectedWeightedGraphClass) ans);
        g.getGraph().removeEdge(0,1);
        g.save(args[1]);
        System.out.print(g.isConnected());


        GuiRun a = new GuiRun(g);
        a.setVisible(true);
        /*System.out.print(g.shortestPathDist(8,13));
        System.out.print("\n");
        ArrayList<NodeData> path = (ArrayList<NodeData>) g.shortestPath(8,13);
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i).getKey() + ", ");
        }
        System.out.print("\n");
        System.out.print(g.center().getKey());
        System.out.print("\n");
        System.out.print("\n");
        List<NodeData> cities = new ArrayList<>();
        cities.add(g.getGraph().getNode(0));
        cities.add(g.getGraph().getNode(6));
        cities.add(g.getGraph().getNode(5));
        cities.add(g.getGraph().getNode(7));
        //cities.add(g.getGraph().getNode(9));
        //cities.add(g.getGraph().getNode(11));
        List<NodeData> ans1 =  g.tsp(cities);
        if (ans1 != null){
            for (int i = 0; i < ans1.size(); i++) {
                System.out.print(ans1.get(i).getKey());
                System.out.print("\n");
            }
        }*/





    }
}