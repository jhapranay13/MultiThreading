package tut.multithreading.someFunProblems;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ParallelMaxFinder extends RecursiveTask< Integer > {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2961451671433602088L;
	private int lo;
	private int hi;
	private int[] arr;
	
	public ParallelMaxFinder(int lo, int hi, int[] arr) {
		super();
		this.lo = lo;
		this.hi = hi;
		this.arr = arr;
	}
	
	private int sequentialMaxFinding() {
		int max = 0;
		
		for( int i = lo; i <= hi; i++ ) {
			
			if( arr[ i ] > max ) {
				max = arr[ i ];
			}
		}
		return max;
	}


	@Override
	protected Integer compute() {
		
		if( hi - lo < 70 ) {
			return sequentialMaxFinding();
		} else {
			int pivot = lo + ( hi - lo ) / 2;
			
			ParallelMaxFinder leftSide = new ParallelMaxFinder(lo, pivot, arr);
			ParallelMaxFinder rightSide = new ParallelMaxFinder(pivot + 1, hi, arr);
			
			invokeAll( leftSide, rightSide );
			return Math.max( leftSide.join() , rightSide.join() );

		}
	}
	
}

public class ParallelMaxFindingUsingRecursiveTask {

	public static void main(String[] args) {
		int size = 100;
		Random random = new Random();
		int arr[] = new int[ size ];
		
		for( int i = 0; i < size; i++ ) {
			arr[ i ] = random.nextInt( size );
		}
		System.out.println( Arrays.toString( arr ) );
		ForkJoinPool pool = new ForkJoinPool( Runtime.getRuntime().availableProcessors() );
		ParallelMaxFinder finder = new ParallelMaxFinder(0, size - 1 , arr);
		
		int max = pool.invoke( finder );
		System.out.println( max );
	}
}
