package org.example.kolo2powtjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameCanvas gameCanvas = new GameCanvas(512, 512);
        gameCanvas.draw();

        StackPane root = new StackPane(gameCanvas);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Gra");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
