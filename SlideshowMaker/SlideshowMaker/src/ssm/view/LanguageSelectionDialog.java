package ssm.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static ssm.StartupConstants.ENGLISH_LANG;
import static ssm.StartupConstants.FINNISH_LANG;
import static ssm.StartupConstants.LABEL_LANGUAGE_SELECTION_PROMPT;
import static ssm.StartupConstants.OK_BUTTON_TEXT;
import static ssm.StartupConstants.STYLE_SHEET_UI;

/**
 *
 * @author McKillaGorilla
 */
public class LanguageSelectionDialog extends Stage {
    VBox vBox;
    Label languagePromptLabel;
    ComboBox languageComboBox;
    Button okButton;
    String selectedLanguage = ENGLISH_LANG;
    
    public LanguageSelectionDialog() {
	languagePromptLabel = new Label(LABEL_LANGUAGE_SELECTION_PROMPT);
	
	// INIT THE LANGUAGE CHOICES
	ObservableList<String> languageChoices = FXCollections.observableArrayList();
	languageChoices.add(ENGLISH_LANG);
	languageChoices.add(FINNISH_LANG);
	languageComboBox = new ComboBox(languageChoices);
	languageComboBox.getSelectionModel().select(ENGLISH_LANG);
        languageComboBox.getStylesheets().add("comboBox.listview");
	okButton = new Button(OK_BUTTON_TEXT);
	
	vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        
        vBox.setPadding(new Insets(50, 50, 50, 50));
        
        
        vBox.getStylesheets().add("slide_show_edit_vbox");
	vBox.getChildren().add(languagePromptLabel);
	vBox.getChildren().add(languageComboBox);
	vBox.getChildren().add(okButton);
	
	okButton.setOnAction(e -> {
	    selectedLanguage = languageComboBox.getSelectionModel().getSelectedItem().toString();
	    this.hide();
	});
	
	// NOW SET THE SCENE IN THIS WINDOW
	Scene scene = new Scene(vBox,300,250);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	this.setX(bounds.getMinX());
	this.setY(bounds.getMinY());
	this.setWidth(bounds.getWidth());
	this.setHeight(bounds.getHeight());
        
        scene.getStylesheets().add("SlideShowMakerStyle.css");
       	setScene(scene);
    }
    
    public String getSelectedLanguage() {
	return selectedLanguage;
    }
}
