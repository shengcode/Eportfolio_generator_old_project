package ssm.view;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static ssm.StartupConstants.CSS_CLASS_LANG_DIALOG_PANE;
import static ssm.StartupConstants.CSS_CLASS_LANG_OK_BUTTON;
import static ssm.StartupConstants.CSS_CLASS_LANG_PROMPT;
import static ssm.StartupConstants.CSS_CLASS_LANG_TEXT_FIELD;
import static ssm.StartupConstants.STYLE_SHEET_DIALOGBOX_UI;

/**
 *
 * @author shengchun
 */
public class AddListDialogBox extends Stage {

    VBox messagePane;
    Scene messageScene;
    Button yesButton;
    Button noButton;
    Button cancelButton;
    Label videoNameLabel;
    ArrayList<TextField> listTextFieldArrayList;
    ObservableList<String> listContentsArrayList;
    public static final String YES = "Yes";
    public static final String CANCEL = "Cancel";
    Label fontSizeLabel;
    ComboBox fontSizeCombobox;
    Label fontFamilyLabel;
    ComboBox fontFamilyCombobox;
    Double textAreaHeight = 0.0;
    Double textAreaWidth = 0.0;
    String fontFamilyText;
    String fontSizeText;
    Label titleOfList;
    TextField titleText = new TextField();
    String listTitle = "";
    VBox addListPane;
    ScrollPane addListScrollPane;
    Label heightLabel;
    TextField heightTextArea = new TextField();
    boolean updated = false;

    public boolean isUpdated() {
        return updated;
    }
    Label widthLabel;
    TextField widthTextArea = new TextField();

