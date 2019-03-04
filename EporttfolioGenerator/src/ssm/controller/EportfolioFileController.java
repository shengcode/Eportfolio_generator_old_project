package ssm.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import static ssm.StartupConstants.PATH_SLIDE_SHOWS;
import ssm.error.EportfolioErrorHandler;
import ssm.file.EportfolioFileManager;
import static ssm.file.SlideShowFileManager.JSON_EXT;
import static ssm.file.SlideShowFileManager.SLASH;
import ssm.model.Components;
import ssm.model.EportfolioModel;
import ssm.model.ImageComponent;
import ssm.model.Page;
import ssm.model.Slide;
import ssm.model.SlideShowComponent;
import ssm.model.VideoComponent;
import ssm.view.EportfolioMakerView;
import ssm.view.PageEditView;

public class EportfolioFileController {

    private boolean saved;
    private EportfolioMakerView ui;
    private EportfolioFileManager eportfolioIO;
    String saveAsFileName = "";

    public EportfolioFileController(EportfolioMakerView initUI, EportfolioFileManager initePortfolioShowIO) {
        saved = true;
        ui = initUI;
        eportfolioIO = initePortfolioShowIO;
    }

    public void markAsEdited() {
        saved = false;
        //ui.updateToolbarControls(saved);
    }

    public void handleNewEportFolioRequest() {
        boolean continueToMakeNew = true;
        if (!saved) {
            // THE USER CAN OPT OUT HERE WITH A CANCEL
            // continueToMakeNew = promptToSave();
        }
        if (continueToMakeNew) {
            EportfolioModel eportfolioModel = ui.getEportfolioModel();
            eportfolioModel.reset();
            saved = false;
            ui.updatFileToolBarControls(saved);
            ui.addSiteEditorWorkSpace();

        }

    }

