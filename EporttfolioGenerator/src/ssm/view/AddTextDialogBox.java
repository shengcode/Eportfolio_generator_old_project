
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
public class AddTextDialogBox extends Stage{  
    VBox messagePane;
    Scene messageScene;    
    Button yesButton;
    Button cancelButton;
    String selection="";    
   ComboBox addTextCombobox;  
    public static final String YES = "Yes";   
    public static final String CANCEL = "Cancel";
    
    public AddTextDialogBox (Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        ObservableList<String> textChoice = FXCollections.observableArrayList("header", "paragraph", "list");
        addTextCombobox = new ComboBox(textChoice);        
       // addTextCombobox.set
        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        cancelButton = new Button(CANCEL);       
        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        yesButton.setOnAction(e->{   
           selection=addTextCombobox.getSelectionModel().getSelectedItem().toString();
           AddTextDialogBox.this.hide(); 
        });
         buttonBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(e->{
           AddTextDialogBox.this.hide(); 
        
        });
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);       
        messagePane.getChildren().add(addTextCombobox);
        messagePane.getChildren().add(buttonBox);       
      	yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
	cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
	messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);  
       addTextCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT); 
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
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    
   public String getSelection(){
   return selection;
  }  
   
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
    public void show(String message) {
           this.showAndWait();
    }
}
