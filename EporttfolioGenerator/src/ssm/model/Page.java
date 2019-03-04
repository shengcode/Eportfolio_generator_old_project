/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.model;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import ssm.view.PageEditView;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import ssm.view.EportfolioMakerView;

public class Page {
    String pageTitle;
    String pageFooter;
    String studentName;
    String bannerImageFileName;
    String bannerImagePath;
    String pageType;
    String pageStyle;
    ArrayList<Components>components;
    Components selectedComponent;
    PageEditView pageEdit;
    String headerString="";
    boolean  isHeaderSelected;

    public String getHeaderString() {
        return headerString;
    }
    
    public JsonObject toJSON() {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("title", pageTitle)
                .add("studentName", studentName)
                .add("pageFooter", pageFooter)
                .add("bannerImageFileName", bannerImageFileName)
                .add("bannerImagePath", bannerImagePath)
                .add("pageType",pageType)
                .add("pageStyle",pageStyle);
        
        JsonArrayBuilder jcomponents = Json.createArrayBuilder();
        for (Components c : components) {
            jcomponents = jcomponents.add(c.toJSON());
        }
        obj = obj.add("components", jcomponents);
        return obj.build();
    }
    
   public static Page fromJSON(JsonObject obj) {
        Page p = new Page(obj.getString("title"),
                obj.getString("studentName"),
                obj.getString("pageFooter"),
                obj.getString("bannerImageFileName"),
                obj.getString("bannerImagePath"),
                obj.getString("pageType"),
                obj.getString("pageStyle"));
               
        JsonArray comps = obj.getJsonArray("components");
        for (int i = 0; i < comps.size(); ++i) {
            JsonObject o = (JsonObject) comps.get(i);
            switch (o.getString("type")) {
                case ParagraphComponent.TYPE:
                    p.getComponents().add(ParagraphComponent.fromJSON(o));
                    break;
                case ImageComponent.TYPE:
                    p.getComponents().add(ImageComponent.fromJSON(o));
                    break;
                case VideoComponent.TYPE:
                    p.getComponents().add(VideoComponent.fromJSON(o));
                    break;
                case SlideShowComponent.TYPE:
                    p.getComponents().add(SlideShowComponent.fromJSON(o));
                    break;
                case ListComponent.TYPE:
                    p.getComponents().add(ListComponent.fromJSON(o));
                    break;
            }
        }
        return p;
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
    }
    public Page(String initPageTitle, String initStudentName,String footer,String initBannerImageFileName,String initBannerImagePath, String initPageType, String initPageStyle){
        this.pageTitle=initPageTitle;
        this.studentName=initStudentName;
        this.pageFooter=footer;
        this.bannerImageFileName=initBannerImageFileName;
        this.bannerImagePath=initBannerImagePath;
        this.pageType=initPageType;
        this.pageStyle=initPageStyle;
        this.components = new ArrayList<Components>();
        isHeaderSelected=false;
        
    } 
    

    public Page(String initPageTitle, String initStudentName,String footer,String initBannerImageFileName,String initBannerImagePath, String initPageType, String initPageStyle,EportfolioMakerView ui){
        this.pageTitle=initPageTitle;
        this.studentName=initStudentName;
        this.pageFooter=footer;
        this.bannerImageFileName=initBannerImageFileName;
        this.bannerImagePath=initBannerImagePath;
        this.pageType=initPageType;
        this.pageStyle=initPageStyle;
        this.components = new ArrayList<Components>();
        isHeaderSelected=false;
        pageEdit = new PageEditView(this,ui.getEportfolioModel(),ui);
    } 
    
  
    public String getPageTitle() {return pageTitle;}
    public String getPageFooter() {return pageFooter;}
    public String getStudentName(){return studentName;}
    public String getBannerImageFileName() { return bannerImageFileName; }
    public String getBannerImagePath() { return bannerImagePath; }
    public PageEditView getPageEditView(){return pageEdit;  }
    public String getPageType() { return pageType;}
    public String getPageStyle() {return pageStyle; }  
    
   public void setHeaderSelected(boolean selected){
      isHeaderSelected=selected;
   }
   public boolean getIfHeaderSelected(){
        return isHeaderSelected;
   }
    
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
    public void setPageFooter(String pageFooter) {
        this.pageFooter = pageFooter;
    }
    public void setStudentName(String studentName){
      this.studentName=studentName;
    }
    public void setPageType(String pageType) {
        this.pageType=pageType;
    }
    public void setPageStyle(String pageStyle) {
        this.pageStyle=pageStyle;
    }
    
    public void setComponents(ArrayList<Components> initComponents) {
        this.components = initComponents;
    }
    public ArrayList<Components> getComponents() {
        return components;
    }
   
     public void setBannerImage(String initPath,String initFileName){
     bannerImagePath=initPath;
     bannerImageFileName = initFileName;
    }
  public void setBannerImageFileName(String initImageFileName) {
	bannerImageFileName = initImageFileName;    }    
    public void setBannerImagePath(String initImagePath) {
	bannerImagePath = initImagePath;
    }     
   
    public void setPgeEditView(PageEditView pageEditView){
        pageEdit=pageEditView;
   }
    public boolean isComponentSelected(){
	return selectedComponent != null;
    }    
    public boolean isSelectedComponent(Components testComponent) {
	return selectedComponent == testComponent;
    }    
       
    public Components getSelectedComponent() {
	return selectedComponent;
    }    
    // MUTATOR METHODS
    public void setSelectedComponent(Components initSelectedComponent) {
	selectedComponent = initSelectedComponent;
        
    }
    
    public void addComponent(Components c){
      // Components componentToAdd = new Components();
       components.add(c);
        // ui.reloadEportfolioPane();
    }   
    public void removeSelectedComponent() {
	   components.remove(selectedComponent);
	    selectedComponent = null;
	    //ui.reloadEportfolioPane();
 }
}

