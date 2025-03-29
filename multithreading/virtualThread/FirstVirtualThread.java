package tut.multithreading.virtualThread;

class VirtualThread1 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            System.out.println("Thread Name: " + Thread.currentThread().getName());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class FirstVirtualThread {

    public static void main(String args[]) throws InterruptedException {
        // All Virtual Threads are deamons threads
        Thread.ofVirtual().name("Virtual Thread - ", 0).start(new VirtualThread1()).join();
        //var builder = Thread.ofVirtual().name("Virtual Thread - ", 0);
       //builder.start(new VirtualThread1()).join();
       //builder.start(new VirtualThread1()).join();
        var factory = Thread.ofVirtual().name("Virtual Thread Factory - ", 0).factory();
        var t1 = factory.newThread(new VirtualThread1());
        var t2 = factory.newThread(new VirtualThread1());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        // Using Synchronized block of code is not preferred in Virtual Threads as it gets pinned to platform thread
        // and won't be unmounted
    }
}
