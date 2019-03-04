package ssm.view;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import static ssm.StartupConstants.CSS_CLASS_LANG_DIALOG_PANE;
import static ssm.StartupConstants.CSS_CLASS_LANG_OK_BUTTON;
import static ssm.StartupConstants.CSS_CLASS_LANG_PROMPT;
import static ssm.StartupConstants.STYLE_SHEET_DIALOGBOX_UI;
/**
 *
 * @author shengchun
 */
public class JsonFileTitleDialogBox extends Stage{  
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button yesButton;
    Button cancelButton;
    TextField headerTextField;
    String selection="";    
    public static final String YES = "Yes";
    public static final String CANCEL = "Cancel";    
    public  JsonFileTitleDialogBox (Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        messageLabel = new Label("please enter the Json file name:");       
        headerTextField = new TextField();       
         // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
         cancelButton = new Button(CANCEL);
        yesButton.setOnAction(e->{
          selection=headerTextField.getText();
          this.hide();        
        });       
        cancelButton.setOnAction(e->{
            this.hide();        
        });
        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(cancelButton);
	       
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(headerTextField);
        messagePane.getChildren().add(buttonBox);

	// CSS CLASSES
	yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
	cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
	messageLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
	messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
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

    public String getJsonFileName() {
        return selection;
    }
     public void show(String message) {
        messageLabel.setText(message);
        this.showAndWait();
    }
}

