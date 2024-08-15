package MultiThreading.FirstChapters;

/**
*
* Thread Priority :-
*
* MIN_PRIORITY 1
* NORM_PRIORITY 5
* MAX_PRIORITY 10
*
* setPriority(int newPriority)
*
*/

public class ThreadPriority {
    static class Task1 implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 1000; i++) {
                System.out.print("C");
            }
        }
    }

    static class Task2 implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 1000; i++) {
                System.out.print("-");
            }
        }
    }

    public static void main(String args[]) {
        Thread t1 = new Thread(new Task1());
        Thread t2 = new Thread(new Task2());

        t1.setPriority(Thread.NORM_PRIORITY + 3);
        t2.setPriority(Thread.NORM_PRIORITY);
        t1.start();
        t2.start();
    }
}
