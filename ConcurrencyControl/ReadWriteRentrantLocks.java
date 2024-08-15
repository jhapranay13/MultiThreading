package MultiThreading.ConcurrencyControl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Sample {
    int x = 12;
    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void incr() {
        Lock lock = rwLock.writeLock();
        lock.lock();

        try {
            x++;
        } finally {
            lock.unlock();// finally coz if any exception the resource should be freed up
        }
    }
}

class MyAction implements Runnable {
    Sample sample;

    public MyAction(Sample sample) {
        this.sample = sample;
    }

    @Override
    public void run() {

    }
}

public class ReadWriteRentrantLocks {

    public static void main(String args[]) {
        Sample sample = new Sample();
        Thread t1 = new Thread(new MyAction(sample));
        Thread t2 = new Thread(new MyAction(sample));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sample.x);
    }
}
