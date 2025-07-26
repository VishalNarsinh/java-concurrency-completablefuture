package com.example.future;

import com.example.apis.FileService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
    public static void main(String[] args) {
        try {
            // Create a fixed thread pool with 2 threads
            try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
                FileService fileService = new FileService();

                // Define a task to upload an image
                Callable<String> imageTask = () -> {
                    System.out.println("Image task executed by: " + Thread.currentThread().getName());
                    return fileService.uploadImage();
                };

                // Define a task to save a video
                Callable<String> videoTask = () -> {
                    System.out.println("Video task executed by: " + Thread.currentThread().getName());
                    return fileService.saveVideo();
                };

                // Record start time
                long start = System.currentTimeMillis();

                // Submit both tasks to executor service for parallel execution
                Future<String> imageFuture = executorService.submit(imageTask);
                Future<String> videoFuture = executorService.submit(videoTask);

                // Wait for both tasks to complete (blocking calls)
                String imageUrl = imageFuture.get();
                String videoPath = videoFuture.get();

                // Record end time
                long end = System.currentTimeMillis();

                System.out.println("With async execution using Callable and Future");
                System.out.println("Image URL: " + imageUrl);
                System.out.println("Video Path: " + videoPath);
                System.out.println("Total time taken: " + (end - start) + " ms");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
