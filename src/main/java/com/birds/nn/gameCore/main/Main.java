package com.birds.nn.gameCore.main;

import com.birds.nn.utils.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class Main extends Application {

    private static ApplicationContext context;

    @Autowired
    private Config config;

    public static void main(String[] args) {
        context = SpringApplication.run(Main.class, args);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        context.getAutowireCapableBeanFactory().autowireBean(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/birds/nn/main.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(config.game.gameTitle);
        stage.setResizable(true);
        stage.setMinHeight(config.game.windowConfig.minHeight);
        stage.setMinWidth(config.game.windowConfig.minWidth);
        stage.setScene(scene);
        stage.getIcons().add(new Image(config.resources.iconImage));
        stage.show();

        stage.setOnCloseRequest(event -> SpringApplication.exit(context));
    }
}
