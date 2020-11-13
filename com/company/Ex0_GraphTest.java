package com.company;

import ex0.NodeData;
import ex0.node_data;
import ex1.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class represents a very simple (naive) JUnit test case
 * for Graph (Ex0)
 */
public class Ex0_GraphTest {
    private static Random _rand;
    private static long _seed;

    private static double distance = 0;
    public static void initSeed(long seed) {
        _seed = seed;
        _rand = new Random(_seed);
    }
    public static void initSeed() {
        initSeed(_seed);
    }
    @Test
    public void graphTest_0() {
        int v=20, e=15;
        weighted_graph g = graph_creator(v,e,1);

        weighted_graph_algorithms graph = new WGraph_Algo(g);
        double dist =  graph.shortestPathDist(0, e);
        System.out.println(dist + " ," + distance);
        assertEquals(dist, distance);

    }

    /////////////////////////////////////////////////
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rand.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }

    private static weighted_graph graph_creator(int v_size, int connected, int seed) {
        weighted_graph g = new WGraph_DS();
        initSeed(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }

        if(connected > v_size - 1){
            connected = v_size - 1;
        }

        for(int i=0;i<connected;i++) {
            double weight = nextRnd(0, 10.0);
            g.connect(i, i+1, weight);
            distance += weight;
        }

        return g;
    }
}
