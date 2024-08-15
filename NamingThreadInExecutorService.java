package tut.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class NamedThreadFactory implements ThreadFactory {
	private static int count = 0;
	
	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "Custom Name >> " +count++);
	}
	
}

class NamedThreadClass implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
					
			if (i % 9 == 0) {
				try {
					//Thread.sleep() doesn’t lose any monitors or lock the current thread it has acquired.
					Thread.sleep(1000);
					
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " >> " + i);
		}
	}
	
}

public class NamingThreadInExecutorService {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool(new NamedThreadFactory());
		service.submit(new NamedThreadClass());
		service.submit(new NamedThreadClass());
		service.submit(new NamedThreadClass());
		
	}

}
