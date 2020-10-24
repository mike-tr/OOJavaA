package com.company;

import ex0.graph;
import ex0.graph_algorithms;
import ex0.node_data;

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
    public boolean isConnected(int i) {
        if(i == 0){
            return isConnected1();
        }
        return isConnected2();
    }

    public boolean isConnected1() {
        // this method is Very slow if the number of edges is low
        // on the drop side it becomes fast with more edges
        // at about *5 edges then nodes it is much faster then the other approach
        HashSet<node_data> nodes = new HashSet<>();
        nodes.addAll(data.getV());
        if(nodes.size() < 2){
            return true;
        }

        ArrayList<node_data> open = new ArrayList<>();
        HashSet<node_data> closed = new HashSet<>();



        node_data current = nodes.iterator().next();
        nodes.remove(current);
        open.add(current);

        while (open.size() > 0){
            current = open.remove(0);
            closed = new HashSet<>();
            if(nodes.size() <= 0){
                return true;
            }

            for (node_data node : nodes){
                if(current.hasNi(node.getKey())){
                    closed.add(node);
                    open.add(node);
                }
            }
            nodes.removeAll(closed);
        }

        return false;
    }

    public boolean isConnected2() {
        // this method Is VERY VERY SLOW as the number of edges is increased
        // probably about e^2 * n complexity if not worse.
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

    public boolean isConnected3() {
        // this method Is VERY VERY SLOW as the number of edges is increased
        // probably about e^2 * n complexity if not worse.
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
            while (current != null){
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
