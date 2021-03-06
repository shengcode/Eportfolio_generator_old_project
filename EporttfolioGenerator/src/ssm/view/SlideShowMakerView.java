package ssm.view;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
import static ssm.LanguagePropertyType.PROMPT_MESSAGE;
import static ssm.LanguagePropertyType.TOOLTIP_ADD_SLIDE;
import static ssm.LanguagePropertyType.TOOLTIP_EXIT;
import static ssm.LanguagePropertyType.TOOLTIP_LOAD_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static ssm.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static ssm.LanguagePropertyType.TOOLTIP_NEW_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_REMOVE_SLIDE;
import static ssm.LanguagePropertyType.TOOLTIP_SAVE_SLIDE_SHOW;
import static ssm.LanguagePropertyType.TOOLTIP_VIEW_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_WINDOW;
import static ssm.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static ssm.StartupConstants.CSS_CLASS_SLIDE_SHOW_EDIT_VBOX;
import static ssm.StartupConstants.ICON_ADD_SLIDE;
import static ssm.StartupConstants.ICON_EXIT;
import static ssm.StartupConstants.ICON_LOAD_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_MOVE_DOWN;
import static ssm.StartupConstants.ICON_MOVE_UP;
import static ssm.StartupConstants.ICON_NEW_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_REMOVE_SLIDE;
import static ssm.StartupConstants.ICON_SAVE_SLIDE_SHOW;
import static ssm.StartupConstants.ICON_VIEW_SLIDE_SHOW;
import static ssm.StartupConstants.PATH_ICONS;
import static ssm.StartupConstants.STYLE_SHEET_SLIDESHOW_UI;
import ssm.controller.FileController;
import ssm.controller.SlideShowEditController;
import ssm.model.Slide;
import ssm.model.SlideShowModel;
import ssm.error.ErrorHandler;
import ssm.file.SlideShowFileManager;

/**
 * This class provides the User Interface for this application, providing
 * controls and the entry points for creating, loading, saving, editing, and
 * viewing slide shows.
 *
 * @author McKilla Gorilla & _____________
 */
public class SlideShowMakerView {

    Stage primaryStage;
    Stage newStage;
    Scene primaryScene;
    Scene newScene;
    BorderPane ssmPane;
    BorderPane newPane;
    FlowPane fileToolbarPane;
    Button newSlideShowButton;
    Button loadSlideShowButton;
    Button saveSlideShowButton;
    Button viewSlideShowButton;
    Button exitButton;
    Button removeSlideButton;
    Button moveUpSlideButton;
    Button moveDownSlideButton;
    VBox controlVbox;
    GridPane slideGridPane;
    Button submitButton;
    Button noButton;
    Button cancelButton;
    HBox buttonBox;
    Double slideHeightValue = 0.0;
    Double slideWidthValue = 0.0;

    // WORKSPACE
    HBox workspace;
    // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN    
    VBox slideEditToolbar;
    Button addSlideButton;
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Label slideTitle;
    TextField slideTitleTextField = new TextField();
    // AND THIS WILL GO IN THE CENTER
    ScrollPane slidesEditorScrollPane;
    VBox slidesEditorPane;
    // THIS IS THE SLIDE SHOW WE'RE WORKING WITH
    SlideShowModel slideShow;
    // THIS IS FOR SAVING AND LOADING SLIDE SHOWS
    SlideShowFileManager fileManager;
    // THIS CLASS WILL HANDLE ALL ERRORS FOR THIS PROGRAM
    private ErrorHandler errorHandler;
    // THIS CONTROLLER WILL ROUTE THE PROPER RESPONSES
    // ASSOCIATED WITH THE FILE TOOLBAR
    private FileController fileController;
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    private SlideShowEditController editController;

    ObservableList<Slide> submittedSlides = FXCollections.observableArrayList();
    String getSubmittedTitle = "Default";
    TextField slideWidth = new TextField();
    TextField slideHeight = new TextField();
    boolean updated = false;

    public boolean isUpdated() {
        return updated;
    }

    /**
     * Default constructor, it initializes the GUI for use, but does not yet
     * load all the language-dependent controls, that needs to be done via the
     * startUI method after the user has selected a language.
     */
    public SlideShowMakerView(SlideShowFileManager initFileManager) {
        // FIRST HOLD ONTO THE FILE MANAGER
        fileManager = initFileManager;
        // MAKE THE DATA MANAGING MODEL
        slideShow = new SlideShowModel(this);
        // WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
        errorHandler = new ErrorHandler(this);
        //this.getStyleClass().add(CSS_CLASS_SLIDESHOW_MAKER_VIEW);	
    }

    // ACCESSOR METHODS
    public SlideShowModel getSlideShow() {
        return slideShow;
    }

