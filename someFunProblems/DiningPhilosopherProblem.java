package tut.multithreading.someFunProblems;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum State {
	LEFT, RIGHT;
}

class ChopStick {
	private int id;
	private Lock lock;
	
	public ChopStick(int id) {
		super();
		this.id = id;
		this.lock = new ReentrantLock();
	}
	
	public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException {
		
		if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println(philosopher + " picked up " + this + " as " + state.toString() + " Chopstick");
			return true;
		}
		return false;
	}
	
	public void putDown(Philosopher philosopher, State state) {
		lock.unlock();
		System.out.println(philosopher + " put down " + this + " as " + state.toString());
	}
	
	public String toString() {
		return "Chopstick " + id;
	}
}

class Philosopher implements Runnable {
	private int id;
	public boolean isFull;
	private ChopStick left;
	private ChopStick right;
	public int eatCounter = 0;
	
	
	public Philosopher(int id, boolean isFull, ChopStick left, ChopStick right) {
		super();
		this.id = id;
		this.isFull = isFull;
		this.left = left;
		this.right = right;
	}

	@Override
	public void run() {
		
		while (!isFull) {
			try {
				
				if(left.pickUp(this, State.LEFT)) {
					
					if (right.pickUp(this, State.RIGHT)) {
						eat();
						right.putDown(this, State.RIGHT);
					}
					left.putDown(this, State.LEFT);
				}
				think();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void eat() throws InterruptedException {
		System.out.println(this + " Eating.....");
		eatCounter++;
		Thread.sleep((long) (Math.random() * 1000));
	}
	
	public void think() throws InterruptedException {
		System.out.println(this + " Thinking.....");
		Thread.sleep((long) (Math.random() * 1000));
	}

	public String toString() {
		return "Philosopher " + id;
	}
}

public class DiningPhilosopherProblem {

	public static void main(String[] args) {
		ExecutorService svc = Executors.newFixedThreadPool(5);

		ChopStick[] sticks = new ChopStick[5];
		Philosopher[] phils = new Philosopher[5];

		try {
			
			for (int i = 0; i < 5; i++) {
				sticks[i] = new ChopStick(i);
			}
			
			for (int i = 0; i < 5; i++) {
				phils[i] = new Philosopher(i, false, sticks[i], sticks[(i + 1) % 5]);
				svc.execute(phils[i]);
			}
			
			Thread.sleep(10000);
			
			for (int i = 0; i < 5; i++) {
				phils[i].isFull = true;
			}
			svc.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
			if(!svc.isTerminated()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < 5; i++) {
				System.out.println("Philosopher" + phils[i] + " ate " + phils[i].eatCounter);
			}
		}

		System.out.println("End Main");
	}

}
