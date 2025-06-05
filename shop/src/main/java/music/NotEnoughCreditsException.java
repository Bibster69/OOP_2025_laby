package music;

public class NotEnoughCreditsException extends Exception {
    public NotEnoughCreditsException() {
        super("Nie wystarczająca ilość kredytów do kupna piosenki.");
    }
}
