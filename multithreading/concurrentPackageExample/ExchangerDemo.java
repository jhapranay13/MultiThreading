package tut.multithreading.concurrentPackageExample;

import java.util.Random;
import java.util.concurrent.Exchanger;

//Used for exchanging Value between Two Threads

class  Incrementer implements Runnable {
	private Exchanger< Integer > exchanger;
	private int counter;
	private Random random = new Random();
	
	public Incrementer(Exchanger<Integer> exchanger) {
		super();
		this.exchanger = exchanger;
	}
	
	@Override
	public void run() {
		
		while( true ) {
			System.out.println( "Incrementing and Exchanging" );
			counter++;
			System.out.println( "Value in Incrementer >> " + counter );
			try {
				Thread.sleep( random.nextInt( 2000 ) );
				counter = exchanger.exchange( counter );
				System.out.println( "Exchnged Value in Incrementer >> " + counter  );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class  Decrementer implements Runnable {
	private Exchanger< Integer > exchanger;
	private int counter;
	private Random random = new Random();
	
	public Decrementer(Exchanger<Integer> exchanger) {
		super();
		this.exchanger = exchanger;
	}
	
	@Override
	public void run() {
		
		while( true ) {
			System.out.println( "Decrementing and Exchanging" );
			counter--;
			System.out.println( "Value in Decrementer >> " + counter );
			try {
				Thread.sleep( random.nextInt( 2000 ) );
				counter = exchanger.exchange( counter );
				System.out.println( "Exchnged Value in Decrementer >> " + counter  );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class ExchangerDemo {

	public static void main(String[] args) {
		Exchanger< Integer > exchanger = new Exchanger<>();
		
		new Thread( new Incrementer( exchanger ) ).start();
		new Thread( new Decrementer( exchanger ) ).start();
	}

}
