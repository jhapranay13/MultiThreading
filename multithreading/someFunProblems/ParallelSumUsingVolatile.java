package tut.multithreading.someFunProblems;

import java.util.Arrays;
import java.util.Random;

public class ParallelSumUsingVolatile {

	static volatile long returnVal = 0;


	public static void main(String[] args) {
		int size = 100;
		int arr[] = new int[ size ];
		Random random = new Random();

		for( int i = 0; i < size; i++ ) {
			arr[ i ] = random.nextInt( size );
		}
		
		System.out.println( Arrays.toString( arr ) );
		int sum = 0;
		
		for( int i = 0; i < size; i++ ) {
			 sum += arr[ i ];	
		}
		System.out.println( "Sequential Sum >> " + sum );
		int lo = 0;
		int hi = size - 1;
		parallelSum( arr, lo, hi );
		System.out.println( "Parallel Sum >> " + returnVal );
	}

	private static void parallelSum(int[] arr, int lo, int hi) {
		int pivot = lo + ( hi - lo ) / 2;
		
		Thread threadLeft = new Thread( new Runnable() {

			@Override
			public void run() {

				for( int i = 0; i <= pivot; i++ ) {
					returnVal += arr[ i ];
				}
			}
		} );

		Thread threadRight = new Thread( new Runnable() {

			@Override
			public void run() {

				for( int i = pivot + 1; i <= hi; i++ ) {
					returnVal += arr[ i ];
				}
			}
		} );
		
		threadLeft.start();
		threadRight.start();
		
		try {
			threadLeft.join();
			threadRight.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
