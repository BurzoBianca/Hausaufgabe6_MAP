package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        URL fxmlLocation = GUI.class.getResource("LogInWindow.fxml");

        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        Parent root = loader.load();
        primaryStage.setTitle("Login As");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }
}