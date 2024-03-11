package main;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {

    Queue<Integer> queue;

    private int capacity;

    private BlockingQueue(int cap)
    {
        queue = new LinkedList<>();
        capacity = cap;
    }

    public boolean add(int item)
    {
        synchronized (queue)
        {
            // Note while sleeping thread doesn't relinques the lock it is still holding it
            while(queue.size() == capacity) // Instead of using if, use a while loop (because let suppose after adder1 executes, adder2 agains get block
            {
                // wait when some items are removed
                try {
                    // when a thread call wait() method, it relinques the lock it does not have the lock itself anymore, and when it is awakened it again has to
                    // fight for getting the lock, so if after adder1 is completed its work and queue is full adder2 agains go in waiting state
                    queue.wait(); //adder1, adder2 (See the image - ) Any thread b/w adder1 or adder2 can get the chance first(win the lock) when they are awakend

        // when thread is notified it doesn't directly jump to running state, it is tries to acquire a lock(Blocked for lock acquisiting) before going to running state
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.add(item);
            queue.notifyAll(); // To notify all threads, to notify one thread (notify())
            return true;
        }
    }

    public int remove()
    {
        synchronized (queue)
        {
            // If first gets the lock first and size of queue is 0. And other thread are blocked until is locked
            while(queue.size()==0)
            {
                // Wait till the condition is met - whenever there is item added to the queue
                try {
                    queue.wait();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            int element = queue.poll();
            queue.notifyAll();
            return element;
        }
    }
}
