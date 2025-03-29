package tut.multithreading.syncandlocking;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProcessorThree {
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	public void producer() throws InterruptedException {
		lock.lock();
		System.out.println( "Producer here...." );
		condition.await();
		System.out.println( "Producer start again...." );
		condition.signal();
		lock.unlock();
	}

	public void consumer() throws InterruptedException {
		lock.lock();
		System.out.println( "conumer here...." );
		condition.signal();
		condition.await();
		System.out.println( "conumer again...." );
		lock.unlock();
	}
}
public class RentrantLockDemo {

	public static void main(String[] args) {
		ProcessorThree op = new ProcessorThree();
		Thread thread1 = new Thread( new Runnable() {

			@Override
			public void run() {
				try {
					op.producer();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}

			}
		});

		Thread thread2 = new Thread( new Runnable() {

			@Override
			public void run() {
				try {
					op.consumer();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}

			}
		});
		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
