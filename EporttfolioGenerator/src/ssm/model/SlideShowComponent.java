/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.model;

import java.io.File;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import properties_manager.PropertiesManager;
import static ssm.LanguagePropertyType.NOT_A_IMAGE;
import static ssm.LanguagePropertyType.NOT_A_IMAGE_NOTICE;
import static ssm.LanguagePropertyType.TITLE_WINDOW;
import static ssm.StartupConstants.PATH_DATA;
import ssm.file.SlideShowFileManager;
import static ssm.file.SlideShowFileManager.SLASH;
//import static ssm.model.ListComponent.addListBox;
import ssm.view.SlideShowMakerView;

/**
 *
 * @author shengchun
 */
public class SlideShowComponent extends Components {

    static final String TYPE = "slides";

    SlideShowFileManager fileManager = new SlideShowFileManager();
    SlideShowMakerView ui = new SlideShowMakerView(fileManager);
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    // SlideShowModel slideModel = new SlideShowModel(ui);
    Stage m_stage = new Stage();
    int i = 0;
    Label titleLabel;
    Label title = new Label();
    HBox titleHolder;
    Image slideImage;
    ImageView slideView = new ImageView();
    VBox slideShowBox;
    Button previous;
    Button next;
    HBox buttonBox;
    HBox captionHolder;
    Label captionLabel;
    Label caption;
    String slideTitle;
    double slideWidth;
    double slideHeight;
    ObservableList<Slide> slide = FXCollections.observableArrayList();

    public SlideShowComponent() {

    }

    public SlideShowComponent(String initSlidetitle, double initWidth, double initHeight, ObservableList<Slide> initslide) {

        this.slideTitle = initSlidetitle;
        this.slideWidth = initWidth;
        this.slideHeight = initHeight;
        this.slide = initslide;
    }

    @Override
    public JsonObject toJSON() {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (Slide oneslide : slide) {
            arr.add(oneslide.toJSON());
        }

        return Json.createObjectBuilder()
                .add("type", TYPE)
                .add("title", slideTitle)
                .add("width", slideWidth)
                .add("height", slideHeight)
                .add("slides", arr)
                .build();
    }

    public static SlideShowComponent fromJSON(JsonObject obj) {
        ObservableList<Slide> slides = FXCollections.observableArrayList();
        JsonArray arr = obj.getJsonArray("slides");
        for (int i = 0; i < arr.size(); ++i) {
            slides.add(Slide.fromJSON((JsonObject) arr.get(i)));
        }

        return new SlideShowComponent(obj.getString("title"), obj.getJsonNumber("width").doubleValue(), obj.getJsonNumber("height").doubleValue(), slides);

    }

    public String getSlideTitle() {
        return slideTitle;
    }

    public void setSlideTitle(String initTitle) {
        this.slideTitle = initTitle;
    }

    public ObservableList<Slide> getSlide() {
        return slide;
    }

    public void setSlide(ObservableList<Slide> initSlide) {
        this.slide = initSlide;
    }

    public double getSlideWidth() {
        return slideWidth;
    }

    public void setSlideWidth(double slideWidth) {
        this.slideWidth = slideWidth;
    }

    public double getSlideHeight() {
        return slideHeight;
    }

    public void setSlideHeight(double slideHeight) {
        this.slideHeight = slideHeight;
    }

    public VBox createUI() {
        slideShowBox = new VBox();
        titleLabel = new Label("Title:");
        title.setText(slideTitle);
        titleHolder = new HBox();
        previous = new Button("previous");
        next = new Button("next");
        buttonBox = new HBox();
        if(slide.isEmpty()){
           return slideShowBox; 
        }
        captionLabel = new Label("Caption:");
        caption = new Label(slide.get(0).getCatption());
        captionHolder = new HBox();
        slideShowBox.setStyle("-fx-padding: 50px 50px 50px 50px;");
        previous.setOnAction(e -> {
            if (i == 0) {
                i = slide.size() - 1;
            } else {
                i--;
            }
            this.reloadVbox();
        });
        next.setOnAction(e -> {
            if (i == slide.size() - 1) {
                i = 0;
            } else {
                i++;
            }
            this.reloadVbox();
        });

        String imagePath = slide.get(i).getImagePath() + SLASH + slide.get(i).getImageFileName();

        File file = new File(imagePath);
        try {
            URL fileURL = file.toURI().toURL();
            slideImage = new Image(fileURL.toExternalForm());
            slideView = new ImageView(slideImage);
            slideView.setFitWidth(slideWidth);
            slideView.setFitHeight(slideHeight);
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

        titleHolder.getChildren().addAll(titleLabel, title);
        buttonBox.getChildren().addAll(previous, next);
        captionHolder.getChildren().addAll(captionLabel, caption);
        // slideShowBox.setAlignment(Pos.CENTER);
        slideShowBox.getChildren().add(titleHolder);
        slideShowBox.getChildren().add(slideView);
        slideShowBox.getChildren().addAll(buttonBox);
        slideShowBox.getChildren().addAll(captionHolder);
        return slideShowBox;
    }

    public void reloadVbox() {
        slideShowBox.getChildren().clear();
        captionHolder.getChildren().clear();
        String imagePath = slide.get(i).getImagePath() + SLASH + slide.get(i).getImageFileName();
        File file = new File(imagePath);
        try {
            URL fileURL = file.toURI().toURL();
            slideImage = new Image(fileURL.toExternalForm());
            slideView = new ImageView(slideImage);
            slideView.setFitWidth(slideWidth);
            slideView.setFitHeight(slideHeight);
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

        //       titleHolder.getChildren().addAll(titleLabel,title);
        //  buttonBox.getChildren().addAll(previous, next);
        caption = new Label(slide.get(i).getCatption());
        captionHolder.getChildren().addAll(captionLabel, caption);
        slideShowBox.setAlignment(Pos.CENTER);
        slideShowBox.getChildren().add(titleHolder);
        slideShowBox.getChildren().add(slideView);
        slideShowBox.getChildren().addAll(buttonBox);
        slideShowBox.getChildren().addAll(captionHolder);
    }

    @Override
    public void showDialogBox() {
        props.addProperty(props, PATH_DATA);
        String appTitle = props.getProperty(TITLE_WINDOW);
        m_stage.hide();
        ui.startUI(m_stage, appTitle);

        ui.setSlideHeight(slideHeight + "");
        ui.setSlideWidth(slideWidth + "");
        ui.setTitle(slideTitle);
        ui.setSlides(slide);

        ui.getWindow().showAndWait();
        
        updated = ui.isUpdated();

        if (ui.isUpdated()) {
            this.setSlideTitle(ui.getSlideTitle());
            this.setSlideHeight(ui.getSlideHeight());
            this.setSlideWidth(ui.getSlideWidth());
            this.setSlide(ui.getSlides());
        }

        // this.setSlideModel(ui.getSubmittedSlideShow());
    }

}
