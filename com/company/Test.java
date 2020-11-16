package com.company;

import ex0.NodeData;
import ex0.node_data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Test {

//    @Override
//    public int hashCode() {
//        return key;
//    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof Test){
            return ((Test) obj).key == key;
        }
        return false;
    }

    int key = 0;
    Test(int key){
        this.key = key;
    }

    public static void main(String[] args) {

        HashMap<Test, Integer> test = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            test.put(new Test(i), i);
        }
        System.out.println("test[5] = " + test.get(new Test(5)) + " || size-test = " + test.size());
        System.out.println(new Test(5).equals(new Test(5)));
//        testCastTime(10000);
//        testCastTime(100000);
//        testCastTime(1000000);
//        testCastTime(10000000);
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
