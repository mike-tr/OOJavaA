package com.company;

import java.lang.reflect.Method;

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
    public static void printTimeElapsed(String name){
        System.out.println(name + "Time elapsed : " + getTimeElapsed() + "ms");
    }

    public static void printTime(long startTime, String name){
        System.out.println(name + " Time elapsed : " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
