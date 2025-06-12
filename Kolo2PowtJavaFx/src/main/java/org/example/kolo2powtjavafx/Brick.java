package org.example.kolo2powtjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GraphicsItem {
    private static int gridRows;
    private static int gridCols;

    private final Color color;

    // Zad. 6

    public enum CrushType {
        NoCrush,
        HorizontalCrush,
        VerticalCrush
    }

    public static void setGridSize(int rows, int cols) {
        gridRows = rows;
        gridCols = cols;
    }

    public Brick(int gridX, int gridY, Color color) {
        this.color = color;

        this.x = (double) gridX / gridCols;
        this.y = (double) gridY / gridRows;
        this.width = 1.0 / gridCols;
        this.height = 1.0 / gridRows;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double realX = x * canvasWidth;
        double realY = y * canvasHeight;
        double realW = width * canvasWidth;
        double realH = height * canvasHeight;

        // Główna część
        gc.setFill(color);
        gc.fillRect(realX, realY, realW, realH);

        // Cień dla "trójwymiarowości"
        gc.setStroke(Color.GRAY);
        gc.strokeLine(realX, realY, realX + realW, realY); // górna krawędź
        gc.strokeLine(realX, realY, realX, realY + realH); // lewa krawędź
    }

    // Zad. 6

    public CrushType checkCollision(double ballLeft, double ballRight, double ballTop, double ballBottom) {
        double brickLeft = this.x;
        double brickRight = this.x + this.width;
        double brickTop = this.y;
        double brickBottom = this.y + this.height;

        boolean intersects = ballRight > brickLeft && ballLeft < brickRight &&
                ballBottom > brickTop && ballTop < brickBottom;

        if (!intersects) {
            return CrushType.NoCrush;
        }

        double ballCenterX = (ballLeft + ballRight) / 2;
        double ballCenterY = (ballTop + ballBottom) / 2;

        double brickCenterX = (brickLeft + brickRight) / 2;
        double brickCenterY = (brickTop + brickBottom) / 2;

        double dx = Math.abs(ballCenterX - brickCenterX);
        double dy = Math.abs(ballCenterY - brickCenterY);

        if (dx > dy) {
            return CrushType.HorizontalCrush;
        } else {
            return CrushType.VerticalCrush;
        }
    }
}
