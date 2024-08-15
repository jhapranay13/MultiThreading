package MultiThreading.ExceptionInThread;

class ThreadExceptionHandler1 implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Error in >> " + t.getName());
        e.printStackTrace();
    }
}

class ExceptionThread1 implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }
}

public class HandlingExceptionForEveryThread {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler1());
        new Thread(new ExceptionThread1()).start();
        new Thread(new ExceptionThread1()).start();
        new Thread(new ExceptionThread1()).start();
        new Thread(new ExceptionThread1()).start();
    }
}
