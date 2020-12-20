package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main extends Application {

    static final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static final String logoPath = String.format("%simages%sbyz_logo.png", File.separator, File.separator);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {

        String fxmlFile = String.format("%sFXML%sui.fxml", File.separator, File.separator);
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        myStage.setTitle("ByzToMXML");
        myStage.setScene(new Scene(root, 700, 500));
        addStageIcon(myStage, getClass());
        myStage.show();
    }

    static void addStageIcon(Stage stage, Class<?> clazz) {
        stage.getIcons().add(new Image(clazz.getResourceAsStream(logoPath)));
    }

    static Stage getStage(Alert alert) {
        return (Stage) alert.getDialogPane().getScene().getWindow();
    }
}
