package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * This is an unbounded BlockingQueue of objects that implement the Delayed
 * interface
 * 
 * - DelayQueue keeps the elements internally until a certain delay has expired
 * 
 * - an object can only be taken from the queue when its delay has expired !!! -
 * 
 * We cannot place null items in the queue - The queue is sorted so that the
 * object at the head has a delay that has expired for the longest time.
 * 
 * If no delay has expired, then there is no head element and poll( ) will
 * return null
 * 
 * size() return the count of both expired and unexpired items !!!
 *
 */

class DelayedWorker implements Delayed {
	private long duration;
	private String message;
	
	public DelayedWorker(long duration, String message) {
		super();
		this.duration = System.currentTimeMillis() + duration;
		this.message = message;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int compareTo(Delayed obj) {
		
		if( this.duration < ( (DelayedWorker) obj ).getDuration() ) {
			return -1;
		} else if ( this.duration > ( (DelayedWorker) obj ).getDuration() ) {
			return 1;
		}
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public String toString() {
		return "DelayedWorker [message=" + message + "]";
	}
	
}

public class DelayedQueueDemo {
	
	public static void main( String args[] ) {
		BlockingQueue< DelayedWorker > queue = new DelayQueue<>();
		
		queue.add( new DelayedWorker(1000, "First Message....") );
		queue.add( new DelayedWorker(10000, "Second Message....") );
		queue.add( new DelayedWorker(2000, "Third Message....") );
		
		while( !queue.isEmpty() ) {
			
			try {
				System.out.println( queue.take() );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
