package com.example.completablefuture;

import com.example.apis.FileService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureWithExecutorDemo {
    public static void main(String[] args) {
        try {
            // Record the start time
            long start = System.currentTimeMillis();

            FileService fileService = new FileService();

//            Create a virtual thread per task executor
            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

                // Run 'uploadImage' asynchronously using the custom thread pool
                CompletableFuture<String> imageFuture = CompletableFuture.supplyAsync(fileService::uploadImage, executorService);

                // Run 'saveVideo' asynchronously using the same executor
                CompletableFuture<String> videoFuture = CompletableFuture.supplyAsync(fileService::saveVideo, executorService);

                // Print result of image upload once completed
                imageFuture.thenAccept(System.out::println);

                // Print result of video save once completed
                videoFuture.thenAccept(System.out::println);

                // Wait for both tasks to complete
                CompletableFuture.allOf(imageFuture, videoFuture).join();

                // Record the end time and print total time taken
                long end = System.currentTimeMillis();
                System.out.println("Both tasks completed");
                System.out.println("Total time taken: " + (end - start) + " ms");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
