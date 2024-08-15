package tut.multithreading.joinexp;

class ReturnValTask implements Runnable {
	int a;
	int b;
	int result = 0;
	
	public ReturnValTask(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " Task Started");
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		result = a + b;
		System.out.println(Thread.currentThread().getName() + " DONE COMPUTATION");
	}
	
	public int getResult() {
		return result;
	}
}

public class JoinExample1 {

	public static void main(String[] args) throws InterruptedException {
		ReturnValTask r1 = new ReturnValTask(10, 34);
		ReturnValTask r2 = new ReturnValTask(100, 34);
		ReturnValTask r3 = new ReturnValTask(10, 340);
		Thread t1 = new Thread(r1, "Thread 1");
		Thread t2 = new Thread(r2, "Thread 2");
		Thread t3 = new Thread(r3, "Thread 3");
		
		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		System.out.println("Joined " + t1.getName() + " " + r1.getResult());
		t2.join();
		System.out.println("Joined " + t2.getName() + " " + r2.getResult());
		t3.join();
		System.out.println("Joined " + t3.getName() + " " + r3.getResult());
		
		System.out.println("Main Ended");
		
	}

}
