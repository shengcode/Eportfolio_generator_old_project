package ssm.view;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_IMAGE;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_PAGE;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_TEXT;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_VIDEO;
import static ssm.LanguagePropertyType.TOOLTIP_DELETE_COMPONENT;
import static ssm.LanguagePropertyType.TOOLTIP_EXIT;
import static ssm.LanguagePropertyType.TOOLTIP_LOAD_EPORTFOLIO;
import static ssm.LanguagePropertyType.TOOLTIP_NEW_EPORTFOLIO;
import static ssm.LanguagePropertyType.TOOLTIP_REMOVE_PAGE;
import static ssm.LanguagePropertyType.TOOLTIP_SAVE_AS_EPORTFOLIO;
import static ssm.LanguagePropertyType.TOOLTIP_SAVE_EPORTFOLIO;
import static ssm.LanguagePropertyType.TOOLTIP_SITE_VIEWER;
import static ssm.LanguagePropertyType.TOOLTIP_VIEW_EPORTFOLIO;
import static ssm.LanguagePropertyType.TOOLTIP_WORKSPACE_MODE;
import static ssm.StartupConstants.CSS_CLASS_FIVE_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.CSS_CLASS_FOUR_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static ssm.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.CSS_CLASS_SLIDE_SHOW_EDIT_VBOX;
import static ssm.StartupConstants.CSS_CLASS_THREE_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.CSS_CLASS_TWO_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.ICON_ADD_IMAGE;
import static ssm.StartupConstants.ICON_ADD_PAGE;
import static ssm.StartupConstants.ICON_ADD_SLIDESHOW;
import static ssm.StartupConstants.ICON_ADD_TEXT;
import static ssm.StartupConstants.ICON_ADD_VIDEO;
import static ssm.StartupConstants.ICON_DELETE_COMPONENT;
import static ssm.StartupConstants.ICON_EXIT;
import static ssm.StartupConstants.ICON_LOAD_EPORTFOLIO_SHOW;
import static ssm.StartupConstants.ICON_NEW_EPORTFOLIO_SHOW;
import static ssm.StartupConstants.ICON_REMOVE_PAGE;
import static ssm.StartupConstants.ICON_SAVE_AS_EPORTFOLIO_SHOW;
import static ssm.StartupConstants.ICON_SAVE_EPORTFOLIO_SHOW;
import static ssm.StartupConstants.ICON_VIEW_EPORTFOLIO_SHOW;
import static ssm.StartupConstants.ICON_VIEW_PAGE;
import static ssm.StartupConstants.ICON_WINDOW;
import static ssm.StartupConstants.ICON_WORKSPACE_MODE;
import static ssm.StartupConstants.PATH_ICONS;
import static ssm.StartupConstants.STYLE_SHEET_EPORTFOLIO_UI;
import ssm.controller.EportfolioEditController;
import ssm.controller.EportfolioFileController;
import ssm.error.EportfolioErrorHandler;
import ssm.file.EportfolioFileManager;
import ssm.model.EportfolioModel;
import ssm.model.ImageComponent;
import ssm.model.ListComponent;
import ssm.model.Page;
import ssm.model.Slide;
import ssm.model.SlideShowComponent;
import ssm.model.VideoComponent;
import ssm.model.ParagraphComponent;

/**
 * * *
 * @author shengchun
 */
enum PageType {

    ONE, TWO, THREE, FOUR, FIVE
}

public class EportfolioMakerView {

