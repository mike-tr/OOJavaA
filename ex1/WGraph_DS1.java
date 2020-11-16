package ex1;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS1 extends WGraphBasics {
    // this is  a failed class as serialization cannot be done,
    // there is cyclic dependencies, in this class!

    private class Node implements node_info, Serializable {
        private class EdgeData implements Serializable {
            private double weight;
            private Node dest;
            private Node parent;

            private boolean dead = false;
            public EdgeData(Node dest, double weight){
                this.dest = dest;
                this.weight = weight;
                this.parent = Node.this;

//                // add edge to edge data
                parent.edges.put(dest.key, this);
                dest.edges.put(parent.key, this);
//
//                //add to neighbour list, this will be O(1) return time.
                parent.neighbours.add(dest);
                dest.neighbours.add(parent);

                edgeNum++;
                actionMade++;
            }

            public double getWeight(){
                return this.weight;
            }

            public void setWeight(double weight){
                this.weight = weight;
                actionMade++;
            }

            public void removeEdge(){
                if(dead){
                    return;
                }

                parent.neighbours.remove(dest);
                dest.neighbours.remove(parent);

                parent.edges.remove(dest.key);
                dest.edges.remove(parent.key);

                edgeNum--;
                actionMade++;

                dead = true;
            }
        }
        private int key;
        private transient String info = "";
        private transient double tag = 0;

        private HashMap<Integer, EdgeData> edges = new HashMap<>();
        private Collection<node_info> neighbours = new HashSet<>();
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

        public boolean addEdge(Node target, double weight){
            int tKey = target.getKey();
            if(tKey == getKey()){
                return false;
            }

            if(this.hasEdge(tKey)){
                edges.get(target.getKey()).setWeight(weight);
                return false;
            }


            // create an edge with the given weight, and add it to both sides.
            // as this is an unidirectional graph.
            new EdgeData(target, weight);
            return true;
        }

        public boolean removeEdge(int target){
            EdgeData edge = edges.remove(target);
            if(edge != null){
                edge.removeEdge();
                return true;
            }
            return false;
        }

        public boolean hasEdge(int neighbour){
            return edges.containsKey(neighbour);
        }

        public EdgeData getEdge(int neighbour){
            return edges.get(neighbour);
        }

        public void removeNode(){
            Collection<EdgeData> ed = new HashSet<>(edges.values());
            for (EdgeData edge: ed) {
                edge.removeEdge();
            }

            //neighbours = new LinkedList<>();
            neighbours = null;
            edges = new HashMap<>();
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

    private HashMap<Integer,Node> nodes;
    private int actionMade;
    private int edgeNum;

    public WGraph_DS1(){
        init();
    }

    public WGraph_DS1(weighted_graph graph){
        super(graph);
    }

    @Override
    public WGraphBasics getDeepCopy() {
        return new WGraph_DS1(this);
    }

    @Override
    protected void init() {
        // initializing on start.
        nodes = new HashMap<>();
        actionMade = 0;
        edgeNum = 0;
    }

    @Override
    protected void setMC(int mc) {
        actionMade = mc;
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
        Node first = nodes.get(node1);
        if(first != null){
            return first.hasEdge(node2);
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        Node first = nodes.get(node1);
        if(first != null){
            Node.EdgeData edge = first.getEdge(node2);
            if(edge != null){
                return edge.getWeight();
            }
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(!nodes.containsKey(key)){
            nodes.put(key, new Node(key));
            actionMade++;
        }
    }

    @Override
    public void connect(int node1, int node2, double w) {
        Node first = nodes.get(node1);
        if(first != null){
            Node second = nodes.get(node2);
            if(second != null){
                first.addEdge(second, w);
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        // b.t.w this casting operation is O(1) already tested.
        // its safe because i know that all the objects in nodes are indeed node_info.
        return (Collection<node_info>) (Collection<? extends node_info>) nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        Node node = nodes.get(node_id);
        if(node != null){
            return node.neighbours;
        }
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        Node node = nodes.get(key);
        if(node != null){
            node.removeNode();
            nodes.remove(key);
            actionMade++;
        }
        return node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        Node node = nodes.get(node1);
        if(node != null) {
            node.removeEdge(node2);
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
