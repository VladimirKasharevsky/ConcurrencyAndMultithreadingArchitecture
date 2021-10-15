package com.epam.task3;


import java.util.*;

/**
 * Implement message bus using Producer-Consumer pattern.
 * 1.	Implement asynchronous message bus. Do not use queue implementations from java.util.concurrent.
 * 2.	Implement producer, which will generate and post randomly messages to the queue.
 * 3.	Implement consumer, which will consume messages on specific topic and log to the console message payload.
 * 4.	(Optional) Application should create several consumers and producers that run in parallel.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        Random random = new Random();

        Thread thread1 = new Thread(new Poducer(queue, random));
        Thread thread2 = new Thread(new Consumer(queue, random));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


    }
}

//////////////////
class Poducer implements Runnable {

    private Queue queue;
    private Random random;

    public Poducer(Queue queue, Random random) {
        this.queue = queue;
        this.random = random;
    }

    public void add(Queue<Integer> queue, Random random) throws InterruptedException {
        Thread.sleep(random.nextInt(1000));
        synchronized (queue) {
            int value;
            value = random.nextInt((10));
            queue.add(value);
            System.out.println("Summ of messages: " + queue.size());
            System.out.println("Add message to queue: " + value);
            queue.notify();
            queue.wait();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                add(queue, random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//////////////////
class Consumer implements Runnable {

    private Queue queue;
    private Random random;

    public Consumer(Queue queue, Random random) {
        this.queue = queue;
        this.random = random;
    }

    public void read(Queue<Integer> queue, Random random) throws InterruptedException {
        Thread.sleep(random.nextInt(3000));
        synchronized (queue) {
            System.out.println("Summ of messages for reading: " + queue.size());
            queue.forEach(integer -> {
                System.out.println("Read: " + integer);
                queue.remove(integer);
                System.out.println("Messages after reading queue: " + queue.size());
            });
            queue.notify();
            queue.wait();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                read(queue, random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
