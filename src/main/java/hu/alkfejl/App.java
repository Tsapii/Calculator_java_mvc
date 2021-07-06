package hu.alkfejl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Calculator.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Számológép");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }

}