package com.helycopternicht.epocket.client.tasks;

import java.util.concurrent.atomic.AtomicInteger;

public class QueryCounter {
    private static AtomicInteger counter = new AtomicInteger();
    public static AtomicInteger getCounter() {
        return counter;
    }
}