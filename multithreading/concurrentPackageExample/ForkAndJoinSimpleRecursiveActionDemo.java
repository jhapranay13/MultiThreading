package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class SimpleRecursiveAction extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6281955542026166542L;
	private int simulatedWork;
		
	public SimpleRecursiveAction(int simulatedWork) {
		super();
		this.simulatedWork = simulatedWork;
	}

	@Override
	protected void compute() {
		
		if( simulatedWork > 100 ) {
			System.out.println( "Dividing for parallel execution....." + simulatedWork );
			
			SimpleRecursiveAction firstWorker = new SimpleRecursiveAction( simulatedWork / 2 );
			SimpleRecursiveAction secondWorker = new SimpleRecursiveAction( simulatedWork / 2 );
			
			firstWorker.fork();
			secondWorker.fork();
		} else {
			System.out.println( "Sequential Processing...no need to divide further.." + simulatedWork );
		}
	}
}

public class ForkAndJoinSimpleRecursiveActionDemo {

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool( Runtime.getRuntime().availableProcessors() ) ;
		SimpleRecursiveAction action = new SimpleRecursiveAction( 1000 );
		pool.invoke( action );
	}

}
