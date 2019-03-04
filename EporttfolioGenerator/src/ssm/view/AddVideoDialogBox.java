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
public class AddVideoDialogBox extends Stage {

    VBox messagePane;
    Scene messageScene;
    Button yesButton;
    Button noButton;
    Button cancelButton;
    String selection;
    String videoPath = "";
    String videoNameD = "";
    String videoAlignmentD = "";
    double videoWidthD = 0.0;
    double videoHeightD = 0.0;
    String videoCaptionD = "";
    String path;
    String fileName;

    Label videoNameLabel=new Label("default");
    ComboBox videoAlignmentCombobox;
    Label WidthLabel;
    TextField videoWidth = new TextField();
    Label HeightLabel;
    TextField videoHeight = new TextField();
    Label captionLabel;
    TextField captionTextField = new TextField();
    
    boolean updated = false;

    public boolean isUpdated() {
        return updated;
    }
    
    

    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String CANCEL = "Cancel";

    public AddVideoDialogBox(Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        ObservableList<String> videoAlignment = FXCollections.observableArrayList("left", "center", "right");
        Label videoAlignmentLabel = new Label("what's the allignment of the video");
        videoAlignmentCombobox = new ComboBox(videoAlignment);
        Button videoFileChooser = new Button("click to choose video file");
        //videoNameLabel = new Label("Default");
        videoFileChooser.setOnAction(a -> {
            FileChooser videoFile = new FileChooser();
            // SET THE STARTING DIRECTORY
            videoFile.setInitialDirectory(new File(PATH_EPORTFOLIO_IMAGES));
            // LET'S ONLY SEE IMAGE FILES
            FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");
            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("AVi files (*.avi)", "*.avi");

            videoFile.getExtensionFilters().addAll(jpgFilter, pngFilter);
            // LET'S OPEN THE FILE CHOOSER       
            File file = videoFile.showOpenDialog(null);
         
            if (file != null) {
                path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
                fileName = file.getName();
                videoNameLabel.setText(fileName);
            }
        });

        GridPane videoGridPane = new GridPane();
        videoGridPane.setPadding(new Insets(10, 10, 10, 10));
        videoGridPane.setVgap(8);
        videoGridPane.setHgap(10);
        WidthLabel = new Label("Video Width");
        //videoWidth = new TextField();
        HeightLabel = new Label("Video Height");
        // videoHeight = new TextField();                          
        captionLabel = new Label("Caption of the video");
      //  TextField captionTextField = new TextField();
        GridPane.setConstraints(videoFileChooser, 0, 0);
        GridPane.setConstraints(videoNameLabel, 1, 0);

        GridPane.setConstraints(videoAlignmentLabel, 0, 1);
        GridPane.setConstraints(videoAlignmentCombobox, 1, 1);
        GridPane.setConstraints(WidthLabel, 0, 2);
        GridPane.setConstraints(videoWidth, 1, 2);
        GridPane.setConstraints(HeightLabel, 0, 3);
        GridPane.setConstraints(videoHeight, 1, 3);
        GridPane.setConstraints(captionLabel, 0, 4);
        GridPane.setConstraints(captionTextField, 1, 4);

        videoGridPane.getChildren().addAll(videoFileChooser, videoNameLabel, videoAlignmentLabel, videoAlignmentCombobox, WidthLabel, videoWidth, HeightLabel, videoHeight, captionLabel, captionTextField);
        videoGridPane.setAlignment(Pos.CENTER);
        videoGridPane.getStylesheets().add("gridpane");

        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        //  noButton = new Button(NO);
        cancelButton = new Button(CANCEL);

        yesButton.setOnAction(e -> {
            videoAlignmentD = videoAlignmentCombobox.getSelectionModel().getSelectedItem().toString();
            videoPath = path;
            videoNameD = fileName;
            videoHeightD = Double.parseDouble(videoHeight.getText());
            videoWidthD = Double.parseDouble(videoWidth.getText());
            videoCaptionD = captionTextField.getText();
            AddVideoDialogBox.this.hide();
            updated = true;
        });
        
        cancelButton.setOnAction(e -> {             
            AddVideoDialogBox.this.hide();
            updated = false;

        });
        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(cancelButton);
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(videoGridPane);
        messagePane.getChildren().add(buttonBox);
        // CSS CLASSES
        yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
//	noButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        videoFileChooser.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        videoAlignmentLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        videoNameLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        WidthLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        HeightLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        videoWidth.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        videoHeight.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        captionLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        captionTextField.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        videoAlignmentCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT);

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
    public String getVideoPath() {
        return videoPath;
    }

    public String getVideoFileName() {
        return videoNameD;
    }

    public String getVideoAlignment() {
        return videoAlignmentD;
    }

    public double getVideoWidth() {
        return videoWidthD;
    }

    public double getVideoHeight() {
        return videoHeightD;
    }

    public String getVideoCaption() {
        return videoCaptionD;
    }

    
    
    public void setVideoName(String videoNameS) {
        videoNameLabel.setText(videoNameS);;
    }

    public void setVideoAlignment(String alignmentS) {
        videoAlignmentCombobox.setValue(alignmentS);
    }

    public void setVideoWidth(String videoWidthS) {
        videoWidth.setText(videoWidthS);
    }

    public void setVideoHeight(String videoHeightS) {
        videoHeight.setText(videoHeightS);
    }

    public void setVideoCaption(String videoCaptionS) {
        captionTextField.setText(videoCaptionS);
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
