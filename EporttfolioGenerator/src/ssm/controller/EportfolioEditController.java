/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.controller;

import static ssm.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static ssm.StartupConstants.DEFAULT_SLIDE_IMAGE;
import static ssm.StartupConstants.PATH_EPORTFOLIO_IMAGES;
import ssm.model.EportfolioModel;
import ssm.view.EportfolioMakerView;

/**
 *
 * @author shengchun
 */
public class EportfolioEditController {
    // APP UI
    private EportfolioMakerView ui;    
    /**
     * This constructor keeps the UI for later.
     */
    public EportfolioEditController(EportfolioMakerView initUI) {
	ui = initUI;
    }    
    /**
     * Provides a response for when the user wishes to add a new
     * slide to the slide show.
     */
    public void processAddPageRequest() {
        EportfolioModel eportfolioModel = ui.getEportfolioModel();
        eportfolioModel.addPage("ENTER PAGE TILE", "ENTER STUDENT NAME", "ENTER PAGE FOOTER", DEFAULT_SLIDE_IMAGE, PATH_EPORTFOLIO_IMAGES,"typeone", CSS_CLASS_SLIDE_EDIT_VIEW,ui);
	
       
    }
    public void processRemovePageRequest() {
	EportfolioModel eportfolioModel = ui.getEportfolioModel();
	eportfolioModel.removeSelectedPage();
  }
public void selectSiteViewerWorkSpace(){
              EportfolioModel eportfolioModel = ui.getEportfolioModel();
	      ui.addSiteEditorWorkSpace();
}
public void selectPageEditWorkSpace(){
   ui.addPageEditorWorkSpace();

}   
    
}

    
    
    

