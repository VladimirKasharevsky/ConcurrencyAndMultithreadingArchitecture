package com.epam.task2;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create three threads:
 * •	1st thread is infinitely writing random number to the collection;
 * •	2nd thread is printing sum of the numbers in the collection;
 * •	3rd is printing square root of sum of squares of all numbers in the collection.
 * Make these calculations thread-safe using synchronization block. Fix the possible deadlock.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();

        Thread thread1 = new Thread(new AddValues(list, random));
        Thread thread2 = new Thread(new CountValues(list));
        Thread thread3 = new Thread(new Square(list));
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

    }
}
//////////////////
class AddValues implements Runnable {

    private List list;
    private Random random;

    public AddValues(List list, Random random) {
        this.list = list;
        this.random = random;
    }

    public synchronized void add(List<Integer> list, Random random) throws InterruptedException {
        int value;
        value = random.nextInt((10));
        list.add(value);
        System.out.println("writeRandomNumber: " + value);
        Thread.sleep(1000);

    }

    @Override
    public void run() {
        while (true) {
            try {
                add(list, random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


//////////////////
class CountValues implements Runnable {

    private List list;

    public CountValues(List list) {
        this.list = list;
    }

    public synchronized void print(List<Integer> list) throws InterruptedException {
        System.out.println("printingSumOfValues " + list.stream().reduce(0, Integer::sum));
        Thread.sleep(5000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                print(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//////////////////
class Square implements Runnable {

    private List list;

    public Square(List list) {
        this.list = list;
    }

    public synchronized void print(List<Integer> list) throws InterruptedException {
        System.out.println("printingSquare " + Math.sqrt(list.stream().reduce(0, Integer::sum)));
        Thread.sleep(10000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                print(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
