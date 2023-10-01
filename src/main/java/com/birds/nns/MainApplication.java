package com.birds.nns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("learning birds");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("com/birds/nns/bird.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}