package com.company;

import ex0.graph;
import ex0.node_data;

import java.util.*;

public class Graph_DS implements graph {
    private HashMap<Integer, node_data> nodes = new HashMap<>();
    private int numEdges;
    private int numOperations;
    public Graph_DS(){
        numOperations = 0;
        numEdges = 0;
    }

    /**
     * this is a copy contractor
     * we loop over all the nodes and create a new node copy
     * then we loop over all the nodes once more from the origin
     * and go over their neighbours to create the same connections
     *
     * complexity O(2n + e) - n number of nodes , e number of edges
     * we must go over all nodes + edges
     * @param graph
     */
    public Graph_DS(graph graph){
        // go over all the nodes in origin and copy them
        Collection<node_data> original = graph.getV();
        for (node_data node: original) {
            addNode(new NodeData(node));
        }

        for (node_data node: original) {
            int k = node.getKey();
            for(node_data ni: node.getNi()){
                connect(k, ni.getKey());
            }
        }


        if(this.numEdges != graph.edgeSize()){
            System.out.println("something went wrong while copying");
        }
        numOperations = graph.getMC();
    }

    @Override
    public String toString() {
        return "nodes : " + nodeSize() + " || edges : " + edgeSize() + " || MC : " + getMC();
    }

    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        // iff there might be neighbour
        node_data node = getNode(node1);
        if(node != null){
            return node.hasNi(node2);
        }
        return false;
    }

    @Override
    public void addNode(node_data n) {
        // if the node is not in the graph add it.
        if(!nodes.containsKey(n.getKey())){
            // maybe we want to always create a copy of a node instead of using the original?
            // from my understanding this is node_data, with means it should be different
            // for different graphs, i don't want to add "outside" edges into my new graph.
            // assume i create a sub graph of graph A, with nodes a,b,c
            // i will pass those nodes, but i do not want to also pass the same edges!
            // with means i will have the same nodes but different data, hence new node_data!
            node_data nn = new NodeData(n); // i wont new node_data with the same key.
            nodes.put(nn.getKey(), nn);
            numOperations++;
        }
    }

    @Override
    public void connect(int node1, int node2) {
        // if the target = origin we skip
        if(node1 == node2){
            return;
        }

        node_data first = getNode(node1);
        if(first == null){
            // source doesn't exist we skip
            return;
        }

        node_data second = getNode(node2);
        if(second == null){
            // target doesn't exist we skip
            return;
        }

        if(first.hasNi(node2) && second.hasNi(node1)){
            // if for some reason there is one sided connection,
            // we would consider it as no connection hence,
            // we want to create an edge.
            return;
        }

        // as the graph is two sided, we connect both a to b and b to a.
        first.addNi(second);
        second.addNi(first);
        numEdges++;
        numOperations++;
    }

    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        node_data node = getNode(node_id);
        if(node != null){
            // only if the node exist we return a list
            return node.getNi();
        }
        return null; // either null or empty list would do, i like null better as node is also null.
    }

    @Override
    public node_data removeNode(int key) {
        // if the node exist we remove it.
        node_data node = getNode(key);
        if(node != null){
            // go over all the neighbours and remove the edge between
            // neighbour and target.
            Collection<node_data> neighbours = new HashSet<>(node.getNi());
            for (node_data ni: neighbours) {
               removeEdge(ni.getKey(), key);
            }
            nodes.remove(key);
            numOperations++;
        }
        return node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            // straight forward if the edge exist, remove it.
            node_data first = getNode(node1);
            node_data second = getNode(node2);

            second.removeNode(first);
            first.removeNode(second);

            numEdges--;
            numOperations++;
        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return numEdges;
    }

    @Override
    public int getMC() {
        return numOperations;
    }
}
