package com.company;

import ex0.NodeData;
import ex0.node_data;

import java.util.Collection;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        testCastTime(10000);
        testCastTime(100000);
        testCastTime(1000000);
        testCastTime(10000000);
    }

    public static void testCastTime(int size){
        Collection<NodeData> nodes = new HashSet<>();
        for (int i = 0; i < size; i++) {
            nodes.add(new NodeData());
        }

        MyTimer.Start();
        Collection<node_data> nd = (Collection<node_data>)(Collection<? extends  node_data>) nodes;
        MyTimer.printTimeElapsed("test size : " + size);

    }
}
