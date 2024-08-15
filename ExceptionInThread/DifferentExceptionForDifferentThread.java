package MultiThreading.ExceptionInThread;

class ThreadExceptionHandler2 implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Error in ThreadExceptionHandler2 >> " + t.getName());
        e.printStackTrace();
    }
}

class ThreadExceptionHandler3 implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Error in ThreadExceptionHandler3 >> " + t.getName());
        e.printStackTrace();
    }
}

class ExceptionThread2 implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }
}

public class DifferentExceptionForDifferentThread {

    public static void main(String[] args) {
        Thread t1 = new Thread(new ExceptionThread2());
        Thread t2 = new Thread(new ExceptionThread2());
        t1.setUncaughtExceptionHandler(new ThreadExceptionHandler3());
        t2.setUncaughtExceptionHandler(new ThreadExceptionHandler2());
        t1.start();
        t2.start();
    }
}
