package com.company;

import ex0.node_data;
import java.util.*;

public class NodeData implements node_data {
    private static int nextID;
    private int key;
    private HashMap<Integer, node_data> neighbors = new HashMap<>();
    //private HashSet<node_data> values = new HashSet<>();
    private String info;
    private int tag;

    public NodeData(){
        key = nextID;
        nextID++;
    }

    public NodeData(int key){
        this.key = key;
    }

    public NodeData(node_data node){
        this.key = node.getKey();
        this.tag = node.getTag();
        this.info = node.getInfo();
    }

    @Override
    public String toString() {
        return "" + this.getKey();
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        // we can say that the hashCode of a given node is just the key.
        return getKey();
    }

    /**
     * this method is used for equality check
     * given 2 nodes we would say they are equal if the key is the same!
     * useful for "searching" in list's etc...
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        if(other instanceof Integer){
            return this.key == (Integer) other;
        }

        if (!(other instanceof node_data)) {
            return true;
        }
        return this.getKey() == ((node_data)other).getKey();
    }

    @Override
    public Collection<node_data> getNi() {
        return neighbors.values();
    }

    @Override
    public boolean hasNi(int key) {
        return neighbors.containsKey(key);
    }

    @Override
    public void addNi(node_data t) {
        if(t.equals(this) || hasNi(t.getKey())){
            return;
        }
        neighbors.put(t.getKey(), t);
    }

    @Override
    public void removeNode(node_data node) {
        neighbors.remove(node);
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String newInfo) {
        info = newInfo;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int tag) {
        this.tag = tag;
    }
}
