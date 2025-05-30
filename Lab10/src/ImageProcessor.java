import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

public class ImageProcessor {

    private BufferedImage image; // Field to store the image


    public void loadImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        if (!file.exists()) {
            throw new IOException("Brak pliku w : " + imagePath);
        }
        this.image = ImageIO.read(file);
        if (this.image == null) {
            throw new IOException("Nie można odczytać pluku z: " + imagePath);
        }
        System.out.println("Załadowano obraz z: " + imagePath);
    }

    public void saveImage(String outputPath) throws IOException {
        if (this.image == null) {
            throw new IllegalStateException("Brak obrazu");
        }


        String format = getFileExtension(outputPath);
        if (format == null || format.isEmpty()) {
            throw new IllegalArgumentException("Plik obrazu misi mieć rozszerzenie typu .jpg");
        }

        File outputfile = new File(outputPath);
        ImageIO.write(this.image, format, outputfile);
        System.out.println("Zapisano obraz w: " + outputPath);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return null;
    }

    public BufferedImage getImage() {
        return image;
    }

    // Zad.2

    public void adjustBrightness(int brightnessIncrease) {
        if (this.image == null) {
            throw new IllegalStateException("Brak Obrazu");
        }

        int width = this.image.getWidth();
        int height = this.image.getHeight();

        int[] pixelComponents = new int[this.image.getRaster().getNumBands()];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.image.getRaster().getPixel(x, y, pixelComponents);
                for (int i = 0; i < pixelComponents.length; i++) {
                    pixelComponents[i] = Math.max(0, Math.min(255, pixelComponents[i] + brightnessIncrease));
                }
                this.image.getRaster().setPixel(x, y, pixelComponents);
            }
        }
        System.out.println("Brightness adjusted by: " + brightnessIncrease);
    }

    public void adjustBrightnessMultiThreaded(int brightnessIncrease) throws InterruptedException {
        if (this.image == null) {
            throw new IllegalStateException("Brak Obrazu"); // Checks if an image is loaded
        }

        // 1. Sprawdzamy ile mamy rdzeni
        int numCores = Runtime.getRuntime().availableProcessors();
        int width = this.image.getWidth();
        int height = this.image.getHeight();

        int numThreads = numCores;
        if (numThreads == 0) numThreads = 1; // Program wymaga przynajmniej jednego watku

        // 2. Zarządanie watkami
        Thread[] threads = new Thread[numThreads]; // Tablica przechowuje reference to 'workerów' wykonujących zadania na rdzeniach
        int rowsPerThread = height / numThreads;    // Ile rzędów będze przetwarzał jeden wątek
        int remainingRows = height % numThreads;    // Reszta rzędów dodawan do ostatniego watku

        // 3. Dzielenie zadań i tworzenie wątków
        for (int i = 0; i < numThreads; i++) {
            int startY = i * rowsPerThread; // obliczamy poczatkowy nr. rzędu dla watku
            int endY = startY + rowsPerThread - 1; // końcowy

            if (i == numThreads - 1) {
                // ostatni dostaje reszte
                endY += remainingRows;
            }
            endY = Math.min(endY, height - 1); // endY nie może być większy niż wysokośc obrazu

            // tworzymy nowy wątek i przekazujemy mu workera który wykonuje zadanie
            threads[i] = new Thread(new BrightnessWorker(this.image, brightnessIncrease, startY, endY, width));

            // Włączamy wątek
            threads[i].start();
        }

        // 4. Czekamy aż wszystkie watki wykonają swoje zadania
        for (int i = 0; i < numThreads; i++) {
            // thread join każe obecnemu wątkowi, czyli main czekać na zakończenie wykonania wątku threads[i]
            threads[i].join();
        }
    }

    public void adjustBrightnessThreadPool(int brightnessIncrease) throws InterruptedException {
        if (this.image == null) {
            throw new IllegalStateException("Brak Obrazu");
        }

        int width = this.image.getWidth();
        int height = this.image.getHeight();
        int numCores = Runtime.getRuntime().availableProcessors();
        // Sugerowany rozmiar puli: liczba rdzeni, lub 2x liczba rdzeni dla zadań z potencjalnymi blokadami I/O.
        // Dla CPU-bound zadań zwykle numCores jest dobre.
        int poolSize = numCores;
        if (poolSize == 0) poolSize = 1; // Upewnij się, że pula ma co najmniej 1 wątek

        // Tworzymy pulę wątków o stałym rozmiarze
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        System.out.println("Starting thread pool brightness adjustment with " + poolSize + " threads in pool.");


        // Dla każdego wiersza tworzymy zadanie i wysyłamy je do puli
        for (int y = 0; y < height; y++) {
            // Nowy RowBrightnessWorker, który zajmuje się tylko jednym wierszem
            executor.submit(new RowBrightnessWorker(this.image, brightnessIncrease, y, width));
        }

        // Zakończ działanie puli:
        // 1. Zakończ przyjmowanie nowych zadań.
        executor.shutdown();
        // 2. Czekaj na zakończenie wszystkich zadań w puli z timeoutem.
        // To jest kluczowe, aby mieć pewność, że wszystkie operacje na obrazie się zakończyły.
        // Dajemy sensowny timeout (np. 1 godzinę), aby program nie wieszał się w nieskończoność.
        if (!executor.awaitTermination(1, TimeUnit.HOURS)) { // Max wait 1 hour
            System.err.println("Thread pool did not terminate in time!");
            // Możesz dodać executor.shutdownNow(); aby wymusić zakończenie, jeśli timeout się skończył
        }
    }

    // Worker, to on będzie rozjaśniał kawałek obrazu, na pojedynczym wątku
    private static class BrightnessWorker implements Runnable {
        private final BufferedImage image;
        private final int brightnessIncrease;
        private final int startY;
        private final int endY;
        private final int width;
        private final int[] pixelComponents;

        public BrightnessWorker(BufferedImage image, int brightnessIncrease, int startY, int endY, int width) {
            this.image = image;
            this.brightnessIncrease = brightnessIncrease;
            this.startY = startY;
            this.endY = endY;
            this.width = width;
            this.pixelComponents = new int[image.getRaster().getNumBands()];
        }

        @Override
        public void run() {
            for (int y = this.startY; y <= this.endY; y++) {
                for (int x = 0; x < this.width; x++) {
                    this.image.getRaster().getPixel(x, y, this.pixelComponents);
                    for (int i = 0; i < this.pixelComponents.length; i++) {
                        this.pixelComponents[i] = Math.max(0, Math.min(255, this.pixelComponents[i] + this.brightnessIncrease));
                    }
                    this.image.getRaster().setPixel(x, y, this.pixelComponents);
                }
            }
        }
    }

    //Zad. 3

    private static class RowBrightnessWorker implements Runnable {
        private final BufferedImage image;
        private final int brightnessIncrease;
        private final int rowY; // Only one row to process
        private final int width;
        private final int[] pixelComponents;

        public RowBrightnessWorker(BufferedImage image, int brightnessIncrease, int rowY, int width) {
            this.image = image;
            this.brightnessIncrease = brightnessIncrease;
            this.rowY = rowY;
            this.width = width;
            this.pixelComponents = new int[image.getRaster().getNumBands()];
        }

        @Override
        public void run() {
            // Process only the assigned row (rowY)
            for (int x = 0; x < this.width; x++) {
                this.image.getRaster().getPixel(x, this.rowY, this.pixelComponents);
                for (int i = 0; i < this.pixelComponents.length; i++) {
                    this.pixelComponents[i] = Math.max(0, Math.min(255, this.pixelComponents[i] + this.brightnessIncrease));
                }
                this.image.getRaster().setPixel(x, this.rowY, this.pixelComponents);
            }
        }
    }



}