package Byzantine;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.collections4.list.SetUniqueList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// B043 έμεινα εκεί στην εισαγωγή
public class CharsInput extends Application {

    private VBox nodeList = new VBox(8);
    private static final String codePattern = "([BFILPX])((0[4-9][0-9])|(0[3-9][3-9])|(1[0-9][0-9])|(2[0-4][0-9])|(250))";
    private List<UnicodeChar> charList;
    private Stage primaryStage;
    private VBox parent;
    private TextField charCodeText;
    private SetUniqueList<UnicodeChar> uniqueCharList;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

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

        uniqueCharList = SetUniqueList.setUniqueList(charList);

        this.primaryStage.setTitle("JavaFX Welcome");

        HBox buttonsBox = new HBox(8);
        buttonsBox.setPadding(new Insets(10));

        parent = new VBox(8);
        //parent.setFillWidth(false);
        parent.setPadding(new Insets(10));
        parent.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");
        buttonsBox.getChildren().add(saveButton);

        Button addButton = new Button("Add");
        buttonsBox.getChildren().add(addButton);

        Button getButton = new Button("Get");
        buttonsBox.getChildren().add(getButton);

        Button addQuantity = new Button("Add Quantity");
        buttonsBox.getChildren().add(addQuantity);
        addQuantity.setOnAction(e -> {
            nodeList.getChildren().add(MovesVBox());
            this.primaryStage.sizeToScene();
        });

        Button addTime = new Button("Add Time");
        buttonsBox.getChildren().add(addTime);
        addTime.setOnAction(e -> {
            nodeList.getChildren().add(timesVBox());
            this.primaryStage.sizeToScene();
        });

        Button clearButton = new Button("Clear");
        buttonsBox.getChildren().add(clearButton);
        clearButton.setOnAction(e -> deletePrevious());

        charCodeText = new TextField();
        charCodeText.setPromptText("charCode");
        charCodeText.setPrefWidth(60);
        charCodeText.setMaxWidth(60);
        buttonsBox.getChildren().add(charCodeText);

        parent.getChildren().add(buttonsBox);
        parent.getChildren().add(nodeList);

        getButton.setOnAction(this::getButtonHandle);
        addButton.setOnAction(this::addButtonHandle);
        saveButton.setOnAction(this::saveHandle);

