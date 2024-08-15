package tut.multithreading.someFunProblems;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class ForkJoinMergeSort extends RecursiveAction{
	private int lo; 
	private int hi; 
	private int[] arr;
	
	public ForkJoinMergeSort(int lo, int hi, int[] arr) {
		super();
		this.lo = lo;
		this.hi = hi;
		this.arr = arr;
	}

	@Override
	protected void compute() {
		
		if (lo >= hi) {
			return;
		}
		int pivot = lo + (hi - lo) / 2;
		ForkJoinMergeSort srt1 = new ForkJoinMergeSort(lo, pivot, arr);
		ForkJoinMergeSort srt2 = new ForkJoinMergeSort(pivot + 1, hi, arr);
		invokeAll(srt1, srt2);
		merge(arr, lo, pivot, hi);
	}
	
	private void sort(int[] arr, int lo, int hi) {
		
		if (lo >= hi) {
			return;
		}
		int pivot = lo + (hi - lo) / 2;
		sort(arr, lo, pivot);
		sort(arr, pivot + 1, hi);
		merge(arr, lo, pivot, hi);
	}

	private void merge(int[] arr, int lo, int pivot, int hi) {
		int[] helper = new int[arr.length];
		
		int l = lo;
		int h = pivot + 1;
		int index = l;
		
		while(l <= pivot && h <= hi) {
			
			if (arr[l] <= arr[h]) {
				helper[index] = arr[l++];
			} else {
				helper[index] = arr[h++];
			}
			index++;
		}
		
		while(l <= pivot) {
			helper[index] = arr[l++];
			index++;
		}
		
		while(h <= hi) {
			helper[index] = arr[h++];
			index++;
		}
		l = lo;
		
		while (l <= hi) {
			arr[l] = helper[l++];
		}
	}
}

public class MergeSortUsingForkAndJoin {

	public static void main(String[] args) {
		int[] arr = {34, 23, 33, 22, 67, 2, 5, 67, 12, 9, 5, 79, 1, 39};

		ForkJoinPool pool = ForkJoinPool.commonPool();
		pool.invoke(new ForkJoinMergeSort(0, arr.length - 1, arr));
		System.out.println(Arrays.toString(arr));
	}

}