    FlowPane fileToolbarPane;
    BorderPane eportfolioPane;
    Button newEportfolioButton;
    Button loadEportfolioButton;
    Button saveEportfolioButton;
    Button saveAsEportfolioButton;
    Button viewEportfolioButton;
    Button exportEportfolioButton;
    Button exitEportfolioButton;
    HBox SiteEditorWorkSpace;
    VBox SiteEditorToolbar;
    Button addPageButton;
    Button removePageButton;
    Button selectPageButton;
    Button selectPageEditorWorkSpaceButton;
    Button selectSiteViewerWorkSpaceButton;
    VBox SiteEditorPane;
    ScrollPane SiteEditorScrollPane;
    HBox pageEditorWorkSpace;
    VBox pageEditorToolbar;
    Button editHeaderButton;
    Button selectBannerImageButton;
    Button addTextButton;
    Button addImageButton;
    Button addSlideShowButton;
    Button addVideoButton;
    Button addListButton;
    Button addHyperLinkerButton;
    Button removeComponentButton;
    Button selectComponentButton;
    Button viewThePageButton;
    TabPane pageLayoutTabPane;
    TabPane colorTabPane;
    Tab layOut1;
    Tab layOut2;
    Tab layOut3;
    Tab layOut4;
    Tab layOut5;
    Tab colorOne;
    Tab colorTwo;
    Tab colorThree;
    Tab colorFour;
    Tab colorFive;
    TabPane pageTabPane = new TabPane();
    VBox pageEditorPane;
    ScrollPane pageEditorScrollPane;
    Scene primaryScene;
    Stage primaryStage;
    Stage pageEditStage;
    EportfolioFileController eportfolioFileControll;
    EportfolioFileManager eportfolioFileManager;
    EportfolioModel ePortfolioModel;
    EportfolioErrorHandler ePortfolioerrorHandler;
    EportfolioEditController ePortfolioEditController;
    Page theTabSelectedPage;
    String headerString = "";
    String paragraphString = "";
    String paraFontFamily = "";
    String paraFontSize = "";
    Double paraTextHeight = 0.0;
    Double paraTextWidth = 0.0;
    ObservableList<String> list = FXCollections.observableArrayList();
    String listFontSize = "";
    String listFontFamily = "";
    Double listAreaHeight = 0.0;
    Double listAreaWidth = 0.0;
    String listTitle = "";
    ObservableList<Slide> slide;
    Double SlideWidth = 0.0;
    Double SlideHeight = 0.0;
    String addImageFileName;
    String addImageFilePath;
    String addImageAlignment;
    String addImageCaption;
    Double addImageHeight;
    Double addImageWidth;
    String addVideoFileName;
    String addVideoFilePath;
    String addVideoAlignment;
    String addVideoCaption;
    Double addVideoHeight;
    Double addVideoWidth;
    String whatKindText = "";
    String JsonFileName = "";
    boolean beSavedAs = false;
    Stage addTextStage;
    AddTextDialogBox addText;
    Stage addHeaderStage;
    EditHeaderDialogBox editHeaderBox;
    Stage addParagraphStage;
    AddParagraphDialogBox addParagraph;
    Stage addListStage;
    AddListDialogBox addListBox;
    Stage addImageStage;
    AddImageDialogBox addImageBox;
    Stage addVideoStage;
    AddVideoDialogBox addVideoBox;

    public static final HashMap<String, String> PageType2CSS;
    public static final HashMap<String, String> PageStyle2CSS;

    static {
        PageType2CSS = new HashMap<String, String>();
        PageStyle2CSS = new HashMap<String, String>();

        PageType2CSS.put("typeone", "page1layout.css");
        PageType2CSS.put("typetwo", "page2layout.css");
        PageType2CSS.put("typethree", "page3layout.css");
        PageType2CSS.put("typefour", "page4layout.css");
        PageType2CSS.put("typefive", "page5layout.css");

        PageStyle2CSS.put(CSS_CLASS_SLIDE_EDIT_VIEW, "page1style.css");
        //     PageStyle2CSS.put("CSS_CLASS_ONE_SLIDE_EDIT_VIEW", "page1style.css");
        PageStyle2CSS.put(CSS_CLASS_TWO_SLIDE_EDIT_VIEW, "page2style.css");
        PageStyle2CSS.put(CSS_CLASS_THREE_SLIDE_EDIT_VIEW, "page3style.css");
        PageStyle2CSS.put(CSS_CLASS_FOUR_SLIDE_EDIT_VIEW, "page4style.css");
        PageStyle2CSS.put(CSS_CLASS_FIVE_SLIDE_EDIT_VIEW, "page5style.css");

    }

