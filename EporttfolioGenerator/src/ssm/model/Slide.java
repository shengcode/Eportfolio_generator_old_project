package ssm.model;

import javax.json.Json;
import javax.json.JsonObject;
import ssm.view.SlideEditView;
import ssm.view.SlideShowMakerView;

/**
 * This class represents a single slide in a slide show.
 *
 * @author McKilla Gorilla & _____________
 */
public class Slide {

    String imageFileName;
    String imagePath;
    String caption;
    SlideEditView slideEdit;

    /**
     * Constructor, it initializes all slide data.
     *
     * @param initImageFileName File name of the image.
     *
     * @param initImagePath File path for the image. *
     */
    public Slide(String initImageFileName, String initImagePath) {
        imageFileName = initImageFileName;
        imagePath = initImagePath;
        caption = "";
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("caption", caption)
                .add("imageFileName", imageFileName)
                .add("imagePath", imagePath)
                .build();        
    }
    
    public static Slide fromJSON(JsonObject obj) {
        return new Slide (
                obj.getString("imageFileName"),
                obj.getString("imagePath"),
                obj.getString("caption"));               
    }

    public Slide(String initImageFileName, String initImagePath, String initCaption) {
        imageFileName = initImageFileName;
        imagePath = initImagePath;
        caption = initCaption;
    }

    // ACCESSOR METHODS

    public String getImageFileName() {
        return imageFileName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getCatption() {
        return caption;
    }

    // MUTATOR METHODS

    public void setImage(String initPath, String initFileName) {
        imagePath = initPath;
        imageFileName = initFileName;
    }

    public void setImageFileName(String initImageFileName) {
        imageFileName = initImageFileName;
    }

    public void setImagePath(String initImagePath) {
        imagePath = initImagePath;
    }

    public void setCaption(String initCaption) {
        caption = initCaption;
    }

    public SlideEditView getSlideEditView() {
        return slideEdit;

    }
    public void setSlideEditView(SlideEditView slideShowMakerView) {
        this.slideEdit = slideShowMakerView;
    }

//    @Override
//    public boolean equals(Object o) {
//        if(o instanceof Slide){
//            Slide s = (Slide)o;
//            if(s.imagePath.equals(this.imagePath)
//                    && s.imageName.equals(this.imageName)){
//                
//            }
//        }
//        return super.equals(o); //To change body of generated methods, choose Tools | Templates.
//    }
}
