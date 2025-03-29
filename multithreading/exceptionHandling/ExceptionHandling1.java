package tut.multithreading.exceptionHandling;

import java.lang.Thread.UncaughtExceptionHandler;

class ExceptionLeak implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		throw new RuntimeException("Throwing an exception");
	}
}

class CustomExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(t.getName() + " Caught Exception " + e.getMessage());
	}
	
}

class CustomExceptionHandler1 implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(t.getName() + " Caught Exception inone " + e.getMessage());
	}
	
}

public class ExceptionHandling1 {

	public static void main(String[] args) {
		Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler());
		new Thread(new ExceptionLeak()).start();
		new Thread(new ExceptionLeak()).start();
		new Thread(new ExceptionLeak()).start();
		Thread thr = new Thread(new ExceptionLeak());
		thr.setUncaughtExceptionHandler(new CustomExceptionHandler1());
		thr.start();
	}

}
