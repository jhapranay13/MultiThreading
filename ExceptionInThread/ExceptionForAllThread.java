package MultiThreading.ExceptionInThread;

class ExceptionThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }
}

public class ExceptionForAllThread {

    public static void main(String args[]) {

        try {
            new Thread(new ExceptionThread()).start();
            new Thread(new ExceptionThread()).start();
            new Thread(new ExceptionThread()).start();
            new Thread(new ExceptionThread()).start();
        } catch(RuntimeException re) {
            re.printStackTrace();
        }
    }
}
