/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.error;

import javafx.scene.control.Alert;
import properties_manager.PropertiesManager;
import ssm.LanguagePropertyType;
import ssm.view.EportfolioMakerView;

/**
 *
 * @author shengchun
 */
public class EportfolioErrorHandler {
     // APP UI
    private EportfolioMakerView ui;
    
    // KEEP THE APP UI FOR LATER
    public EportfolioErrorHandler(EportfolioMakerView initUI) {
	ui = initUI;
    }
    
    /**
     * This method provides all error feedback. It gets the feedback text,
     * which changes depending on the type of error, and presents it to
     * the user in a dialog box.
     * 
     * @param errorType Identifies the type of error that happened, which
     * allows us to get and display different text for different errors.
     */
    public void processError(LanguagePropertyType errorType, String errorDialogTitle, String errorDialogMessage)
    {
        // GET THE FEEDBACK TEXT        TITLE
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String errorFeedbackText = props.getProperty(errorType.toString());       
               
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorDialogTitle);
         alert.setHeaderText(errorDialogMessage);
         alert.setContentText(errorFeedbackText);
        alert.showAndWait();
    }    
}
