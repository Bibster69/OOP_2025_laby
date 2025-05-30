import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();

        String inputImagePath = "resources/tajger.jpg";
        String outputImagePath = "resources/tajger_dwa.jpg";
        String outputImagePathBrighter = "resources/tajger_brajt.jpg";
        String outputImagePathDarker = "resources/tajger_dark.jpg";

        try {
            processor.loadImage(inputImagePath);
            processor.saveImage(outputImagePath);
            processor.adjustBrightness(70);
            processor.saveImage(outputImagePathBrighter);
            processor.adjustBrightness(-70);
            processor.saveImage(outputImagePathDarker);

        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Processing error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        }


        long startTime;
        long endTime;
        long singleThreadTime = 0;
        long multiThreadTime = 0;

        try {
            System.out.println("\n--- Running Single-threaded Brightness Adjustment (Raster) ---");
            processor.loadImage(inputImagePath); 

            startTime = System.currentTimeMillis(); 
            processor.adjustBrightness(70); 
            endTime = System.currentTimeMillis();   

            singleThreadTime = endTime - startTime;
            System.out.println("Single-threaded brightness adjustment finished in " + singleThreadTime + " ms.");

            processor.saveImage("img_single_thread.jpg"); 

        } catch (IOException e) {
            System.err.println("I/O error during single-threaded process: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error in single-threaded process: " + e.getMessage());
        }

        
        try {
            System.out.println("\n--- Running Multi-threaded Brightness Adjustment (Raster) ---");
           
            processor.loadImage(inputImagePath);

            startTime = System.currentTimeMillis(); 
           
            int numCores = Runtime.getRuntime().availableProcessors();
            int numThreads = numCores == 0 ? 1 : numCores;
            System.out.println("Using " + numThreads + " threads.");

            processor.adjustBrightnessMultiThreaded(70); 
            endTime = System.currentTimeMillis();    

            multiThreadTime = endTime - startTime;   
            System.out.println("Multi-threaded brightness adjustment finished in " + multiThreadTime + " ms.");

            processor.saveImage("img_multi_thread.jpg"); 

        } catch (IOException e) {
            System.err.println("I/O error during multi-threaded process: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error in multi-threaded process: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Multi-threaded process interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); 
        }
    }
}
