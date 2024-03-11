package main;

public class Thread1 extends Thread
{
    public Thread1( String threadName )
    {
        super(threadName);
    }

    @Override
    public void run()
    {
        for(int i=0;i<5;i++)
        {
            // Thread.currentThread() (gives)-> Thread[thread1,5,main] === which means [currThreadName, threadPriority, parentCurrThreadName]
            System.out.println("Inside thread1 "+ Thread.currentThread() + " | i : " + i); // .getName()
        }

    }
}
