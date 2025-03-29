package tut.multithreading.someFunProblems;

import java.util.Arrays;
import java.util.Random;

class SumWorkerOne extends Thread {
	private int[] arr;
	private int hi;
	private int lo;
	private long sum;

	public SumWorkerOne(int[] arr, int hi, int lo) {
		super();
		this.arr = arr;
		this.hi = hi;
		this.lo = lo;
	}

	public int[] getArr() {
		return arr;
	}

	public void setArr(int[] arr) {
		this.arr = arr;
	}

	public int getHi() {
		return hi;
	}

	public void setHi(int hi) {
		this.hi = hi;
	}

	public int getLo() {
		return lo;
	}

	public void setLo(int lo) {
		this.lo = lo;
	}

	public long getSum() {
		return sum;
	}

	@Override
	public void run() {
	
		for( int i = lo; i <= hi; i++ ) {
			sum += arr[ i ];
		}
	}
}

public class ParallelSumUsingMoreThan2Threads {

	public static void main(String[] args) {

		int size = 10000;
		int arr[] = new int[ size ];
		Random random = new Random();

		for( int i = 0; i < size; i++ ) {
			arr[ i ] = random.nextInt( size );
		}

		System.out.println( Arrays.toString( arr ) );
		long sum = 0;

		for( int i = 0; i < size; i++ ) {
			sum += arr[ i ];	
		}
		System.out.println( "Sequential Sum >> " + sum );
		int lo = 0;
		int hi = size - 1;
		long sumParallel = parallelSum( arr, lo, hi );
		System.out.println( "Parallel Sum >> " + sumParallel );
	}

	private static long parallelSum(int[] arr, int lo, int hi) {
		long returnValue = 0;
		int numOfProcessors = Runtime.getRuntime().availableProcessors();
		int steps = (int) Math.ceil( hi / numOfProcessors );
		int indexCounter = lo;

		SumWorkerOne[] threads = new SumWorkerOne[ numOfProcessors ];

		for( int i = 0; i < numOfProcessors; i++ ) {
			threads[ i ] = new SumWorkerOne( arr, indexCounter + steps, indexCounter );
			indexCounter += steps + 1;
			threads[ i ].start();
		}

		for( SumWorkerOne thread : threads ) {

			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for( SumWorkerOne thread : threads ) {
			returnValue += thread.getSum();		
		}

		return returnValue;
	}


}
