package com.github.raphaelfontoura.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/*
Code get from book "Modern Concurrency in Java"
Run with Java 21 or later.
 */
public class LittleLawExample {
    public static void main(String[] args) {
        int numTasks = 10_000;
        int avgResponseTimeMillis = 500;

        // Average task response time
        // Simulate adjustable I/O - bound work
        Runnable ioBoundTask = () -> {
            try {
                Thread.sleep(Duration.ofMillis(avgResponseTimeMillis));
            } catch (InterruptedException e) {}
        };

        benchmark("Virtual Threads", Executors.newVirtualThreadPerTaskExecutor(), ioBoundTask, numTasks);
        benchmark("Fixed ThreadPool (100)", Executors.newFixedThreadPool(100), ioBoundTask, numTasks);
        benchmark("Fixed ThreadPool (500)",  Executors.newFixedThreadPool(500), ioBoundTask, numTasks);
        benchmark("Fixed ThreadPool (1000)", Executors.newFixedThreadPool(1000), ioBoundTask, numTasks);

    }

    static void benchmark(String type, ExecutorService executor, Runnable task, int numTasks) {
        Instant start = Instant.now();
        AtomicLong completed = new AtomicLong();
        try (executor) {
            IntStream.range(0, numTasks)
                    .forEach(i -> executor.submit(() -> {
                        task.run();
                        completed.incrementAndGet();
                    }));
        }
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toMillis();
        double throughput = (double) completed.get() / duration * 1000.0;

        // Tasks per second
        System.out.printf("%s - Time: %dms, Throughput: %.2f/s\n", type, duration, throughput);
    }

}
