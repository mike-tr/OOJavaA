package com.company;

public class MyTimer {
    private static long startTime;
    public static void Start(){
        startTime = System.currentTimeMillis();
    }

    public static long getTimeElapsed(){
        return System.currentTimeMillis() - startTime;
    }

    public static void printTimeElapsed(){
        System.out.println("Time elapsed : " + getTimeElapsed() + "ms");
    }
}
