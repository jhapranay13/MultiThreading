package MultiThreading.ConcurrentPackage;
//Thread execution proceeds only when all the threads have reached the barrier

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class A implements Runnable {
    CyclicBarrier cyclicBarrier;

    public A(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Processing A");
    }
}

class B implements Runnable {
    CyclicBarrier cyclicBarrier;

    public B(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Processing B");
    }
}

public class CyclicBarrierDemo {

    public static void main(String args[]) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(new A(cyclicBarrier)).start();

        try {
            Thread.sleep(2000); //Simulating Delay in 2nd Task
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new B(cyclicBarrier)).start();
    }
}
