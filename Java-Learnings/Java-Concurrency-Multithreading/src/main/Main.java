package main;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main is starting");

//        // Creating and starting thread1 using a class extending Thread
//        Thread thread1 = new Thread1("thread1");
//        // Note: Method overriding occurs in Thread1's run() method. So, after calling thread1.start(), jvm at some point calls the overrided run() method directly.
//
//        // 1) Starting thread1 is an asynchronous operation.
//        // The thread starts execution when JVM deems fit; we only request its initiation.
//        // Do not assume the exact moment when the thread starts; it is determined by the JVM.
//        // If .start() is invoked, JVM will eventually invoke the run() method at some point.
//
//        // Setting thread1 as a daemon thread (optional).
//        // thread1.setDaemon(true); // Deamon thread will be stopped the moment main thread ends not like user threads that will finish their execution even if
//        // main thread ends. Deamon thread is on mercy of JVM
//
//        // Thread1 is a child of the main thread.
//        thread1.start();
//
//        // Creating and starting thread2 using a class implementing Runnable interface.
//        Thread thread = new Thread(new Thread2(), "thread2"); // name is optional parameter here
//        // Note: Providing an instance of Runnable as a parameter.
//
//        // 2) Output indicating the start of both threads.
//        thread.start();
//
//        // Invoking run() on a newly created Thread object.
//        // Note: As target is null by default, calling .run() on the thread does not execute the Runnable target's run() method. ctrl click on run()
//        new Thread().run();
//        // To execute target.run(), ensure target is not null by passing an instance of Runnable as the target parameter.
//        // Example: new Thread(new Thread2()).start()
//
//        // When deciding between extending Thread or implementing Runnable interface to create a thread:
//        // It's recommended to implement Runnable as Java allows implementing multiple interfaces but only extends a single class.
//
//
//        // Passing the implementation of the run method as a parameter involves the compiler generating a class that implements the Runnable interface, incorporating the provided code within its run method.
//        // This process effectively manages boilerplate code on your behalf.
//        Thread thread3 = new Thread(()->
//                                    {
//                                        for(int i=0;i<5;i++)
//                                            System.out.println("Inside thread3 "+ Thread.currentThread() +", i : "+i);
//                                    }, "thread3");
//        thread3.start();

//        Stack stack = new Stack( 5 );
//        // Example when class is not thread safe, we cannot use it's method(push and pop in this case) in a multi-threaded environment
//        new Thread(() -> {
//            int counter = 0;
//            while(++counter < 10)
//                System.out.println("Pushed : "+stack.push( 100+counter ));
//        }, "Pusher").start();
//
//        new Thread(() -> {
//            int counter = 0;
//            while(++counter<10)
//                System.out.println("Popped : "+stack.pop());
//        }, "Popper").start();

//        Thread thread3 = new Thread(() ->{
//            try {
//                Thread.sleep(1); // Sleeping causes timed waiting
//                for(int i=10000;i>0;i--);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "States");
//
//        boolean check = true;
//        while (true)
//        {
//            Thread.State state = thread3.getState();
//            System.out.println(state);
//            if(state==Thread.State.TERMINATED) break;
//            if(check) {
//                thread3.start();
//                check = false;
//            }
//
//        }

        // In this code, we create a new thread called "Our Thread" to execute a task concurrently with the main thread.
        // However, if we need to ensure that the main thread waits for "Our Thread" to complete before continuing,
        // we can achieve this by calling the join() method on "Our Thread".
        // This ensures that the main thread waits until "Our Thread" completes its execution or until the specified waiting time
        // if we use thread.start(timeInMillis) to start the thread with a time limit for execution.
//        Thread thread = new Thread(() -> {
//            System.out.println(Thread.currentThread());
//        }, "Our Thread");
//        thread.start();
//
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(thread.getPriority());
//        thread.setPriority(5);

        // Below, there is a potential deadlock condition. Deadlock occurs when two or more threads are waiting indefinitely for each other
        // to release resources that they need. In this case, thread1 and thread2 both acquire a lock on one resource (lock1 or lock2) and then attempt to
        // acquire a lock on the other resource. If both threads acquire one lock but are unable to acquire the second lock because it's held by the other
        // thread, a deadlock can occur.

        // To resolve the deadlock, you can ensure that both threads acquire the locks in the same order. This prevents the possibility of circular wait,
        // where each thread is waiting for a resource held by the other.
        String lock1 = "riddhi";
        String lock2 = "dutta";

        Thread thread1 = new Thread( () -> {
            synchronized (lock1) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println("lock acquired");
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            // reverse lock order
            synchronized (lock2) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1) {
                    System.out.println("lock acquired");
                }
            }
        }, "thread1");

        thread1.start();
        thread2.start();

//        try {
//            thread1.join(); // Wait for thread1 to complete
//            thread2.join(); // Wait for thread2 to complete
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("Main is exiting");
    }
}
