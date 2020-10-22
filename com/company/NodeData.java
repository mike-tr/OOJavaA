package com.company;

import ex0.node_data;

import javax.xml.soap.Node;
import java.util.Collection;
import java.util.HashSet;

public class NodeData implements CopyableNode {
    private static int nextID;
    private int key;
    private HashSet<node_data> neighbors = new HashSet<>();
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

        if (!(other instanceof node_data)) {
            return true;
        }
        return this.getKey() == ((node_data)other).getKey();
    }

    @Override
    public Collection<node_data> getNi() {
        return neighbors;
    }

    @Override
    public boolean hasNi(int key) {
        return neighbors.contains(new NodeData(key));
    }

    @Override
    public void addNi(node_data t) {
        if(t.equals(this) || neighbors.contains(t)){
            return;
        }
        neighbors.add(t);
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

    @Override
    public CopyableNode getDeepCopy() {
        return new NodeData(this);
    }
}
