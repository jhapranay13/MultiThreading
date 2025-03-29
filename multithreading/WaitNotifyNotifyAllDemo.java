package tut.multithreading;

class AllOp {

	public void producer() throws InterruptedException {

		synchronized ( this ) {
			System.out.println( "In the producer method..." );
			wait(); // wait releases the resources for other thread to access or process
			System.out.println( "proucer again...." );
		}
	}

	public void consumer() throws InterruptedException {

		Thread.sleep( 1000 ); //Thread.sleep does not release the resources
		synchronized ( this ) {
			System.out.println( "In the consumer method..." );
			notify(); //notifyAll() to notify all the waiting thread
			//Any code after the notify will still execute regardless
			System.out.println( "consumer again...." );
		}
	}
}

public class WaitNotifyNotifyAllDemo {

	public static void main(String[] args) {
		AllOp op = new AllOp();

		Thread thread1 = new Thread( new Runnable() {

			@Override
			public void run() {
				try {
					op.producer();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}

			}
		});

		Thread thread2 = new Thread( new Runnable() {

			@Override
			public void run() {
				try {
					op.consumer();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}

			}
		});
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
