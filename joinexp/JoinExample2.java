package tut.multithreading.joinexp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ReturnValTask1 implements Runnable {
	int a;
	int b;
	int result = 0;
	CountDownLatch cdl;
	
	public ReturnValTask1(int a, int b, CountDownLatch cdl) {
		super();
		this.a = a;
		this.b = b;
		this.cdl = cdl;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " Task Started");
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		result = a + b;
		System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
		cdl.countDown();
	}
	
	public int getResult() {
		return result;
	}
}

public class JoinExample2 {

	public static void main(String[] args) {
		ExecutorService svc = Executors.newCachedThreadPool();
		CountDownLatch ctd = new CountDownLatch(2);
		
		svc.submit(new ReturnValTask1(10, 23, ctd));
		svc.submit(new ReturnValTask1(10, 230, ctd));
		svc.shutdown();
		System.out.println("Awaiting");
		
		try {
			ctd.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("End Of Main Thread");
	}

}
