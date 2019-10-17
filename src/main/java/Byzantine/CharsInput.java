package Byzantine;

import com.google.common.base.Charsets;
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
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// B043 έμεινα εκεί στην εισαγωγή
// TODO have to check chars with codePoint > F000 in the ttx files, because they are not the same with 33 - 255 range in matching
public class CharsInput extends Application {

    private VBox nodeList = new VBox(8);
    private static final String codePattern = "([BFILPX])((0[4-9][0-9])|(0[3-9][3-9])|(1[0-9][0-9])|(2[0-4][0-9])|(250))";
    private List<ByzChar> charList;
    private Stage primaryStage;
    private VBox parent;
    private TextField charCodeText;
    private SetUniqueList<ByzChar> uniqueCharList;

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        charList = Engine.getCharList();
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

        Button addFthora = new Button("Add Fthora");
        buttonsBox.getChildren().add(addFthora);
        addFthora.setOnAction(e -> {
            nodeList.getChildren().add(FthoraVBox());
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
        this.primaryStage.setResizable(true);
        this.primaryStage.setTitle("ByzChars");
        this.primaryStage.sizeToScene();
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
            divisions = Integer.parseInt(divisionsText.getText());
        } catch (NumberFormatException ne) {
            showAlertMessage("Division must be a number");
            return null;
        }

        int dotPlace;
        try {
            dotPlace = Integer.parseInt(dotPlaceText.getText());
        } catch (NumberFormatException ne) {
            showAlertMessage("dotPlace must be a number");
            return null;
        }
        return new TimeChar(codePoint, byzClass, dotPlace, divisions, argo);
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
                move = Integer.parseInt(MoveText.getText());
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
        //System.out.println(new QuantityChar(codePoint, byzClass, moves));
        return new QuantityChar(codePoint, byzClass, moves);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private FthoraChar getFthoraChar(int codePoint, ByzClass byzClass, @NotNull VBox vBox) {
        HBox FthoraHBox = (HBox) vBox.getChildren().get(1);

        ChoiceBox<Type> typeBox = (ChoiceBox<Type>) FthoraHBox.getChildren().get(1);
        ChoiceBox<ByzStep> stepBox = (ChoiceBox<ByzStep>) FthoraHBox.getChildren().get(3);
        TextField commasText = (TextField) FthoraHBox.getChildren().get(5);

        Type type = typeBox.getSelectionModel().getSelectedItem();
        ByzStep step = stepBox.getSelectionModel().getSelectedItem();
        int commas;

        try {
            commas = Integer.parseInt(commasText.getText());
        } catch (NumberFormatException ne) {
            showAlertMessage("Error in commas\ncommas must be number!");
            return null;
        }

        return new FthoraChar(codePoint, byzClass, type, step, commas);
    }

    private void deletePrevious() {
        // delete nodes used in previous choice
        nodeList.getChildren().clear();
        primaryStage.sizeToScene();
    }

    private VBox FthoraVBox() {
        VBox FthoraVBox = new VBox(8);
        FthoraVBox.setPadding(new Insets(10));
        // HBox containing moves number selection ChoiceBox
        HBox FthoraHBox = new HBox(8);
        //movesHBox.setFillHeight(false);
        FthoraHBox.setPadding(new Insets(10));

        ChoiceBox<Type> type = new ChoiceBox<>();
        type.setItems(FXCollections.observableArrayList(Type.values()));
        type.getSelectionModel().selectFirst();

        ChoiceBox<ByzStep> step = new ChoiceBox<>();
        step.setItems(FXCollections.observableArrayList(ByzStep.values()));
        step.getSelectionModel().selectFirst();

        TextField commasText = new TextField();
        commasText.setText("0");
        commasText.setPrefWidth(80);
        commasText.setMaxWidth(80);

        FthoraHBox.getChildren().addAll(
                new Label("Type"),
                type,
                new Label("Step"),
                step,
                new Label("commas"),
                commasText
        );

        FthoraVBox.getChildren().addAll(
                new Label("Fthora")
                ,FthoraHBox
        );

        //parent.getChildren().add(timesVBox);
        //primaryStage.sizeToScene();
        return FthoraVBox;
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
        String json = new ByzCharDeSerialize().toJson(charList);
        try {
            FileUtils.writeStringToFile(new File(Engine.JSON_CHARS_FILE), json, Charsets.UTF_8);
            showAlertMessage("Successfuly Saved(Serialized) list");
        } catch (IOException ex) {
            showAlertMessage("Couldn't Save(Serialize) list");
            ex.printStackTrace();
        }
    }

    private void getButtonHandle(ActionEvent e) {
        String charCode = charCodeText.getText();

        ByzChar byzChar1 = uniqueCharList.stream().
                filter(Char -> Objects.equals(Char.getCodePointClass(), charCode)).
                findAny().
                orElseGet(() -> {
                    showAlertMessage("there in no such Character in the list");
                    return null;
                });
        if (byzChar1 != null) {
            switch (byzChar1.getClass().getSimpleName()) {
                case "QuantityChar": {
                    deletePrevious();
                    caseQuantity((QuantityChar) byzChar1);
                    this.primaryStage.sizeToScene();
                } break;
                case "TimeChar": {
                    deletePrevious();
                    caseTime((TimeChar) byzChar1);
                    this.primaryStage.sizeToScene();
                } break;
                case "FthoraChar": {
                    deletePrevious();
                    caseFthora((FthoraChar) byzChar1);
                    this.primaryStage.sizeToScene();
                } break;
                case "MixedChar": {
                    MixedChar mixedChar = (MixedChar) byzChar1;
                    deletePrevious();
                    mixedChar.forEach(byzChar -> {
                        if (byzChar.getClass().getSimpleName().equals("QuantityChar")) {
                            caseQuantity((QuantityChar) byzChar);
                        } else if (byzChar.getClass().getSimpleName().equals("TimeChar")) {
                            caseTime((TimeChar) byzChar);
                        }
                    });
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

    @SuppressWarnings("unchecked")
    private void caseFthora(@NotNull FthoraChar unicodeChar) {
        VBox FthoraVBox = FthoraVBox();
        HBox FthoraHBox = (HBox) FthoraVBox.getChildren().get(1);

        ChoiceBox<Type> typeBox = (ChoiceBox<Type>) FthoraHBox.getChildren().get(1);
        ChoiceBox<ByzStep> stepBox = (ChoiceBox<ByzStep>) FthoraHBox.getChildren().get(3);
        TextField commasText = (TextField) FthoraHBox.getChildren().get(5);

        typeBox.getSelectionModel().select(unicodeChar.type);
        stepBox.getSelectionModel().select(unicodeChar.step);
        commasText.setText(String.valueOf(unicodeChar.commas));
        nodeList.getChildren().add(FthoraVBox);
    }

    @SuppressWarnings("unchecked")
    private void caseQuantity(@NotNull QuantityChar quantityChar) {
        VBox movesVBox = MovesVBox();
        ChoiceBox<Integer> movesNum = (ChoiceBox<Integer>) ((HBox) movesVBox.getChildren().get(1)).getChildren().get(1);
        VBox movesBox = (VBox) movesVBox.getChildren().get(2);
        nodeList.getChildren().add(movesVBox);
        movesNum.getSelectionModel().select(quantityChar.getMovesLength() - 1);
        movesBox.getChildren().clear();
        quantityChar.forEach(move -> {
            CheckBox lyricCheckBoxl = new CheckBox("lyric");
            lyricCheckBoxl.setSelected(move.getLyric());
            CheckBox timeCheckBoxl = new CheckBox("time");
            timeCheckBoxl.setSelected(move.getTime());
            TextField textFieldl = new TextField();
            textFieldl.setPrefWidth(40);
            textFieldl.setText(move.getMove() + "");
            movesBox.getChildren().add(new HBox(textFieldl, lyricCheckBoxl, timeCheckBoxl));
        });
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
                .filter(Char -> Objects.equals(Char.getCodePointClass(), charCode))
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
            switch (Label.getText()) {
                case "Quantity":
                    QuantityChar quantityChar = getQuantityChar(codePoint, byzClass, vBox);
                    if (quantityChar == null) return;
                    //System.out.println(uniqueCharList.get(uniqueCharList.size()-1).equals(quantityChar));
                    if (uniqueCharList.add(quantityChar)) {
                        showAlertMessage("Successfully added: " + QuantityChar.class.toString());
                        System.out.println(charList);
                    } else
                        showAlertMessage("Already exists in the List");
                    //System.out.println(uniqueCharList.add(new QuantityChar(codePoint, byzClass, moves)))
                    break;
                case "Time":
                    TimeChar timeChar = getTimeChar(codePoint, byzClass, vBox);
                    if (timeChar == null) return;
                    if (uniqueCharList.add(timeChar)) {
                        showAlertMessage("Successfully added: " + TimeChar.class.toString());
                        System.out.println(charList);
                    } else
                        showAlertMessage("Already exists in the List");
                    break;
                case "Fthora":
                    FthoraChar fthoraChar = getFthoraChar(codePoint, byzClass, vBox);
                    if (fthoraChar == null) return;
                    if (uniqueCharList.add(fthoraChar)) {
                        showAlertMessage("Successfully added: " + FthoraChar.class.toString());
                        System.out.println(charList);
                    } else
                        showAlertMessage("Already exists in the List");
                    break;
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
            MixedChar mixedChar = new MixedChar(codePoint, byzClass, byzChars);
            if (uniqueCharList.add(mixedChar)) {
                showAlertMessage("Successfully added: " + MixedChar.class.toString());
                System.out.println(charList);
            } else
                showAlertMessage("Already exists in the List");
        }
    }
}