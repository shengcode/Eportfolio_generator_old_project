package ssm.model;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonObject;
import ssm.view.AddParagraphDialogBox;
import ssm.view.AddVideoDialogBox;
/*
 * @author shengchun
 */
public class ParagraphComponent extends Components {
    static final String TYPE = "Paragraph";
    String textFontSize;
    String textFontFamily;
    Double textHeight;
    Double textWidth;
    String textContent;
    AddParagraphDialogBox addParagraphBox =  new AddParagraphDialogBox(new Stage());
   
    
    public ParagraphComponent(){
      
   
   
   }

    public ParagraphComponent(String initFontSize, String initFontFamily,Double paraTextHeight,Double paraTextWidth,String initTextContent) {
        this.textFontSize = initFontSize;
        this.textFontFamily = initFontFamily;
        this.textHeight=paraTextHeight;
        this.textWidth=paraTextWidth;
        this.textContent = initTextContent;
       
    }
    
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("type", TYPE)
                .add("textFontSize", Integer.parseInt(textFontSize))
                .add("textFontFamily", textFontFamily)
                .add("textHeight", textHeight)
                .add("textWidth", textWidth)
                .add("textContent", textContent)
                .build();
    }
    
    public static Components fromJSON(JsonObject obj) {
        return new ParagraphComponent(Integer.toString(obj.getInt("textFontSize")), 
                obj.getString("textFontFamily"),
                obj.getJsonNumber("textHeight").doubleValue(),
                obj.getJsonNumber("textWidth").doubleValue(),
                obj.getString("textContent"));
    }
    
    public VBox createUI() {
        VBox textHbox = new VBox();
           TextArea paragraph = new TextArea();
            paragraph.setText(textContent);
            String styleString = "-fx-font-family: "+textFontFamily+"; "+"-fx-font-size: "+textFontSize;
            paragraph.setStyle(styleString);
            paragraph.setPrefWidth(textWidth);
            paragraph.setPrefHeight(textHeight);
            textHbox.getChildren().add(paragraph);
            textHbox.setStyle( "-fx-border-color:RED");
            textHbox.setStyle( "-fx-background-color:RED");
            textHbox.setStyle("-fx-padding: 50px 50px 50px 50px;");
            paragraph.setEditable(false);
             return textHbox;
    }    
   

    public String getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(String textFontSize) {
        this.textFontSize = textFontSize;
    }

    public String getTextFontFamily() {
        return textFontFamily;
    }

    public void setTextFontFamily(String textFontFamily) {
        this.textFontFamily = textFontFamily;
    }

    public Double getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(Double textHeight) {
        this.textHeight = textHeight;
    }

    public Double getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(Double textWidth) {
        this.textWidth = textWidth;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    
     public void showDialogBox() {
       addParagraphBox.setParagraph(textContent);
        addParagraphBox.setTextAreaHeight(textHeight+"");
        addParagraphBox.setTextAreaWidth(textWidth+"");
        addParagraphBox.setFontFamily(textFontFamily);
        addParagraphBox.setFontSize(textFontSize);
        
        addParagraphBox.showAndWait();
        updated = addParagraphBox.isUpdated();
        
      setTextContent(addParagraphBox.getParagraph());
      setTextHeight(addParagraphBox.getTextAreaHeight());
      setTextWidth(addParagraphBox.getTextAreaWidth());
      setTextFontFamily(addParagraphBox.getFontFamily());
      setTextFontSize(addParagraphBox.getFontSize());
     
    }
    
    
    
   
}
