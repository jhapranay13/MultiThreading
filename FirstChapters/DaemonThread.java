package MultiThreading.FirstChapters;

public class DaemonThread {
    public static void main(String args[]) {
        Thread thread = Thread.currentThread();
        ThreadGroup grp = thread.getThreadGroup();

        while (grp.getParent() != null) {
            grp = grp.getParent();
        }
        Thread[] threads = new Thread[10];
        int n = grp.enumerate(threads);
        //you can use setDaemon to set the thread as daemon
        for (int i = 0; i < n; i++) {
            System.out.println("Thread Namd >> " + threads[i].getName() + " || is Daemon >> " + threads[i].isDaemon());
        }
    }
}
