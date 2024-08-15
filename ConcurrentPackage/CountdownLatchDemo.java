package MultiThreading.ConcurrentPackage;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountdownLatchDemo {

    public static void main(String[] args) {
        int records = 2320;
        int pageSize = 134;
        int pages = records /pageSize;
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CountDownLatch latch = new CountDownLatch(pages);

        for (int i = 0; i <= pages; i++) {
            final int pageNum = i;

            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + " Processing page.. >> " + pageNum);
                    latch.countDown();
                }
            });
        }
        executorService.shutdown();
        //If we do not await then main thread will complete the process before the
        // subtasks finish
        boolean success = false;
        try {
            //latch.await();
            success = latch.await(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (success) {
            System.out.println("Migration Complete");
        } else {
            System.out.println("Process Timed out");
        }

    }
}
