package com.example.kursfx;


import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

import java.io.IOException;

public class MainApplication extends Application {
    private Scene scene;
    private double xOffset;
    private double yOffset;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                xOffset = event.getSceneX();
//                yOffset = event.getSceneY();
//            }
//        });
//        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                stage.setX(event.getScreenX() - xOffset);
//                stage.setY(event.getScreenY() - yOffset);
//            }
//        });

        Scene scene = new Scene(root);
        stage.setTitle("Менеджер білетів");
        this.scene = scene;

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        ResizeHelper.addResizeListener(stage);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch();
    }
}