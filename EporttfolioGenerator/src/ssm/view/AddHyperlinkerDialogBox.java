package ssm.view;

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
/*
 * @author shengchun
 */

public class AddHyperlinkerDialogBox extends Stage {

    VBox messagePane;
    Scene messageScene;
    Button yesButton;
    Button cancelButton;
    Label messageLabel = new Label("enter the  hyperlinlker:");
    String selection;
    Label TextLabel;
    TextField text;
    Label HyperlinkLabel;
    TextField hyperlinker;
    public static final String YES = "Yes";
    public static final String CANCEL = "Cancel";
    String htmlString = "";
    

   

    public AddHyperlinkerDialogBox(Stage primaryStage, String selectedtext) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        HBox hyperBox = new HBox();
        HyperlinkLabel = new Label("Hyperlink: ");
        hyperlinker = new TextField();
        hyperBox.getChildren().addAll(HyperlinkLabel, hyperlinker);

        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        cancelButton = new Button(CANCEL);

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        yesButton.setOnAction(e -> {
            htmlString= "<a href=\"" + hyperlinker.getText() + "\">" + selectedtext+ "</a>";
             AddHyperlinkerDialogBox.this.hide();
        });

        buttonBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(e -> {
            AddHyperlinkerDialogBox.this.hide();

        });
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(hyperBox);
        messagePane.getChildren().add(buttonBox);
        // CSS CLASSES
        yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        messageLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        HyperlinkLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        hyperlinker.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
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
/*
    public String getHyperlinker() {
        return hyperLinkerString;
    }*/
 public String htmlhyper(String text, String link) {
       return htmlString;
        
    }
}
