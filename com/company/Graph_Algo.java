package com.company;

import com.sun.javafx.sg.prism.NodePath;
import ex0.graph;
import ex0.graph_algorithms;
import ex0.node_data;

import java.nio.file.Path;
import java.util.*;

public class Graph_Algo implements graph_algorithms {
    private graph data;

    public Graph_Algo(graph g){
        init(g);
    }

    @Override
    public void init(graph g) {
        this.data = g;
    }

    @Override
    public graph copy() {
        if(data instanceof CopyableGraph){
            return ((CopyableGraph)data).getDeepCopy();
        }
        // honestly have no idea how to pass something like dat!
        return null;
    }

    @Override
    public boolean isConnected() {
        Collection<node_data> nodes = data.getV();
        if(nodes.size() < 2){
            return true;
        }

        ArrayList<node_data> open = new ArrayList<>();
        HashSet<node_data> closed = new HashSet<>();



        node_data current = nodes.iterator().next();
        open.add(current);

        while (open.size() > 0){
            current = open.remove(0);
            closed.add(current);

            if(closed.size() >= data.nodeSize()){
                return true;
            }

            for (node_data node: current.getNi()) {
                if(open.contains(node) || closed.contains(node)){
                    continue;
                }
                open.add(node);
            }
        }

        return false;
    }

    @Override
    public int shortestPathDist(int src, int dest) {
        PathNode current = FindShortestPath(src, dest);
        if(current != null){
            int distance = 0;
            System.out.println("????");
            while (current != null){
                //System.out.println(current.getKey());
                if(current.getKey() == src){
                    break;
                }
                current = current.getParent();
                distance++;
            }
            return distance;
        }
        return Integer.MAX_VALUE;
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
        // this is an A* algorithm
        // i know how to implement it from long long ago.
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
            System.out.println(current.getNode());

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
