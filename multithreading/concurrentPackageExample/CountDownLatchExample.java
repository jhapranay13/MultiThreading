package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LatchTask implements Runnable {

	private CountDownLatch latch;

	public LatchTask(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread()+ "Running");
		latch.countDown();

	}
}

public class CountDownLatchExample {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(5);
		ExecutorService service = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 5; i++) {
			service.execute(new LatchTask(latch));
		}
		
		service.shutdown();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Process Completed");
	}

}
