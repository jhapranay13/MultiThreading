package tut.multithreading.syncandlocking;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 
 * 
 * 
 * 
 * ReentrantLock allows threads to enter into the lock on a resource more than once. 
 * When the thread first enters into the lock, a hold count is set to one. 
 * Before unlocking the thread can re-enter into lock again and every time hold count 
 * is incremented by one. For every unlocks request, hold count is decremented by 
 * one and when hold count is 0, the resource is unlocked. 

Reentrant Locks also offer a fairness parameter, by which the lock would abide by the 
order of the lock request i.e. after a thread unlocks the resource, the lock would go 
to the thread which has been waiting for the longest time. This fairness mode is set 
up by passing true to the constructor of the lock.
 *
 */
class Sample {
	
    private int x;
	
    // ReadWriteLock object for requesting the lock.
    ReadWriteLock rw_lock = new ReentrantReadWriteLock();
 
    public int getX() {
        return x;
    }
 
    public void setX(int x) {
		this.x = x;
	   }
	
    public void incr() {
		
	// Request the write lock as the 
	// operation is intended to modify the data.
 
		Lock lock = rw_lock.writeLock();
		Condition condition = lock.newCondition();
		lock.lock();
	 
		try {
			
		    int y = getX();
		    y++;
				
		    // Just for simulation
		    try { Thread.sleep(1); } catch(Exception e) {}
				
		    setX(y);
	 
		} finally {
		    // Unlock 
			condition.signal();
		    lock.unlock();
		}	
    }
	
}
 
class MyThread extends Thread {
	
    Sample obj;
	
    public MyThread(Sample obj) {
    	this.obj = obj;
    }
	
    public void run() {
    	obj.incr();
    }	
}

public class LockExample1 {

	public static void main(String[] args) {
		Sample obj = new Sample();
		obj.setX(10);
			
		MyThread t1 = new MyThread(obj);
		MyThread t2 = new MyThread(obj);
			
		t1.start();
		t2.start();
			
		try {
		    t1.join();
		    t2.join();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}	
		System.out.println( obj.getX() );
	}

}
