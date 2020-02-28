package test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ThreadTest {

    public static void main(String[] args) {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("223");
                return "123";
            }
        };
        try {
            FutureTask<String> future = new FutureTask<>(callable);
            Thread thread = new Thread(future);
            thread.start();
            System.out.println(future.get());
        } catch (Exception e) {

        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("222");
            }
        };

    }
}
