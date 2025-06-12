package com.example.circleapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider radiusSlider;

    @FXML
    public void onMouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double radius = radiusSlider.getValue();
        Color fillColor = colorPicker.getValue();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(fillColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    // Puste metody, jeśli nie chcesz jeszcze implementować przycisków:
    @FXML
    public void onStartServerClicked() {
        // implementacja opcjonalna
    }

    @FXML
    public void onConnectClicked() {
        // implementacja opcjonalna
    }
}