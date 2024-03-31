package ac.umons.be.firstg.segmentintersection;

import ac.umons.be.firstg.segmentintersection.view.pages.MainPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class test extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new MainPage(stage));
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("Love Finder ver.1.22474487139...");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}