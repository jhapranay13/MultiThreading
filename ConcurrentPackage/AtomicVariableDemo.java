package MultiThreading.ConcurrentPackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

class Shared {
    // Won't require to be Sunchronized
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public void init() {

        if (!atomicBoolean.compareAndSet(false, true)) {
            System.out.println("Already intialized");
            return;
        }
        Thread.yield(); // To simulate Thread giving up CPU
        System.out.println("Intitalizing ....");
    }

    public void service() {
        init();
        System.out.println("service");
    }
}

public class AtomicVariableDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Shared shared = new Shared();
                    shared.service();
                }
            });
        }
        executorService.shutdown();
    }
}
