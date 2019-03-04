package ssm.view;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
public class AddParagraphDialogBox extends Stage {    

    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button yesButton;
    Button noButton;
    Button cancelButton;
    TextArea textToAdd = new TextArea();  ;
    String selection;    
    String replace = "";
    Label fontSizeLabel;
    ComboBox fontSizeCombobox;
    Label fontFamilyLabel;
     TextField heightTextArea= new TextField();
     TextField widthTextArea= new TextField();
    ComboBox fontFamilyCombobox;
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String CANCEL = "Cancel";
    double textAreaHeight=0.0;
    double textAreaWidth=0.0;
    String fontFamilyText;
    String fontSizeText;
    
    boolean updated = false;

    public boolean isUpdated() {
        return updated;
    }
    
    public AddParagraphDialogBox(Stage primaryStage) {
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
       messageLabel = new Label("please input the paragraph you want to add:");        
     // Region region =(Region) textToAdd.lookup(".content");
     // region.setStyle("fx-background-size: ");
     // region.setStyle( "-fx-background-color: yellow" );
      
       textToAdd.setPrefWidth(300);
       textToAdd.setPrefHeight(300);
         ObservableList<String> fontSize = FXCollections.observableArrayList("8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72");
             fontSizeLabel = new Label("Font Size:");
            fontSizeCombobox = new ComboBox(fontSize);            
       ObservableList<String> fontFamily = FXCollections.observableArrayList("Gafata", "Inconsolata", "Share Tech Mono","Nova Mono","VT323","Roboto Mono","Droid Sans Mono");
             fontFamilyLabel = new Label("Font Family:");
            fontFamilyCombobox = new ComboBox(fontFamily);   
            Label heightLabel = new Label("Height of TextArea: ");
           // TextField heightTextArea= new TextField();
            Label widthLabel = new Label("Width of TextArea: ");
           // TextField widthTextArea= new TextField();
            GridPane paragraphStyle = new GridPane();
            paragraphStyle.setPadding(new Insets(10,10,10,10));
            paragraphStyle.setVgap(8);
            paragraphStyle.setHgap(10);        
            GridPane.setConstraints(fontSizeLabel,0,0);
            GridPane.setConstraints(fontSizeCombobox,1,0);
            GridPane.setConstraints(fontFamilyLabel, 0, 1);
            GridPane.setConstraints(fontFamilyCombobox, 1, 1);
            GridPane.setConstraints(heightLabel, 0, 2);
            GridPane.setConstraints(heightTextArea, 1, 2);
            GridPane.setConstraints(widthLabel, 0, 3);
            GridPane.setConstraints(widthTextArea, 1, 3);
            paragraphStyle.getChildren().addAll(fontSizeLabel,fontSizeCombobox,fontFamilyLabel,fontFamilyCombobox,heightLabel,heightTextArea,widthLabel,widthTextArea);
            paragraphStyle.setAlignment(Pos.CENTER);
            paragraphStyle.getStylesheets().add("gridpane");
       Button addHyperLinkerButton = new Button("add hyperlinker");        
        addHyperLinkerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent l) {
                Stage inputLink = new Stage();
                AddHyperlinkerDialogBox addLinker = new AddHyperlinkerDialogBox(inputLink, textToAdd.getSelectedText());
                addLinker.showAndWait();
                   if (!textToAdd.getSelectedText().isEmpty()) {
                    replace = addLinker.htmlString;
                    if (!replace.equals("")) {
                        textToAdd.replaceSelection(replace);
                      
                    }                    
                }
            }
        });
        
    fontSizeCombobox.setOnAction(e->{
       if( fontSizeCombobox.getValue()!=null){
          String styleString = "-fx-font-family: "+fontFamilyCombobox.getValue()+"; "+"-fx-font-size: "+fontSizeCombobox.getValue();
    
    textToAdd.setStyle(styleString);
    }
        });
   fontFamilyCombobox.setOnAction(e->{
    if( fontSizeCombobox.getValue()!=null){
    String styleString = "-fx-font-family: "+fontFamilyCombobox.getValue()+"; "+"-fx-font-size: "+fontSizeCombobox.getValue();
    textToAdd.setStyle(styleString);
    }
   // String styleString = "-fx-font-family: "+fontFamilyCombobox.getValue()+"; "+"-fx-font-size: "+fontSizeCombobox.getValue();
   });
        // YES, NO, AND CANCEL BUTTONS
    //fontFamily=fontFamilyCombobox.G;
        yesButton = new Button(YES);
        yesButton.setOnAction((ActionEvent e) -> {
            selection = textToAdd.getText();  
         if(!heightTextArea.getText().isEmpty()){          
          textAreaHeight=Double.parseDouble(heightTextArea.getText());
          
         }
         if(!widthTextArea.getText().isEmpty()){
          textAreaWidth=Double.parseDouble(widthTextArea.getText());   
         }
           fontFamilyText=fontFamilyCombobox.getValue().toString();
           fontSizeText=fontSizeCombobox.getValue().toString();
            AddParagraphDialogBox.this.hide(); 
            updated = true;
        });        
        cancelButton = new Button(CANCEL);
        cancelButton.setOnAction(e -> {
            AddParagraphDialogBox.this.hide();
            updated = false;
        });

        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(cancelButton);

        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(textToAdd);
        messagePane.getChildren().add(paragraphStyle);
        messagePane.getChildren().add(addHyperLinkerButton);
        messagePane.getChildren().add(buttonBox);

        // CSS CLASSES
        yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        addHyperLinkerButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        messageLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontSizeLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontFamilyLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontSizeCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
       fontFamilyCombobox.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
       heightLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
       heightTextArea.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        widthLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        widthTextArea.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
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
    
 public String getParagraph() {
        return selection;
  }
   public double getTextAreaHeight(){   
   return textAreaHeight;
   }
   public double getTextAreaWidth(){
    return textAreaWidth;   
   }
   public String getFontFamily(){
      return fontFamilyText;   
   }
   public String getFontSize(){
      return fontSizeText;
   }
  
   
   public void setParagraph(String oldText) {
        textToAdd.setText(oldText);
  }
   public void setTextAreaHeight(String textHeight){   
        heightTextArea.setText(textHeight);    
   }
   public void setTextAreaWidth(String textWidth){
     widthTextArea.setText(textWidth);
   }
   public void setFontFamily(String font){
       fontFamilyCombobox.setValue(font);
   }
   public void setFontSize(String fontSize){
      fontSizeCombobox.setValue(fontSize);
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
