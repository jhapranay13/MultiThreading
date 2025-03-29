package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer1 extends Thread {
	private BlockingQueue<Integer> queue;
	
	public Producer1(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		for (int i = 1; i <= 20; i++) {
			System.out.println("Enqueing " + i);
			
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer1 extends Thread {
	private BlockingQueue<Integer> queue;
	
	public Consumer1(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		for (int i = 1; i <= 20; i++) {
			int num = -1;
			
			try {
				num = queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Dequeing ");
			System.out.println("Val is " + num);
		}
	}
}

public class BlockinqQueueExample {
	// PriorityBlocking queue is similar except it would need comparator or comparable objects
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		new Producer1(queue).start();
		new Consumer1(queue).start();
	}

}