    public AddListDialogBox(Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        titleOfList = new Label("enter the title of the list");
        //titleText = new TextField();
        listTextFieldArrayList = new ArrayList<>();
        listContentsArrayList = FXCollections.observableArrayList();
        ObservableList<String> fontSize = FXCollections.observableArrayList("8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72");
        fontSizeLabel = new Label("Font Size:");
        fontSizeLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontSizeCombobox = new ComboBox(fontSize);
        ObservableList<String> fontFamily = FXCollections.observableArrayList("Gafata", "Inconsolata", "Share Tech Mono", "Nova Mono", "VT323", "Roboto Mono", "Droid Sans Mono");
        fontFamilyLabel = new Label("Font Family:");
        fontFamilyCombobox = new ComboBox(fontFamily);
        heightLabel = new Label("Height of listArea: ");
        //  heightTextArea = new TextField();
        widthLabel = new Label("Width of listArea: ");
        //  widthTextArea = new TextField();
        GridPane paragraphStyle = new GridPane();
        paragraphStyle.setPadding(new Insets(10, 10, 10, 10));
        paragraphStyle.setVgap(8);
        paragraphStyle.setHgap(10);
        GridPane.setConstraints(fontSizeLabel, 0, 0);
        GridPane.setConstraints(fontSizeCombobox, 1, 0);
        GridPane.setConstraints(fontFamilyLabel, 0, 1);
        GridPane.setConstraints(fontFamilyCombobox, 1, 1);
        GridPane.setConstraints(heightLabel, 0, 2);
        GridPane.setConstraints(heightTextArea, 1, 2);
        GridPane.setConstraints(widthLabel, 0, 3);
        GridPane.setConstraints(widthTextArea, 1, 3);
        GridPane.setConstraints(titleOfList, 0, 4);
        GridPane.setConstraints(titleText, 1, 4);
        paragraphStyle.getChildren().addAll(fontSizeLabel, fontSizeCombobox, fontFamilyLabel, fontFamilyCombobox, heightLabel, heightTextArea, widthLabel, widthTextArea, titleOfList, titleText);
        paragraphStyle.setAlignment(Pos.CENTER);
        paragraphStyle.getStylesheets().add("gridpane");
        fontSizeCombobox.setOnAction(e -> {
            if (fontSizeCombobox.getValue() != null) {
                String styleString = "-fx-font-family: " + fontFamilyCombobox.getValue() + "; " + "-fx-font-size: " + fontSizeCombobox.getValue();
                for (int i = 0; i < listTextFieldArrayList.size(); i++) {
                    listTextFieldArrayList.get(i).setStyle(styleString);
                }

            }
        });
        fontFamilyCombobox.setOnAction(e -> {
            if (fontSizeCombobox.getValue() != null) {
                String styleString = "-fx-font-family: " + fontFamilyCombobox.getValue() + "; " + "-fx-font-size: " + fontSizeCombobox.getValue();
                for (int i = 0; i < listTextFieldArrayList.size(); i++) {
                    listTextFieldArrayList.get(i).setStyle(styleString);
                }
            }

        });

        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        yesButton.setOnAction(e -> {
            listContentsArrayList.clear();

            for (int i = 0; i < listTextFieldArrayList.size(); i++) {
                listContentsArrayList.add(listTextFieldArrayList.get(i).getText());
            }
            textAreaHeight = Double.parseDouble(heightTextArea.getText());
            textAreaWidth = Double.parseDouble(widthTextArea.getText());
            fontFamilyText = fontFamilyCombobox.getValue().toString();
            fontSizeText = fontSizeCombobox.getValue().toString();
            listTitle = titleText.getText();

            AddListDialogBox.this.hide();
            updated = true;
        });
        cancelButton = new Button(CANCEL);
        cancelButton.setOnAction(e -> {
            AddListDialogBox.this.hide();
            updated = false;
        });

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(cancelButton);
        Button addListButton = new Button("add list");
        addListButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        addListPane = new VBox();
        addListScrollPane = new ScrollPane(addListPane);
        addListScrollPane.setPrefWidth(200);
        addListScrollPane.setPrefHeight(300);

        addListButton.setOnAction(l -> {
            Label listLabel = new Label("input the list");
            TextField listTextField = new TextField("enter the list");
            listTextField.setPrefSize(100, 20);
            listLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
            listTextField.getStyleClass().add(CSS_CLASS_LANG_TEXT_FIELD);

            listTextFieldArrayList.add(listTextField);
            Button removeButton = new Button("remove list");
            removeButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
            addListPane.getChildren().add(listLabel);
            addListPane.getChildren().add(listTextField);
            addListPane.getChildren().add(removeButton);

            removeButton.setOnAction(r -> {
                listTextFieldArrayList.remove(listTextField);
                addListPane.getChildren().remove(listTextField);
                addListPane.getChildren().remove(listLabel);
                addListPane.getChildren().remove(removeButton);

            });
        });

        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        addListPane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(addListButton);
        messagePane.getChildren().add(addListScrollPane);
        messagePane.getChildren().add(paragraphStyle);
        messagePane.getChildren().add(buttonBox);
        // CSS CLASSES
        yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        fontSizeLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontFamilyLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontSizeCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontFamilyCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        titleOfList.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        titleText.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        heightLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        heightTextArea.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        widthLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        widthTextArea.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        buttonBox.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);
        buttonBox.setSpacing(50);
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        messageScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto+Mono");
        messageScene.getStylesheets().add("http://fonts.googleapis.com/css?family=Gafata");
        messageScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Inconsolata");
        messageScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Share+Tech+Mono");
        messageScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Nova+Mono");
        messageScene.getStylesheets().add("https://fonts.googleapis.com/css?family=VT323");
        messageScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Droid+Sans+Mono");
        messageScene.getStylesheets().add(STYLE_SHEET_DIALOGBOX_UI);
        this.setScene(messageScene);

    }

    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which button the user
     * selected when this dialog was presented.
     */
    public ObservableList<String> getSelection() {
        return listContentsArrayList;
    }

    public double getTextAreaHeight() {
        return textAreaHeight;
    }

    public double getTextAreaWidth() {
        return textAreaWidth;
    }

    public String getFontFamily() {
        return fontFamilyText;
    }

    public String getFontSize() {
        return fontSizeText;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setTextContent(ObservableList MyListContent) {
        addListPane.getChildren().clear();
          listTextFieldArrayList.clear();
        for (int i = 0; i < MyListContent.size(); i++) {

            Label listLabel = new Label("input the list");
            TextField listTextField = new TextField(MyListContent.get(i).toString());
            listTextField.setPrefSize(100, 20);
            listLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
            listTextField.getStyleClass().add(CSS_CLASS_LANG_TEXT_FIELD);

            listTextFieldArrayList.add(listTextField);
            Button removeButton = new Button("remove list");
            removeButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
            addListPane.getChildren().add(listLabel);
            addListPane.getChildren().add(listTextField);
            addListPane.getChildren().add(removeButton);

            removeButton.setOnAction(r -> {
                listTextFieldArrayList.remove(listTextField);
                addListPane.getChildren().remove(listTextField);
                addListPane.getChildren().remove(listLabel);
                addListPane.getChildren().remove(removeButton);

            });

        }

    }

    public void setTextAreaHeight(String textHeight) {
        heightTextArea.setText(textHeight);
    }

    public void setTextAreaWidth(String textWidth) {
        widthTextArea.setText(textWidth);
    }

    public void setFontFamily(String font) {
        fontSizeCombobox.setValue(font);
    }

    public void setFontSize(String fontSize) {
        fontFamilyCombobox.setValue(fontSize);
    }

    public void setListTitle(String listTitle) {
        titleText.setText(listTitle);
    }

    /**
     * This method loads a custom message into the label and then pops open the
     * dialog.
     *
     * @param message Message to appear inside the dialog.
     */
    public void show(String message) {
        // messageLabel.setText(message);
        this.showAndWait();
    }

    /*
     *      HBox addListHbox = new HBox();
            
     Button addListButton = new Button("add list");

     Button addListSubmitButton = new Button("submit");
     Button addListCancelButton = new Button("cancel");
     HBox buttonBox = new HBox();
     buttonBox.getChildren().add(addListSubmitButton);
     buttonBox.getChildren().add(addListCancelButton);
     buttonBox.setSpacing(20);
     addListSubmitButton.setOnAction(l -> {
     addListStage.hide();
     });
     addListCancelButton.setOnAction(lc -> {
     addListStage.hide();
     });
     VBox addListPane = new VBox();        
     ScrollPane addListScrollPane = new ScrollPane(addListPane);
            
     addListHbox.getChildren().add(addListScrollPane);
     addListHbox.getChildren().add(addListButton);
     addListHbox.getChildren().add(buttonBox);
     addListButton.setOnAction(l -> {
     Label listLabel = new Label("input the list");
     TextField listTextField = new TextField();
     Button removeButton = new Button("remove");
     addListPane.getChildren().add(listLabel);
     addListPane.getChildren().add(listTextField);
     addListPane.getChildren().add(removeButton);
     removeButton.setOnAction(r -> {
     addListPane.getChildren().remove(listTextField);
     addListPane.getChildren().remove(removeButton);
     addListPane.getChildren().remove(listLabel);
     });
     });
     addListHbox.setSpacing(20);
     Scene addListScene = new Scene(addListHbox);
     //addListScene.getStylesheets().add(STYLE_SHEET_UI);
     addListStage.setScene(addListScene);
     */
}
