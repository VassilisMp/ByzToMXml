package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {

        String fxmlFile = "/FXML/UI.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        Controller controller = loader.getController();
        controller.setMyStage(myStage);

        myStage.setTitle("JavaFX Text Editor");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.show();
    }
}
