package MultiThreading.ConcurrencyControl;

import java.util.Deque;
import java.util.LinkedList;

class MessageQueue {
    Deque<String> q = new LinkedList<>();
    int size = 10;

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public boolean isFull() {
        return size == q.size();
    }

    public synchronized void offer(String str) throws InterruptedException {

        while (isFull()) {
            this.wait();
        }
        System.out.println("Offering >> " + str);
        q.offer(str);
        this.notify();
    }

    public synchronized String poll() throws InterruptedException {

        while (isEmpty()) {
            this.wait();
        }
        String str = q.poll();
        this.notify();
        return str;
    }
}

class Producer implements Runnable {
    MessageQueue mq = new MessageQueue();
    int num = 0;

    public Producer(MessageQueue mq) {
        this.mq = mq;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {

            try {
                mq.offer("Hello " + (num++));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    MessageQueue mq = new MessageQueue();

    public Consumer(MessageQueue mq) {
        this.mq = mq;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            try {
                System.out.println("Recieved Message >> " + mq.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        MessageQueue mq = new MessageQueue();
        Thread prod = new Thread(new Producer(mq));
        Thread cons = new Thread(new Consumer(mq));
        prod.start();
        cons.start();
    }
}
