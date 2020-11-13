package ex1;


import com.company.MyTimer;
import ex0.node_data;

import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    weighted_graph graph;
    public WGraph_Algo(weighted_graph graph){
        init(graph);
    }
    @Override
    public void init(weighted_graph g) {
        graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return null;
    }

    @Override
    public weighted_graph copy() {
        return graph;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        WPathNode path = calculateShortestPath(src, dest);
        return path != null ? path.getDistance() : -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        WPathNode pathN = calculateShortestPath(src, dest);
        if(pathN != null){
            List<node_info> path = new ArrayList<>();
            while (pathN != null){
                path.add(0, pathN.getNode());
                if(pathN.getKey() == src){
                    break;
                }
                pathN = pathN.getParent();
            }
            return path;
        }
        return null;
    }

    public WPathNode calculateShortestPath(int src, int dest) {
        //PriorityQueue<WPathNode> open = new PriorityQueue<WPathNode>();
        PriorityQueue<WPathNode> open = new PriorityQueue<WPathNode>(new Comparator<WPathNode>() {
            @Override
            public int compare(WPathNode o1, WPathNode o2) {
                return o1.getDistance() > o2.getDistance() ? 1 : -1;
            }
        });

        HashMap<node_data, WPathNode> hashed = new HashMap<>();

        node_info source = graph.getNode(src);
        if(source == null){
            return null;
        }

        if(graph.getNode(dest) == null){
            return null;
        }

        for (node_info node: graph.getV()) {
            node.setTag(-1);
        }

        MyTimer.Start();
        open.add(new WPathNode(source));
        while (open.size() > 0){
            WPathNode current = open.poll();

            if(current.getKey() == dest){
                MyTimer.printTimeElapsed("fpath " + src + ", " + dest);
                return current;
            }
            int key = current.getKey();
            current.getNode().setTag(0);

            for (node_info node: graph.getV(key)) {
                if(node.getTag() == 0){
                    continue;
                }
                double weight = graph.getEdge(key, node.getKey());
                //double weight = 1;
                WPathNode pnode = hashed.get(node.getKey());
                if(pnode != null){
                    if(pnode.updatePath(current, weight)){
                        open.remove(pnode);
                        open.add(pnode);
                    }
                }else{
                    pnode = new WPathNode(node, current, weight);
                    open.add(pnode);
                }
            }
        }
        return null;
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
