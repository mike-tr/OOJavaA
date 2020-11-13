package ex1;

import ex0.node_data;

public class WPathNode implements Comparable<WPathNode> {
    private node_info node;
    private WPathNode neighbour;
    private double distance;
    private int key;

    WPathNode(node_info node){
        this.node = node;
        this.key = node.getKey();
        distance = 0;
    }

    WPathNode(node_info node, WPathNode root, double weight){
        this.node = node;
        this.key = node.getKey();
        this.neighbour = root;
        distance = root.distance + weight;
    }

    public boolean updatePath(WPathNode neighbour, double weight){
        // this method returns true if the path from the given node is shorter
        // actually this never can happen but whatever
        double distance = weight + neighbour.distance;
        if(this.distance > distance){
            this.distance = distance;
            this.neighbour = neighbour;
            return true;
        }
        return false;
    }

    public WPathNode getParent(){
        return neighbour;
    }

    public node_info getNode(){
        return node;
    }

    public int getKey(){
        return key;
    }

    public double getDistance(){
        return distance;
    }

    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        if(other instanceof Integer){
            return this.key == (Integer) other;
        }

        if (!(other instanceof WPathNode)) {
            return false;
        }
        return this.key == ((WPathNode) other).key;
    }

    @Override
    public int compareTo(WPathNode o) {
        return this.distance - o.distance >= 0 ? 1 : 0;
    }
}
