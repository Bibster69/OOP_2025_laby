package org.example.kolo2powtjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.kolo2powtjavafx.GraphicsItem;

public class Paddle extends GraphicsItem {

    private final Color color = Color.LIGHTBLUE;

    public Paddle() {
        // Domyślny rozmiar platformy (względny)
        this.width = 0.2;
        this.height = 0.03;
        this.x = 0.4;  // środek ekranu
        this.y = 1.0 - this.height - 0.02; // lekko nad dolną krawędzią
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(
                x * canvasWidth,
                y * canvasHeight,
                width * canvasWidth,
                height * canvasHeight
        );
    }

    public void moveTo(double mouseX) {
        // Ustaw środek paletki na pozycji myszy (współrzędna względna)
        double relativeX = mouseX / canvasWidth;
        this.x = relativeX - this.width / 2;

        // Zapobiegaj wyjechaniu poza ekran
        if (x < 0) x = 0;
        if (x + width > 1.0) x = 1.0 - width;
    }
}
