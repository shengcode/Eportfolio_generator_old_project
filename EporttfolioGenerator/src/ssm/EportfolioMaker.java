/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import static ssm.StartupConstants.PATH_DATA;
import static ssm.StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;


import ssm.file.EportfolioFileManager;
import ssm.view.EportfolioMakerView;
import ssm.view.SlideShowMakerView;
//import xml_utilities.InvalidXMLFileFormatException;


/**
 *
 * @author shengchun
 */
public class EportfolioMaker extends Application {
   EportfolioFileManager eportfolioFileManager = new EportfolioFileManager();
   EportfolioMakerView ui = new EportfolioMakerView(eportfolioFileManager);
   
    
	  
  
   
    @Override
    public void start(Stage primaryStage) throws Exception {
       String propertiesFileName = "properties_EN.xml";
       PropertiesManager props = PropertiesManager.getPropertiesManager();
        props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
         props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
         ui.startUI(primaryStage);
    }       
              

    public static void main(String[] args) {
	launch(args);
    }
}
