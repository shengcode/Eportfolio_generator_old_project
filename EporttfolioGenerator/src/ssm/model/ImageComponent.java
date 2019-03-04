package ssm.model;

import java.io.File;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonObject;
import properties_manager.PropertiesManager;
import static ssm.LanguagePropertyType.NOT_A_IMAGE;
import static ssm.LanguagePropertyType.NOT_A_IMAGE_NOTICE;
import static ssm.file.SlideShowFileManager.SLASH;
//import static ssm.model.VideoComponent.addVideoBox;
import ssm.view.AddImageDialogBox;
import ssm.view.AddVideoDialogBox;

/**
 * *
 * @author shengchun
 */
public class ImageComponent extends Components {

    static final String TYPE = "image";
    String imageFileName;
    String imagePath;
    Double imageHeight;
    Double imageWidth;
    String imageAlignment;
    String imageCaption;
    ImageView imageViewToAdd;
    AddImageDialogBox addImageBox = new AddImageDialogBox(new Stage());

    public ImageComponent() {

    }

    public ImageComponent(String imageFileName, String imagePath, String imageCaption, String imageAlignment, Double imageHeight, Double imageWidth) {

        this.imageFileName = imageFileName;
        this.imagePath = imagePath;
        this.imageCaption = imageCaption;
        this.imageAlignment = imageAlignment;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }

    @Override
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("type", TYPE)
                .add("imageFileName", imageFileName)
                .add("imagePath", imagePath)
                .add("imageAlignment", imageAlignment)
                .add("imageHeight", imageHeight)
                .add("imageWidth", imageWidth)
                .add("imageCaption", imageCaption)
                .build();
    }

    public static Components fromJSON(JsonObject obj) {
        return new ImageComponent(
                obj.getString("imageFileName"),
                obj.getString("imagePath"),
                obj.getString("imageCaption"),
                obj.getString("imageAlignment"),
                obj.getJsonNumber("imageHeight").doubleValue(),
                obj.getJsonNumber("imageWidth").doubleValue());
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Double getImageHeight() {
        return imageHeight;
    }

    public Double getImageWidth() {
        return imageWidth;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageHeight(Double imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageWidth(Double imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageAlignment() {
        return imageAlignment;
    }

    public void setImageAlignment(String imageAlignment) {
        this.imageAlignment = imageAlignment;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public VBox createUI() {
        VBox imageBox = new VBox();
        String imageFilePath = imagePath + SLASH + imageFileName;
        File file = new File(imageFilePath);
        try {
            // GET AND SET THE IMAGE
            URL fileURL = file.toURI().toURL();
            Image imageToAdd = new Image(fileURL.toExternalForm());
            // Image imageToAdd = new Image(fileURL.toString());
            imageViewToAdd = new ImageView();
            imageViewToAdd.setImage(imageToAdd);
            imageViewToAdd.setFitHeight(imageHeight);
            imageViewToAdd.setFitWidth(imageWidth);

        } catch (Exception e) {

            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String notAImage = props.getProperty(NOT_A_IMAGE.toString());
            String notice = props.getProperty(NOT_A_IMAGE_NOTICE.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(notAImage);
            alert.setHeaderText(notice);
            alert.setContentText(notice);
            alert.showAndWait();
        }
        Label captionLabel = new Label("Caption: ");
        Label caption = new Label();
        caption.setText(imageCaption);
        HBox captionBox = new HBox();
        captionBox.getChildren().addAll(captionLabel, caption);
        imageBox.getChildren().addAll(imageViewToAdd, captionBox);
        return imageBox;
    }

    public void showDialogBox() {

        addImageBox.setImageName(imageFileName);
        addImageBox.setImageAlignment(imageAlignment);
        addImageBox.setImageCaption(imageCaption);
        addImageBox.setImageHeight(imageHeight + "");
        addImageBox.setImageWidth(imageWidth + "");

        addImageBox.showAndWait();
        
        updated = addImageBox.isUpdated();

        setImageFileName(addImageBox.getImageFileName());
        setImagePath(addImageBox.getImagePath());
        setImageCaption(addImageBox.getImageCaption());
        setImageAlignment(addImageBox.getImageAlignment());
        setImageHeight(addImageBox.getImageHeight());
        setImageWidth(addImageBox.getImageWidth());

    }

}
