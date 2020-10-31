package com.company;

import ex0.graph;
import ex0.graph_algorithms;
import ex0.node_data;

import java.nio.file.Path;
import java.util.*;

public class Graph_Algo implements graph_algorithms {
    private graph data;

    public Graph_Algo(){
        // create new empty graph
        data = new Graph_DS();
    }

    public Graph_Algo(graph g){
        init(g);
    }

    @Override
    public void init(graph g) {
        this.data = g;
    }

    @Override
    public graph copy() {
        if(data != null)
            return new Graph_DS(data);
        return null;
    }


    @Override
    public boolean isConnected() {
        //boolean d = isConnectedBF();
        //System.out.println(d);
        //return isConnectedRemovalApproach();
        return isConnectedBF();
    }

    public boolean isConnectedBF() {
        // this is another implementation of the same code,
        // this one works a little bit faster
        // set all tags to -1
        // start with a node set tag to 0
        // go over neighbours
        // if tag == 0, skip if tag is -1, add to queue and set as 0. counter --
        // repeat for every node in queue.
        // stop when queue.size = 0 hence not connected
        // if counter <= 0 stop -> is connected!

        Collection<node_data> nodes = data.getV();
        if(nodes.size() < 2){
            return true;
        }

        for (node_data node: nodes) {
            node.setTag(-1);
        }

        ArrayList<node_data> open = new ArrayList<>();
        node_data current = nodes.iterator().next();
        current.setTag(0);
        open.add(current);

        int max = nodes.size() - 1;

        while (open.size() > 0){
            current = open.remove(0);

            for (node_data node : current.getNi()) {
                //if(!open.contains(node) && !closed.contains(node)){
                if(node.getTag() < 0){
                    node.setTag(0);
                    open.add(node);
                    max--;
                }
            }

            if(max <= 0){
                return true;
            }
        }

        return false;
    }

    public boolean isConnectedRemovalApproach() {
        // method works really simple
        // start with the first node in the set
        // go over all neighbours
        // if neighbour is in nodes list, remove it from there and add to queue
        // if not skip
        HashSet<node_data> nodes = new HashSet<>(data.getV());
        if(nodes.size() < 2){
            return true;
        }

        ArrayList<node_data> open = new ArrayList<>();

        node_data current = nodes.iterator().next();
        nodes.remove(current);
        open.add(current);

        while (open.size() > 0){
            current = open.remove(0);

            for (node_data node : current.getNi()) {
                if (nodes.contains(node)) {
                    open.add(node);
                    nodes.remove(node);
                }
            }

            if(nodes.size() <= 0){
                // if the size of nodes is 0, that means all the nodes connected
                return true;
            }
        }

        return false;
    }

    @Override
    public int shortestPathDist(int src, int dest) {
        PathNode current = FindShortestPath(src, dest);
        return current != null ? current.getDistance() : -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        PathNode current = FindShortestPath(src, dest);
        if(current != null){
            List<node_data> path = new ArrayList<>();
            while (current != null){
                path.add(current.getNode());
                if(current.getKey() == src){
                    break;
                }
                current = current.getParent();
            }
            return path;
        }
        System.out.println("No path");
        return new ArrayList<>();
    }

    public PathNode FindShortestPath(int src, int dest){
        // this is an A* algorithm, not really more like bfs.
        // i know how to implement it from long long ago.

        // add dest to open
        // loop over nodes in open
        // on given node, if its not in closed or open, we add to open and set distance + parent
        // if at any point we find that neighbour is dest we stop and return the "PathNode"
        // with hold distance + traceback to src.
        // hence this method gives us distance + path.
        List<node_data> path = new ArrayList<>();

        List<PathNode> open = new ArrayList<>();
        HashSet<Integer> closed = new HashSet<>();

        HashMap<node_data, PathNode> hashed = new HashMap<>();

        node_data source = data.getNode(src);
        if(source == null){
            return null;
        }

        open.add(new PathNode(source));
        while (open.size() > 0){
            PathNode current = open.remove(0);
            closed.add(current.getKey());

            if(current.getKey() == dest){
                return current;
            }

            for (node_data node: current.getNode().getNi()) {
                if(node == null){
                    continue;
                }
                if(closed.contains(node.getKey())){
                    continue;
                }
                if(node.getKey() == dest){
                    return new PathNode(node, current);
                }

                PathNode cpath = hashed.get(node);
                if(cpath != null){
                    cpath.tryNewPath(current);
                }else{
                    cpath = new PathNode(node, current);
                    open.add(cpath);
                    hashed.put(node, cpath);
                }
            }
        }
        return null;
    }
}
