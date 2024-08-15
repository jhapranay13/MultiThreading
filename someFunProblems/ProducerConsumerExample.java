package tut.multithreading.someFunProblems;

import java.util.ArrayList;
import java.util.List;

class Producer extends Thread {
	private MessageQueue queue;
	
	public Producer(MessageQueue queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		for (int i = 1; i <= 20; i++) {
			System.out.println("Enqueing " + i);
			queue.enqueu(i);
		}
	}
}

class Consumer extends Thread {
	private MessageQueue queue;
	
	public Consumer(MessageQueue queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		for (int i = 1; i <= 20; i++) {
			int num = queue.deque();
			System.out.println("Dequeing ");
			System.out.println("Val is " + num);
		}
	}
}

class MessageQueue {
	private List<Integer> list = new ArrayList<>();
	private int limit;
	
	
	
	public MessageQueue(int limit) {
		super();
		this.limit = limit;
	}

	public synchronized void enqueu(int num) {
		
		while (list.size() == limit) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		list.add(num);
		notify();
	}
	
	public synchronized int deque() {
		
		while (list.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int num = list.remove(0);
		notify();
		return num;
	}
}

public class ProducerConsumerExample {

	public static void main(String[] args) {
		MessageQueue queue = new MessageQueue(1);
		Producer p = new Producer(queue);
		Consumer c = new Consumer(queue);
		p.start();
		c.start();
		System.out.println("Main Ends");
	}

}
