package tut.multithreading.virtualThread;

import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.StructuredTaskScope;

class TaskProcess implements Callable<String> {

    private final String name;
    private final String returnVal;
    private final long sleeptime;
    private final boolean throwException;

    public TaskProcess(String name, String returnVal, long sleeptime, boolean throwException) {
        this.name = name;
        this.returnVal = returnVal;
        this.sleeptime = sleeptime;
        this.throwException = throwException;
    }
    
    @Override
    public String call() {
        System.out.println("TaskProcess " + name + " started");
        try {
            Thread.sleep( sleeptime );

            if (throwException) {
                throw new RuntimeException("Exception from " + name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnVal;
    }
}


public class StructuredTaskDemo {
    // Demo of StructuredConcurrency
    // java: --enable-preview must be used with either -source or --release
    public static void main(String[] args) {

       /* try(StructuredTaskScope scope = new StructuredTaskScope<String>()) {
            TaskProcess task1 = new TaskProcess("Task1", "Task1", 1000, false);
            TaskProcess task2 = new TaskProcess("Task2", "Task2", 2000, false);

            StructuredTaskScope.Subtask<String> subtask1 = scope.fork(task1);
            StructuredTaskScope.Subtask<String> subtask2 = scope.fork(task2);

            scope.join();
            System.out.println("Task1 result: " + subtask1.state() + " Task2 result: " + subtask2.state());

            System.out.println("Task1 result: " + subtask1.get() + " Task2 result: " + subtask2.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try(StructuredTaskScope.ShutdownOnFailure scope = new StructuredTaskScope.ShutdownOnFailure()) {
            TaskProcess task1 = new TaskProcess("Task1", "Task1", 1000, true);
            TaskProcess task2 = new TaskProcess("Task2", "Task2", 7000, false);

            StructuredTaskScope.Subtask<String> subtask1 = scope.fork(task1);
            StructuredTaskScope.Subtask<String> subtask2 = scope.fork(task2);

            scope.join();

            try{
                scope.throwIfFailed();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("Task1 result: " + subtask1.get() + " Task2 result: " + subtask2.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try(StructuredTaskScope.ShutdownOnSuccess scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            TaskProcess task1 = new TaskProcess("Task1", "Task1", 1000, true);
            TaskProcess task2 = new TaskProcess("Task2", "Task2", 7000, false);

            StructuredTaskScope.Subtask<String> subtask1 = scope.fork(task1);
            StructuredTaskScope.Subtask<String> subtask2 = scope.fork(task2);

            scope.join();

            System.out.println("Task1 resul: " + scope.result());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } */
    }
}
