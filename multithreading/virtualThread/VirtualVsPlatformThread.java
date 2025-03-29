package tut.multithreading.virtualThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualVsPlatformThread {

    public static void main(String args[]) {

        for (int i = 0; i < 100000; i++) {
            Thread.ofPlatform().start(() -> {
                System.out.println(Thread.currentThread() + " Task Started");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
            });
        }
        // The abovwe code will throw java.lang.OutOfMemoryError: unable to create new native thread
        // but the below code will not. So virtual threads are lightweight and powerful
        // and can be used in large numbers.
        var service = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 100000; i++) {
            service.submit(() -> {
                System.out.println(Thread.currentThread() + " Task Started");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
            });
        }
    }
}
