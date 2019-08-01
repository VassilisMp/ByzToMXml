package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {

        //String fxmlFile = "/FXML/View.fxml";
        String fxmlFile = "/FXML/UI.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));


        Controller controller = loader.getController();

        myStage.setTitle("JavaFX Text Editor");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.show();
    }
}