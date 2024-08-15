package tut.multithreading.exceptionHandling;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionHandlingThreadFactory implements ThreadFactory {
	static int id = 0;
	
	@Override
	public Thread newThread(Runnable r) {
		ExceptionLeak ex = new ExceptionLeak();
		Thread t = new Thread(ex);
		
		if (id++ % 2 == 0) {
			t.setUncaughtExceptionHandler(new CustomExceptionHandler2());
		}
		return t;
	}
	
}

class CustomExceptionHandler2 implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(t.getName() + " Custom 2 Caught Exception " + e.getMessage());
	}
	
}

public class ExceptionHandling2 {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler());
		ExecutorService srvc1 = Executors.newCachedThreadPool();
		srvc1.execute(new ExceptionLeak());
		srvc1.execute(new ExceptionLeak());
		srvc1.execute(new ExceptionLeak());
		
		ExecutorService srvc2 = Executors.newCachedThreadPool(new ExceptionHandlingThreadFactory());
		srvc2.execute(new ExceptionLeak());
		srvc2.execute(new ExceptionLeak());
		srvc2.execute(new ExceptionLeak());
		
		srvc1.shutdown();
		srvc2.shutdown();
	}

}
