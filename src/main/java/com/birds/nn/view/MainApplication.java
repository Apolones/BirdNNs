package com.birds.nn.view;

import com.birds.nn.config.Config;
import com.birds.nn.config.ConfigLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Config config;
    @Override
    public void start(Stage stage) throws IOException {
        config = ConfigLoader.loadConfig("src/main/resources/com/birds/nn/config.json");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/birds/nn/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("learning birds");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("com/birds/nn/bird.png"));
        stage.show();
    }

    public static Config getConfig() {
        return config;
    }

    public static void main(String[] args) {
        launch(args);
    }
}