    public EportfolioMakerView(EportfolioFileManager initFileManager) {
        eportfolioFileManager = initFileManager;
        ePortfolioModel = new EportfolioModel(this);
        ePortfolioerrorHandler = new EportfolioErrorHandler(this);
    }

    public void startUI(Stage initPrimaryStage) {
        // THE TOOLBAR ALONG THE NORTH
        initFileToolbar();
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET        
        // NOW SETUP THE EVENT HANDLERS
        setUpSiteEditorWorkSpace();
        setUppageEditorWorkspace();
        initEventHandlers();
        //AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        // KEEP THE WINDOW FOR LATER                  
        primaryStage = initPrimaryStage;
        initWindow();
    }

    public EportfolioModel getEportfolioModel() {
        return ePortfolioModel;
    }

    public EportfolioErrorHandler getErrorHandler() {
        return ePortfolioerrorHandler;
    }

    public Stage getWindow() {
        return primaryStage;
    }

    public String getJsonFileName() {
        return JsonFileName;
    }

    public void setJsonFileName(String jsonName) {
        JsonFileName = jsonName;
    }

    private void setUpSiteEditorWorkSpace() {
        SiteEditorWorkSpace = new HBox();
        SiteEditorWorkSpace.getStyleClass().add("workspace_hbox");
        SiteEditorToolbar = new VBox();
        SiteEditorToolbar.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
        addPageButton = initChildButton(SiteEditorToolbar, ICON_ADD_PAGE, TOOLTIP_ADD_PAGE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        removePageButton = initChildButton(SiteEditorToolbar, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_PAGE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        selectPageEditorWorkSpaceButton = initChildButton(SiteEditorToolbar, ICON_WORKSPACE_MODE, TOOLTIP_WORKSPACE_MODE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        //  setPageFontSizeButton = initChildButton(SiteEditorToolbar, ICON_WORKSPACE_MODE, TOOLTIP_WORKSPACE_MODE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        SiteEditorPane = new VBox();
        SiteEditorPane.setPrefWidth(1300);
        SiteEditorPane.setPrefHeight(2000);
        SiteEditorScrollPane = new ScrollPane(SiteEditorPane);
        SiteEditorWorkSpace.getChildren().add(SiteEditorToolbar);
        SiteEditorWorkSpace.getChildren().add(SiteEditorScrollPane);
        SiteEditorToolbar.getChildren().add(pageTabPane);
        pageTabPane.setSide(Side.LEFT);
    }

    public void addSiteEditorWorkSpace() {
        eportfolioPane.setCenter(SiteEditorWorkSpace);
    }

    public void setUppageEditorWorkspace() {
        // add video, add image, add text, add hyperlinker etc 
        pageEditorWorkSpace = new HBox();
        pageEditorWorkSpace.getStyleClass().add("workspace_hbox");
        pageEditorToolbar = new VBox();
        pageEditorToolbar.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
        addTextButton = initChildButton(pageEditorToolbar, ICON_ADD_TEXT, TOOLTIP_ADD_TEXT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        addImageButton = initChildButton(pageEditorToolbar, ICON_ADD_IMAGE, TOOLTIP_ADD_IMAGE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        addSlideShowButton = initChildButton(pageEditorToolbar, ICON_ADD_SLIDESHOW, TOOLTIP_ADD_SLIDE_SHOW, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        addVideoButton = initChildButton(pageEditorToolbar, ICON_ADD_VIDEO, TOOLTIP_ADD_VIDEO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        removeComponentButton = initChildButton(pageEditorToolbar, ICON_DELETE_COMPONENT, TOOLTIP_DELETE_COMPONENT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        viewThePageButton = initChildButton(pageEditorToolbar, ICON_VIEW_PAGE, TOOLTIP_SITE_VIEWER, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        pageLayoutTabPane = new TabPane();

        layOut1 = new Tab();
        layOut1.setText("LayoutOne");
        layOut1.closableProperty().setValue(false);
        layOut1.setOnSelectionChanged(e -> {
            if (layOut1.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageType("typeone");
                this.reloadEportfolioPane();
            }
        });
        pageLayoutTabPane.getTabs().add(layOut1);
        layOut2 = new Tab();
        layOut2.setText("LayoutTwo");
        layOut2.closableProperty().setValue(false);
        layOut2.setOnSelectionChanged(e -> {
            if (layOut2.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageType("typetwo");
                this.reloadEportfolioPane();
            }
        });
        pageLayoutTabPane.getTabs().add(layOut2);
        layOut3 = new Tab();
        layOut3.setText("LayoutThree");
        layOut3.closableProperty().setValue(false);
        layOut3.setOnSelectionChanged(e -> {
            if (layOut3.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageType("typethree");
                this.reloadEportfolioPane();
            }
        });
        pageLayoutTabPane.getTabs().add(layOut3);
        layOut4 = new Tab();
        layOut4.setText("LayoutFour");
        layOut4.closableProperty().setValue(false);
        layOut4.setOnSelectionChanged(e -> {
            if (layOut4.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageType("typefour");
                this.reloadEportfolioPane();
            }
        });
        pageLayoutTabPane.getTabs().add(layOut4);
        layOut5 = new Tab();
        layOut5.setText("LayoutFive");
        layOut5.closableProperty().setValue(false);
        layOut5.setOnSelectionChanged(e -> {
            if (layOut5.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageType("typefive");
                this.reloadEportfolioPane();
            }
        });
        pageLayoutTabPane.getTabs().add(layOut5);
        colorTabPane = new TabPane();
        colorOne = new Tab();
        colorOne.setText("colorOne");
        colorOne.closableProperty().setValue(false);
        colorOne.setOnSelectionChanged(e -> {
            if (colorOne.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageStyle(CSS_CLASS_SLIDE_EDIT_VIEW);
                //ePortfolioModel.getSelectedPage().setPageStyle("colorOne");
                this.reloadEportfolioPane();
            }
        });
        colorTabPane.getTabs().add(colorOne);
        colorTwo = new Tab();
        colorTwo.setText("colorTwo");
        colorTwo.closableProperty().setValue(false);
        colorTwo.setOnSelectionChanged(e -> {
            if (colorTwo.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageStyle(CSS_CLASS_TWO_SLIDE_EDIT_VIEW);
                this.reloadEportfolioPane();
            }
        });
        colorTabPane.getTabs().add(colorTwo);
        colorThree = new Tab();
        colorThree.setText("colorThree");
        colorThree.closableProperty().setValue(false);
        colorThree.setOnSelectionChanged(e -> {
            if (colorThree.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageStyle(CSS_CLASS_THREE_SLIDE_EDIT_VIEW);
                // ePortfolioModel.getSelectedPage().setPageStyle("colorThree");
                this.reloadEportfolioPane();
            }
        });
        colorTabPane.getTabs().add(colorThree);
        colorFour = new Tab();
        colorFour.setText("colorFour");
        colorFour.closableProperty().setValue(false);
        colorFour.setOnSelectionChanged(e -> {
            if (colorFour.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageStyle(CSS_CLASS_FOUR_SLIDE_EDIT_VIEW);
                this.reloadEportfolioPane();

            }
        });
        colorTabPane.getTabs().add(colorFour);
        colorFive = new Tab();
        colorFive.setText("colorFive");
        colorFive.closableProperty().setValue(false);
        colorFive.setOnSelectionChanged(e -> {
            if (colorFive.isSelected() && ePortfolioModel.isPageSelected()) {
                ePortfolioModel.getSelectedPage().setPageStyle(CSS_CLASS_FIVE_SLIDE_EDIT_VIEW);
                // ePortfolioModel.getSelectedPage().setPageStyle("colorFive");
                this.reloadEportfolioPane();

            }
        });
        colorTabPane.getTabs().add(colorFive);
        pageLayoutTabPane.setSide(Side.LEFT);
        colorTabPane.setSide(Side.LEFT);
        pageEditorWorkSpace.getChildren().add(pageLayoutTabPane);
        pageEditorWorkSpace.getChildren().add(colorTabPane);
        HBox tabPaneHbox = new HBox();
        tabPaneHbox.getChildren().add(pageLayoutTabPane);
        tabPaneHbox.getChildren().add(colorTabPane);
        pageEditorWorkSpace.getChildren().addAll(pageEditorToolbar, tabPaneHbox);
    }

    public void addPageEditorWorkSpace() {
        eportfolioPane.setRight(pageEditorWorkSpace);
    }

    public void initEventHandlers() {
        eportfolioFileControll = new EportfolioFileController(this, eportfolioFileManager);

        newEportfolioButton.setOnAction(e -> {
            eportfolioFileControll.handleNewEportFolioRequest();
        });
        loadEportfolioButton.setOnAction(e -> {

            try {
                eportfolioFileControll.handleNewEportFolioRequest();
                eportfolioFileControll.handleLoadEportfolioRequest(ePortfolioModel);
            } catch (IOException exception1) {

            }
        });
        saveEportfolioButton.setOnAction((ActionEvent e) -> {
            try {
                if (JsonFileName.isEmpty()) {
                    Stage askFileName = new Stage();
                    JsonFileTitleDialogBox fileName = new JsonFileTitleDialogBox(askFileName);
                    fileName.showAndWait();
                    JsonFileName = fileName.getJsonFileName();

                }

                eportfolioFileControll.handleSaveAsEportfolioRequest(ePortfolioModel, JsonFileName);

            } catch (IOException exception1) {

            }
            // System.out.println(ePortfolioModel.toJSON());
        });
        saveAsEportfolioButton.setOnAction(e -> {
            Stage askFileName = new Stage();
            JsonFileTitleDialogBox fileName = new JsonFileTitleDialogBox(askFileName);
            fileName.showAndWait();
            JsonFileName = fileName.getJsonFileName();

            try {
                eportfolioFileControll.handleSaveAsEportfolioRequest(ePortfolioModel, JsonFileName);
                beSavedAs = false;
            } catch (IOException exception2) {
            }

        });
        exportEportfolioButton.setOnAction(e -> {
            try {
                eportfolioFileControll.handleViewEportfolioRequest();
            } catch (IOException ex) {
                Logger.getLogger(EportfolioMakerView.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        exitEportfolioButton.setOnAction(e -> {
            eportfolioFileControll.handleExitRequest();
        });
        ePortfolioEditController = new EportfolioEditController(this);

        addPageButton.setOnAction(e -> {

            ePortfolioEditController.processAddPageRequest();
            pageTabPane.getTabs().add(ePortfolioModel.getSelectedPage().getPageEditView().newTab);
            EportfolioMakerView.this.reloadEportfolioPane();

        });

        removePageButton.setOnAction(e -> {
            ePortfolioEditController.processRemovePageRequest();
            // pageTabPane.getTabs().remove(ePortfolioModel.getSelectedPage().getPageEditView().newTab);
            EportfolioMakerView.this.reloadEportfolioPane();

        });
        selectPageEditorWorkSpaceButton.setOnAction(e -> {
            ePortfolioEditController.selectPageEditWorkSpace();
        });

        addTextButton.setOnAction((ActionEvent e) -> {
            addTextStage = new Stage();
            addText = new AddTextDialogBox(addTextStage);
            addText.showAndWait();
            whatKindText = addText.getSelection();

            if (!whatKindText.equals("") && whatKindText.equals("header")) {
                addHeaderStage = new Stage();
                editHeaderBox = new EditHeaderDialogBox(addHeaderStage);
                editHeaderBox.showAndWait();
                headerString = editHeaderBox.getSelection();
                if (!headerString.equals("") && ePortfolioModel.isPageSelected()) {
                    ePortfolioModel.getSelectedPage().setHeaderString(headerString);
                    EportfolioMakerView.this.reloadEportfolioPane();
                }

            } else if (!whatKindText.equals("") && whatKindText.equals("paragraph")) {
                ParagraphComponent paragraphToAdd = new ParagraphComponent();
                paragraphToAdd.showDialogBox();
                if (paragraphToAdd.isUpdated()) {
                    ePortfolioModel.getSelectedPage().addComponent(paragraphToAdd);
                    EportfolioMakerView.this.reloadEportfolioPane();
                }
            } else if (!whatKindText.equals("") && whatKindText.equals("list")) {
                ListComponent listToAdd = new ListComponent();
                listToAdd.showDialogBox();
                if (listToAdd.isUpdated()) {
                    ePortfolioModel.getSelectedPage().addComponent(listToAdd);
                    EportfolioMakerView.this.reloadEportfolioPane();
                }
            }

        });
        addImageButton.setOnAction(e -> {
            ImageComponent imageToAdd = new ImageComponent();
            imageToAdd.showDialogBox();
            if (imageToAdd.isUpdated()) {
                ePortfolioModel.getSelectedPage().addComponent(imageToAdd);
                this.reloadEportfolioPane();
            }
        });

        addSlideShowButton.setOnAction((ActionEvent e) -> {
            SlideShowComponent slideToAdd = new SlideShowComponent();
            slideToAdd.showDialogBox();
            if (slideToAdd.isUpdated()) {
                ePortfolioModel.getSelectedPage().addComponent(slideToAdd);
                this.reloadEportfolioPane();
            }
        });

        addVideoButton.setOnAction(e -> {
            VideoComponent videoToAdd = new VideoComponent();
            videoToAdd.showDialogBox();
            if (videoToAdd.isUpdated()) {
                ePortfolioModel.getSelectedPage().addComponent(videoToAdd);
                this.reloadEportfolioPane();
            }

        });

        viewThePageButton.setOnAction(vp -> {
            try {
               eportfolioFileControll.handleViewEportfolioRequest();
            } catch (IOException ex) {
                Logger.getLogger(EportfolioMakerView.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        removeComponentButton.setOnAction(e -> {
            if (ePortfolioModel.getSelectedPage().getIfHeaderSelected() == true) {
                ePortfolioModel.getSelectedPage().setHeaderString("");
                this.reloadEportfolioPane();
            } else {
                ePortfolioModel.getSelectedPage().removeSelectedComponent();
                //ePortfolioModel.getSelectedPage().getPageEditView().reloadPageEditView();
                this.reloadEportfolioPane();
            }
        });

    }

    public void initFileToolbar() {
        fileToolbarPane = new FlowPane();
        newEportfolioButton = initChildButton(fileToolbarPane, ICON_NEW_EPORTFOLIO_SHOW, TOOLTIP_NEW_EPORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        loadEportfolioButton = initChildButton(fileToolbarPane, ICON_LOAD_EPORTFOLIO_SHOW, TOOLTIP_LOAD_EPORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        saveEportfolioButton = initChildButton(fileToolbarPane, ICON_SAVE_EPORTFOLIO_SHOW, TOOLTIP_SAVE_EPORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        saveAsEportfolioButton = initChildButton(fileToolbarPane, ICON_SAVE_AS_EPORTFOLIO_SHOW, TOOLTIP_SAVE_AS_EPORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        exportEportfolioButton = initChildButton(fileToolbarPane, ICON_VIEW_EPORTFOLIO_SHOW, TOOLTIP_VIEW_EPORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        exitEportfolioButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }

    private void initWindow() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_ICONS + ICON_WINDOW;
        Image windowIcon = new Image(imagePath);
        primaryStage.getIcons().add(windowIcon);
        // SET THE WINDOW TITLE
        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
        eportfolioPane = new BorderPane();
        eportfolioPane.getStylesheets().add("SSpaneStyle");
        eportfolioPane.setTop(fileToolbarPane);
        primaryScene = new Scene(eportfolioPane);
        primaryScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Roboto+Mono");
        primaryScene.getStylesheets().add("http://fonts.googleapis.com/css?family=Gafata");
        primaryScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Inconsolata");
        primaryScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Share+Tech+Mono");
        primaryScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Nova+Mono");
        primaryScene.getStylesheets().add("https://fonts.googleapis.com/css?family=VT323");
        primaryScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Droid+Sans+Mono");
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(STYLE_SHEET_EPORTFOLIO_UI);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public Button initChildButton(
            Pane toolbar,
            String iconFileName,
            LanguagePropertyType tooltip,
            String cssClass,
            boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_ICONS + iconFileName;
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.getStyleClass().add(cssClass);
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }

    public void addSiteToolBarControls(boolean saved) {
        saveEportfolioButton.setDisable(saved);
        viewEportfolioButton.setDisable(false);
        // AND THE SLIDESHOW EDIT TOOLBAR            
        addPageButton.setDisable(saved);
        removePageButton.setDisable(false);
        selectPageButton.setDisable(true);
    }

    public void updatFileToolBarControls(boolean saved) {

    }

    public void updateSiteTollBarControls(boolean selected) {

    }

    public void reloadEportfolioPane() {
        SiteEditorPane.getChildren().clear();
        ePortfolioModel.getSelectedPage().getPageEditView().reloadPageEditView();
        SiteEditorPane.getChildren().add(ePortfolioModel.getSelectedPage().getPageEditView());
    }

    public AddTextDialogBox getAddTextDialog() {
        return addText;
    }

    public EditHeaderDialogBox getEditHeaderDialog() {
        return editHeaderBox;
    }

    public AddParagraphDialogBox getAddParagraphDialogBox() {
        return addParagraph;
    }

    public AddListDialogBox getAddListDialogBox() {
        return addListBox;
    }

    public AddImageDialogBox getAddImageDialogBox() {
        return addImageBox;
    }

    public AddVideoDialogBox getAddVideoDialogBox() {
        return addVideoBox;
    }

    public TabPane getPageTabPane() {
        return pageTabPane;
    }

    /*public void reloadEportfolioPane() {
     SiteEditorPane.getChildren().clear();
     //SiteEditorToolbar.getChildren().remove(pageTabPane);
     pageTabPane.getTabs().clear();
     //SiteEditorToolbar.getChildren().add(pageTabPane);
     // pageTabPane.setSide(Side.LEFT);
     Tab selectedTab=new Tab();
     for (Page page : ePortfolioModel.getPages()) {
     final PageEditView pageEdit;
     pageEdit = new PageEditView(page, ePortfolioModel);
     if(ePortfolioModel.isSelectedPage(page)){
     selectedTab=pageEdit.newTab;
            
     }
     if (page.getHeaderString() != "") {
     Label headerLabel = new Label(page.getHeaderString());
     pageEdit.headerHbox.getChildren().add(headerLabel);
     headerLabel.setOnMouseClicked(e -> {
     page.setHeaderSelected(true);
     });
     }
     for (Components a : page.getComponents()) {
     VBox sb = a.createUI();
     pageEdit.contentHbox.getChildren().add(sb);
     //pageEdit.getChildren().add(sb);
     sb.setOnMouseClicked(e -> {
     page.setSelectedComponent(a);
                    
     });

     }
     pageTabPane.getTabs().add(pageEdit.newTab);
     pageEdit.newTab.setOnSelectionChanged(e -> {
     ePortfolioModel.setSelectedPage(page);   
     //PageEditView selectPageView =  new PageEditView(ePortfolioModel.getSelectedPage(),ePortfolioModel);
     SiteEditorPane.getChildren().clear();
     SiteEditorPane.getChildren().add( pageEdit );
               
     });
     pageEdit.newTab.setOnClosed(i -> {
     SiteEditorPane.getChildren().remove(pageEdit);
     ePortfolioModel.getPages().remove(page);
     this.reloadEportfolioPane();

     });
     SiteEditorPane.getChildren().add(pageEdit);
     }
     pageTabPane.getSelectionModel().select(selectedTab);

     }
     */
}
