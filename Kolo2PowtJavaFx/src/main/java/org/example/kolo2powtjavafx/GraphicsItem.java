package org.example.kolo2powtjavafx;

import javafx.scene.canvas.GraphicsContext;

public abstract class GraphicsItem {

    // Wspólne wymiary kanwy (dla przeliczeń względnych)
    protected static double canvasWidth;
    protected static double canvasHeight;

    public static void setCanvasSize(double width, double height) {
        canvasWidth = width;
        canvasHeight = height;
    }

    // Pozycja i rozmiar w przestrzeni względnej (0.0 - 1.0)
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    // Gettery i settery
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // Abstrakcyjna metoda rysująca
    public abstract void draw(GraphicsContext gc);
}
