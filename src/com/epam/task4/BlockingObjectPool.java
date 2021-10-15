package com.epam.task4;


import java.util.*;

/**
 * Create simple object pool with support for multithreaded environment.
 * No any extra inheritance, polymorphism or generics needed here, just implementation of simple class:
 */

class BlockingObjectPool {

    private int size;
    private List<Object> queue = new ArrayList<>(size);

    public BlockingObjectPool(int size) {
        this.size = size;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public synchronized Object get(int index) throws InterruptedException {
        if (queue.size() != 0) {
            return queue.get(index);
        }
        return null;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public synchronized void take(Object object) {
        queue.add(object);
    }
}

