package com.helycopternicht.epocket.client.tasks;

import java.util.concurrent.atomic.AtomicInteger;

public class QueryCounter {
    private static AtomicInteger counter = new AtomicInteger();
    private static long start;
    private static long finish;

    public static void increment() {
        counter.incrementAndGet();
    }

    public static void begin() {
        start = System.currentTimeMillis();
    }

    public static void finish() {
        finish = System.currentTimeMillis();
    }

    public static double elapsedTime() {
        return ((finish - start) / 1000.);
    }

    public static int countProcessed() {
        return counter.get();
    }

    public static double countPerSecond() {
        return (double)countProcessed() / elapsedTime();
    }
}