    public Stage getWindow() {
        return primaryStage;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    /**
     * Initializes the UI controls and gets it rolling.
     *
     * @param initPrimaryStage The window for this application.
     *
     * @param windowTitle The title for this window.
     */
    public void startUI(Stage initPrimaryStage, String windowTitle) {
        // THE TOOLBAR ALONG THE NORTH
        initFileToolbar();
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();
        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        // KEEP THE WINDOW FOR LATER                  
        primaryStage = initPrimaryStage;
        initWindow(windowTitle);
    }

    // UI SETUP HELPER METHODS
    private void initWorkspace() {
        // FIRST THE WORKSPACE ITSELF, WHICH WILL CONTAIN TWO REGIONS
        workspace = new HBox();
        workspace.getStyleClass().add("workspace_hbox");
        // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
        slideEditToolbar = new VBox();
        slideEditToolbar.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
        addSlideButton = this.initChildButton(slideEditToolbar, ICON_ADD_SLIDE, TOOLTIP_ADD_SLIDE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        removeSlideButton = initChildButton(slideEditToolbar, ICON_REMOVE_SLIDE, TOOLTIP_REMOVE_SLIDE, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        moveUpSlideButton = initChildButton(slideEditToolbar, ICON_MOVE_UP, TOOLTIP_MOVE_UP, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        moveDownSlideButton = initChildButton(slideEditToolbar, ICON_MOVE_DOWN, TOOLTIP_MOVE_DOWN, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);

        //slideEditToolbar.getChildren().add(cancelButton);
        slideGridPane = new GridPane();
        slideGridPane.setPadding(new Insets(10, 10, 10, 10));
        slideGridPane.setVgap(8);
        slideGridPane.setHgap(10);
        Label WidthLabel = new Label("slide Width");
        // TextField slideWidth = new TextField();
        Label HeightLabel = new Label("slide Height");
        // TextField slideHeight = new TextField();                      
        GridPane.setConstraints(WidthLabel, 0, 0);
        GridPane.setConstraints(slideWidth, 1, 0);
        GridPane.setConstraints(HeightLabel, 0, 1);
        GridPane.setConstraints(slideHeight, 1, 1);
        slideGridPane.getChildren().addAll(WidthLabel, slideWidth, HeightLabel, slideHeight);
        slideGridPane.setAlignment(Pos.CENTER);
        slideGridPane.getStylesheets().add("gridpane");
        WidthLabel.getStyleClass().add("label");
        // slideWidth.getStyleClass().add("vertical_toolbar_button");
        HeightLabel.getStyleClass().add("label");
        // slideHeight.getStyleClass().add("vertical_toolbar_button");
        submitButton = new Button("submit");
        cancelButton = new Button("cancel");
        // slideEditToolbar.getChildren().add(submitButton);

        submitButton.setOnAction(e -> {
            submittedSlides = slideShow.getSlides();
            // slideShow.getSlides().get(0).getSlideEditView().captionTextField.getText();
            for (int i = 0; i < slideShow.getSlides().size(); i++) {
                slideShow.getSlides().get(i).setCaption(slideShow.getSlides().get(i).getSlideEditView().captionTextField.getText());
            }
            slideShow.setTitle(slideTitleTextField.getText());
            slideHeightValue = Double.parseDouble(slideHeight.getText());
            slideWidthValue = Double.parseDouble(slideWidth.getText());
            primaryStage.hide();
            updated = true;
        });
        cancelButton.setOnAction(e -> {
            updated = false;
            primaryStage.hide();
        });
        controlVbox = new VBox();
        buttonBox = new HBox();
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        controlVbox.getChildren().addAll(slideGridPane, buttonBox);
        slideTitle = new Label(props.getProperty(LanguagePropertyType.LABEL_TITLE));
// AND THIS WILL GO IN THE CENTER
        slidesEditorPane = new VBox();
        slidesEditorScrollPane = new ScrollPane(slidesEditorPane); //@@@@@@@@@@@@@@@@@@@@@@@
        // NOW PUT THESE TWO IN THE WORKSPACE
        workspace.getChildren().add(slideEditToolbar);
        workspace.getChildren().add(slidesEditorScrollPane);
        workspace.getChildren().add(slideTitle);
        workspace.getChildren().add(slideTitleTextField);

    }

    private void initEventHandlers() {
        // FIRST THE FILE CONTROLS
        fileController = new FileController(this, fileManager);

        newSlideShowButton.setOnAction(e -> {
            fileController.handleNewSlideShowRequest();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String promptMessage = props.getProperty(PROMPT_MESSAGE.toString());
            slideTitleTextField.getStyleClass().add("textfield");
            slideTitleTextField.setPromptText(promptMessage);

        });

        loadSlideShowButton.setOnAction(e -> {
            fileController.handleLoadSlideShowRequest();
            slideTitleTextField.setText(slideShow.getTitle());

        });
        // THEN THE SLIDE SHOW EDIT CONTROLS
        editController = new SlideShowEditController(this);

        addSlideButton.setOnAction(e -> {
            editController.processAddSlideRequest();
        });

        saveSlideShowButton.setOnAction(e -> {
            slideShow.setTitle(slideTitleTextField.getText());
            primaryStage.setTitle(slideTitleTextField.getText());
            fileController.handleSaveSlideShowRequest();

        });
        removeSlideButton.setOnAction(e -> {
            editController.processRemoveSlideRequest();

        });
        moveUpSlideButton.setOnAction(e -> {
            moveDownSlideButton.setDisable(false);
            editController.processMoveUpSlideRequest();

        });
        moveDownSlideButton.setOnAction(e -> {
            moveUpSlideButton.setDisable(false);
            editController.processMoveDownSlideRequest();

        });
        viewSlideShowButton.setOnAction(e -> {
            String myPathpart1 = "file:///C:/Users/shengchun/Documents/NetBeansProjects/SlideshowMaker/Sites/";
            String myPathpart2 = slideShow.getTitle();

            File newFile = new File("data/slide_shows/index.html");
            //File newFile= new File("Sites/UtahTrip/index.html");
            WebView newWebview = new WebView();
            WebEngine newengine = newWebview.getEngine();
            newengine.load(newFile.toURI().toString());
            newengine.setJavaScriptEnabled(true);
            //webEngine.setJava
            Stage stage = new Stage();
            Scene scene;
            stage.setTitle("Web View");
            scene = new Scene(newWebview, 750, 500, Color.web("#666970"));
            stage.setScene(scene);
            stage.show();

        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest();
        });
    }

    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
        fileToolbarPane = new FlowPane();
        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        newSlideShowButton = initChildButton(fileToolbarPane, ICON_NEW_SLIDE_SHOW, TOOLTIP_NEW_SLIDE_SHOW, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        loadSlideShowButton = initChildButton(fileToolbarPane, ICON_LOAD_SLIDE_SHOW, TOOLTIP_LOAD_SLIDE_SHOW, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        saveSlideShowButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW, TOOLTIP_SAVE_SLIDE_SHOW, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        viewSlideShowButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW, TOOLTIP_VIEW_SLIDE_SHOW, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }

    private void initWindow(String windowTitle) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_ICONS + ICON_WINDOW;
        Image windowIcon = new Image(imagePath);
        primaryStage.getIcons().add(windowIcon);
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);
        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
        ssmPane = new BorderPane();
        ssmPane.setCenter(workspace);
        ssmPane.setRight(controlVbox);
        //ssmPane.setTop(fileToolbarPane);
        primaryScene = new Scene(ssmPane);
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(STYLE_SHEET_SLIDESHOW_UI);
        primaryStage.setScene(primaryScene);
        // primaryStage.show();
    }

    /**
     * This helps initialize buttons in a toolbar, constructing a custom button
     * with a customly provided icon and tooltip, adding it to the provided
     * toolbar pane, and then returning it.
     *
     * @param toolbar
     * @param iconFileName
     * @param tooltip
     * @param cssClass
     * @param disabled
     * @return
     */
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

    /**
     * Updates the enabled/disabled status of all toolbar buttons.
     *
     * @param saved
     */
    public void updateToolbarControls(boolean saved) {
        // FIRST MAKE SURE THE WORKSPACE IS THERE
        ssmPane.setCenter(workspace);
        // NEXT ENABLE/DISABLE BUTTONS AS NEEDED IN THE FILE TOOLBAR
        saveSlideShowButton.setDisable(saved);
        viewSlideShowButton.setDisable(false);
        // AND THE SLIDESHOW EDIT TOOLBAR            
        addSlideButton.setDisable(saved);
        removeSlideButton.setDisable(false);
        moveUpSlideButton.setDisable(false);
        moveDownSlideButton.setDisable(false);
    }

    public void updateButton(boolean check1, boolean check2, boolean check3) {
        removeSlideButton.setDisable(check1);
        moveUpSlideButton.setDisable(check2);
        moveDownSlideButton.setDisable(check3);

    }

    /**
     * Uses the slide show data to reload all the components for slide editing.
     *
     *
     * @param slideShowToLoad SLide show being reloaded.
     */
    public void reloadSlideShowPane(SlideShowModel slideShowToLoad) {
        slidesEditorPane.getChildren().clear();
        for (Slide slide : slideShowToLoad.getSlides()) {
            SlideEditView slideEditor = new SlideEditView(slide, slideShowToLoad);
            if (slideShow.getSlides().isEmpty()) {
                removeSlideButton.setDisable(true);
                moveUpSlideButton.setDisable(true);
                moveDownSlideButton.setDisable(true);
            }

            slideEditor.setOnMouseClicked(e -> {
                slideShow.setSelectedSlide(slide);
            });
            slide.setSlideEditView(slideEditor);
            slideEditor.captionTextField.setText(slide.getCatption());
            slidesEditorPane.getChildren().add(slideEditor);
        }
    }

    public ObservableList<Slide> getSlides() {
        return slideShow.getSlides();
    }

    public double getSlideHeight() {
        return slideHeightValue;
    }

    public double getSlideWidth() {
        return slideWidthValue;
    }

    public String getSlideTitle() {
        return slideShow.getTitle();
    }

    public void setSlides(ObservableList<Slide> initSlides) {
        slideShow.setSlides(initSlides);
        this.reloadSlideShowPane(slideShow);
    }

    public void setTitle(String titleS) {
        slideShow.setTitle(titleS);
        slideTitleTextField.setText(titleS);
    }

    public void setSlideHeight(String heightS) {
        slideHeight.setText(heightS);

    }

    public void setSlideWidth(String widthS) {
        slideWidth.setText(widthS);
    }

}
