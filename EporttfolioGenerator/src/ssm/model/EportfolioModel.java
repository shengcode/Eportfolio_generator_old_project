package ssm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import ssm.view.EportfolioMakerView;
import ssm.view.PageEditView;

public class EportfolioModel {

    EportfolioMakerView ui;
    ObservableList<Page> pages;
    Page selectedPage;

    Components selectedComponent;

    public EportfolioModel(EportfolioMakerView initUI) {
        ui = initUI;
        pages = FXCollections.observableArrayList();
        reset();
    }

    public JsonArray toJSON() {
        JsonArrayBuilder arr = Json.createArrayBuilder();

        for (Page page : pages) {
            arr.add(page.toJSON());
        }
        return arr.build();
    }

    public static ObservableList<Page> fromJSON(JsonArray arr) {
        
        ObservableList<Page> pages = FXCollections.observableArrayList();
        for (int i = 0; i < arr.size(); ++i) {
            pages.add(Page.fromJSON(arr.getJsonObject(i)));
        }
        return pages;
    }

    // ACCESSOR METHODS
    public boolean isPageSelected() {
        return selectedPage != null;
    }

    public boolean isSelectedPage(Page testpage) {
        return selectedPage == testpage;
    }

    public ObservableList<Page> getPages() {
        return pages;
    }
  public void setPages(ObservableList<Page> initPages){
     this.pages=initPages;
  }
    public Page getSelectedPage() {
        return selectedPage;
    }

    // MUTATOR METHODS

    public void setSelectedPage(Page initSelectedPage) {
        selectedPage = initSelectedPage;
      /*  for (Page page : this.getPages()) { 
             PageEditView pageEdit = new PageEditView(page,this);
                 pageEdit.setEffect(null);
                  //page.getPageEditView().setEffect(null);                   
                }
        DropShadow ds = new DropShadow(20, Color.RED);
        selectedPage.getPageEditView().setEffect(ds);*/
    }

    public EportfolioMakerView getUi() {
        return ui;
    }
    // SERVICE METHODS    
    /**
     * Resets the slide show to have no slides and a default title.
     */
    public void reset() {
        pages.clear();
        selectedPage = null;
    }

    public void addPage(String initPageTitle, String initStudentName, String initPageFooter, String initBannerImageFileName, String initBannerImagePath, String initPageType, String initPageStyle,EportfolioMakerView ui) {
        Page pageToAdd = new Page(initPageTitle, initStudentName, initPageFooter, initBannerImageFileName, initBannerImagePath, initPageType, initPageStyle,ui);
        pages.add(pageToAdd);
        this.setSelectedPage(pageToAdd);
    }

    public void removeSelectedPage() {
          pages.remove(selectedPage);
          selectedPage = null;
      }

    

}
