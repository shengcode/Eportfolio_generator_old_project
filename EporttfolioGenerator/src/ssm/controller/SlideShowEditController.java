package ssm.controller;
import properties_manager.PropertiesManager;
import static ssm.LanguagePropertyType.DEFAULT_IMAGE_CAPTION;
import static ssm.StartupConstants.DEFAULT_SLIDE_IMAGE;
import static ssm.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import ssm.model.Slide;
import ssm.model.SlideShowModel;
import ssm.view.SlideShowMakerView;
/**
 * This controller provides responses for the slideshow edit toolbar,
 * which allows the user to add, remove, and reorder slides.
 * 
 * @author McKilla Gorilla & _____________
 */
public class SlideShowEditController {
    // APP UI
    private SlideShowMakerView ui;   
    /**
     * This constructor keeps the UI for later.
     */
    public SlideShowEditController(SlideShowMakerView initUI) {
	ui = initUI;
    }    
    /**
     * Provides a response for when the user wishes to add a new
     * slide to the slide show.
     */
    public void processAddSlideRequest() {
        // add that ? image 
	SlideShowModel slideShow = ui.getSlideShow();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	slideShow.addSlide(DEFAULT_SLIDE_IMAGE, PATH_SLIDE_SHOW_IMAGES,"");     
                     
    }
    
    public void processRemoveSlideRequest(){      
      SlideShowModel slideShow = ui.getSlideShow();
      Slide slideToRemove = slideShow.getSelectedSlide();
      slideShow.removeSlide(slideToRemove);     
    }
    public void processMoveUpSlideRequest(){       
    SlideShowModel slideShow = ui.getSlideShow();
    Slide slideToMoveUp = slideShow.getSelectedSlide();
    slideShow.moveUpSlide(slideToMoveUp);
    }    
    public void processMoveDownSlideRequest(){
    SlideShowModel slideShow = ui.getSlideShow();
    Slide slideToMoveDown = slideShow.getSelectedSlide();
    slideShow.moveDownSlide(slideToMoveDown);
    }
}
