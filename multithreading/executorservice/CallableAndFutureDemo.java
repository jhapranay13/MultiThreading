package tut.multithreading.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ProcessFive implements Callable<String> {
	
	private int id;

	public ProcessFive(int id) {
		super();
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(1000);
		return "ID >> " + id;
	}
}

public class CallableAndFutureDemo {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(3);
		List< Future< String > > result = new  ArrayList<>();
		
		for( int i = 0; i < 5; i++ ) {
			Future< String > futureResult =  service.submit( new ProcessFive( i ) );
			result.add( futureResult );
		}
		service.shutdown();
		
		for( Future< String > fut : result ) {
			try {
				System.out.println( fut.get() );
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}
