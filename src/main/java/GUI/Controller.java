package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import parser.Engine;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Stage myStage;
    private File selectedFile;
    private FileChooser fileChooser = new FileChooser();


    @FXML
    MenuItem MenuOpen;

    @FXML
    private Button startButton;

    @FXML
    void MenuOpenOnAction(ActionEvent event) {
        selectedFile = fileChooser.showOpenDialog(myStage);
    }

    @FXML
    void startButtonOnAction(ActionEvent event) {
        try {
            Engine engine = new Engine(selectedFile.getPath());
            engine.run();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText(ExceptionUtils.getStackTrace(e));

            alert.showAndWait();
        }
    }

    void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ByzScore Docx", "*.docx"));
    }
}
