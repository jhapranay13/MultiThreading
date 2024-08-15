package MultiThreading.FirstChapters;

import java.util.concurrent.*;

class TaskReturn implements Callable<Double> {

    @Override
    public Double call() throws Exception {
        Thread.sleep((long) Math.random() * (6000 - 1000) + 1000);
        double res = Math.random();
        System.out.println("Executing >> " + Thread.currentThread() + " value >> " + res);
        return res;
    }
}

public class ReturningThreadValuesAsAndWhenItCompletes {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletionService<Double> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(new TaskReturn());
        completionService.submit(new TaskReturn());
        completionService.submit(new TaskReturn());
        completionService.submit(new TaskReturn());

        for (int i = 0; i < 4; i++) {

            try {
                System.out.println("Got back >> " + completionService.take().get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
