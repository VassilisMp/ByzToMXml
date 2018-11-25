package Byzantine;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.collections4.list.SetUniqueList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// L059 έμεινα εκεί στην εισαγωγή
public class CharsInput extends Application {

    private static HBox timesHBox;
    private static VBox movesVBox;
    private static ChoiceBox<Integer> movesNum;
    private static String codePattern = "([BFILPX])((0[4-9][0-9])|(0[3-9][3-9])|(1[0-9][0-9])|(2[0-4][0-9])|(250))";
    private List<UnicodeChar> charList;


    @Override
    public void start(Stage primaryStage) {


        try {
            FileInputStream fileIn = new FileInputStream("lis.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            charList = (List<UnicodeChar>) in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(charList);

        SetUniqueList<UnicodeChar> UniqueCharList = SetUniqueList.setUniqueList(charList);

        primaryStage.setTitle("JavaFX Welcome");


        VBox parent = new VBox(8);
        //parent.setFillWidth(false);
        parent.setPadding(new Insets(10));
        parent.setAlignment(Pos.CENTER);

        Button saveButton = new Button ("Save");
        parent.getChildren().add(saveButton);

        Button addButton = new Button("Add");
        parent.getChildren().add(addButton);

        TextField charCodeText = new TextField();
        charCodeText.setPromptText("charCode");
        charCodeText.setPrefWidth(60);
        charCodeText.setMaxWidth(60);
        parent.getChildren().add(charCodeText);

        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.setItems(FXCollections.observableArrayList(
                "Quantity", "Time", "Mixed")
        );
        parent.getChildren().add(cb);
        //cb.getSelectionModel().selectFirst();

        // Add Button Listener
        addButton.setOnAction(e -> {
            String charCode = charCodeText.getText();

            // Check if charCode input is right
            Matcher codeMatcher = Pattern.compile(codePattern).matcher(charCode);
            if(!codeMatcher.matches()) {
                showAlertCharCode();
                return;
            }

            int codePoint = Integer.parseInt(charCode.substring(1, charCode.length()));
            ByzClass byzClass = ByzClass.valueOf(charCode.charAt(0) + "");

            int selected = cb.getSelectionModel().getSelectedIndex();
            switch (selected) {
                case 0: {
                    QuantityChar quantityChar = getQuantityChar(codePoint, byzClass);
                    if (quantityChar == null) return;
                    //System.out.println(UniqueCharList.get(UniqueCharList.size()-1).equals(quantityChar));
                    if (UniqueCharList.add(quantityChar)) {
                        showAlertSuccess(QuantityChar.class.toString());
                        System.out.println(charList);
                    } else
                        showGeneralAlert("Already exists in the List");
                    //System.out.println(UniqueCharList.add(new QuantityChar(codePoint, "", byzClass, moves)));
                } break;
                case 1: {
                    TimeChar timeChar = getTimeChar(codePoint, byzClass);
                    if (timeChar == null) return;
                    if (UniqueCharList.add(timeChar)) {
                        showAlertSuccess(TimeChar.class.toString());
                        System.out.println(charList);
                    } else
                        showGeneralAlert("Already exists in the List");
                } break;
                case 2: {
                    QuantityChar quantityChar = getQuantityChar(codePoint, byzClass);
                    if (quantityChar == null) return;
                    TimeChar timeChar = getTimeChar(codePoint, byzClass);
                    if (timeChar == null) return;
                    MixedChar mixedChar = new MixedChar(codePoint, "", byzClass, quantityChar, timeChar);
                    if (UniqueCharList.add(mixedChar)) {
                        showAlertSuccess(MixedChar.class.toString());
                        System.out.println(charList);
                    } else
                        showGeneralAlert("Already exists in the List");
                }
            }
        });

        saveButton.setOnAction(e -> {
            try {
                FileOutputStream fileOut = new FileOutputStream("lis.obj");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(charList);
                showGeneralAlert("Successfuly Saved(Serialized) list");
            }catch (IOException ex) {
                showGeneralAlert("Couldn't Save(Serialize) list");
                ex.printStackTrace();
            }

            /*try {
                JAXBContext context = JAXBContext.newInstance(this.getClass());

                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                m.marshal(this, System.out);
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }*/
        });

        // Show window
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("ByzChars");
        primaryStage.show();

        // CharType choiceBox Listener
        cb.getSelectionModel()
                .selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Quantity": {
                    deletePrevious(parent);
                    timesHBox = null;
                    MovesHBox(primaryStage, parent);
                    System.out.println(oldValue + "  " + newValue);
                }
                break;
                case "Time":
                    deletePrevious(parent);
                    movesVBox = null;
                    timesHBox(primaryStage, parent);
                    //charList.add(TChar(sc, charCode));
                    break;
                case "Mixed":
                    deletePrevious(parent);
                    parent.getChildren().add(new Label("Quantity"));
                    MovesHBox(primaryStage, parent);
                    parent.getChildren().addAll(new Separator());
                    parent.getChildren().add(new Label("Time"));
                    timesHBox(primaryStage, parent);
                    //charList.add(MChar(sc, charCode));
                    break;
                default:
                    System.out.println("option out of options");
            }
        });
    }

    private TimeChar getTimeChar(int codePoint, ByzClass byzClass) {
        CheckBox argoCheckBox = (CheckBox) timesHBox.getChildren().get(0);
        TextField divisionsText = (TextField) timesHBox.getChildren().get(1);
        TextField dotPlaceText = (TextField) timesHBox.getChildren().get(2);

        boolean argo = argoCheckBox.selectedProperty().get();

        int divisions;
        try {
            divisions = Integer.valueOf(divisionsText.getText());
        } catch (NumberFormatException ne) {
            showGeneralAlert("Division must be a number");
            return null;
        }

        int dotPlace;
        try {
            dotPlace = Integer.valueOf(dotPlaceText.getText());
        } catch (NumberFormatException ne) {
            showGeneralAlert("dotPlace must be a number");
            return null;
        }
        TimeChar timeChar = new TimeChar(codePoint, "", byzClass, dotPlace, divisions, argo);
        return timeChar;
    }

    private QuantityChar getQuantityChar(int codePoint, ByzClass byzClass) {
        List<Move> moves = new ArrayList<>(movesNum.getValue());
        for (int i = 0; i < movesNum.getValue(); i++) {
            HBox hBox = (HBox) movesVBox.getChildren().get(i);

            TextField MoveText = (TextField) hBox.getChildren().get(0);
            CheckBox lyricCheckBox = (CheckBox) hBox.getChildren().get(1);
            CheckBox timeCheckBox = (CheckBox) hBox.getChildren().get(2);

            int move;
            try {
                move = Integer.valueOf(MoveText.getText());
            } catch (NumberFormatException ne) {
                showAlertMove(i+1);
                return null;
            }
            boolean lyric = lyricCheckBox.selectedProperty().get();
            boolean time = timeCheckBox.selectedProperty().get();
            Move mov = new Move(move, lyric, time);
            //System.out.println(mov);
            moves.add(mov);
        }
        //System.out.println(new QuantityChar(codePoint, "", byzClass, moves));
        QuantityChar quantityChar = new QuantityChar(codePoint, "", byzClass, moves);
        return quantityChar;
    }

    private void deletePrevious(VBox parent) {
        // delete nodes used in previous choice
        if (parent.getChildren().size() > 4)
            parent.getChildren().remove(4, parent.getChildren().size());
    }

    private void timesHBox(Stage primaryStage, VBox parent) {
        // HBox containing moves number selection ChoiceBox
        timesHBox = new HBox(8);
        //movesHBox.setFillHeight(false);
        timesHBox.setPadding(new Insets(10));

        CheckBox argoCheckBox = new CheckBox("Argo");
        argoCheckBox.setSelected(false);

        TextField divisionsText = new TextField();
        divisionsText.setPromptText("divisions");
        divisionsText.setPrefWidth(40);
        divisionsText.setMaxWidth(40);

        TextField dotPlaceText = new TextField();
        dotPlaceText.setPrefWidth(40);
        dotPlaceText.setMaxWidth(40);
        dotPlaceText.setPromptText("dotPlace");

        timesHBox.getChildren().addAll(argoCheckBox, divisionsText, dotPlaceText);
        parent.getChildren().add(timesHBox);
        primaryStage.sizeToScene();
    }

    private void MovesHBox(Stage primaryStage, VBox parent) {
        // HBox containing moves number selection from ChoiceBox
        HBox movesHBox = new HBox(8);
        movesHBox.setPadding(new Insets(10));

        movesNum = new ChoiceBox<>();
        movesNum.setItems(FXCollections.observableArrayList(
                1, 2, 3)
        );
        movesNum.getSelectionModel().selectFirst();

        movesHBox.getChildren().addAll(new Label("moves"), movesNum);
        parent.getChildren().add(movesHBox);
        //primaryStage.sizeToScene();

        movesVBox = new VBox(8);
        movesVBox.setPadding(new Insets(10));
        CheckBox lyricCheckBox = new CheckBox("lyric");
        lyricCheckBox.setSelected(true);
        CheckBox timeCheckBox = new CheckBox("time");
        timeCheckBox.setSelected(true);
        TextField MoveText = new TextField();
        MoveText.setPrefWidth(40);
        MoveText.setPromptText("value");
        movesVBox.getChildren().add(new HBox(MoveText, lyricCheckBox, timeCheckBox));
        parent.getChildren().add(movesVBox);
        primaryStage.sizeToScene();

        movesNum.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int dif = newValue - oldValue;
            if(dif > 0) {
                for (int i = 0; i < dif; i++) {
                    CheckBox lyricCheckBoxl = new CheckBox("lyric");
                    lyricCheckBoxl.setSelected(true);
                    CheckBox timeCheckBoxl = new CheckBox("time");
                    timeCheckBoxl.setSelected(true);
                    TextField textFieldl = new TextField();
                    textFieldl.setPrefWidth(40);
                    movesVBox.getChildren().add(new HBox(textFieldl, lyricCheckBoxl, timeCheckBoxl));
                }
                primaryStage.sizeToScene();
            } else {
                ObservableList<Node> children = movesVBox.getChildren();
                children.remove(children.size()-1+dif, children.size()-1);
                primaryStage.sizeToScene();
            }
        });
    }

    // Show a Information Alert for wrong input in move TextField
    private void showAlertMove(int moveNum) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Error in move: " + moveNum + "\nMove must be number!");

        alert.showAndWait();
    }

    // Show a Information Alert for wrong input in charCode TextField
    private void showAlertCharCode() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Error in charCode");

        alert.showAndWait();
    }

    // Show a Information Alert for wrong input in charCode TextField
    private void showAlertSuccess(String charClass) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Successfuly added: " + charClass);

        alert.showAndWait();
    }

    // Show a General Information Alert
    private void showGeneralAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
