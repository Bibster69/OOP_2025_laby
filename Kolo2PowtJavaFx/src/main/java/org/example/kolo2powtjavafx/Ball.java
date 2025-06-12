package org.example.kolo2powtjavafx;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends GraphicsItem {

    private Point2D moveVector;
    private double velocity;

    public Ball() {
        this.width = 0.03;
        this.height = 0.03;

        // Początkowy kierunek: 45 stopni w prawo i w górę
        this.moveVector = new Point2D(1, -1).normalize();
        this.velocity = 0.005; // jednostki kanwy na klatkę
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Point2D point) {
        this.x = point.getX() - this.width / 2;
        this.y = point.getY() - this.height / 2;
    }

    public void updatePosition(double elapsedSeconds) {
        this.x += this.moveVector.getX() * this.velocity * elapsedSeconds;
        this.y += this.moveVector.getY() * this.velocity * elapsedSeconds;
    }

    // Zad. 7
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(
                x * canvasWidth,
                y * canvasHeight,
                width * canvasWidth,
                height * canvasHeight
        );
    }

    // Zad.5

    public void bounceHorizontally() {
        moveVector = new Point2D(-moveVector.getX(), moveVector.getY());
    }

    public void bounceVertically() {
        moveVector = new Point2D(moveVector.getX(), -moveVector.getY());
    }

    // Zad. 7

    public double getTop() {
        return y;
    }

    public double getBottom() {
        return y + height;
    }

    public double getLeft() {
        return x;
    }

    public double getRight() {
        return x + width;
    }

    // Zad.8

    public void bounceFromPaddle(double offset) {
        // Zakładamy, że offset jest z przedziału [-1, 1]
        // -1 = lewa krawędź paletki, 0 = środek, 1 = prawa krawędź

        // Kąt odbicia będzie się zmieniał między 45° w lewo (-45°) a 45° w prawo (45°)
        double maxAngle = Math.toRadians(60); // większy zakres = ostrzejsze kąty
        double angle = offset * maxAngle;

        // Nowy znormalizowany wektor ruchu
        double dx = Math.sin(angle);
        double dy = -Math.cos(angle); // ujemny, bo piłka odbija się w górę

        moveVector = new Point2D(dx, dy).normalize();
    }




}
