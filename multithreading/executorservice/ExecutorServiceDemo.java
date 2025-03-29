package tut.multithreading.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 		ExecutorService executorService = Executors.newCachedThreadPool();
		Dynamically resuses the thread.
		Checks to see if any thread has finished task and would reuse it.
		If no threads available creates new thread.
		
		ExecutorService executorService = Executors.newFixedThreadPool(nThreads)
		if all threads are busy waits for it to terminate.
		
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Uses Single Thread for execution.
 */

class ProcessFour implements Runnable{

	@Override
	public void run() {
		
		for( int i = 0; i < 10; i++ ) {
			System.out.println( i );
			
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class ExecutorServiceDemo {

	public static void main(String[] args) {
		//ExecutorService executorService = Executors.newCachedThreadPool();
		//ExecutorService executorService = Executors.newFixedThreadPool(3);
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		for( int i = 0; i < 5; i++ ) {
			executorService.submit( new ProcessFour() );
		}
		
		executorService.shutdown();
	}

}
