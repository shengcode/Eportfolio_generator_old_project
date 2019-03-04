package ssm.view;

import java.io.File;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
import static ssm.file.SlideShowFileManager.SLASH;
import ssm.model.Components;
import ssm.model.EportfolioModel;
import ssm.model.ImageComponent;
import ssm.model.Page;
import ssm.model.VideoComponent;

/**
 * *
 * @author shengchun
 */
public class PageEditView extends VBox {

    Page page;
    EportfolioModel eportfolioModel;
    HBox headerHbox;
    Label headerLabel;
    HBox bannerHbox;
    Label pageTitleLabel;
    TextField pageTitleTextField;
    Label studentNameLabel;
    TextField studentNameTextField;
    Image bannerImage;
    ImageView bannerImageView;
    ImageSelectionController bannerImageController;
    VBox contentHbox;
    HBox footerHbox;
    Label pageFooterLabel;
    TextField pageFooterTextField;
    Tab newTab;
    Label testLabel;
    EportfolioMakerView ui;

    public PageEditView(Page initPage, EportfolioModel eportfolioModel, EportfolioMakerView initUi) {
        page = initPage;
        this.getStyleClass().add(initPage.getPageStyle());
        newTab = new Tab(initPage.getPageTitle());
        this.ui = initUi;

        this.newTab.setOnSelectionChanged(e -> {
            eportfolioModel.setSelectedPage(page);
            this.reloadPageEditView();
            ui.reloadEportfolioPane();
        });
        this.newTab.setOnClosed(i -> {
            eportfolioModel.getPages().remove(page);
            ui.reloadEportfolioPane();
        });

       
            headerHbox = new HBox();
            headerLabel = new Label("default header");
            bannerHbox = new HBox();
            bannerImageController = new ImageSelectionController();
            bannerImageView = new ImageView();
            updateBannerImage();
            bannerImageView.setOnMousePressed(e -> {
                bannerImageController.processSelectBannerImageFive(page, this);
            });
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            studentNameLabel = new Label(props.getProperty(LanguagePropertyType.LABEL_STUDENTNAME));
            studentNameTextField = new TextField();
            studentNameTextField.getStyleClass().add("textfield");
            studentNameTextField.setText(page.getStudentName());
            studentNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                page.setStudentName(newValue);
            });
            pageTitleLabel = new Label(props.getProperty(LanguagePropertyType.LABEL_PAGETITLE));
            pageTitleTextField = new TextField("title");
            pageTitleTextField.getStyleClass().add("textfield");
            pageTitleTextField.setText(page.getPageTitle());

            pageTitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                page.setPageTitle(newValue);
                newTab.setText(newValue);
            });
            contentHbox = new VBox();
            footerHbox = new HBox();
            pageFooterLabel = new Label("footer:");
            pageFooterTextField = new TextField();
            pageFooterTextField.getStyleClass().add("textfield");
            pageFooterTextField.setText(page.getPageFooter());
            pageFooterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                page.setPageFooter(newValue);
            });
            headerHbox.getChildren().add(headerLabel);
            testLabel = new Label();
            testLabel.setText("THIS IS TYPE ONE");  //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
           
            headerHbox.getChildren().add(testLabel);
            footerHbox.getChildren().add(pageFooterLabel);
            footerHbox.getChildren().add(pageFooterTextField);
            bannerHbox.getChildren().add(pageTitleLabel);
            bannerHbox.getChildren().add(pageTitleTextField);
            bannerHbox.getChildren().add(studentNameLabel);
            bannerHbox.getChildren().add(studentNameTextField);
            bannerHbox.getChildren().add(bannerImageView);
            this.getChildren().add(headerHbox);
            this.getChildren().add(bannerHbox);
            this.getChildren().add(contentHbox);
            contentHbox.setPrefHeight(1000);
            this.getChildren().add(footerHbox);
        } 

    public Page getPage() {
        return page;
    }
    public Tab getTab(){
      return newTab;
    }

    public void reloadPageEditView() {
        this.headerHbox.getChildren().clear();
        this.contentHbox.getChildren().clear();
       

        this.getStyleClass().add(page.getPageStyle());
        if (page.getHeaderString() != "") {
            Label headerLabel = new Label(page.getHeaderString());
            this.headerHbox.getChildren().add(headerLabel);
            headerLabel.setOnMouseClicked(e -> {
                int counterHeader = e.getClickCount();
                if (counterHeader==1){
                page.setHeaderSelected(true);
                }
                else if (counterHeader==2){
                  
                }
            });
        }
        for (Components components : page.getComponents()) {
            VBox sb = components.createUI();
            sb.setOnMouseClicked(e -> {
                int counter = e.getClickCount();
                if (counter == 1) {
                    page.setSelectedComponent(components);
                } else if (counter == 2) {
                    components.showDialogBox();
                       ui.reloadEportfolioPane();
                }

            });

            this.contentHbox.getChildren().add(sb);
        }     
        
        if(page.getPageType().equals("typeone")){
              this.testLabel.setText("layoutOne");             
        }
        else if(page.getPageType().equals("typetwo")){
              this.testLabel.setText("layoutTwo");              
        }
        else if(page.getPageType().equals("typethree")){
              this.testLabel.setText("layoutThree");              
        }
        else if(page.getPageType().equals("typefour")){
              this.testLabel.setText("layoutFour");             
        }
        else {       
              this.testLabel.setText("layoutFive");            
         }

    }

    public void updateBannerImage() {

        String imagePath = page.getBannerImagePath() + SLASH + page.getBannerImageFileName();

        File file = new File(imagePath);
        try {
            // GET AND SET THE IMAGE
            URL fileURL = file.toURI().toURL();
            Image bannerImage = new Image(fileURL.toExternalForm());
            bannerImageView.setImage(bannerImage);
            // AND RESIZE IT
            double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
            double perc = scaledWidth / bannerImage.getWidth();
            double scaledHeight = bannerImage.getHeight() * perc;
            bannerImageView.setFitWidth(scaledWidth);
            bannerImageView.setFitHeight(scaledHeight);
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
