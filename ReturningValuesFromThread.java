package tut.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ReturnValTask implements Runnable {
	int sleepTime;
	int a;
	int b;
	int result = 0;
	boolean done = false;
	
	public ReturnValTask(int sleepTime, int a, int b) {
		super();
		this.sleepTime = sleepTime;
		this.a = a;
		this.b = b;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " Task Started");
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		result = a + b;
		System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
		done = true;
		
		synchronized(this) {
			System.out.println(Thread.currentThread().getName() + " Notifying....");
			notifyAll();  // notify and notifyAll
		}
	}
	
	public int getResult() {
		synchronized(this) {
			
			if (!done) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}

interface ResultListener<T> {
	void notify(T result);
}

class ResultObserver implements ResultListener<Integer> {
	String taksId;
	
	
	public ResultObserver(String taksId) {
		super();
		this.taksId = taksId;
	}


	@Override
	public void notify(Integer result) {
		System.out.println("Result for TaskId " + taksId + " >> " + result);
	}
	
}

class ReturnValTask1 implements Runnable {
	int sleepTime;
	int a;
	int b;
	int result = 0;
	ResultObserver observer;
	
	public ReturnValTask1(int sleepTime, int a, int b, ResultObserver observer) {
		super();
		this.sleepTime = sleepTime;
		this.a = a;
		this.b = b;
		this.observer = observer;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " Task Started");
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		result = a + b;
		System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
		observer.notify(result);
	}
}

class ReturnValTask2 implements Callable<Integer> {
	int sleepTime;
	int a;
	int b;
	
	public ReturnValTask2(int sleepTime, int a, int b) {
		super();
		this.sleepTime = sleepTime;
		this.a = a;
		this.b = b;
	}

	public Integer call() {
		System.out.println(Thread.currentThread().getName() + " Task Started");
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
		return a + b;
	}
}

public class ReturningValuesFromThread {

	public static void main(String[] args) {
		ReturnValTask r1 = new ReturnValTask(1000, 10, 20);
		ReturnValTask r2 = new ReturnValTask(10, 100, 20);
		ReturnValTask r3 = new ReturnValTask(100, 10000, 20);
		Thread t1 = new Thread(r1, "Thread 1");
		Thread t2 = new Thread(r2, "Thread 2");
		Thread t3 = new Thread(r3, "Thread 3");
		
		t1.start();
		t2.start();
		t3.start();
		// Will be printed in order
		System.out.println(r1.getResult());
		System.out.println(r2.getResult());
		System.out.println(r3.getResult());
		// Technique to print as the result arrives
		ReturnValTask1 r4 = new ReturnValTask1(1000, 10, 20, new ResultObserver("Task 1"));
		ReturnValTask1 r5 = new ReturnValTask1(10, 100, 20,  new ResultObserver("Task 2"));
		ReturnValTask1 r6 = new ReturnValTask1(100, 10000, 20,  new ResultObserver("Task 3"));
		Thread t4 = new Thread(r4, "Thread 4");
		Thread t5 = new Thread(r5, "Thread 5");
		Thread t6 = new Thread(r6, "Thread 6");
		
		t4.start();
		t5.start();
		t6.start();
		// Print in executor. Will be printed in the order
		ExecutorService exec1 = Executors.newCachedThreadPool(new NamedThreadFactory());
		Future<Integer> f1 = exec1.submit(new ReturnValTask2(1000, 20, 30));
		Future<Integer> f2 = exec1.submit(new ReturnValTask2(10, 100, 20));
		Future<Integer> f3 = exec1.submit(new ReturnValTask2(100, 10000, 20));
		// we can submit runnable task also but future will be Future<?>. and future.get() will return null and when it returns we know execution has finished
		try {
			System.out.println("Future 1 >> " + f1.get());
			System.out.println("Future 2 >> " + f2.get());
			System.out.println("Future 3 >> " + f3.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		ExecutorService exec2 = Executors.newCachedThreadPool(new NamedThreadFactory());
		CompletionService<Integer> completionService = new ExecutorCompletionService<>(exec2);
		completionService.submit(new ReturnValTask2(1000, 20, 30));
		completionService.submit(new ReturnValTask2(10, 100, 30));
		completionService.submit(new ReturnValTask2(100, 20000, 30));
		
		for (int i = 0; i < 3; i++) {
			try {
				System.out.println("Completion Service " + i + " " + completionService.take().get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}

}
