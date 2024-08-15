package tut.multithreading;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

class NamedDeamonThreadFactory implements ThreadFactory {
	private static int id = 0; 
	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		Thread t1 = new Thread(r, "New Thread " + id);
		
		if (id % 2 == 0) {
			t1.setDaemon(true);
		}
		id++;
		return t1;
	}
	
}

public class DemonAndIsAliveExp {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool(new NamedDeamonThreadFactory());
		service.execute(new ReturnValTask(100, 10, 20));
		service.execute(new ReturnValTask(200, 100, 120));
		service.execute(new ReturnValTask(300, 100, 320));
		
		service.shutdown();
		
		// thread.isAlive() is to check if alive or not
		ExecutorService service1 = Executors.newCachedThreadPool();
		Future<?> f1 = service1.submit(new ReturnValTask(400, 10, 20)); // runnable
		Future<Integer> f2 = service1.submit(new ReturnValTask2(400, 10, 20)); // callable
		
		FutureTask<?> ft1 = new FutureTask(new ReturnValTask(400, 10, 20), null); // Runnable
		FutureTask<Integer> ft2 = new FutureTask(new ReturnValTask(400, 10, 20), 999); // Runnable
		FutureTask<Integer> ft3 = new FutureTask(new ReturnValTask2(400, 10, 20)); // callable
		service1.execute(ft1);
		service1.execute(ft2);
		service1.execute(ft3);
		
		service1.shutdown();
		
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(300);
				System.out.println(Thread.currentThread() + " " + f1.isDone());
				System.out.println(Thread.currentThread() + " " + f2.isDone());
				System.out.println(Thread.currentThread() + " " + ft1.isDone());
				System.out.println(Thread.currentThread() + " " + ft2.isDone());
				System.out.println(Thread.currentThread() + " " + ft3.isDone());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("====================================================");

		System.out.println(Thread.currentThread() + " " + f1.isDone());
		System.out.println(Thread.currentThread() + " " + f2.isDone());
		System.out.println(Thread.currentThread() + " " + ft1.isDone());
		System.out.println(Thread.currentThread() + " " + ft2.isDone());
		System.out.println(Thread.currentThread() + " " + ft3.isDone());
		System.out.println("====================================================");

		try {
			System.out.println(Thread.currentThread() + " " + f1.get());
			System.out.println(Thread.currentThread() + " " + f2.get());
			System.out.println(Thread.currentThread() + " " + ft1.get());
			System.out.println(Thread.currentThread() + " " + ft2.get());
			System.out.println(Thread.currentThread() + " " + ft3.get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
