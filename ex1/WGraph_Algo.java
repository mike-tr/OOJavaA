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
//        PriorityQueue<WPathNode> open = new PriorityQueue<>(
//                (node1, node2) -> node1.getDistance() > node2.getDistance() ? 1 : -1);

        Heap<WPathNode> open = new Heap();

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

        double tag;
        open.add(new WPathNode(source), 0);
        while (open.size() > 0){
            WPathNode current = open.poll();

            if(current.getKey() == dest){
                return current;
            }
            int key = current.getKey();

            // tag -2 means we already visited this node so we skip
            current.getNode().setTag(-2);

            for (node_info node: graph.getV(key)) {
                tag = node.getTag();
                if(tag == -2){
                    continue;
                }
                double weight = graph.getEdge(key, node.getKey());

                if(tag == -1){
                    WPathNode pathNode = new WPathNode(node, current, weight);
                    open.add(pathNode, pathNode.getPriority());
                    continue;
                }

                double dist = weight + current.getDistance();
                WPathNode pn = open.getAt((int)tag);
                if(open.tryUpgrade(pn, dist) == 1){
                    pn.setParent(current);
                }

                //open.add(pathNode);
            }
        }
        return null;
    }

//    @Override
//    public double shortestPathDist(int src, int dest) {
//        // given the WPathNode we have the distance, so return it.
//        node_info path = calculateShortestPath(src, dest);
//        return path != null ? path.getTag() : -1;
//    }
//
//    @Override
//    public List<node_info> shortestPath(int src, int dest) {
//        //given WPathNode we traverse back and get the original path.
//        node_info pathN = calculateShortestPath(src, dest);
//        if(pathN != null){
//            List<node_info> path = new ArrayList<>();
//            while (pathN != null){
//                path.add(0, pathN);
//                if(pathN.getKey() == src){
//                    break;
//                }
//                pathN = graph.getNode(Integer.parseInt(pathN.getInfo()));
//            }
//            return path;
//        }
//        return null;
//    }
//
//    public node_info calculateShortestPath(int src, int dest) {
//        PriorityQueue<node_info> open = new PriorityQueue<>(
//                (node1, node2) -> node1.getTag() > node2.getTag() ? 1 : -1);
//        HashSet<Integer> closed = new HashSet<>();
//
//        node_info source = graph.getNode(src);
//        if(source == null){
//            return null;
//        }
//
//        if(graph.getNode(dest) == null){
//            return null;
//        }
//
//        for (node_info node: graph.getV()) {
//            node.setTag(-1);
//        }
//
//        node_info current;
//        double distance;
//        double distance_ni;
//        String parent;
//        open.add(source);
//        source.setTag(0);
//        while (open.used() > 0){
//            current = open.poll();
//
//            if(current.getKey() == dest){
//                return current;
//            }
//
//            int key = current.getKey();
//            closed.add(key);
//            parent = Integer.toString(key);
//            distance = current.getTag();
//            //current.setTag(0);
//
//            for (node_info node: graph.getV(key)) {
//                if(closed.contains(node.getKey())){
//                    continue;
//                }
//
//                distance_ni = graph.getEdge(key, node.getKey()) + distance;
//
//                if(node.getTag() == -1){
//                    node.setInfo(parent);
//                    node.setTag(distance_ni);
//                    open.add(node);
//                }else if(node.getTag() > distance_ni){
//                    node.setInfo(parent);
//                    node.setTag(distance_ni);
//                    open.add(node);
//                }
//            }
//        }
//        return null;
//    }

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
