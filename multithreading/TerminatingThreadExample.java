package tut.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class InfiniteTask implements Runnable {
	private static int count = 0;
	private String instance;
	public volatile boolean cancel = false;
	
	
	public InfiniteTask(String instance) {
		super();
		this.instance = instance + " " + count++;
	}


	@Override
	public void run() {
		System.out.println("Running " + instance);
		
		for (int i = 0;; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(instance + " >> " + i);
			
			if (cancel) {
				System.out.println("Stopping " + instance);
				break;
			}
		}
	}
}

class InfiniteTask1 implements Runnable {
	private static int count = 0;
	private String instance;
	
	
	public InfiniteTask1(String instance) {
		super();
		this.instance = instance + " " + count++;
	}


	@Override
	public void run() {
		System.out.println("Running " + instance);
		
		for (int i = 0;; i++) {
			System.out.println(instance + " >> " + i);
			
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Stopping " + instance);
				break;
			}		
		}
	}	
}

class InfiniteTask2 implements Runnable {
	private static int count = 0;
	private String instance;
	
	
	public InfiniteTask2(String instance) {
		super();
		this.instance = instance + " " + count++;
	}


	@Override
	public void run() {
		System.out.println("Running " + instance);
		
		for (int i = 0;; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
				break;
			}
			System.out.println(instance + " >> " + i);
		}
	}
}

class InfiniteTask3 implements Callable<Integer> {
	private static int count = 0;
	private String instance;
	public volatile boolean cancel = false;
	
	
	public InfiniteTask3(String instance) {
		super();
		this.instance = instance + " " + count++;
	}


	@Override
	public Integer call() {
		System.out.println("Running " + instance);
		int i = 0;
		for (;; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Interrupted from SLeep");
				break;
			}
			System.out.println(instance + " >> " + i);
			
			if (cancel) {
				System.out.println("Stopping " + instance);
				break;
			}
		}
		return i;
	}
}

class InfiniteTask4 implements Callable<Integer> {
	private static int count = 0;
	private String instance;
	
	
	public InfiniteTask4(String instance) {
		super();
		this.instance = instance + " " + count++;
	}


	@Override
	public Integer call() {
		System.out.println("Running " + instance);
		int i = 0;
		for (;; i++) {
			
			System.out.println(instance + " >> " + i);
			
			if (Thread.currentThread().isInterrupted()) {
				System.out.println(instance + " >> " + "Interrupted called");
				break;
			}
		}
		return i;
	}
}

public class TerminatingThreadExample {

	public static void main(String[] args) throws InterruptedException {
		InfiniteTask t1 = new InfiniteTask("Thread 1");
		InfiniteTask t2 = new InfiniteTask("Thread 2");
		InfiniteTask t3 = new InfiniteTask("Thread 3");
		
		new Thread(t1).start();
		new Thread(t2).start();
		new Thread(t3).start();
		
		Thread.sleep(500);
		
		t1.cancel = true;
		t2.cancel = true;
		t3.cancel = true;
		
		Thread t4 = new Thread(new InfiniteTask1("Thread 4"));
		Thread t5 = new Thread(new InfiniteTask1("Thread 5"));
		Thread t6 = new Thread(new InfiniteTask1("Thread 6"));
		t4.start();
		t5.start();
		t6.start();
		
		Thread.sleep(500);
		
		t4.interrupt();
		t5.interrupt();
		t6.interrupt();

		Thread t7 = new Thread(new InfiniteTask2("Thread 7"));
		Thread t8 = new Thread(new InfiniteTask2("Thread 8"));
		Thread t9 = new Thread(new InfiniteTask2("Thread 9"));
		t7.start();
		t8.start();
		t9.start();
		
		Thread.sleep(500);
		
		t7.interrupt();
		t8.interrupt();
		t9.interrupt();
		
		ExecutorService svc1 = Executors.newCachedThreadPool();
		InfiniteTask t10 = new InfiniteTask("Thread 10");
		InfiniteTask3 t11 = new InfiniteTask3("Thread 11");
		svc1.execute(t10);
		svc1.submit(t11);
		
		svc1.shutdown();
		Thread.sleep(500);
		
		t10.cancel = true;
		t11.cancel = true;
		
		ExecutorService svc2 = Executors.newCachedThreadPool();
		InfiniteTask1 t12 = new InfiniteTask1("Thread 12");
		InfiniteTask4 t13 = new InfiniteTask4("Thread 13");
		Future<?> f1 = svc2.submit(t12);
		Future<Integer> f2 = svc2.submit(t13);
		
		svc2.shutdown();
		Thread.sleep(500);
		
		f1.cancel(true);
		f2.cancel(true);
		
		ExecutorService svc3 = Executors.newCachedThreadPool();
		InfiniteTask2 t14 = new InfiniteTask2("Thread 14");
		InfiniteTask3 t15 = new InfiniteTask3("Thread 15");
		Future<?> f3 = svc3.submit(t14);
		Future<Integer> f4 = svc3.submit(t15);
		
		svc3.shutdown();
		Thread.sleep(500);
		
		f3.cancel(true);
		f4.cancel(true);
		
		// Executor.shutdownNow()  terminates all tasks weather executing or waiting
		// Executor.awaitTermintation(500, Timunit.MILLISECONDS)  terminates all tasks weather executing or waiting
		
	}

}
