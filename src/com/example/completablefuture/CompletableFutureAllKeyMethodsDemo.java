package com.example.completablefuture;

import com.example.apis.FileService;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAllKeyMethodsDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis(); // Starting time of total execution

        FileService fileService = new FileService();

        // IMAGE FUTURE PIPELINE
        // Step 1: Upload image asynchronously
        // Step 2: Process the uploaded image
        // Step 3: Append optimization query to final URL
        CompletableFuture<String> imageFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Inside image task 1: " + Thread.currentThread().getName());
                    return fileService.uploadImage();
                })
                .thenApply((url) -> {
                    System.out.println("Inside image task 2: " + Thread.currentThread().getName());
                    return fileService.processImage(url);
                })
                .thenApply(url -> {
                    System.out.println("Inside image task 3: " + Thread.currentThread().getName());
                    return url + "&optimized=true";
                })
                .exceptionally(e->{
                    System.out.println("Image task failed: " + e.getMessage());
                    return "Image task failed";
                });

        // VIDEO FUTURE PIPELINE
        // Step 1: Save video asynchronously
        // Step 2: Append optimization query to final path
        CompletableFuture<String> videoFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Inside video task 1: " + Thread.currentThread().getName());
                    return fileService.saveVideo();
                })
                .thenApply(video -> {
                    System.out.println("Inside video task 2: " + Thread.currentThread().getName());
                    return video + "&optimized=true";
                })
                .exceptionally(e->{
                    System.out.println("Vide task failed: " + e.getMessage());
                    return "Video task failed";
                });

        // COMBINE imageFuture AND videoFuture
        // Notify instructor once both are ready
        CompletableFuture<Void> notifyFuture = imageFuture
                .thenCombine(videoFuture, (imageUrl, videoPath) -> {
                    System.out.println("Inside notify task 1: " + Thread.currentThread().getName());
                    return "Image URL : " + imageUrl + "\nVideo path: " + videoPath;
                })
                .thenAccept((result) -> fileService.notifyInstructor(result));

        // TRANSCRIPT FUTURE PIPELINE
        // Upload transcript and handle any exception gracefully
        CompletableFuture<String> transcriptFuture = CompletableFuture
                .supplyAsync(() -> fileService.uploadTranscript())
                .exceptionally(e -> "Transcript failed, but continuing..") // Handles specific exceptions
                .handle((result, ex) -> {
                    // Final handling: check if exception occurred and return appropriate response
                    if (ex != null) {
                        return "Handled transcript error!";
                    } else {
                        return result;
                    }
                });

        // Wait for all futures to complete
        CompletableFuture<Void> all = CompletableFuture.allOf(imageFuture, videoFuture, notifyFuture, transcriptFuture);

        all.join(); // Block until all tasks complete
        long end = System.currentTimeMillis(); // ending time
        // Print final results
        try {
            System.out.println("Processed Image: " + imageFuture.get());
            System.out.println("Video Saved Path: " + videoFuture.get());
            System.out.println("Transcript Result: " + transcriptFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Total time: " + (end - start) + "ms");
    }
}
