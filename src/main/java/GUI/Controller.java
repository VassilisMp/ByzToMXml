package GUI;

import Byzantine.ByzantineException;
import Byzantine.NotSupportedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.audiveris.proxymusic.Step;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
            Byzantine.Engine engine = new Byzantine.Engine(selectedFile.getPath(), Step.A, 1).setTimeBeats(4);
            engine.run();
        } catch (JAXBException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText(ExceptionUtils.getStackTrace(e));

            alert.showAndWait();
        } catch (NullPointerException | ByzantineException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ByzScore Doc", "*.doc")
                ,new FileChooser.ExtensionFilter("ByzScore Docx", "*.docx")
        );
    }
}
