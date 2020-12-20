package gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.audiveris.proxymusic.ScorePartwise;
import org.audiveris.proxymusic.util.Marshalling;
import parser.Engine;

import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileOutputStream;

import static gui.Main.*;
import static javafx.application.Platform.runLater;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class FileController {

    private static final String REGEX_VALID_INTEGER = "([2-9]|[1-9][0-9])";

    String getFileName() {
        return removeExtension(file.getName());
    }

    private File file;
    private Parent root;
    private VBox filesVbox;
    private final FileChooser saveFileChooser = new FileChooser();

    @FXML
    private TextField fileNameText;

    @FXML
    private CheckBox timeBeatsCheck;

    @FXML
    private TextField timeBeatsText;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private ImageView finishImage;

    @FXML
    private Button converButton;

    @FXML
    private Button saveAsButton;

    public ScorePartwise getScorePartwise() {
        return scorePartwise;
    }

    private ScorePartwise scorePartwise = null;

    @FXML
    void closeOnAction() {
        filesVbox.getChildren().remove(root);
        MainController.removeFileController(this);
    }

    @FXML
    void convertOnAction() {
        executor.execute(() -> {
            try {
                runLater(() -> {
                    progress.setVisible(true);
                    converButton.setDisable(true);
                });
                Integer timebeats = null;
                if (timeBeatsText.isVisible()) timebeats = Integer.valueOf(timeBeatsText.getText());
                Engine engine = new Engine(file.getPath(), timebeats);
                long start = System.nanoTime();
                scorePartwise = engine.run();
                double elapsedTime = (System.nanoTime() - start)/1000000000.0;
                runLater(() -> {
                    progress.setVisible(false);
                    finishImage.setVisible(true);
                    saveAsButton.setDisable(false);
//                    showExecutionTimeAlert(elapsedTime);
                });

            } catch (Exception e) {
                runLater(() -> showExceptionAlert(e));
            }
        });
    }

    private void showExceptionAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(ExceptionUtils.getStackTrace(e));
        alert.setResizable(true);
        addStageIcon(getStage(alert), getClass());

        alert.showAndWait();
    }

    @FXML
    void saveAsOnAction() {
        saveFileChooser.setInitialFileName(getFileName() + ".xml");
        File file = saveFileChooser.showSaveDialog(filesVbox.getScene().getWindow());
        if (file == null) return;
        executor.execute(() -> {
            String filePath = file.getParentFile().getAbsolutePath() + File.separator + removeExtension(file.getName()) + ".xml";
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                Marshaller marshaller = Marshalling.getContext(ScorePartwise.class).createMarshaller();
                marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(scorePartwise, fos);
            } catch (Exception e) {
                runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    addStageIcon(getStage(alert), getClass());
                    alert.showAndWait();
                });
            }
        });
    }

    @FXML
    void initialize() {
        timeBeatsCheck.selectedProperty()
                .addListener((observable, oldValue, newValue) -> timeBeatsText.setVisible(newValue));
        timeBeatsText.setTextFormatter(new TextFormatter<>(this::filterTimebeatsText));
        saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("music xml", "*.xml"));
    }

    private TextFormatter.Change filterTimebeatsText(TextFormatter.Change change) {
        if (!change.getControlNewText().matches(REGEX_VALID_INTEGER))
            change.setText("");
        return change;
    }

    public void setAll(File file, Parent root, VBox filesVbox) {
        this.file = file;
        fileNameText.setText(file.getAbsolutePath());
        this.root = root;
        this.filesVbox = filesVbox;
        filesVbox.getChildren().add(root);
    }

    private static void showExecutionTimeAlert(double elapsedTime) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Execution Time");
        alert.setContentText("Elapsed time: " + elapsedTime);
        alert.setResizable(true);
        alert.showAndWait();
    }
}
