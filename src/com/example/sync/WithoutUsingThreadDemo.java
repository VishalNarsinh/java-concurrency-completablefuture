package com.example.sync;

import com.example.apis.FileService;

public class WithoutUsingThreadDemo {
    public static void main(String[] args) {
        FileService fileService = new FileService();
//        start time of execution
        long start = System.currentTimeMillis();

        // Executing sequentially
        String imageUrl = fileService.uploadImage();
        String videoPath = fileService.saveVideo();

//        end time
        long end = System.currentTimeMillis();

        System.out.println("With traditional synchronized way");
        System.out.println("Image URL: " + imageUrl);
        System.out.println("Video Path: " + videoPath);
        System.out.println("Total time taken: " + (end - start) + " ms");
    }
}
