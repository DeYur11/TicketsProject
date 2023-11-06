package com.example.kursfx;


import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

import java.io.IOException;

public class MainApplication extends Application {
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Менеджер білетів");
        this.scene = scene;
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}