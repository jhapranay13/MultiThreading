package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SimpleRecursiveTask extends RecursiveTask< Integer > {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5813279436720841409L;
	private int simulatedWork;
	
	public SimpleRecursiveTask(int simulatedWork) {
		super();
		this.simulatedWork = simulatedWork;
	}

	@Override
	protected Integer compute() {
		if( simulatedWork > 100 ) {
			System.out.println( "Dividing for parallel execution....." + simulatedWork );
			
			SimpleRecursiveTask firstWorker = new SimpleRecursiveTask( simulatedWork / 2 );
			SimpleRecursiveTask secondWorker = new SimpleRecursiveTask( simulatedWork / 2 );
			
			firstWorker.fork();
			secondWorker.fork();
			
			int solution = 0;
			
			solution += firstWorker.join();
			solution += secondWorker.join();
			return solution;
		} else {
			System.out.println( "Sequential Processing...no need to divide further.." + simulatedWork );
			return simulatedWork * 2;
		}
	}
	
}

public class ForkAndJoinSimpleRecursiveTaskDemo {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool( Runtime.getRuntime().availableProcessors() ) ;
		SimpleRecursiveTask action = new SimpleRecursiveTask( 1000 );
		System.out.println( pool.invoke( action ) );
	}

}
