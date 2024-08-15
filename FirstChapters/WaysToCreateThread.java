package MultiThreading.FirstChapters;

class MyThread extends Thread {

    public void run() {
        for(int i = 0; i < 1000; i++) {
            System.out.println("--------------------------------------------------------------");
            System.out.print("MyThread ");
        }
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        for(int i = 0; i < 1000; i++) {
            System.out.println("--------------------------------------------------------------");
            System.out.print("MyRunnable ");
        }
    }
}

public class WaysToCreateThread {
    // Main is always a seperate thread
    public static void main(String args[]) {
        MyThread thread1 = new MyThread();
        thread1.start();
        MyRunnable thread2 = new MyRunnable();
        Thread thread = new Thread(thread2);
        thread.start();

        for(int i = 0; i < 1000; i++) {
            System.out.println("--------------------------------------------------------------");
            System.out.print("Main ");
        }
    }
}
