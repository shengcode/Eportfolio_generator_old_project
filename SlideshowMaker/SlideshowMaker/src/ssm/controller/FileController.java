package ssm.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ssm.LanguagePropertyType;
import ssm.model.SlideShowModel;
import ssm.error.ErrorHandler;
import ssm.file.SlideShowFileManager;
import ssm.view.SlideShowMakerView;
import static ssm.StartupConstants.PATH_SLIDE_SHOWS;
import static ssm.file.SlideShowFileManager.SLASH;
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
                // THE USER CAN OPT OUT HERE WITH A CANCEL
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

		// MAKE SURE THE TITLE CONTROLS ARE ENABLED
		ui.reloadTitleControls();		
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
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
            eH.processError(LanguagePropertyType.ERROR_DATA_FILE_LOADING);
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
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
	    return false;
        }
    }

    /**
     * This method shows the current slide show in a separate window.
     */
    public void handleViewSlideShowRequest() {      
       
       String folderName = ui.getSlideShow().getTitle();   
         ui.getSlideShow().getSlides().get(0).getImagePath(); // so you get the image path 
         
             
        // copy the whole template folder       
       Path to = Paths.get("Sites/"+folderName);
       Path from = Paths.get("Sites/template");       
      try{
      Files.walkFileTree(from, new CopyDirVisitor(from,to));       
      } 
      catch (IOException e){
         e.printStackTrace();          
           }  
      // now copy the images to my img folder 
       for (int i = 0; i<ui.getSlideShow().getSlides().size();i++){
             File ImageSource = new File(ui.getSlideShow().getSlides().get(i).getImagePath()+ui.getSlideShow().getSlides().get(i).getImageFileName());
             File ImageDes = new File("Sites/"+folderName+"/img/"+ui.getSlideShow().getSlides().get(i).getImageFileName());  
             try{ 
                 Files.copy(ImageSource.toPath(),
                     ImageDes.toPath());}
             catch(IOException e){
               
             }
         }        
        // copy JASON file      
      File OriJsonFile = new File("data/slide_shows/"+folderName+".JSON");
      File CopyJsonFile = new File("Sites/"+folderName+"/"+folderName+".JSON");
      BufferedReader Jsonreader;
      PrintWriter Jsonwriter;      
      String JsonFileLine; // over write the 
      try{
         if (CopyJsonFile.createNewFile()||!CopyJsonFile.createNewFile()){
          Jsonreader = new BufferedReader(new FileReader(OriJsonFile));
          Jsonwriter = new PrintWriter(new FileWriter(CopyJsonFile));
          while((JsonFileLine=Jsonreader.readLine())!=null){
           Jsonwriter.println(JsonFileLine);
          }
         Jsonreader.close();
         Jsonwriter.close();
         }
      }
      catch(IOException ioEx){
      System.err.println("I could not copy the file to a destination");
      }
     // change the content in the HTML file
      /*ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        try {
            FileReader fr = new FileReader("Sites/" + folderName + "/index.html");
            BufferedReader br = new BufferedReader(fr);
            
            while ((line = br.readLine()) != null) {
                if (line.contains("Slideshow.js")) {
                    line = line.replaceAll("Slideshow.js", folderName+ ".js");
                }
                lines.add(line);
              //  bw.write(line);
            }  //end if                 
            br.close();
            fr.close();
            FileWriter fw = new FileWriter("Sites/" + folderName + "/index.html");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < lines.size(); ++i) {
                bw.write(lines.get(i)+"\n");
               // bw.write(line);
            }
            bw.close();
            fw.close();
        }     //end try
        catch (Exception e) {

        }      */
      // change the content in the javascript file 
        ArrayList<String> JavaScriptlines = new ArrayList<String>();
        String javaScriptline = null;
        try {
            FileReader fr = new FileReader("Sites/" + folderName + "/js/Slideshow.js");
            BufferedReader br = new BufferedReader(fr);
            
            while ((javaScriptline = br.readLine()) != null) {
                if (javaScriptline.contains("SlideShow.json")) {
                    javaScriptline = javaScriptline.replaceAll("SlideShow.json", folderName+ ".json");
                }
                JavaScriptlines.add(javaScriptline);
              //  bw.write(line);
            }  //end if                 
            br.close();
            fr.close();
            FileWriter fw = new FileWriter("Sites/" + folderName + "/js/Slideshow.js");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < JavaScriptlines.size(); ++i) {
                bw.write(JavaScriptlines.get(i)+"\n");
                   }
            bw.close();
            fw.close();
        }     //end try
        catch (Exception e) { 
           
        }   
      
        File newFile = new File("Sites/"+ folderName+"/index.html");
        WebView newWebview = new WebView();
        WebEngine newengine = newWebview.getEngine();
        newengine.load(newFile.toURI().toString());
        newengine.setJavaScriptEnabled(true);
        Stage stage = new Stage();
        Scene scene;
        stage.setTitle("web view");
        scene = new Scene(newWebview,750,500,Color.web("#666970"));	
         stage.setScene(scene);
         stage.show();           
         
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
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
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
        boolean saveWork = true; 

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (saveWork) {
            SlideShowModel slideShow = ui.getSlideShow();
            slideShowIO.saveSlideShow(slideShow);
            saved = true;
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (!true) {
            return false;
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
                ui.reloadSlideShowPane();
                saved = true;
                ui.updateToolbarControls(saved);
            } catch (Exception e) {
                ErrorHandler eH = ui.getErrorHandler();
		eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
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
  class CopyDirVisitor extends SimpleFileVisitor<Path> {
    private Path fromPath;
    private Path toPath;
    private StandardCopyOption copyOption = StandardCopyOption.REPLACE_EXISTING;
    
    public CopyDirVisitor(Path myFromPath, Path myToPath){
      fromPath = myFromPath;
      toPath=myToPath;
    
    }
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path targetPath = toPath.resolve(fromPath.relativize(dir));
        if(!Files.exists(targetPath)){
            Files.createDirectory(targetPath);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.copy(file, toPath.resolve(fromPath.relativize(file)), copyOption);
        return FileVisitResult.CONTINUE;
    }
}
