import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraphClass g;
    private HashMap<Integer,HashMap<Integer,HashMap<String,Double>>> dijkstra_map;

    public DirectedWeightedGraphAlgorithmsClass(DirectedWeightedGraphClass g){
        this.g = g;
        this.dijkstra_map = new HashMap<>();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = (DirectedWeightedGraphClass) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (!dijkstra_map.containsKey(src)){
            dijkstra_map.put(src,dijkstra(src));
        }
        return dijkstra_map.get(src).get(dest).get("total_w");
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        ArrayList<NodeData> ans = new ArrayList<>();
        if (!dijkstra_map.containsKey(src)){
            dijkstra_map.put(src,dijkstra(src));
        }
        ans.add(g.getNode(dest));
        NodeData current_n = g.getNode((dijkstra_map.get(src).get(dest).get("previous").intValue()));
        ans.add(0,current_n);
        while (current_n.getKey() != src){
            current_n = g.getNode((dijkstra_map.get(src).get(current_n.getKey()).get("previous").intValue()));
            ans.add(0,current_n);
        }
        return ans;
    }

    public HashMap<Integer,HashMap<String,Double>> dijkstra(int src){
        HashMap<Integer,HashMap<String,Double>> dijkstra_table = new HashMap<>();
        Iterator<NodeData> n_iter = g.nodeIter();
        ArrayList<Integer> unvisited = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        while (n_iter.hasNext()){
            NodeData n = n_iter.next();
            dijkstra_table.put(n.getKey(),new HashMap<>());
            if (n.getKey() == src){
                dijkstra_table.get(n.getKey()).put("total_w", (double) 0);
            }
            else{
                dijkstra_table.get(n.getKey()).put("total_w", Double.MAX_VALUE);
            }
            dijkstra_table.get(n.getKey()).put("previous", null);
            unvisited.add(n.getKey());
        }
        while (!unvisited.isEmpty()){
            int shortest_node_from_source = 0;
            double shortest_dist = Double.MAX_VALUE;
            for (int i = 0; i < unvisited.size(); i++) {
                int key = (int)dijkstra_table.keySet().toArray()[unvisited.get(i)];
                if (dijkstra_table.get(key).get("total_w") < shortest_dist){
                    shortest_dist = dijkstra_table.get(key).get("total_w");
                    shortest_node_from_source = key;
                }
            }
            ArrayList<Integer> unvisited_neighbours = new ArrayList<>();
            Iterator<EdgeData> e_iter = g.edgeIter(shortest_node_from_source);
            HashMap<Integer,Double> dist_from_prev = new HashMap<>();
            while (e_iter.hasNext()){
                EdgeData e = e_iter.next();
                if (!visited.contains(e.getDest())){
                    unvisited_neighbours.add(e.getDest());
                    dist_from_prev.put(e.getDest(),e.getWeight());
                }
            }

            for (int i = 0; i < unvisited_neighbours.size(); i++) {
                int current_n = unvisited_neighbours.get(i);
                double new_dist = dijkstra_table.get(shortest_node_from_source).get("total_w") + dist_from_prev.get(current_n);
                if (new_dist < dijkstra_table.get(current_n).get("total_w")){
                    dijkstra_table.get(current_n).replace("total_w",new_dist);
                    dijkstra_table.get(current_n).replace("previous",(double)shortest_node_from_source);
                }
            }
            unvisited.remove((Object)shortest_node_from_source);
            visited.add(shortest_node_from_source);
        }
        return dijkstra_table;
    }

    @Override
    public NodeData center() {
        Iterator<NodeData> nodeiter = g.nodeIter();
        while (nodeiter.hasNext()){
            NodeData n = nodeiter.next();
            if (!dijkstra_map.containsKey(n.getKey())){
                dijkstra_map.put(n.getKey(),dijkstra(n.getKey()));
            }
        }
        int center = 0;
        double min_max_w = Double.MAX_VALUE;
        for (int i = 0; i < dijkstra_map.size(); i++) {
            int key = (int) dijkstra_map.keySet().toArray()[i];
            double max_w = 0;
            for (int j = 0; j < dijkstra_map.get(key).size(); j++) {
                int current_key = (int)dijkstra_map.get(key).keySet().toArray()[j];
                if (max_w < dijkstra_map.get(key).get(current_key).get("total_w")){
                    max_w = dijkstra_map.get(key).get(current_key).get("total_w");
                }
            }
            if (max_w < min_max_w){
                center = key;
                min_max_w = max_w;
            }
        }
        return g.getNode(center);
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        Iterator<NodeData> nodeiter = g.nodeIter();
        while (nodeiter.hasNext()){
            NodeData n = nodeiter.next();
            if (!dijkstra_map.containsKey(n.getKey())){
                dijkstra_map.put(n.getKey(),dijkstra(n.getKey()));
            }
        }
        List<NodeData> quickest_path = new ArrayList<>();
        double smallest_w = Double.MAX_VALUE;
        for (int i = 0; i < cities.size()-1; i++) {
            for (int j = i+1; j < cities.size(); j++) {
                List<NodeData> best_path = shortestPath(cities.get(i).getKey(),cities.get(j).getKey());
                if (best_path.containsAll(cities)){
                    double path_w = dijkstra_map.get(cities.get(i).getKey()).get(cities.get(j).getKey()).get("total_w");
                    if (path_w < smallest_w){
                        smallest_w = path_w;
                        quickest_path = new ArrayList<>(best_path);
                    }
                }
            }
        }
        if (quickest_path.size() == 0){
            List<NodeData> absolute_quickest_path = new ArrayList<>();
            double absolute_w = Double.MAX_VALUE;
            List<NodeData> visited = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                absolute_w = Double.MAX_VALUE;
                visited = new ArrayList<>();
                NodeData start = cities.get(i);
                quickest_path = new ArrayList<>();
                quickest_path.add(start);
                visited.add(start);
                double path_w = 0;
                NodeData current_n = start;
                for (int j = 0; j < cities.size(); j++) {
                    NodeData closet_one = null;
                    double w = Double.MAX_VALUE;
                    for (int l = 0; l < cities.size(); l++) {
                        double current_w = dijkstra_map.get(current_n.getKey()).get(cities.get(l).getKey()).get("total_w");
                        if (!visited.contains(cities.get(l)) && current_w < w){
                            closet_one = cities.get(l);
                            w = current_w;
                        }
                    }
                    if (closet_one != null){
                        visited.add(closet_one);
                        List<NodeData> path = shortestPath(current_n.getKey(),closet_one.getKey());
                        path = path.subList(1,path.size());
                        quickest_path.addAll(path);
                        path_w += w;
                        current_n = closet_one;
                    }
                }
                if (path_w < absolute_w){
                    absolute_w = path_w;
                    absolute_quickest_path = new ArrayList<>(quickest_path);
                }
            }
            quickest_path = new ArrayList<>(absolute_quickest_path);
        }
        if (quickest_path.size() == 0){
            return null;
        }
        Collections.reverse(quickest_path);
        return quickest_path;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
