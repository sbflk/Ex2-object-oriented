import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DirectedWeightedGraphClass implements DirectedWeightedGraph {
    private ArrayList<NodeData> Nodes;
    private ArrayList<EdgeData> Edges;
    private HashMap<Integer, ArrayList<Integer>> from_node;
    private HashMap<Integer, ArrayList<Integer>> to_node;

    public DirectedWeightedGraphClass(HashMap<String, ArrayList<HashMap<String,Double>>> h){
        Nodes = new ArrayList<>();
        for(int i = 0; i < h.get("Nodes").size(); i++){
            String pos = String.valueOf(h.get("Nodes").get(i).get("pos"));
            double x = Double.parseDouble(pos.split(",")[0]);
            double y = Double.parseDouble(pos.split(",")[1]);
            double z = Double.parseDouble(pos.split(",")[2]);
            GeoLocation g = new GeoLocationClass(x,y,z);
            NodeData n = new NodeDataClass(h.get("Nodes").get(i).get("id"),g);
            Nodes.add(n);
        }
        Edges = new ArrayList<>();
        for (int i = 0; i < h.get("Edges").size(); i++){
            EdgeData e = new EdgeDataClass(h.get("Edges").get(i));
            Edges.add(e);
        }
        from_node = new HashMap<>();
        to_node = new HashMap<>();
        for (int i = 0; i < Nodes.size();i++){
            from_node.put(Nodes.get(i).getKey(), new ArrayList<>());
            to_node.put(Nodes.get(i).getKey(), new ArrayList<>());
        }
        for(EdgeData e: Edges){
            from_node.get(e.getSrc()).add(e.getDest());
            to_node.get(e.getDest()).add(e.getSrc());
        }
    }

    @Override
    public NodeData getNode(int key) {
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(NodeData n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
