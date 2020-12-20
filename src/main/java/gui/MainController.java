package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.util.Marshalling;

import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static gui.Main.executor;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

public class MainController implements Initializable {

    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private Stage myStage;
    private static final List<FileController> fileControllersOpened = new ArrayList<>();

    static void removeFileController(FileController fileController) {
        fileControllersOpened.remove(fileController);
    }

    @FXML
    private VBox filesVbox;

    @FXML
    void aboutOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Software for converting Byzantine music scores written with MK software(https://papline.gr/) to docx file format, " +
                        "to European music scores using a notation system similar to the Turkish one.");
        alert.setTitle("About");
        alert.setHeaderText("ByzToMXML");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setResizable(true);
        alert.showAndWait();
    }

    @FXML
    void addOnAction() throws IOException {
        File file = fileChooser.showOpenDialog(myStage);
        // check if null, because we don' t want to lose the last selection
        if (file != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/file.fxml"));
            Parent root = fxmlLoader.load();
            FileController fileController = fxmlLoader.getController();
            fileController.setAll(file, root, filesVbox);
            fileControllersOpened.add(fileController);
        }
    }

    @FXML
    void closeAllOnAction() {
        filesVbox.getChildren().clear();
        fileControllersOpened.clear();
    }

    @FXML
    void quitOnAction() {
        Platform.exit();
    }

    @FXML
    void saveAllOnAction() {
        if (fileControllersOpened.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No files to save");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setResizable(true);
            alert.show();
            return;
        }
        Stage newWindow = new Stage();
        newWindow.setScene(new Scene(new Pane(), 230, 100));
        File file = directoryChooser.showDialog(newWindow);
        if (file == null) return;
        fileControllersOpened.forEach(controller -> executor.execute(() -> {
            System.out.printf("%s%s.xml", file.getAbsolutePath(), controller.getFileName());
            try (FileOutputStream fos = new FileOutputStream(
                    String.format("%s%s%s.xml", file.getAbsolutePath(), File.separator, controller.getFileName())
            )) {
                Marshaller marshaller = Marshalling.getContext(ScorePartwise.class).createMarshaller();
                marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(controller.getScorePartwise(), fos);
            } catch (Exception e) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait());
            }
        }));
    }

    @FXML
    void initialize() {
        myStage = (Stage) filesVbox.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ByzScore Docx", "*.docx"));
    }
}
