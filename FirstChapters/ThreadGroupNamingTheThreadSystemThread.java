package MultiThreading.FirstChapters;

public class ThreadGroupNamingTheThreadSystemThread {

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        System.out.println("--------------Thread Info----------------");
        Thread thread = Thread.currentThread();
        ThreadGroup grp = thread.getThreadGroup();

        while (grp.getParent() != null) {
            grp = grp.getParent();
        }
        grp.list();

        Thread thread1 = new Thread(new MyTask(), "MyTask 1");
        thread1.start();
        System.out.println("--------------MyTask Info----------------");
        thread = Thread.currentThread();
        ThreadGroup grp1 = thread.getThreadGroup();

        while (grp1.getParent() != null) {
            grp1 = grp1.getParent();
        }
        grp1.list();

        ThreadGroup myGrp = new ThreadGroup("MyGroup");
        myGrp.setMaxPriority(7); // Setting Max priority for threads of this grp
        Thread thread2 = new Thread(myGrp, new MyTask(), "MyTask 2");
        thread2.start();
        System.out.println("--------------MyGroup Info----------------");
        thread = Thread.currentThread();
        ThreadGroup grp2 = thread.getThreadGroup();

        while (grp2.getParent() != null) {
            grp2 = grp2.getParent();
        }
        grp2.list();

    }
}
