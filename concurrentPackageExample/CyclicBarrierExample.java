package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// Can be resused again and again
// Similar to join
// when a thread calls await it waits for next thread to call await

class CBExample implements Runnable {
	private CyclicBarrier cb;
	
	
	
	public CBExample(CyclicBarrier cb) {
		super();
		this.cb = cb;
	}



	@Override
	public void run() {
		System.out.println(Thread.currentThread() + " Finished");

		try {
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

public class CyclicBarrierExample {

	public static void main(String[] args) {
		CyclicBarrier cb= new CyclicBarrier(2, () -> {
			System.out.println("All Tasks Done");
		});
		new Thread(new CBExample(cb)).start();
		new Thread(new CBExample(cb)).start();
		try {
			cb.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Main ends");
	}

}
