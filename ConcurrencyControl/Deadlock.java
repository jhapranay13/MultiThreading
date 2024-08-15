package MultiThreading.ConcurrencyControl;

class Writer1 implements Runnable {
    Object book;
    Object pen;

    public Writer1(Object book, Object pen) {
        this.book = book;
        this.pen = pen;
    }

    @Override
    public void run() {
        System.out.println("Writer 1 Trying to lock book");
        synchronized (book) {
            // This simulates that Thread losses CPU to other thread so that deadlock situation happens
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Writer 1 Trying to lock pne");
            synchronized (pen) {
                System.out.println("Writer 1 Writing");
            }
        }
    }
}

class Writer2 implements Runnable {
    Object book;
    Object pen;

    public Writer2(Object book, Object pen) {
        this.book = book;
        this.pen = pen;
    }

    @Override
    public void run() {
        System.out.println("Writer 2 Trying to lock pen");
        synchronized (pen) {
            System.out.println("Writer 2 Trying to lock book");
            synchronized (book) {
                System.out.println("Writer 2 Writing");
            }
        }
    }
}
// if the sequence of locking is corrected the deadlock usually gets resolved
public class Deadlock {
    public static void main(String[] args) {
        Object book = new Object();
        Object pen = new Object();
        Thread t1 = new Thread(new Writer1(book, pen));
        Thread t2 = new Thread(new Writer2(book, pen));

        t1.start();
        t2.start();
    }
}
