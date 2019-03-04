package ssm.controller;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import static ssm.LanguagePropertyType.CANCEL_STRING;
import static ssm.LanguagePropertyType.CHOOSE_SAVE;
import static ssm.LanguagePropertyType.FILE_NOT_SAVED;
import static ssm.LanguagePropertyType.NOT_ABLE_TO_LOAD_SLIDE;
import static ssm.LanguagePropertyType.NOT_ABLE_TO_OPEN;
import static ssm.LanguagePropertyType.NOT_ABLE_TO_SAVE;
import static ssm.LanguagePropertyType.NOT_ABLE_TO_SAVE_BEFORE_EXIT;
import static ssm.LanguagePropertyType.NO_STRING;
import static ssm.LanguagePropertyType.SAVE_CONFIRMATION;
import static ssm.LanguagePropertyType.SLIDE_FILE_CREATED;
import static ssm.LanguagePropertyType.SLIDE_FILE_NOT_CREATED;
import static ssm.LanguagePropertyType.WANT_TO_SAVE;
import static ssm.LanguagePropertyType.YES_STRING;
import ssm.model.SlideShowModel;
import ssm.error.ErrorHandler;
import ssm.file.SlideShowFileManager;
import ssm.view.SlideShowMakerView;
import static ssm.StartupConstants.PATH_SLIDE_SHOWS;
/**
 * This class serves as the controller for all file toolbar operations,
 * driving the loading and saving of slide shows, among other things.
 * 
 * @author McKilla Gorilla & _____________
 */
public class FileController {
    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;
    // THE APP UI
    private SlideShowMakerView ui;    
    // THIS GUY KNOWS HOW TO READ AND WRITE SLIDE SHOW DATA
    private SlideShowFileManager slideShowIO;    
    private boolean saveWork;
    /**
     * This default constructor starts the program without a slide show file being
     * edited.
     *
     * @param initSlideShowIO The object that will be reading and writing slide show
     * data.
     */
  
    
    public FileController(SlideShowMakerView initUI, SlideShowFileManager initSlideShowIO) {
        // NOTHING YET
        saved = true;
	ui = initUI;
        slideShowIO = initSlideShowIO;
    }
    
