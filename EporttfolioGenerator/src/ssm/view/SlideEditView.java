package ssm.view;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import static ssm.LanguagePropertyType.NOT_A_IMAGE;
import static ssm.LanguagePropertyType.NOT_A_IMAGE_NOTICE;
import static ssm.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import ssm.controller.ImageSelectionController;
import ssm.model.Slide;
import static ssm.file.SlideShowFileManager.SLASH;
import ssm.model.SlideShowModel;

/**
 * This UI component has the controls for editing a single slide in a slide
 * show, including controls for selected the slide image and changing its
 * caption.
 *
 * @author McKilla Gorilla & _____________
 */
public class SlideEditView extends HBox {

    Slide slide;
    SlideShowModel slideShow;
    ImageView imageSelectionView;
    VBox captionVBox;
    Label captionLabel;
    TextField captionTextField;
    ImageSelectionController imageController;

    public SlideEditView(Slide initSlide, SlideShowModel slideShow) {
        // FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
        this.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
        slide = initSlide;
        // MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE
        imageSelectionView = new ImageView();

        updateSlideImage();
        // SETUP THE CAPTION CONTROLS
        captionVBox = new VBox();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        captionLabel = new Label(props.getProperty(LanguagePropertyType.LABEL_CAPTION));

        captionTextField = new TextField();
        captionTextField.getStyleClass().add("textfield");
        captionTextField.setText(slide.getCatption());
       
        captionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            slide.setCaption(newValue);
        });
        captionVBox.getChildren().add(captionLabel);
        captionVBox.getChildren().add(captionTextField);
        // LAY EVERYTHING OUT INSIDE THIS COMPONENT 
        getChildren().add(imageSelectionView);
        getChildren().add(captionVBox);
        // SETUP THE EVENT HANDLERS        
        imageController = new ImageSelectionController();
        imageSelectionView.setOnMousePressed(e -> {
            imageController.processSelectImage(slide, this);
        });

    }

    /**
     * This function gets the image for the slide and uses it to update the
     * image displayed.
     */
    public void updateSlideImage() {
        String imagePath = slide.getImagePath() + SLASH + slide.getImageFileName();
        File file = new File(imagePath);
        try {
            // GET AND SET THE IMAGE
            URL fileURL = file.toURI().toURL();
            Image slideImage = new Image(fileURL.toExternalForm());
            imageSelectionView.setImage(slideImage);
            // AND RESIZE IT
            double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
            double perc = scaledWidth / slideImage.getWidth();
            double scaledHeight = slideImage.getHeight() * perc;
            imageSelectionView.setFitWidth(scaledWidth);
            imageSelectionView.setFitHeight(scaledHeight);
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
    }

}
