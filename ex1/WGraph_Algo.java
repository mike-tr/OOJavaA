package ex1;

import ex0.node_data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    public WGraph_Algo(){
        graph = new WGraph_DS();
    }
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
        return graph;
    }

    @Override
    public weighted_graph copy() {
        if(graph instanceof WGraphBasics){
            System.out.println("Making WGraph copy");
            //return ((WGraphBasics) graph).saveGraph();
            //return new WGraph_DS3(graph);
            return ((WGraphBasics)graph).getDeepCopy();
        }
        return graph;
    }

    @Override
    public boolean isConnected() {
        Collection<node_info> nodes = graph.getV();
        if(nodes.size() < 2){
            return true;
        }

        int numNodes = nodes.size() - 1;
        int edges = graph.edgeSize();
        if(numNodes > edges){
            // if the graph has less then n-1 edges its not connected
            return false;
        }

        for (node_info node: nodes) {
            node.setTag(-1);
            if(graph.getV(node.getKey()).size() == 0){
                return false;
            }
        }

        ArrayDeque<node_info> open = new ArrayDeque<>();
        node_info current = nodes.iterator().next();
        current.setTag(0);
        open.add(current);

        while (open.size() > 0){
            current = open.poll();
            if(numNodes <= 0){
                return true;
            }
            int key = current.getKey();
            for (node_info node : graph.getV(key)) {
                //if(!open.contains(node) && !closed.contains(node)){
                if(node.getTag() == -1){
                    node.setTag(0);
                    open.add(node);
                    numNodes--;
                }
            }
        }

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
//        PriorityQueue<WPathNode> open = new PriorityQueue<>(new Comparator<WPathNode>() {
////            @Override
////            public int compare(WPathNode o1, WPathNode o2) {
////                return o1.getDistance() > o2.getDistance() ? 1 : -1;
////            }
////        });
        PriorityQueue<WPathNode> open = new PriorityQueue<>(
                (node1, node2) -> node1.getDistance() > node2.getDistance() ? 1 : -1);


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

        open.add(new WPathNode(source));
        while (open.size() > 0){
            WPathNode current = open.poll();

            if(current.getKey() == dest){
                return current;
            }
            int key = current.getKey();
            current.getNode().setTag(0);

            for (node_info node: graph.getV(key)) {
                if(node.getTag() == 0){
                    continue;
                }
                double weight = graph.getEdge(key, node.getKey());
                WPathNode pathNode = hashed.get(node.getKey());
                if(pathNode != null){
                    if(pathNode.updatePath(current, weight)){
                        open.remove(pathNode);
                        open.add(pathNode);
                    }
                }else{
                    pathNode = new WPathNode(node, current, weight);
                    open.add(pathNode);
                }
            }
        }
        return null;
    }

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream myFile = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(myFile);
            oos.writeObject(graph);

            oos.close();
            myFile.close();
            return true;
        }catch (Exception ex){
            System.out.println(ex.fillInStackTrace());
        }
        return false;
    }

    @Override
    public boolean load(String file) {
        try{
            FileInputStream myFile = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(myFile);
            weighted_graph graph = (weighted_graph)ois.readObject();

            ois.close();
            myFile.close();

            if(graph != null) {
                this.graph = graph;
                return true;
            }
        }
        catch (Exception error){
            error.printStackTrace();
        }
        return false;
    }
}
