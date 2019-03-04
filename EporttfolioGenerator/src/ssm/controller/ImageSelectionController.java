package ssm.controller;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import static ssm.LanguagePropertyType.IMAGE_DOSE_NOT_EXISTS;
import static ssm.StartupConstants.PATH_EPORTFOLIO_IMAGES;
import static ssm.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import ssm.model.Page;
import ssm.model.Slide;
import ssm.view.PageEditView;
import ssm.view.SlideEditView;

/**
 * This controller provides a controller for when the user chooses to
 * select an image for the slide show.
 * 
 * @author McKilla Gorilla & _____________
 */
public class ImageSelectionController {    
    /**
     * Default contstructor doesn't need to initialize anything
     */
    public ImageSelectionController() {    }
    
    /**
     * This function provides the response to the user's request to
     * select an image.
     * 
     * @param slideToEdit - Slide for which the user is selecting an image.
     * 
     * @param view The user interface control group where the image
     * will appear after selection.
     */
    public void processSelectImage(Slide slideToEdit, SlideEditView view) {
	FileChooser imageFileChooser = new FileChooser();	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOW_IMAGES));	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);	
        if (file != null ) {
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
	    slideToEdit.setImage(path, fileName);
	    view.updateSlideImage();
	}	    
	else {
            
            PropertiesManager props = PropertiesManager.getPropertiesManager();
           String ImagedoseNotExist = props.getProperty(IMAGE_DOSE_NOT_EXISTS.toString());
              
            Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle(ImagedoseNotExist);
           alert.setHeaderText(ImagedoseNotExist);
          alert.setContentText(ImagedoseNotExist);
           alert.showAndWait();        
                
            
	}
    }
    
     public void processSelectBannerImageFive(Page pageToEdit, PageEditView view) {
	FileChooser imageFileChooser = new FileChooser();	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File(PATH_EPORTFOLIO_IMAGES));	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);	
        if (file != null ) {
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
	    pageToEdit.setBannerImage(path, fileName);
	    view.updateBannerImage();
	}	    
	else {
            
            PropertiesManager props = PropertiesManager.getPropertiesManager();
           String ImagedoseNotExist = props.getProperty(IMAGE_DOSE_NOT_EXISTS.toString());
              
            Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle(ImagedoseNotExist);
           alert.setHeaderText(ImagedoseNotExist);
          alert.setContentText(ImagedoseNotExist);
           alert.showAndWait();        
                
            
	}
    }
    
}
