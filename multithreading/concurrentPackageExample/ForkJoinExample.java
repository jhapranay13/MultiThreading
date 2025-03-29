package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Reursive task returns a value. Recursive action does not return a value
class SearchTask extends RecursiveTask<Integer> {
	int[] arr;
	int start;
	int end;
	int searchElem;

	public SearchTask(int[] arr, int start, int end, int searchElem) {
		super();
		this.arr = arr;
		this.start = start;
		this.end = end;
		this.searchElem = searchElem;
	}

	@Override
	protected Integer compute() {
		System.out.println(Thread.currentThread());
		if (end - start == 0) {
			return arr[end] == searchElem ? 1 : 0;
		}
		
		int mid = start + (end - start) / 2;
		SearchTask task1 = new SearchTask(arr, start, mid, searchElem);
		SearchTask task2 = new SearchTask(arr, mid + 1, end, searchElem);
		task1.fork();
		task2.fork();
		
		return task1.join() + task2.join();
	}
	
}

public class ForkJoinExample {

	public static void main(String[] args) {
		int[] arr = {6, 7, 2, 3, 4, 6, 7, 8, 3, 6, 10, 13, 25, 45, 6};
		ForkJoinPool pool = ForkJoinPool.commonPool();
		SearchTask task = new SearchTask(arr, 0, arr.length - 1, 6);
		int result = pool.invoke(task);
		System.out.println(result);
	}

}
