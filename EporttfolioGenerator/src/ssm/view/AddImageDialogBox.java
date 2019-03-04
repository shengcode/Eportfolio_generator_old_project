package ssm.view;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import static ssm.StartupConstants.CSS_CLASS_LANG_DIALOG_PANE;
import static ssm.StartupConstants.CSS_CLASS_LANG_OK_BUTTON;
import static ssm.StartupConstants.CSS_CLASS_LANG_PROMPT;
import static ssm.StartupConstants.PATH_EPORTFOLIO_IMAGES;
import static ssm.StartupConstants.STYLE_SHEET_DIALOGBOX_UI;

/**
 *
 * @author shengchun
 */
public class AddImageDialogBox extends Stage {

    VBox messagePane;
    Scene messageScene;
    Button yesButton;
    Button noButton;
    Button cancelButton;
    String selection;

    Label imageAlignmentLabel;
    ComboBox imageAlignmentCombobox;
    Button imageFileChooser;
    String imagePath = "";
    String imageAlignmentD = "";
    String imageNameD = "";
    double imageHeightD = 0.0;
    double imageWidthD = 0.0;
    String imageCaptionD = "";
    File file;
    String path;
    String fileName;
    Label WidthLabel;
    Label imageNameLabel = new Label("Default");
    TextField imageWidth = new TextField();
    Label HeightLabel;
    TextField imageHeight = new TextField();
    Label captionLabel;
    TextField captionTextField = new TextField();
    boolean updated = false;

    public boolean isUpdated() {
        return updated;
    }

    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String CANCEL = "Cancel";

    public AddImageDialogBox(Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        ObservableList<String> imageAlignment = FXCollections.observableArrayList("left", "center", "right");
        imageAlignmentLabel = new Label("what's the allignment of the image");
        imageAlignmentCombobox = new ComboBox(imageAlignment);
        imageFileChooser = new Button("click to choose image file");
        // imageNameLabel=new Label("Default");
        imageFileChooser.setOnAction(a -> {
            FileChooser imageFile = new FileChooser();
            // SET THE STARTING DIRECTORY
            imageFile.setInitialDirectory(new File(PATH_EPORTFOLIO_IMAGES));
            // LET'S ONLY SEE IMAGE FILES
            FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
            imageFile.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
            // LET'S OPEN THE FILE CHOOSER       
            file = imageFile.showOpenDialog(null);

            if (file != null) {
                path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
                fileName = file.getName();
                imageNameLabel.setText(fileName);
            }
        });

        GridPane imageGridPane = new GridPane();
        imageGridPane.setPadding(new Insets(10, 10, 10, 10));
        imageGridPane.setVgap(8);
        imageGridPane.setHgap(10);
        WidthLabel = new Label("Image Width");
        // imageWidth = new TextField();
        HeightLabel = new Label("Image Height");
        // imageHeight = new TextField();                          
        captionLabel = new Label("Caption of the image");
           // captionTextField = new TextField(); 

        GridPane.setConstraints(imageFileChooser, 0, 0);
        GridPane.setConstraints(imageNameLabel, 1, 0);

        GridPane.setConstraints(imageAlignmentLabel, 0, 1);
        GridPane.setConstraints(imageAlignmentCombobox, 1, 1);
        GridPane.setConstraints(WidthLabel, 0, 2);
        GridPane.setConstraints(imageWidth, 1, 2);
        GridPane.setConstraints(HeightLabel, 0, 3);
        GridPane.setConstraints(imageHeight, 1, 3);
        GridPane.setConstraints(captionLabel, 0, 4);
        GridPane.setConstraints(captionTextField, 1, 4);

        imageGridPane.getChildren().addAll(imageFileChooser, imageNameLabel, imageAlignmentLabel, imageAlignmentCombobox, WidthLabel, imageWidth, HeightLabel, imageHeight, captionLabel, captionTextField);
        imageGridPane.setAlignment(Pos.CENTER);
        imageGridPane.getStylesheets().add("gridpane");

        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        noButton = new Button(NO);
        cancelButton = new Button(CANCEL);

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        yesButton.setOnAction(e -> {
            imageAlignmentD = imageAlignmentCombobox.getSelectionModel().getSelectedItem().toString();
            imagePath = path;
            imageNameD = fileName;
            imageHeightD = Double.parseDouble(imageHeight.getText());
            imageWidthD = Double.parseDouble(imageWidth.getText());
            imageCaptionD = captionTextField.getText();
            AddImageDialogBox.this.hide();
            updated = true;
        });
        //buttonBox.getChildren().add(noButton);
        buttonBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(e -> {
            AddImageDialogBox.this.hide();
            updated = false;

        });
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(imageGridPane);
        messagePane.getChildren().add(buttonBox);
        // CSS CLASSES
        yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        noButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        imageFileChooser.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        imageAlignmentLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        imageNameLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        WidthLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        HeightLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        imageWidth.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        imageHeight.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        captionLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        captionTextField.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        imageAlignmentCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT);

        buttonBox.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);

        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);
        buttonBox.setSpacing(50);
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        messageScene.getStylesheets().add(STYLE_SHEET_DIALOGBOX_UI);
        this.setScene(messageScene);
    }

    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which button the user
     * selected when this dialog was presented.
     */

    public String getImagePath() {
        return imagePath;
    }

    public String getImageFileName() {
        return imageNameD;
    }

    public String getImageAlignment() {
        return imageAlignmentD;
    }

    public Double getImageWidth() {
        return imageWidthD;
    }

    public Double getImageHeight() {
        return imageHeightD;
    }

    public String getImageCaption() {
        return imageCaptionD;
    }

    public void setImageName(String imageName) {
        imageNameLabel.setText(imageName);;
    }

    public void setImageAlignment(String alignment) {
        imageAlignmentCombobox.setValue(alignment);
    }

    public void setImageWidth(String imageWidthS) {
        imageWidth.setText(imageWidthS);
    }

    public void setImageHeight(String imageHeightS) {
        imageHeight.setText(imageHeightS);
    }

    public void setImageCaption(String imageCaptionS) {
        captionTextField.setText(imageCaptionS);

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
}
