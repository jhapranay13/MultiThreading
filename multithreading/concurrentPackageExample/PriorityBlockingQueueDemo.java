package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class PriorityProducer implements Runnable {
	private BlockingQueue< String > queue;
	
	public PriorityProducer( BlockingQueue<String> queue ) {
		super();
		this.queue = queue;
	}
	
	@Override
	public void run() {
		queue.add( "F" );
		queue.add( "E" );
		queue.add( "D" );
		queue.add( "G" );
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		queue.add( "A" );
		queue.add( "B" );
		queue.add( "L" );
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		queue.add( "M" );
		queue.add( "N" );
		queue.add( "O" );
	}
	
}

class PriorityConsumer implements Runnable {
	private BlockingQueue< String > queue;
	
	public PriorityConsumer( BlockingQueue<String> queue ) {
		super();
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(2000);
			System.out.println( queue.take() );
			System.out.println( queue.take() );
			System.out.println( queue.take() );
			Thread.sleep(2000);
			System.out.println( queue.take() );
			System.out.println( queue.take() );
			System.out.println( queue.take() );
			Thread.sleep(2000);
			System.out.println( queue.take() );
			System.out.println( queue.take() );
			System.out.println( queue.take() );
			System.out.println( queue.take() );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}
	
}

public class PriorityBlockingQueueDemo {

	public static void main(String[] args) {
		BlockingQueue< String > queue = new PriorityBlockingQueue<>();
		new Thread( new PriorityProducer( queue ) ).start();
		new Thread( new PriorityConsumer( queue ) ).start();
	}

}
