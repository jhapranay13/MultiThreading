package tut.multithreading;

class WorkerVolatile implements Runnable {
	//volatile keyword forces the progrm to read from Main RAM memory and
	//not from CPU cache. This way all the threads can see the changes.
	//If in CPU cache of the thread other threads could not be able to see the change.
	//Performance might take a hit as it disables instruction reordering which is 
	//an optimization technique of programming language.
	private volatile boolean isTerminated = false;
	
	
	public boolean isTerminated() {
		return isTerminated;
	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	public void run() {
		
		while( !isTerminated ) {
			System.out.println( "IS NOT Terminated >> ");
		}
	}
}
public class VolatileKeyWord {

	public static void main(String[] args) {
		WorkerVolatile worker = new WorkerVolatile();
		Thread thread = new Thread( worker );
		
		thread.start();
		
		try {
			Thread.sleep(100);
			worker.setTerminated( true );
		} catch ( InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println( "FInish Main" );
	}

}
