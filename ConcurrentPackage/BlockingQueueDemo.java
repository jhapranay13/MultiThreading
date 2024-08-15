package MultiThreading.ConcurrentPackage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


class Producer implements Runnable {
    BlockingQueue mq;
    int num = 0;

    public Producer(BlockingQueue mq) {
        this.mq = mq;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {

            try {
                mq.put("Hello " + num);
                System.out.println("Message Put >> " + "Hello " + (num++));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    BlockingQueue mq;

    public Consumer(BlockingQueue mq) {
        this.mq = mq;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            try {
                System.out.println("Recieved Message >> " + mq.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> bq = new ArrayBlockingQueue<String>(1);
        //ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue
        Thread prod = new Thread(new Producer(bq));
        Thread cons = new Thread(new Consumer(bq));
        prod.start();
        cons.start();
    }
}
