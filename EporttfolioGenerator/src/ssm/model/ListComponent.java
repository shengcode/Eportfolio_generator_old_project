package ssm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import ssm.view.AddListDialogBox;

/*
 * @author shengchun
 */
public class ListComponent extends Components {

    static final String TYPE = "list";
    String textFontSize;
    String textFontFamily;
    Double textHeight;
    Double textWidth;
    ObservableList<String> textContent = FXCollections.observableArrayList();
    String listTitle;
    AddListDialogBox addListBox = new AddListDialogBox(new Stage());

    public ListComponent() {

    }

    public ListComponent(String initFontSize, String initFontFamily, Double listTextHeight, Double listTextWidth, ObservableList<String> initTextContent, String initlistTitle) {
        this.textFontSize = initFontSize;
        this.textFontFamily = initFontFamily;
        this.textHeight = listTextHeight;
        this.textWidth = listTextWidth;
        this.textContent = initTextContent;
        listTitle = initlistTitle;
    }

    @Override
    public JsonObject toJSON() {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (String text : textContent) {
            arr.add(text);
        }
        return Json.createObjectBuilder()
                .add("type", TYPE)
                .add("title", listTitle)
                .add("textFontSize", textFontSize)
                .add("textFontFamily", textFontFamily)
                .add("textHeight", textHeight)
                .add("textWidth", textWidth)
                .add("array", arr)
                .build();
    }

    public static ListComponent fromJSON(JsonObject obj) {
        ObservableList<String> textList = FXCollections.observableArrayList();
        JsonArray arr = obj.getJsonArray("array");
        for (int i = 0; i < arr.size(); ++i) {
            textList.add(arr.getString(i));
        }
        return new ListComponent(obj.getString("textFontSize"), obj.getString("textFontFamily"), obj.getJsonNumber("textHeight").doubleValue(), obj.getJsonNumber("textWidth").doubleValue(), textList, obj.getString("listTitle"));

    }

    public VBox createUI() {
        
        VBox textVbox = new VBox();
        Label listTitleLabel = new Label();
        listTitleLabel.setText(listTitle);
        ListView<String> list = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        items = textContent;
        list.setItems(items);
        String styleString = "-fx-font-family: " + textFontFamily + "; " + "-fx-font-size: " + textFontSize;
        list.setStyle(styleString);
        list.setPrefSize(textWidth, textHeight);
        textVbox.setStyle("-fx-padding: 50px 50px 50px 50px;");
        textVbox.getChildren().add(listTitleLabel);
        textVbox.getChildren().add(list);
        return textVbox;
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

    public ObservableList<String> getTextContent() {
        return textContent;
    }

    public void setTextContent(ObservableList<String> textContent) {
        this.textContent = textContent;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public void showDialogBox() {

        addListBox.setTextContent(textContent);
        addListBox.setListTitle(listTitle);
        addListBox.setFontFamily(textFontFamily);
        addListBox.setFontSize(textFontSize);
        addListBox.setTextAreaHeight(textHeight + "");
        addListBox.setTextAreaWidth(textWidth + "");

        addListBox.showAndWait();
        updated = addListBox.isUpdated();

        setTextContent(addListBox.getSelection());
        setListTitle(addListBox.getListTitle());
        setTextHeight(addListBox.getTextAreaHeight());
        setTextWidth(addListBox.getTextAreaWidth());
        setTextFontFamily(addListBox.getFontFamily());
        setTextFontSize(addListBox.getFontSize());

    }

}
