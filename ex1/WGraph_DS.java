package ex1;

import ex0.node_data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class WGraph_DS implements weighted_graph {
    private class Node implements node_info{
        private class EdgeData {
            private double weight;
            private Node dest;
            private Node parent;

            private boolean dead = false;
            public EdgeData(Node dest, double weight){
                if(hasEdge(dest.getKey())){
                    System.out.println("cannot create an edge that already exist");
                    dead = true;
                    return;
                }

                this.dest = dest;
                this.weight = weight;
                this.parent = Node.this;

                // add edge to edge data
                parent.edges.put(dest.key, this);
                dest.edges.put(parent.key, this);

                //add to neighbour list, this will be O(1) return time.
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
                actionMade--;

                dead = true;
            }
        }
        private int key;
        private String info = "";
        private double tag = 0;

        private HashMap<Integer, EdgeData> edges = new HashMap<>();
        private HashSet<node_info> neighbours = new HashSet<>();
        public Node(int key){
            this.key = key;
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
            return this.getKey() == ((node_data)other).getKey();
        }

        public boolean addEdge(Node target, double weight){
            if(this.equals(target)){
                return false;
            }

            int tKey = target.getKey();
            if(this.hasEdge(tKey)){
                edges.get(target.getKey()).setWeight(weight);
                return false;
            }

            // create an edge with the given weight, and add it to both sides.
            // as this is an unidirectional graph.
            EdgeData ed = new EdgeData(target, weight);
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

            neighbours = new HashSet<>();
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

    private HashMap<Integer,Node> nodes  = new HashMap<>();
    private int actionMade = 0;
    private int edgeNum = 0;

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
            Node second = nodes.get(node1);
            if(second != null){
                if(first.addEdge(second, w)){
                    actionMade++;
                }
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
            nodes.remove(node.getKey());
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
