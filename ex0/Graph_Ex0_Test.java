package ex0;
/**
 * This is a very basic test class to test mainly the names of the classes & methods.
 *
 */

import com.company.Graph_Algo;
import com.company.Graph_DS;
import com.company.NodeData;

import java.util.List;
import java.util.Random;

public class Graph_Ex0_Test {
    static int seed = 31;
    static Random _rnd = new Random(seed);
    static int v_size = 40000;
    static int e_size = (int)(v_size * 15);
    static graph g0 = new Graph_DS(), g1;
    static graph_algorithms ga;
    public static void main(String[] args) {
        System.out.println("wtf");
        test1();
        System.out.println(g0);
        //test2();
        System.out.println(g0);
        test3();
        System.out.println(g0);
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
        System.out.println(j);
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
        g1 = ga.copy();
        ga.init(g1);
        System.out.println(g1);
        long start = System.currentTimeMillis();
        boolean isConnected = ga.isConnected(0);

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Is connected: "+isConnected + " : " + timeElapsed);

        start = System.currentTimeMillis();
        boolean isConnected2 = ga.isConnected(1);

        finish = System.currentTimeMillis();
        long timeElapsed2 = finish - start;

        System.out.println("Is connected2: "+isConnected2 + " : " + timeElapsed2);
        int dist19 = ga.shortestPathDist(1,9);
        int dist91 = ga.shortestPathDist(9,1);
        List<node_data> sp = ga.shortestPath(1,9);
        System.out.println(g1);
        System.out.println("Is connected: "+isConnected + " : " + timeElapsed);
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
