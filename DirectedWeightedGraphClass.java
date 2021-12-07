import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DirectedWeightedGraphClass implements DirectedWeightedGraph {
    private ArrayList<HashMap<String,Double>> Nodes;
    private ArrayList<HashMap<String,Double>> Edges;
    private HashMap<Double, ArrayList<Double>> from_node;
    private HashMap<Double, ArrayList<Double>> to_node;

    public DirectedWeightedGraphClass(HashMap<String, ArrayList<HashMap<String,Double>>> h){
        this.Nodes = h.get("Nodes");
        this.Edges = h.get("Edges");
        from_node = new HashMap<>();
        to_node = new HashMap<>();
        for (int i = 0; i < Nodes.size();i++){
            from_node.put(Nodes.get(i).get("id"), new ArrayList<>());
            to_node.put(Nodes.get(i).get("id"), new ArrayList<>());
        }
        for(HashMap<String,Double> e: Edges){
            from_node.get(e.get("src")).add(e.get("dest"));
            to_node.get(e.get("dest")).add(e.get("src"));
        }
    }
    public ArrayList<HashMap<String,Double>> get_nodes(){
        return Nodes;
    }
    public ArrayList<HashMap<String,Double>> get_edges(){
        return Edges;
    }
    public HashMap<Double, ArrayList<Double>> get_from_node(){
        return from_node;
    }
    public HashMap<Double, ArrayList<Double>> get_to_node(){
        return to_node;
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
