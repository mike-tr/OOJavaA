package ex1;

import ex0.node_data;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class WGraph_DS2 implements weighted_graph {
    private class Node implements node_info{
        private int key;
        private String info = "";
        private double tag = 0;

        public Node(int key){
            this.key = key;
        }

        @Override
        public String toString() {
            return "" + getKey();
        }

        @Override
        public int hashCode() {
            return getKey();
        }

        @Override
        public boolean equals(Object other){
            if (other == this) {
                return true;
            }

            if(other instanceof Integer){
                return this.key == (Integer) other;
            }

            if (!(other instanceof node_info)) {
                return true;
            }
            return this.getKey() == ((node_info)other).getKey();
        }

        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }
    }

    private HashMap<Integer,node_info> nodes  = new HashMap<>();
    private HashMap<Integer, HashSet<node_info>> neighbours = new HashMap<>();
    private HashMap<Tuple,Double> edges = new HashMap<>();
    private int actionMade = 0;
    private int edgeNum = 0;

    private Tuple getTuple(int x, int y){
        // this method gives us the following,
        // a tuple where tuple.x is always greater then tuple.y.
        // we always return null if x == y.
        return x == y ? null : x > y ? new Tuple(x, y) : new Tuple(y, x);
    }

    @Override
    public String toString() {
        return "nodes : " + nodeSize() + " || edges : " + edgeSize() + " || MC : " + getMC();
    }

    @Override
    public node_info getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        node_info first = nodes.get(node1);
        if(first != null){
            return neighbours.get(node1).contains(node2);
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        Tuple tuple = getTuple(node1, node2);
        if(tuple != null){
            return edges.get(tuple);
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!nodes.containsKey(key)){
            nodes.put(key, new Node(key));
            neighbours.put(key, new HashSet<>());
            actionMade++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        node_info first = nodes.get(node1);
        if(first != null){
            node_info second = nodes.get(node2);
            if(second != null){
                Tuple tuple = getTuple(node1, node2);
                edges.put(tuple, w);

                actionMade++;
                if(neighbours.get(node1).contains(second)){
                    return;
                }
                edgeNum++;

                neighbours.get(node1).add(second);
                neighbours.get(node2).add(first);
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        // b.t.w this casting operation is O(1) already tested.
        // its safe because i know that all the objects in nodes are indeed node_info.
        return nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        return neighbours.get(node_id);
    }

    @Override
    public node_info removeNode(int key) {
        node_info node = nodes.get(key);
        if(node != null){
            Collection<node_info> neighbours = new HashSet<>(this.neighbours.get(key));
            for (node_info ni: neighbours) {
                removeEdge(ni.getKey(), key);
            }

            nodes.remove(key);
            this.neighbours.remove(key);
            actionMade++;
        }
        return node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        Tuple tuple = getTuple(node1, node2);
        if(edges.containsKey(tuple)){
            edges.remove(tuple);
            neighbours.get(node1).remove(getNode(node2));
            neighbours.get(node2).remove(getNode(node1));

            actionMade++;
            edgeNum--;
        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeNum;
    }

    @Override
    public int getMC() {
        return actionMade;
    }
}
