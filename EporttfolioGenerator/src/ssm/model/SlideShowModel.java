package ssm.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import ssm.view.SlideEditView;
import ssm.view.SlideShowMakerView;
/**
 * This class manages all the data associated with a slideshow.
 * 
 * @author McKilla Gorilla & _____________
 */
public class SlideShowModel {    
    SlideShowMakerView ui;
    String title;
    ObservableList<Slide> slides;
    Slide selectedSlide;
   
   
    public SlideShowModel(SlideShowMakerView initUI) {
	ui = initUI;
	slides = FXCollections.observableArrayList();
	reset();	
    }
    // ACCESSOR METHODS
    
    
    public boolean isSlideSelected() {
	return selectedSlide != null;
    }    
    public ObservableList<Slide> getSlides() {
	return slides;        
    }    
    public void setSlides(ObservableList<Slide> initSlide){
      slides=initSlide;
    }
    public Slide getSelectedSlide() {
	return selectedSlide;
    }
    public String getTitle() { 
	return title; 
    }    
    // MUTATOR METHODS
    public void setSelectedSlide(Slide initSelectedSlide) {
	selectedSlide = initSelectedSlide;
        
        for (Slide s : this.getSlides()) {
                    s.getSlideEditView().setEffect(null);
                }
        DropShadow ds = new DropShadow(20, Color.RED);
        selectedSlide.getSlideEditView().setEffect(ds);
        boolean check1=false;
        boolean check2=false;
        boolean check3=false;
        
        if(slides.indexOf(getSelectedSlide())==0 ){
            check2 = true;        
        }
        else if (slides.indexOf(getSelectedSlide())==slides.size()-1){
            check3=true;
        }
        if(getSelectedSlide()==null){
            check1=true;
            check2=true;
            check3=true;
        }
       ui.updateButton(check1,check2,check3);     
     }
        
        
     
    
    public void setTitle(String initTitle) { 
	title = initTitle; 
    }    
    // SERVICE METHODS    
    /**
     * Resets the slide show to have no slides and a default title.
     */
    public void reset() {
	slides.clear();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	title = props.getProperty(LanguagePropertyType.DEFAULT_SLIDE_SHOW_TITLE);
	selectedSlide = null;
    }
    /**
     * Adds a slide to the slide show with the parameter settings.
     * @param initImageFileName File name of the slide image to add.
     * @param initImagePath File path for the slide image to add.
     */
    public void addSlide(   String initImageFileName,
			    String initImagePath,String initCaption) {
	
        Slide slideToAdd = new Slide(initImageFileName, initImagePath,initCaption );
	slides.add(slideToAdd);
	selectedSlide = slideToAdd;
	ui.reloadSlideShowPane(this);
    }   
    // @shengchun liu 
    public void removeSlide(Slide selectedSlide){  
        slides.remove(selectedSlide);
      ui.reloadSlideShowPane(this);
    }
    public void moveUpSlide(Slide selectedSlide){        
      int a = slides.indexOf(selectedSlide);
      if (a != 0){
        slides.remove(selectedSlide);
        a--;
        slides.add(a, selectedSlide);
        ui.reloadSlideShowPane(this);  
      } 
       setSelectedSlide(selectedSlide);
    }
    public void moveDownSlide(Slide selectedSlide){       
      int a = slides.indexOf(selectedSlide);
       if (a!=slides.size()-1){
      Slide downSlide = slides.get(a+1);
      slides.set(a+1, selectedSlide);
      slides.set(a, downSlide);
        ui.reloadSlideShowPane(this);
        }
       setSelectedSlide(selectedSlide);       
    }    
    
}