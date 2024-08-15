package MultiThreading.ConcurrentPackage;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
// RecursiveAction does not return anything
class SearchTask extends RecursiveTask<Integer> {
    int[] arr;
    int lo;
    int hi;
    int target;

    public SearchTask(int[] arr, int lo, int hi, int target) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread()); //to Show that multiple threads are used

        if (hi - lo + 1 > 3) {
            int mid = (hi + lo) / 2;
            SearchTask task1 = new SearchTask(arr, lo, mid, target);
            SearchTask task2 = new SearchTask(arr, mid + 1, hi, target);
            task1.fork();
            task2.fork();
            return task1.join() + task2.join();
        } else {
            int result = 0;

            for (int i = lo; i <= hi; i++) {

                if (arr[i] == target) {
                    result++;
                }
            }
            return result;
        }
    }
}

public class ForkAndJoinDemo {

    public static void main(String args[]) {
        int[] arr = {3, 5, 6, 9, 3, 6, 12, 14, 6, 34, 45, 56, 6, 34, 6, 42, 44, 6};
        SearchTask searchTask = new SearchTask(arr, 0, arr.length - 1, 6);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int result = pool.invoke(searchTask);
        System.out.println("Count >> " + result);
    }
}
