package tut.multithreading.syncandlocking;

class Writer1 extends Thread {
	Object pen;
	Object book;
	
	
	public Writer1(Object pen, Object book) {
		super();
		this.pen = pen;
		this.book = book;
	}


	public void run() {
		
		synchronized(book) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized(pen) {
				System.out.println("Writer1 Writing");
			}
		}
	}
}

class Writer2 extends Thread {
	Object pen;
	Object book;
	
	
	public Writer2(Object pen, Object book) {
		super();
		this.pen = pen;
		this.book = book;
	}


	public void run() {
		
		synchronized(pen) {
			synchronized(book) {
				System.out.println("Writer2 Writing");
			}
		}
	}
}

public class DeadlockExample {
	// Can be solved be reorganizing synchronized and resequencing it
	public static void main(String[] args) {
		Object pen = new Object();
		Object book = new Object();
		Writer1 w1 = new Writer1(pen, book);
		Writer2 w2 = new Writer2(pen, book);
		w1.start();
		w2.start();
		System.out.println("Main Ends");
	}

}
