package MultiThreading.FirstChapters;

import java.util.concurrent.*;

class TaskWithRetVal implements Runnable {
    int x;
    int y;
    volatile boolean done;
    int sum = 0;

    public TaskWithRetVal(int x, int y) {
        this.x = x;
        this.y = y;
        this.done = false;
    }

    @Override
    public void run() {

        try {
            System.out.println("Processing....");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum = x + y;
        done = true;

        synchronized (this) {
            this.notifyAll(); // or notify
        }
    }

    public int getSum() {
        synchronized (this) {

            if (!done) {
                try {
                    System.out.println(Thread.currentThread() + " Waiting for process");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return sum;
        }
    }
}

class CallableTask implements Callable<Integer> {
    int x;
    int y;
    int sum = 0;

    public CallableTask(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("Callable Processing...");
        Thread.sleep(1000);
        sum = x + y;
        return sum;
    }
}

public class ReturnThreadValuesAndCallableAndFuture {

    public static void main(String args[]) throws ExecutionException, InterruptedException {
        System.out.println("-----------NORMAL THREAD------------");
        TaskWithRetVal task = new TaskWithRetVal(23, 45);
        Thread t1 = new Thread(task);
        t1.start();
        System.out.println(task.getSum());
        System.out.println("-----------Using Callable------------");
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> promise = executor.submit(new CallableTask(87, 67));

        while (!promise.isDone()) {

        }
        System.out.println(promise.get());
        executor.shutdown();
    }
}
