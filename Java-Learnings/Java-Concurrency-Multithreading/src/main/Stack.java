package main;

public class Stack
{
    private int[] array;
    private int stackTop;

    Object lock;

    public Stack(int capacity)
    {
        array = new int[capacity];
        stackTop = -1;
        lock = new Object();
    }

    // Push and pop are changing the state of current Object (affecting stackTop which is a shared resource),
    // you cannot allow multiple threads to change your state in such a way that will run into inconsistency
    // your thread have to apply a lock(image as a room) on shared resource when it is accessing then once it's done it should unlock and give it to JVM
    // That will decide which thread shall get next access based on scheduling algo(We studied in OS)

    // t1, t2, t3 (Different threads)
    public synchronized boolean push(int element)
    {
        // synchronized block -- this is a critical section (critical part of code) and I won't allow more than one thread to access this block of code
        // In java any object(not primitive data type) can be used as a lock ex - new Integer() will work.
//        synchronized(lock) // Anything new String("ss"), new Object(), new Integer()....
//        {

        // Making a method synchronized is equalled to wrapping all of it's code in synchronized(this){} by JVM, the lock will be on the instance of the class

        // How do we synchronize in static methods? As they are not associated with instance of any class
        // We synchronize in class lock itself, that is it will wrap it's all code in synchronized(<className>.class)
            if(isFull()) return false;
            // we are increasing pusher thread to 0 -- non -ve
            ++ stackTop;
            try
            {
                // So when the pusher thread is sleeping, popper() thread might, pop() and decrease stackTop value to -ve
                Thread.sleep( 1000 );
            } catch ( Exception e ) { }
            // and while pushing we might get index out of bound as we have also not put if check here
            array[stackTop] = element;
            return true;
//        }
    }

    // t1, t4, t5 (Different threads)
    public synchronized int pop()
    {
        // synchronized block
        // since both synchornised method push() and pop() are bounded by same lock object, so therefore whichever thread gets access to this lock only that thread can only
        // be able to access any of these methods, other threads will have to wait (These methods might be completely diff to each other)
//        synchronized(lock)
//        {
            if(isEmpty()) return Integer.MIN_VALUE;
            int obj = array[stackTop];
            array[stackTop] = Integer.MIN_VALUE;

            try {Thread.sleep( 1000 );} catch ( Exception e ) {}

            stackTop--;
            return obj;
//        }
    }

    private boolean isFull()
    {
        return stackTop == array.length-1;
    }

    public boolean isEmpty()
    {
        return stackTop < 0;
    }
}
