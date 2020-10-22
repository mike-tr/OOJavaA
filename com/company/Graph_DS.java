package com.company;

import ex0.node_data;

import java.util.*;

public class Graph_DS implements CopyableGraph {

    private HashMap<Integer, node_data> nodes = new HashMap<>();
    private HashSet<node_data> values = new HashSet<>();
    private int numEdges;
    private int numOperations;
    public Graph_DS(){
        numOperations = 0;
        numEdges = 0;
    }

    public Graph_DS(Graph_DS graph){

        HashMap<node_data, node_data> copies = new HashMap<>();
        for (node_data node: graph.getV()) {
            node_data copy;
            if(node instanceof CopyableNode){
                copy = ((CopyableNode) node).getDeepCopy();
            }else{
                copy = new NodeData(node);
            }
            addNode(copy);
            copies.put(node, copy);
        }


        for (node_data node: graph.getV()) {
            node_data copy = copies.get(node);
            for(node_data ni: node.getNi()){
                node_data copy_ni = copies.get(ni);
                connect(copy.getKey(),copy_ni.getKey());
            }
        }
        if(this.numEdges != graph.numEdges){
            System.out.println("something went wrong while copying");
        }
        numOperations = graph.numOperations;
    }

    @Override
    public String toString() {
//        String data = "[";
//        for (node_data node: values) {
//            data += " " + node.toString();
//        }
//        data += " ]";
        return "nodes : " + nodeSize() + " || edges : " + edgeSize() + " || MC : " + getMC() + "\n";
    }

    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        node_data node = getNode(node1);
        if(node != null){
            return node.hasNi(node2);
        }
        return false;
    }

    @Override
    public void addNode(node_data n) {
        if(!nodes.containsKey(n.getKey())){
            nodes.put(n.getKey(), n);
            values.add(n);

            numOperations++;
        }
    }

    @Override
    public void connect(int node1, int node2) {
        if(node1 == node2){
            return;
        }

        node_data first = getNode(node1);
        if(first == null){
            //first = new NodeData(node1);
            //addNode(first);
            return;
        }

        node_data second = getNode(node2);
        if(second == null){
            //second = new NodeData(node2);
            //addNode(second);
            return;
        }

        if(first.hasNi(node2) && second.hasNi(node1)){
            // in case there is one sided connection with shouldn't be possible,
            // we would consider it as a new connection
            return;
        }

        first.addNi(second);
        second.addNi(first);
        numEdges++;
        numOperations++;
    }

    @Override
    public Collection<node_data> getV() {
        return values;
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        node_data node = getNode(node_id);
        if(node != null){
            return node.getNi();
        }
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        node_data node = getNode(key);
        if(node != null){
            for (int n: nodes.keySet()) {
                removeEdge(key, n);
            }
            values.remove(node);
            nodes.remove(key);

            numOperations++;
        }
        return node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            node_data first = getNode(node1);
            node_data second = getNode(node2);

            first.removeNode(second);
            second.removeNode(first);

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

    @Override
    public CopyableGraph getDeepCopy() {
        return new Graph_DS(this);
    }
}
