package com.example.apis;

/**
 * Service class that simulates file operations like uploading images/videos,
 * processing images, notifying instructors, etc.
 */
public class FileService {

    /**
     * Simulates uploading an image to a cloud storage provider.
     * Delays for 3 seconds to mimic real-world latency.
     *
     * @return URL of the uploaded image
     */
    public String uploadImage() {
        try {
            System.out.println("Uploading image to cloud...");
            Thread.sleep(3000); // Simulate 3-second network delay
            System.out.println("Image uploaded to cloud successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "https://cdn.cloud/images/temp.jpg";
    }

    /**
     * Simulates saving a video file to a local server.
     * Delays for 1 second to mimic file write time.
     *
     * @return Local path of the saved video
     */
    public String saveVideo() {
        try {
            System.out.println("Saving video to local server...");
            Thread.sleep(1000); // Simulate 1-second delay
            System.out.println("Video saved to local server successfully.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "/videos/lesson123.mp4";
    }

    /**
     * Simulates additional processing on an uploaded image.
     *
     * @param url URL of the image to process
     * @return Modified URL indicating it's processed
     */
    public String processImage(String url) {
        System.out.println("Processing image: " + url);
        return url + "?processed=true";
    }

    /**
     * Simulates sending a notification message to the instructor.
     *
     * @param message Notification message
     */
    public void notifyInstructor(String message) {
        System.out.println("Notifying instructor: " + message);
    }

    /**
     * Simulates uploading a transcript file.
     * Throws a runtime exception to simulate a failure case.
     *
     * @return Success message if no error
     */
    public String uploadTranscript() {
        try {
            System.out.println("Uploading transcript...");
            Thread.sleep(1000); // Simulate delay
        } catch (Exception e) {
            System.out.println("Transcript upload failed!");
        }
        return "Transcript uploaded successfully!";
    }
}
