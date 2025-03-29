package tut.multithreading.concurrentPackageExample;

import java.util.concurrent.Semaphore;

enum DownloadEnum {
	INSTANCE;
	
	private Semaphore semaphore = new Semaphore(3);
	
	public void download() {
		try {
			semaphore.acquire();
			long time = (long) (Math.random() * 1000);
			System.out.println(Thread.currentThread() + " sleeping for " + time);
			Thread.sleep(time);
			System.out.println(Thread.currentThread() + " downloading data....");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}
}

public class SemaphoreExample {

	public static void main(String[] args) {
		
		for (int i = 0; i < 15; i++) {
			new Thread(() -> {
				DownloadEnum.INSTANCE.download();
			}).start();;
		}
	}

}
