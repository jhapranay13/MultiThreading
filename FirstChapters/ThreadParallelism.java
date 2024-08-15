package MultiThreading.FirstChapters;

import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class IOUtil {
    // If a method does not interact with class state does not need to be synchronized
    // every thread gets it's own copy of method
    public static void write(InputStream in, OutputStream os) throws IOException {
        int value;

        while ((value = in.read()) != -1) {
            os.write(value);
        }
    }
}

class CopyThread implements Runnable {
    InputStream src;
    OutputStream dest;

    public  CopyThread(InputStream src, OutputStream dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public void run() {
        try {
            IOUtil.write(src, dest);
            System.out.println("Copy Complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadParallelism {
    static String path = "/Users/pranayjha/IdeaProjects/Pranay/src/MultiThreading/FirstChapters/";

    public static void main(String args[]) throws FileNotFoundException {
        FileInputStream fisA = new FileInputStream(path + "a.txt");
        FileOutputStream fosA = new FileOutputStream(path + "c.txt");

        FileInputStream fisB = new FileInputStream(path + "b.txt");
        FileOutputStream fosB = new FileOutputStream(path + "d.txt");
        /*
        Thread t1 = new Thread(new CopyThread(fisA, fosA));
        Thread t2 = new Thread(new CopyThread(fisB, fosB));
        t1.start();
        t2.start();
         */
        // This creates thread pool. If a Thread is not available then it Task waits till
        // it becomes available. This saves us from creating new Thread everytime.
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(new CopyThread(fisA, fosA));
        executor.execute(new CopyThread(fisB, fosB));
    }
}