    public void handleLoadEportfolioRequest(EportfolioModel ePortfolioModel) throws IOException {
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
            /*
             ErrorHandler eH = ui.getErrorHandler();
             eH.processError(LanguagePropertyType.ERROR_DATA_FILE_LOADING);*/
        }
    }

   /* public boolean handleSaveEportfolioRequest(EportfolioModel ePortfolioModel) throws IOException {
        //String slideShowTitle = "" + ePortfolioModel.getPages().get(0).getStudentName();
        //ui.setJsonFileName(slideShowTitle);
        String jsonFilePath = PATH_SLIDE_SHOWS + SLASH + slideShowTitle + JSON_EXT;
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);
        jsonWriter.writeArray(ePortfolioModel.toJSON());
        jsonWriter.close();
        return false;
    }*/

    public boolean handleSaveAsEportfolioRequest(EportfolioModel ePortfolioModel, String fileName) throws IOException {
        String jsonFilePath = PATH_SLIDE_SHOWS + SLASH + fileName + JSON_EXT;
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);
        jsonWriter.writeArray(ePortfolioModel.toJSON());
        jsonWriter.close();
        return false;
    }

    public void handleViewEportfolioRequest() throws IOException {
        // copy the whole foler
        saveAsFileName = ui.getJsonFileName();
        // ui.getEportfolioModel().getSelectedPage()
        String folderName = "";
        if (!saveAsFileName.equals("")) {
            folderName = saveAsFileName;
        } else {
            folderName = ui.getEportfolioModel().getPages().get(0).getStudentName();
        }
        ui.getEportfolioModel().getPages().get(0).getBannerImagePath(); // so you get the image path         
        // copy the whole template folder       
        Path to = Paths.get("Sites/" + folderName);
        Path from = Paths.get("Sites/template");
        try {
            Files.walkFileTree(from, new CopyDirVisitor(from, to));
        } catch (IOException e) {
            e.printStackTrace();
        }
      // copy JASON file
        Files.copy(Paths.get("data/ePortfolios/" + saveAsFileName + ".json"), Paths.get(to.toString() + "/" + saveAsFileName + ".json"), StandardCopyOption.REPLACE_EXISTING);       
        // modify javascript file
        
        BufferedReader jsreader = Files.newBufferedReader(Paths.get(to.toString()+"/js/eportfolio.js"));
        BufferedWriter jsWriter = Files.newBufferedWriter(Paths.get(to.toString()+"/js/eportfolio2.js"));
        
        String line = null;
        while ((line = jsreader.readLine()) != null) {
            line = line.replace("ePortfolio.json", saveAsFileName+".json");
            jsWriter.write(line + "\n" );
        }
        
        jsreader.close();
        jsWriter.close();
         // copy Javascript file
        Files.copy(Paths.get(to.toString()+"/js/eportfolio2.js"), Paths.get(to.toString()+"/js/eportfolio.js"), StandardCopyOption.REPLACE_EXISTING);       

        
        String html = ui.getEportfolioModel().getSelectedPage().getPageTitle() + ".html";
        
        for (Page page : ui.getEportfolioModel().getPages()) {
            String pageTitle = page.getPageTitle();
            // copy page1.html pagetitle.html
            Path pageFile = Paths.get(to.toString() + "/" + pageTitle + ".html");
            //html = pageTitle + ".html";
            BufferedReader htmlreader = Files.newBufferedReader(Paths.get(to.toString() + "/page1.html"));
            BufferedWriter htmlwriter = Files.newBufferedWriter(pageFile);
//            String line = null;
            while ((line = htmlreader.readLine()) != null) {
                line = line.replaceAll("page1layout.css", EportfolioMakerView.PageType2CSS.get(page.getPageType()));
                line = line.replaceAll("page1style.css", EportfolioMakerView.PageStyle2CSS.get(page.getPageStyle()));                
                htmlwriter.write(line+"\n");
            }
            htmlwriter.close();
            htmlreader.close();
            
            // copy files
            for (Components c : page.getComponents()) {
                if (c instanceof ImageComponent) {
                    ImageComponent cc = (ImageComponent) c;
                    String fileName = cc.getImageFileName();
                    Files.copy(Paths.get(cc.getImagePath() + "/" + fileName), Paths.get(to.toString() + "/img/" +  fileName ), StandardCopyOption.REPLACE_EXISTING);
                } else if (c instanceof VideoComponent) {
                    VideoComponent cc = (VideoComponent) c;
                    String fileName = cc.getVideoFileName();
                    Files.copy(Paths.get(cc.getVideoPath() + "/" + fileName), Paths.get(to.toString() + "/video/" + fileName ), StandardCopyOption.REPLACE_EXISTING);
                } else if (c instanceof SlideShowComponent) {
                    SlideShowComponent cc = (SlideShowComponent) c;
                    for (Slide s : cc.getSlide()) {
                        String fileName = s.getImageFileName();
                        Files.copy(Paths.get(s.getImagePath() + "/" + fileName), Paths.get(to.toString() + "/img/" +  fileName), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            //EportfolioMakerView.PageStyle2CSS.get(page.getPageType());
        }
        
        File newFile = new File("Sites/"+ folderName+"/"+html);
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
 

        /*
         for(int i =0; i<ui.getEportfolioModel().getPages().size();i++){
         for (int j =0; i<ui.getEportfolioModel().getPages().get(i).getComponents().)
         
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
      
         */
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
            EportfolioErrorHandler eH = ui.getErrorHandler();
            // eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }
    }

    private boolean promptToSave() throws IOException {
        /*     
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
         */
        return true;

    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private static JsonArray loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonArray json = jsonReader.readArray();
        jsonReader.close();
        is.close();
        return json;
    }

    private void promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser ePortfolioFileChooser = new FileChooser();
        ePortfolioFileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOWS));
        File filetobeopen = ePortfolioFileChooser.showOpenDialog(ui.getWindow());
        if (filetobeopen != null) {
            try {
                EportfolioModel eportfolio = ui.getEportfolioModel();

                // LOAD THE JSON FILE WITH ALL THE DATA
                eportfolio.setPages(
                        EportfolioModel.fromJSON(EportfolioFileController.loadJSONFile(filetobeopen.getAbsolutePath()))
                );
                if (!eportfolio.getPages().isEmpty()) {
                    eportfolio.setSelectedPage(eportfolio.getPages().get(0));

                    for (int i = 0; i < eportfolio.getPages().size(); i++) {
                        PageEditView pageView = new PageEditView(eportfolio.getPages().get(i), eportfolio, ui);
                        eportfolio.getPages().get(i).setPgeEditView(pageView);
                        ui.getPageTabPane().getTabs().add(pageView.getTab());

                    }
                }
                // ui.setUppageEditorWorkspace();
                ui.reloadEportfolioPane();
                saved = false;
                // ui.updateToolbarControls(saved);
                // NOW LOAD THE COURSE

            } catch (IOException didi) {

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

    public CopyDirVisitor(Path myFromPath, Path myToPath) {
        fromPath = myFromPath;
        toPath = myToPath;

    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path targetPath = toPath.resolve(fromPath.relativize(dir));
        if (!Files.exists(targetPath)) {
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
