package tut.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/*
 * 
 * stop() method of the Thread class could be used to stop the thread in the middle. But this is the dangerous thing to do as it leaves the system in inconsistent state, because we are not giving the opportunity to the thread to rollback or reverse the actions that it has taken. And hence the stop method is deprecated and should not be used.

Correct approach would be to call the interrupt() method on the thread and then it is up to the thread to consider whether to stop or not.

A thread can then check if it was interrupted or not using interrupted() method of the Thread class. We can design the thread in a way that it reverses the actions/operations performed and then stop when interrupted.

Note - If you are not extending Thread class and instead implementing Runnable interface, then you can use Thread.isInterrupted() to check if the current thread is interrupted.

interrupted() and Thread.isInterrupted() both methods return true if the thread is interrupted when it is alive.
 * 
 * 
 * Thread States -
A thread can be in one of the following states:

NEW

A thread that is created but not yet started is in this state.

RUNNABLE

A thread executing in the Java virtual machine is in this state, internally we can think of it as a combination of two sub states Ready and Running, i.e. when you start the thread it comes to Ready state and wait for the CPU, and if CPU is allocated then it goes into Running state. Once allocated CPU time is completed, in other words when the Thread schedular suspends the thread then it goes back to the Ready state and waits for its turn again.

The yield() method instructs the thread schedular to pass the CPU to other waiting thread if any.

BLOCKED

A thread is blocked if it is waiting for a monitor lock is in this state. Refer synchronized methods and blocks.

WAITING

A thread that is waiting indefinitely for another thread to perform a particular action is in this state. Refer wait(), join()

TIMED_WAITING

A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state. Refer wait(millis), join(millis)

TERMINATED

A thread that has exited i.e. it has either completed its task or terminated in the middle of the execution.


yield() method -
yield() method is important in few scenarios, suppose a thread is given
 5 min of CPU time, now after a minute thread knows that it doesn't need the
  CPU anymore with in that time period, in such scenarios do you think that 
  blocking the CPU for the next four minutes is a good idea ? No, it is better 
  to pass on the control to the threads if any waiting for CPU and that is when 
  we can use the yield() method. Usage Thread.yield(), it is a static method of the
   Thread class and it affects the current thread from which the method is invoked.
 * 
 * 
 */

class FirstThread extends Thread {
	
	public void run() {
		
		for (int i = 0; i < 100; i++) {
			
			if (i % 9 == 0) {
				try {
					//Thread.sleep() doesn’t lose any monitors or lock the current thread it has acquired.
					Thread.sleep(1000);
					
					if (interrupted()) {
						System.out.println("Interrupted");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Thread 1 >> " + i);
		}
	}
}

class SecondThread implements Runnable {
	
	public void run() {
		
		for (int i = 0; i < 100; i++) {
			
			if (i % 9 == 0) {
				try {
					//Thread.sleep() doesn’t lose any monitors or lock the current thread it has acquired.
					Thread.sleep(1000);
					
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Thread 2 >> " + i);
		}
	}
}

public class ThreadCreation {

	public static void main(String[] args) {
		FirstThread thread1 = new FirstThread();
		thread1.setName("Thread 1");
		thread1.start();
		//thread1.interrupt();
		// 1 to 10
		/*
		 * Thread priorities range between 1 and 10.

MIN_PRIORITY - 1 being the minimum priority

NORM_PRIORITY - 5 is the normally priority, this is the default priority value.

MAX_PRIORITY - 10 being the max priority.
		 * 
		 * 
		 */
		thread1.setPriority(Thread.NORM_PRIORITY + 3);
		thread1.setDaemon(false);
		new Thread(new SecondThread(), "Thread 2").start();
		System.out.println("End of main");
		
		ExecutorService service = Executors.newFixedThreadPool(2);  
		// singleThreadExecutor. Creates a single Thread. So it serializes the task execution in sequence
		// newCachedThreadPool will create a new thread if none is avaialble. If one is avaialable that is used tot run the task
		service.execute(thread1);
		service.execute(new SecondThread());
		// service.shutdown();   // To shutdown the service and all the threads associated with it
		System.out.println("System Threads");
		Thread thrd = Thread.currentThread();
		ThreadGroup thrdGrp = thrd.getThreadGroup();
		
		while (thrdGrp.getParent() != null) {
			thrdGrp = thrdGrp.getParent();
		}
		thrdGrp.list();
	}

}
