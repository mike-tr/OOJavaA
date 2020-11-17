package com.company;

import ex0.NodeData;
import ex0.node_data;
import ex1.Heap;
import ex1.INode;
import ex1.node_info;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Test extends INode {

    double priority = 0;
    Test(){ }

    Test(double priority){
        this.priority = priority;
    }

    public static void main(String[] args) {

        PriorityQueue<Test> queueTest = new PriorityQueue<>((a,b) -> a.priority > b.priority ? 1 : -1);

        Test t = new Test(15);
        queueTest.add(new Test(10));
        queueTest.add(t);
        queueTest.add(new Test(5));
        queueTest.add(new Test(3));
        queueTest.add(new Test(6));

        System.out.println(queueTest);
        t.setPriority(1);
        System.out.println(queueTest);
        System.out.println(queueTest.poll());
//        queueTest.remove(t);
//        queueTest.add(t);
        System.out.println(queueTest);

        queueTest.add(new Test(2));
        System.out.println(queueTest);



        System.out.println();

        Heap heap = new Heap();

        heap.add(new Test(), 10);
        heap.add(new Test(), 15);
        heap.add(new Test(), 5);
        heap.add(new Test(), 3);
        heap.add(new Test(), 6);

        System.out.println(heap);

        heap.tryUpgrade(heap.getAt(3), 1);
        System.out.println(heap);
        System.out.println(heap.poll());
        System.out.println(heap);

//
//        heap.poll();
//        System.out.println(heap);



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

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public void setPriority(double priority) {
        this.priority = priority;
    }
}
