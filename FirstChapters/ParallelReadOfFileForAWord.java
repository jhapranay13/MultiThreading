package MultiThreading.FirstChapters;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

class PatternFinder {

    public List<Integer> find(File file, String pattern) {
        List<Integer> ans = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int num = 1;

            while ((line = br.readLine()) != null) {

                if (line.contains(pattern)) {
                    ans.add(num);
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } ;
        return ans;
    }
}

class FileSearchTask implements Callable<List<Integer>> {
    File file;
    String pattern;
    PatternFinder finder = new PatternFinder();

    public FileSearchTask(File file, String pattern) {
        this.file = file;
        this.pattern = pattern;
    }

    @Override
    public List<Integer> call() throws Exception {
        return finder.find(file, pattern);
    }
}

public class ParallelReadOfFileForAWord {

    public static void main(String args[]) throws ExecutionException, InterruptedException {
        String pattern = "public";
        String filePath = "/Users/pranayjha/IdeaProjects/Pranay/src/MultiThreading/FirstChapters";
        PatternFinder finder = new PatternFinder();
        System.out.println("-----------------Sequential Reading------------------");
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        long startTime = System.currentTimeMillis();

        for (File file : files) {
            List<Integer> lineNum = finder.find(file, pattern);

            if (!lineNum.isEmpty()) {
                System.out.println("Pattern [" + pattern + "] Found at " + lineNum + " in file " + file.getName());
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        System.out.println("-----------------Parallel Reading------------------");
        int numOfProcessor = Runtime.getRuntime().availableProcessors();;
        ExecutorService executor = Executors.newFixedThreadPool(numOfProcessor);
        Map<String, Future<List<Integer>>> holder = new HashMap<>();
        Map<String, List<Integer>> result = new HashMap<>();
        startTime = System.currentTimeMillis();

        for (File file : files) {
            Future<List<Integer>> proomise = executor.submit(new FileSearchTask(file, pattern));
            holder.put(file.getName(), proomise);
        }
        waitForAll(holder, result);
        executor.shutdown();
        endTime = System.currentTimeMillis();

        for (String key : result.keySet()) {
            System.out.println("Pattern [" + pattern + "] Found at " + result.get(key) + " in file " + key);
        }
        System.out.println(endTime - startTime);
    }

    private static void waitForAll(Map<String, Future<List<Integer>>> holder, Map<String, List<Integer>> result) throws ExecutionException, InterruptedException {

        for (String key : holder.keySet()) {
            Future<List<Integer>> future = holder.get(key);

            while (!future.isDone()) {
                Thread.yield();
            }

            if (!future.get().isEmpty()) {
                result.put(key, future.get());
            }
        }
    }
}
