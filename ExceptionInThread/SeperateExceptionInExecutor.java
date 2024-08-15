package MultiThreading.ExceptionInThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ThreadExceptionHandler4 implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Error in ThreadExceptionHandler4>> " + t.getName());
        e.printStackTrace();
    }
}

class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        // Can also contain some logic for assigning Exception Handler
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new ThreadExceptionHandler4());
        return t;
    }
}

class ExceptionThread3 implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }
}

public class SeperateExceptionInExecutor {
    public static void main(String args[]) {
        ExecutorService executorService = Executors.newCachedThreadPool(new MyThreadFactory());

        executorService.execute(new ExceptionThread3());
        executorService.execute(new ExceptionThread3());
        executorService.shutdown();
    }
}