        // Show window
        Scene scene = new Scene(parent);
        this.primaryStage.setScene(scene);
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("ByzChars");
        this.primaryStage.show();
    }

    @Nullable
    private TimeChar getTimeChar(int codePoint, ByzClass byzClass, @NotNull VBox vBox) {
        HBox timesHBox = (HBox) vBox.getChildren().get(1);

        CheckBox argoCheckBox = (CheckBox) timesHBox.getChildren().get(0);
        TextField divisionsText = (TextField) timesHBox.getChildren().get(1);
        TextField dotPlaceText = (TextField) timesHBox.getChildren().get(2);

        boolean argo = argoCheckBox.selectedProperty().get();

        int divisions;
        try {
            divisions = Integer.valueOf(divisionsText.getText());
        } catch (NumberFormatException ne) {
            showAlertMessage("Division must be a number");
            return null;
        }

        int dotPlace;
        try {
            dotPlace = Integer.valueOf(dotPlaceText.getText());
        } catch (NumberFormatException ne) {
            showAlertMessage("dotPlace must be a number");
            return null;
        }
        return new TimeChar(codePoint, "", byzClass, dotPlace, divisions, argo);
    }

    @Nullable
    private QuantityChar getQuantityChar(int codePoint, ByzClass byzClass, @NotNull VBox vBox) {
        VBox movesBox = (VBox) vBox.getChildren().get(2);
        int size = movesBox.getChildren().size();
        List<Move> moves = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            HBox hBox = (HBox) movesBox.getChildren().get(i);

            TextField MoveText = (TextField) hBox.getChildren().get(0);
            CheckBox lyricCheckBox = (CheckBox) hBox.getChildren().get(1);
            CheckBox timeCheckBox = (CheckBox) hBox.getChildren().get(2);

            int move;
            try {
                move = Integer.valueOf(MoveText.getText());
            } catch (NumberFormatException ne) {
                showAlertMessage("Error in move: " + (i+1) + "\nMove must be number!");
                return null;
            }
            boolean lyric = lyricCheckBox.selectedProperty().get();
            boolean time = timeCheckBox.selectedProperty().get();
            Move mov = new Move(move, lyric, time);
            //System.out.println(mov);
            moves.add(mov);
        }
        //System.out.println(new QuantityChar(codePoint, "", byzClass, moves));
        return new QuantityChar(codePoint, "", byzClass, moves);
    }

    private void deletePrevious() {
        // delete nodes used in previous choice
        nodeList.getChildren().clear();
        primaryStage.sizeToScene();
    }

    private VBox timesVBox() {
        VBox timesVBox = new VBox(8);
        timesVBox.setPadding(new Insets(10));
        // HBox containing moves number selection ChoiceBox
        HBox timesHBox = new HBox(8);
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

        timesHBox.getChildren().addAll(
                argoCheckBox
                , divisionsText
                , dotPlaceText
        );

        timesVBox.getChildren().addAll(
                new Label("Time")
                ,timesHBox
        );

        //parent.getChildren().add(timesVBox);
        //primaryStage.sizeToScene();
        return timesVBox;
    }

    private VBox MovesVBox() {
        VBox movesVBox = new VBox(8);
        movesVBox.setPadding(new Insets(10));

        // HBox containing moves number selection from ChoiceBox
        HBox movesHBox = new HBox(8);
        movesHBox.setPadding(new Insets(10));

        ChoiceBox<Integer> movesNum = new ChoiceBox<>();
        movesNum.setItems(FXCollections.observableArrayList(
                1, 2, 3)
        );
        movesNum.getSelectionModel().selectFirst();

        movesHBox.getChildren().addAll(new Label("moves"), movesNum);

        VBox movesBox = new VBox(8);
        movesBox.setPadding(new Insets(10));

        CheckBox lyricCheckBox = new CheckBox("lyric");
        lyricCheckBox.setSelected(true);

        CheckBox timeCheckBox = new CheckBox("time");
        timeCheckBox.setSelected(true);

        TextField MoveText = new TextField();
        MoveText.setPrefWidth(40);
        MoveText.setPromptText("value");

        movesBox.getChildren().add(
                new HBox(MoveText, lyricCheckBox, timeCheckBox)
        );

        movesVBox.getChildren().addAll(
                new Label("Quantity")
                , movesHBox
                , movesBox
        );
        //parent.getChildren().addAll(movesVBox);
        //primaryStage.sizeToScene();

        movesNum.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int dif = newValue - oldValue;
            if (dif > 0) {
                for (int i = 0; i < dif; i++) {
                    CheckBox lyricCheckBoxl = new CheckBox("lyric");
                    lyricCheckBoxl.setSelected(true);
                    CheckBox timeCheckBoxl = new CheckBox("time");
                    timeCheckBoxl.setSelected(true);
                    TextField textFieldl = new TextField();
                    textFieldl.setPrefWidth(40);
                    movesBox.getChildren().add(new HBox(textFieldl, lyricCheckBoxl, timeCheckBoxl));
                }
                primaryStage.sizeToScene();
            } else {
                ObservableList<Node> children = movesBox.getChildren();
                children.remove(children.size() - 1 + dif, children.size() - 1);
                primaryStage.sizeToScene();
            }
        });

        return movesVBox;
    }

    // Show a General Information Alert
    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void saveHandle(ActionEvent e) {
        try {
            FileOutputStream fileOut = new FileOutputStream("lis.obj");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(charList);
            showAlertMessage("Successfuly Saved(Serialized) list");
        } catch (IOException ex) {
            showAlertMessage("Couldn't Save(Serialize) list");
            ex.printStackTrace();
        }
    }

    private void getButtonHandle(ActionEvent e) {
        String charCode = charCodeText.getText();

        UnicodeChar unicodeChar = uniqueCharList.stream().
                filter(Char -> Objects.equals(((ByzChar) Char).getCodePointClass(), charCode)).
                findAny().
                orElseGet(() -> {
                    showAlertMessage("there in no such Character in the list");
                    return null;
                });
        if (unicodeChar != null) {
            switch (unicodeChar.getClass().getSimpleName()) {
                case "QuantityChar": {
                    deletePrevious();
                    caseQuantity((QuantityChar) unicodeChar);
                    this.primaryStage.sizeToScene();
                } break;
                case "TimeChar": {
                    deletePrevious();
                    caseTime((TimeChar) unicodeChar);
                    this.primaryStage.sizeToScene();
                } break;
                case "MixedChar": {
                    MixedChar mixedChar = (MixedChar) unicodeChar;
                    ByzChar[] byzChars = mixedChar.getChars();
                    deletePrevious();
                    for (ByzChar byzChar : byzChars) {
                        if (byzChar.getClass().getSimpleName().equals("QuantityChar")) {
                            caseQuantity((QuantityChar) byzChar);
                        } else if (byzChar.getClass().getSimpleName().equals("TimeChar")) {
                            caseTime((TimeChar) byzChar);
                        }
                    }
                    this.primaryStage.sizeToScene();
                } break;
            }
        }
    }

    private void caseTime(@NotNull TimeChar unicodeChar) {
        VBox timesVBox = timesVBox();
        HBox timesHBox = (HBox) timesVBox.getChildren().get(1);
        // Set argoCheckBox
        ((CheckBox) timesHBox.getChildren().get(0)).setSelected(unicodeChar.getArgo());
        // Set divisionsText
        ((TextField) timesHBox.getChildren().get(1)).setText(unicodeChar.getDivisions() + "");
        // Set dotPlaceText
        ((TextField) timesHBox.getChildren().get(2)).setText(unicodeChar.getDotPlace() + "");
        nodeList.getChildren().add(timesVBox);
    }

    private void caseQuantity(@NotNull QuantityChar unicodeChar) {
        Move[] moves = unicodeChar.getMoves();
        VBox movesVBox = MovesVBox();
        ChoiceBox<Integer> movesNum = (ChoiceBox<Integer>) ((HBox) movesVBox.getChildren().get(1)).getChildren().get(1);
        VBox movesBox = (VBox) movesVBox.getChildren().get(2);
        nodeList.getChildren().add(movesVBox);
        movesNum.getSelectionModel().select(moves.length - 1);
        movesBox.getChildren().clear();
        for (Move move : moves) {
            CheckBox lyricCheckBoxl = new CheckBox("lyric");
            lyricCheckBoxl.setSelected(move.getLyric());
            CheckBox timeCheckBoxl = new CheckBox("time");
            timeCheckBoxl.setSelected(move.getTime());
            TextField textFieldl = new TextField();
            textFieldl.setPrefWidth(40);
            textFieldl.setText(move.getMove() + "");
            movesBox.getChildren().add(new HBox(textFieldl, lyricCheckBoxl, timeCheckBoxl));
        }
    }

    private void addButtonHandle(ActionEvent e) {
        String charCode = charCodeText.getText();

        // Check if charCode input is right
        Matcher codeMatcher = Pattern.compile(codePattern).matcher(charCode);
        if (!codeMatcher.matches()) {
            showAlertMessage("Error in charCode input");
            return;
        }

        int codePoint = Integer.parseInt(charCode.substring(1));
        ByzClass byzClass = ByzClass.valueOf(charCode.charAt(0) + "");

        AtomicBoolean flag = new AtomicBoolean(false);
        // delete Character if already exists, to add the new one
        uniqueCharList.stream()
                .filter(Char -> Objects.equals(((ByzChar) Char).getCodePointClass(), charCode))
                .findAny()
                .ifPresent(Char -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Replacement");
                    alert.setContentText("Already exists, proceed replacement?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent()) {
                        if (result.get() == ButtonType.OK) {
                            if (uniqueCharList.remove(Char))
                                System.out.println("removed");
                        }
                        else flag.set(true);
                    }
                });
        if (flag.get()) return;

        int nodeListSize = nodeList.getChildren().size();
        if (nodeListSize == 1) {
            VBox vBox = ((VBox) nodeList.getChildren().get(0));
            Label Label = (javafx.scene.control.Label) vBox.getChildren().get(0);
            if (Label.getText().equals("Quantity")) {
                QuantityChar quantityChar = getQuantityChar(codePoint, byzClass, vBox);
                if (quantityChar == null) return;
                //System.out.println(uniqueCharList.get(uniqueCharList.size()-1).equals(quantityChar));
                if (uniqueCharList.add(quantityChar)) {
                    showAlertMessage("Successfully added: " + QuantityChar.class.toString());
                    System.out.println(charList);
                } else
                    showAlertMessage("Already exists in the List");
                //System.out.println(uniqueCharList.add(new QuantityChar(codePoint, "", byzClass, moves)))
            } else if (Label.getText().equals("Time")) {
                TimeChar timeChar = getTimeChar(codePoint, byzClass, vBox);
                if (timeChar == null) return;
                if (uniqueCharList.add(timeChar)) {
                    showAlertMessage("Successfully added: " + TimeChar.class.toString());
                    System.out.println(charList);
                } else
                    showAlertMessage("Already exists in the List");
            }
        } else if (nodeListSize > 1) { // MixedChar case
            List<ByzChar> byzChars = new ArrayList<>(nodeListSize);
            for (Node node : nodeList.getChildren()) {
                VBox vBox = (VBox) node;
                Label Label = (javafx.scene.control.Label) vBox.getChildren().get(0);
                if (Label.getText().equals("Quantity")) {
                    QuantityChar quantityChar = getQuantityChar(codePoint, byzClass, vBox);
                    if (quantityChar == null) return;
                    byzChars.add(quantityChar);
                } else if (Label.getText().equals("Time")) {
                    TimeChar timeChar = getTimeChar(codePoint, byzClass, vBox);
                    if (timeChar == null) return;
                    byzChars.add(timeChar);
                }
            }
            MixedChar mixedChar = new MixedChar(codePoint, "", byzClass, byzChars);
            if (uniqueCharList.add(mixedChar)) {
                showAlertMessage("Successfully added: " + MixedChar.class.toString());
                System.out.println(charList);
            } else
                showAlertMessage("Already exists in the List");
        }
    }
}