    public void markAsEdited() {
        saved = false;
        ui.updateToolbarControls(saved);
    }
    /**
     * This method starts the process of editing a new slide show. If a pose is
     * already being edited, it will prompt the user to save it first.
     */
    public void handleNewSlideShowRequest() {        
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL in the prompt save method
                continueToMakeNew = promptToSave();         
           
            }
            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
                     
            
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                SlideShowModel slideShow = ui.getSlideShow();
		slideShow.reset();
                saved = false;
                 // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                ui.updateToolbarControls(saved);
                ui.reloadSlideShowPane(slideShow);
                // TELL THE USER THE SLIDE SHOW HAS BEEN CREATED
                // @todo
                 ErrorHandler eH = ui.getErrorHandler();                 
                 PropertiesManager props = PropertiesManager.getPropertiesManager();
                 String slideSaved = props.getProperty(SLIDE_FILE_CREATED.toString()); 
                   eH.processError(SLIDE_FILE_CREATED, slideSaved, slideSaved);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();
              PropertiesManager props = PropertiesManager.getPropertiesManager();
                 String slideNotSaved = props.getProperty(SLIDE_FILE_NOT_CREATED.toString()); 
                   eH.processError(SLIDE_FILE_NOT_CREATED, slideNotSaved, slideNotSaved);
            
            
        }
    }
    /**
     * This method lets the user open a slideshow saved to a file. It will also
     * make sure data for the current slideshow is not lost.
     */
    public void handleLoadSlideShowRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }
            // IF THE USER REALLY WANTS TO OPEN A POSE
            if (continueToOpen) {
                // GO AHEAD AND PROCEED MAKING A NEW POSE
                promptToOpen();
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();            
                  PropertiesManager props = PropertiesManager.getPropertiesManager();
                 String notAbleToLoadSlide = props.getProperty(NOT_ABLE_TO_LOAD_SLIDE.toString());                 
                 ui.getErrorHandler().processError(LanguagePropertyType.NOT_ABLE_TO_LOAD_SLIDE, notAbleToLoadSlide, notAbleToLoadSlide);           
            
            
           
        }
    }

    /**
     * This method will save the current slideshow to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     */
    public boolean handleSaveSlideShowRequest() {
        try {
	    // GET THE SLIDE SHOW TO SAVE
	    SlideShowModel slideShowToSave = ui.getSlideShow();	 
             // SAVE IT TO A FILE
            slideShowIO.saveSlideShow(slideShowToSave);
            // MARK IT AS SAVED
            saved = true;
            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            ui.updateToolbarControls(saved);
            
            
	    return true;
        } catch (IOException ioe) {            
            ErrorHandler eH = ui.getErrorHandler();
                PropertiesManager props = PropertiesManager.getPropertiesManager();
            String notAbleToSave= props.getProperty(NOT_ABLE_TO_SAVE.toString()); 
             eH.processError(NOT_ABLE_TO_SAVE, notAbleToSave, notAbleToSave);
	    return false;
        }
    }

     /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     */    
    
    public void handleExitRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }
            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();            
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String notAbleToSavebeforeExit= props.getProperty(NOT_ABLE_TO_SAVE_BEFORE_EXIT.toString()); 
             eH.processError(NOT_ABLE_TO_SAVE_BEFORE_EXIT, notAbleToSavebeforeExit,notAbleToSavebeforeExit);           
               
        }
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating a new
     * pose, or opening another pose, or exiting. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is retuned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave() throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        // @todo change this to prompt
        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String yesString = props.getProperty(YES_STRING.toString());  
        String noString = props.getProperty(NO_STRING.toString());  
        String cancelString = props.getProperty(CANCEL_STRING.toString());
        String saveConfirmation = props.getProperty(SAVE_CONFIRMATION.toString());
        String wantToSave = props.getProperty(WANT_TO_SAVE.toString());
       String chooseSave = props.getProperty(CHOOSE_SAVE.toString());
         Alert saveChoiceAlert = new Alert(AlertType.CONFIRMATION);
       saveChoiceAlert.setTitle(saveConfirmation);
       saveChoiceAlert.setHeaderText(wantToSave);
       saveChoiceAlert.setContentText(chooseSave);
               
          ButtonType yesButton = new ButtonType(yesString);            
          ButtonType noButton = new ButtonType(noString);
          ButtonType cancelButton = new ButtonType(cancelString);
          saveChoiceAlert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
          Optional<ButtonType> result = saveChoiceAlert.showAndWait();
       if (result.get() == yesButton){
             saveWork=true;
      } else if (result.get() == noButton) {
           saveWork=false;
         } else if (result.get() == cancelButton) {
            saveWork=false; 
            saveChoiceAlert.close();
           
      }       
        
        
        if (saveWork) {
            SlideShowModel slideShow = ui.getSlideShow();
            slideShowIO.saveSlideShow(slideShow);
            saved = true;
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (!saveWork) {
            saved=false;
             // pop up a window to say that it is not saved 
           String fileNotSaved = props.getProperty(FILE_NOT_SAVED.toString());    
             Alert notSavedalert = new Alert(AlertType.INFORMATION);
             notSavedalert.setTitle(fileNotSaved);
            notSavedalert.setHeaderText(fileNotSaved);
            notSavedalert.setContentText(fileNotSaved);
           notSavedalert.showAndWait();
  
        }
        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser slideShowFileChooser = new FileChooser();
        slideShowFileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOWS));
        File selectedFile = slideShowFileChooser.showOpenDialog(ui.getWindow());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
		SlideShowModel slideShowToLoad = ui.getSlideShow();
                slideShowIO.loadSlideShow(slideShowToLoad, selectedFile.getAbsolutePath());
                ui.reloadSlideShowPane(slideShowToLoad);
                saved = false;
                ui.updateToolbarControls(saved);
            } catch (Exception e) {
                ErrorHandler eH = ui.getErrorHandler();
                //eH.processError()
                // @todo
                  PropertiesManager props = PropertiesManager.getPropertiesManager();
                 String notAbleToOpen= props.getProperty(NOT_ABLE_TO_OPEN.toString()); 
                eH.processError(NOT_ABLE_TO_OPEN, notAbleToOpen, notAbleToOpen);
                    }
        }
    }

    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the pose is changed in some way.
     */
    public void markFileAsNotSaved() {
        saved = false;
    }
    /**
     * Accessor method for checking to see if the current pose has been saved
     * since it was last editing. If the current file matches the pose data,
     * we'll return true, otherwise false.
     *
     * @return true if the current pose is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }
}

