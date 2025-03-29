package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.ConcurrentHashMap;

class MapAdder implements Runnable {
	private ConcurrentHashMap< String, Integer > concurrentMap;

	public MapAdder( ConcurrentHashMap<String, Integer> concurrentMap ) {
		super();
		this.concurrentMap = concurrentMap;
	}

	@Override
	public void run() {
		
		try {
			System.out.println( "Adding >> " + concurrentMap.put( "A", 10 ) );
			System.out.println( "Adding >> " + concurrentMap.put( "B", 13 ) );
			Thread.sleep( 1000 );
			System.out.println( "Adding >> " + concurrentMap.put( "C", 122 ) );
			System.out.println( "Adding >> " + concurrentMap.put( "D", 23 ) );
			Thread.sleep( 1000 );
			System.out.println( "Adding >> " + concurrentMap.put( "E", 56 ) );
			System.out.println( "Adding >> " + concurrentMap.put( "F", 46 ) );
			System.out.println( "Adding >> " + concurrentMap.put( "G", 79 ) );
			System.out.println( "Adding >> " + concurrentMap.put( "H", 34 ) );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}
}

class MapRemover implements Runnable {
	private ConcurrentHashMap< String, Integer > concurrentMap;

	public MapRemover( ConcurrentHashMap<String, Integer> concurrentMap ) {
		super();
		this.concurrentMap = concurrentMap;
	}

	@Override
	public void run() {
		
		try {
			Thread.sleep( 4000 );
			System.out.println( "Removing >>" + concurrentMap.remove("A") );
			System.out.println( "Removing >>" + concurrentMap.remove("B") );
			System.out.println( "Removing >>" + concurrentMap.remove("C") );
			Thread.sleep( 1000 );
			System.out.println( "Removing >>" + concurrentMap.remove("D") );
			System.out.println( "Removing >>" + concurrentMap.remove("E") );
			System.out.println( "Removing >>" + concurrentMap.remove("F") );
			Thread.sleep( 500 );
			System.out.println( "Removing >>" + concurrentMap.remove("G") );
			System.out.println( "Removing >>" + concurrentMap.remove("H") );
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}
}

public class ConcurrentMapDemo {

	public static void main(String[] args) {
		ConcurrentHashMap< String, Integer > concurrentMap = new ConcurrentHashMap<>();
		
		new Thread( new MapAdder( concurrentMap ) ).start();
		new Thread( new MapRemover( concurrentMap ) ).start();
		
		//Collections.synchronizedCollection(c)
		//Collections.synchronizedList(list)
		//Collections.synchronizedMap(m);
		//Collections.synchronizedSet(s)
	}

}
