package music;

public class NotEnoughCreditsException extends Exception {
    public NotEnoughCreditsException() {
        super("nie wystarczająca ilość kredytów do kupna piosenki.");
    }
}
