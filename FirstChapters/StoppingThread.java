package MultiThreading.FirstChapters;


public class StoppingThread {
    static class MyRunnable extends Thread {

        @Override
        public void run() {
            for(; ;) {

                if (interrupted()) {
                    System.out.println("Interrupted");
                    break;
                }
                System.out.print("MyRunnable ");
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable thread = new MyRunnable();
        thread.start();
        try {
            Thread.sleep(1000);// does not unlock resource
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //thread.stop(); dangerous don't so it.
        thread.interrupt();
    }
}
