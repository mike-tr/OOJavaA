package ex0;

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

    public static long getEdgesInKGraph(long numNodes){
        // this would return the |E(K_n)|
        return (numNodes*(numNodes-1)/2);
    }

    public boolean hasMaxEdges(int numNodes, int minCount, int minNeighbours, int maxNeighbours, int edges){
        // not lets calculate the minumum edges we need in order for the graph to be 100% connected
        // a fully connected graph has n(n-1)/2 edges we mark it as k.
        // if a given graph has |edges| > k - (n-1) => the graph also connected
        // why? because in order for a graph to have the most edges and not be connected, it must have 1 node
        // with has 0 edges hence the number of edges is k_n - (n-1)

        // but we can improve it!
        // if we know that all nodes has at least 1 connection that means that in order
        // for graph to be not connected (and numNodes num of edges) it must has 2 nodes with edge between them
        // and not connected to the other n-2 nodes.
        // hence |E| <= K_(n-2) - 2

        // denote if w is the minimum edges for any node, then
        // the graph is fully connected if |E| > K_(n-1-w)+K_(w+1)

//        int minNeighbours = numNodes;
//        int maxNeighbours = 0;
//        int minCount = numNodes;
//
//        HashMap<Integer, Integer> values = new HashMap<>();
//
//        node_data current = null;
//        for (node_data node: nodes) {
//            node.setTag(-1);
//            int v = node.getNi().size();
//            if(v < minNeighbours){
//                minNeighbours = v;
//                if(v == 0){
//                    // there is a node with 0 connections
//                    return false;
//                }
//                minCount = 1;
//            }else if(v == minNeighbours){
//                minCount++;
//            }
//
//            if(v > maxNeighbours){
//                current = node;
//                maxNeighbours = v;
//            }
//
//            if(values.containsKey(v)){
//                values.put(v, values.get(v) + 1);
//            }else{
//                values.put(v, 1);
//            }
//        }

        //        String t = "";
//        for (Integer key: values.keySet()) {
//            t += " | " + key + " : " + values.get(key);
//        }
//        System.out.println(t);
//        System.out.println("num nodes : " + (numNodes + 1) + " || num edges : " + edges
//                + " || max neighbours : " + maxNeighbours + " || min neighbours : "
//                + minNeighbours + " || min count : " + minCount);


        if(maxNeighbours >= numNodes - minNeighbours){
            // if this happens that means there must be a K(minNeighbours) graph and another K(numNodes - minNeighbours)
            // and there must be a node that has edge between them.
            return true;
        }

        int x = minCount - (minNeighbours + 1);
        if(x > 0) {
            // calculate num of edges needed! even lower!
            long calc = getEdgesInKGraph(numNodes + 1 - minCount) + x * minNeighbours
                    + getEdgesInKGraph(minNeighbours + 1);
            System.out.println("max : " + calc);
            if(edges > calc){
                System.out.println("Exit 1");

                // if |E| > K(nodes - min_count) + x * min_n + K(
                return true;
            }
        }else{
            long calc = getEdgesInKGraph(numNodes - minNeighbours)
                    + getEdgesInKGraph(minNeighbours + 1);
            System.out.println("max : " + calc);
            if (edges > calc) {
                System.out.println("Exit 1");
                // if |Edges| > numberOfEdgesIn(K_(n-w)) + numOfEIn(w+1)
                return true;
            }
        }
        return false;
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

        int numNodes = nodes.size() - 1;
        int edges = data.edgeSize();
        if(numNodes > edges){
            // if the graph has less then n-1 edges its not connected
            return false;
        }

        for (node_data node: nodes) {
            node.setTag(-1);
            if(node.getNi().size() == 0){
                // there is a node with 0 connections
                return false;
            }
        }

        Queue<node_data> open = new LinkedList<>();
        node_data current = nodes.iterator().next();
        current.setTag(0);
        open.add(current);

        while (open.size() > 0){
            current = open.poll();
            if(numNodes <= 0){
                return true;
            }

            for (node_data node : current.getNi()) {
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


        Queue<PathNode> open = new LinkedList<>();
        HashMap<node_data, PathNode> hashed = new HashMap<>();



        node_data source = data.getNode(src);
        if(source == null){
            return null;
        }

        if(data.getNode(dest) == null){
            return null;
        }

        for (node_data node: data.getV()) {
            node.setTag(-1);
        }

        open.add(new PathNode(source));
        while (open.size() > 0){
            PathNode current = open.poll();
            if(current.getKey() == dest){
                return current;
            }

            for (node_data node: current.getNode().getNi()) {
                if(node == null){
                    continue;
                }
                if(node.getKey() == dest){
                    return new PathNode(node, current);
                }

                if(node.getTag() == -1){
                    // its impossible we see the same node twice
                    PathNode cpath = new PathNode(node, current);
                    open.add(cpath);
                    hashed.put(node, cpath);
                    node.setTag(0);
                }
            }
        }
        return null;
    }
}
