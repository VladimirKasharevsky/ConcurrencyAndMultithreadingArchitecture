package com.epam.task1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create HashMap<Integer, Integer>.
 * The first thread adds elements into the map, the other go along the given map and sum the values.
 * Threads should work before catching ConcurrentModificationException.
 * Try to fix the problem with ConcurrentHashMap and Collections.synchronizedMap().
 * What has happened after simple Map implementation exchanging? How it can be fixed in code?
 * Try to write your custom ThreadSafeMap with synchronization and without.
 * Run your samples with different versions of Java (6, 8, and 10, 11) and measure the performance.
 * Provide a simple report to your mentor.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Map<Integer, Integer> map = new ConcurrentHashMap<>();
//        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> map =  Collections.synchronizedMap(new HashMap<>());
        Thread myThreadAddToMap = new Thread(new AddToMap(map));
        Thread myThreadCountValues = new Thread(new CountValues(map));
        myThreadAddToMap.start();
        myThreadCountValues.start();
        myThreadAddToMap.join();
        myThreadCountValues.join();
    }
}

class AddToMap implements Runnable {

    private Map map;

    public AddToMap(Map map) {
        this.map = map;
    }

    public void addToMap(Map map) throws InterruptedException {
        Random random = new Random();
        int b;
        for (int i = 1; i < 100; i++) {
            b = random.nextInt(10);
            map.put(i, b);
            System.out.println("key: " + i + ", value: " + b);
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        try {
            addToMap(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CountValues implements Runnable {

    private Map map;

    public CountValues(Map map) {
        this.map = map;
    }

    public void count(Map<Integer, Integer> map) throws InterruptedException {
        while (true) {
            map.values().stream().reduce(0, Integer::sum);
            System.out.println("Summ of values " + map.values().stream().reduce(0, Integer::sum));
            Thread.sleep(3000);
        }
    }

    @Override
    public void run() {
        try {
            count(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
