package MultiThreading.ConcurrentPackage;

import java.util.concurrent.Semaphore;

class Printer extends Thread {
    Semaphore semaphore;
    int id;

    public Printer(Semaphore semaphore, int id) {
        this.semaphore = semaphore;
        this.id = id;
    }

    public void print() {

        try {
            semaphore.acquire();
            System.out.println("Printing for ID >> " + id);
            Thread.sleep(1000);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        print();
    }
}

public class SemaphoreDemo {
    public static void main(String args[]) {
        Semaphore semaphore = new Semaphore(3); // 3 permits
        Printer printer1 = new Printer(semaphore, 1);
        Printer printer2 = new Printer(semaphore, 2);
        Printer printer3 = new Printer(semaphore, 3);
        Printer printer4 = new Printer(semaphore, 4);
        printer1.start();
        printer2.start();
        printer3.start();
        printer4.start();
    }
}
