package com.company;
/**
 * This is a very basic test class to test mainly the names of the classes & methods.
 *
 */

import ex0.*;
import ex1.WGraph_Algo;

import java.util.List;
import java.util.Random;

public class Graph_Ex0_Testo {
    static int seed = 31;
    static Random _rnd = new Random(seed);
    static int v_size = 300000;
    static int e_size = (int)(v_size * 10);
    static graph g0 = new Graph_DS(), g1;
    static graph_algorithms ga;
    public static void main(String[] args) {
        MyTimer.Start();
        MyTimer.Start(1);
        test1();
        System.out.println(g0);
        MyTimer.printTimeElapsed("init");
        test2();
        System.out.println(g0);
        test3();
        System.out.println(g0);
        MyTimer.printTimeElapsed(1,"total");
    }
    public static void test1() {

        for(int i=0;i<v_size;i++) {
            node_data n = new NodeData();
            g0.addNode(n);
        }
        int j = 0;
        while(g0.edgeSize() < e_size && j < e_size * 10) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            g0.connect(a,b);
            j++;
        }
       // System.out.println(g0);
    }
    public static void test2() {
        g0.removeEdge(9,3);
        g0.removeEdge(9,3);
        g0.removeEdge(3,9);
        g0.removeEdge(0, 9);
        g0.removeNode(0);
        g0.removeNode(0);
        g0.removeNode(2);
        g0.removeNode(8);
    }
    public static void test3() {
        ga = new Graph_Algo(g0);
        MyTimer.Start();
        g1 = ga.copy();
        MyTimer.printTimeElapsed();
        ga.init(g1);
        System.out.println(g1);


        MyTimer.Start();
        boolean isConnected = ga.isConnected();
        long timee = MyTimer.getTimeElapsed();
        System.out.println("Is connected: "+isConnected + " : time - " + timee + "ms");

        MyTimer.Start();
        int dist19 = ga.shortestPathDist(1,9);
        System.out.println("shortest path: 1,9 dist="+dist19 + " time : " + MyTimer.getTimeElapsed() + "ms");

        int dist91 = ga.shortestPathDist(9,1);
        List<node_data> sp = ga.shortestPath(1,9);
        System.out.println(g1);
        System.out.println("Is connected: "+isConnected + " : time - " + timee + "ms");
        System.out.println("shortest path: 1,9 dist="+dist19);
        System.out.println("shortest path: 9,1 dist="+dist91);
//        for (int i=0;i<sp.size();i++) {
//            System.out.println(" "+sp.get(i));
//        }
    }
    public static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    public static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
}
