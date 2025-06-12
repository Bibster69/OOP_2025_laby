package org.example.kolo2powtjavafx;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.geometry.Point2D;

public class GameCanvas extends Canvas {

    private final GraphicsContext graphicsContext;
    // Zad. 2
    private final Paddle paddle;
    // Zad. 3
    private final Ball ball;
    private boolean gameStarted = false;
    private AnimationTimer timer;
    private long lastFrameTime = 0;

    // Zad.7
    private final ArrayList<Brick> bricks = new ArrayList<>();

    public GameCanvas(double width, double height) {
        super(width, height);
        this.graphicsContext = getGraphicsContext2D();
        GraphicsItem.setCanvasSize(width, height);

        this.paddle = new Paddle();
        this.ball = new Ball();
        this.ball.setVelocity(0.5);

        // Zad. 4
        setupAnimationLoop();


        // Zad.2
        //Reafujemy na rych myszki
        setOnMouseMoved(e -> {
            paddle.moveTo(e.getX());
        });

        // Zad.3
        // Zaczynamy grę gdy klikamy myszką
        setOnMouseClicked(e -> gameStarted = true);
        loadLevel();


        // Timer
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                if (gameStarted) {
//                    ball.updatePosition(0);
//                } else {
//                    // Piłeczka nad środkiem paletki
//                    Point2D paddleCenter = new Point2D(
//                            paddle.getX() + paddle.getWidth() / 2,
//                            paddle.getY() - ball.getHeight()
//                    );
//                    ball.setPosition(paddleCenter);
//                }
//                draw();
//            }
//        };
//        timer.start();
    }

    // Zad.2

    private void handleMouseMoved(javafx.scene.input.MouseEvent mouseEvent) {
        paddle.moveTo(mouseEvent.getX());
        draw();
    }

    public void draw() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, getWidth(), getHeight());

        // Zad.2
        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);

        // Zad.6
        for (Brick brick : bricks) {
            brick.draw(graphicsContext);
        }
    }

    //Zad.4

    private void setupAnimationLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastFrameTime == 0) {
                    lastFrameTime = now;
                    return;
                }
                double elapsedSeconds = (now - lastFrameTime) / 1_000_000_000.0;
                lastFrameTime = now;

                // Aktualizacja pozycji piłki
                if (gameStarted) {
                    ball.updatePosition(elapsedSeconds);

                    double canvasHeight = getHeight();
                    if (ball.getBottom() > canvasHeight) {
                        gameStarted = false;
                        System.out.println("Koniec gry!");
                    }

                } else {
                    Point2D paddleCenter = new Point2D(
                            paddle.getX() + paddle.getWidth() / 2,
                            paddle.getY() - ball.getHeight()
                    );
                    ball.setPosition(paddleCenter);
                }

                if (shouldBallBounceHorizontally()) {
                    ball.bounceHorizontally();
                }
                if (shouldBallBounceVertically()) {
                    ball.bounceVertically();
                }
                if (shouldBallBounceFromPaddle()) {
                    double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                    double ballCenter = ball.getLeft() + ball.getWidth() / 2;
                    double offset = (ballCenter - paddleCenter) / (paddle.getWidth() / 2); // wartość z przedziału -1 do 1

                    ball.bounceFromPaddle(offset); // odbicie od paddle = pionowe odbicie
                }

                // Zad. 7
                Iterator<Brick> iterator = bricks.iterator();
                while (iterator.hasNext()) {
                    Brick brick = iterator.next();
                    Brick.CrushType result = brick.checkCollision(
                            ball.getLeft(), ball.getRight(), ball.getTop(), ball.getBottom()
                    );

                    if (result != Brick.CrushType.NoCrush) {
                        iterator.remove(); // usuń cegłę

                        if (result == Brick.CrushType.HorizontalCrush) {
                            ball.bounceHorizontally();
                        } else {
                            ball.bounceVertically();
                        }

                        break; // tylko jedna kolizja na klatkę
                    }
                }

                // Rysowanie
                draw();
            }
        };
        timer.start();
    }

    // Zad. 5

    private boolean shouldBallBounceHorizontally() {
        return ball.getX() <= 0 || (ball.getX() + ball.getWidth()) >= 1;
    }

    private boolean shouldBallBounceVertically() {
        return ball.getY() <= 0;
    }

    private boolean shouldBallBounceFromPaddle() {
        return ball.getY() + ball.getHeight() >= paddle.getY() &&
                ball.getY() <= paddle.getY() + paddle.getHeight() &&
                ball.getX() + ball.getWidth() >= paddle.getX() &&
                ball.getX() <= paddle.getX() + paddle.getWidth();
    }

    // Zad.6
    private void loadLevel() {
        Brick.setGridSize(20, 10);
        bricks.clear();

        Color[] rowColors = {
                Color.RED, Color.ORANGE, Color.YELLOW,
                Color.GREEN, Color.BLUE, Color.VIOLET
        };

        for (int row = 2; row <= 7; row++) {
            Color color = rowColors[row - 2];
            for (int col = 0; col < 10; col++) {
                bricks.add(new Brick(col, row, color));
            }
        }
    }
}
