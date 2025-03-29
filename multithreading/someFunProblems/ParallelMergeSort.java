package tut.multithreading.someFunProblems;

import java.util.Arrays;

public class ParallelMergeSort {

	public static void main(String[] args) {
		int[] arr = {34, 23, 33, 22, 67, 2, 5, 67, 12, 9, 5, 79, 1, 39};
		int numOfProcessor = Runtime.getRuntime().availableProcessors();
		ParallelMergeSort pms = new ParallelMergeSort();
		pms.parallelMergeSort(arr, 0, arr.length - 1, numOfProcessor);
		System.out.println(Arrays.toString(arr));
	}

	private void mergeSort(int[] arr, int lo, int hi) {
		sort(arr, lo, hi);
	}
	
	public void parallelMergeSort(int[] arr, int lo, int hi, int numOfProcessor) {
		
		if (numOfProcessor <= 1 || lo >= hi) {
			mergeSort(arr, lo, hi);
			return;
		}
		int pivot = lo + (hi - lo) / 2;
		Thread t1 = createThread(arr, lo, pivot, numOfProcessor / 2);
		Thread t2 = createThread(arr, pivot + 1, hi, numOfProcessor / 2);
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		merge(arr, lo, pivot, hi);
	}
	
	private Thread createThread(int[] arr, int lo, int hi, int numOfProcessor) {
		return new Thread(() -> {
			parallelMergeSort(arr, lo, hi, numOfProcessor / 2);
		});
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
