package tut.multithreading.virtualThread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureFirstDemo {

    public static void main(String args[]) {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread() + " Task Started");
            return "Hello";
        })
                .thenApply(result -> {
                    System.out.println(Thread.currentThread() + " Task Started");
                    return result + " World";
                })                              // Then apply can have different executor pool as well
                .thenApplyAsync(result -> {    // This will run in a different thread Like Fork and join
                    System.out.println(Thread.currentThread() + " Task Started");
                    return result + "!";
                })
                .thenAccept(System.out::println);

        ExecutorService service = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread() + " Task Started");
                    return "Hello";
                })
                .thenCombine(CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread() + " Task Started");
                    return " World";
                }, service), (result1, result2) -> result1 + result2)
                .thenApplyAsync(result -> {    // This will run in a different thread Like Fork and join
                    System.out.println(Thread.currentThread() + " Task Started");
                    return result + "!";
                })
                .thenAccept(System.out::println);

        String str = CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread() + " Task Started");
                    return "Hello";
                })
                .thenCombine(CompletableFuture.supplyAsync(() -> {
                    System.out.println(Thread.currentThread() + " Task Started");
                    return " World";
                }, service), (result1, result2) -> result1 + result2)
                .thenApplyAsync(result -> {    // This will run in a different thread Like Fork and join
                    System.out.println(Thread.currentThread() + " Task Started");
                    return result + "!";
                })
                .join();  // this is blocking call
        System.out.println("Result >> " + str);
    }

}
