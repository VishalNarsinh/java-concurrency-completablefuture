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

            // Create a fixed thread pool with 2 threads
            try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {

                // Run 'uploadImage' asynchronously using the custom thread pool
                CompletableFuture<String> imageFuture = CompletableFuture.supplyAsync(()->{
                    System.out.println("Image task executed by: " + Thread.currentThread().getName());
                    return fileService.uploadImage();
                }, executorService);

                // Run 'saveVideo' asynchronously using the same executor
                CompletableFuture<String> videoFuture = CompletableFuture.supplyAsync(()->{
                    System.out.println("Video task executed by: " + Thread.currentThread().getName());
                    return fileService.saveVideo();
                }, executorService);

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
