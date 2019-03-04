/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.model;
import java.util.ArrayList;
/**
 *
 * @author shengchun
 */
public class Eportfolio {
    String studentName;
    ArrayList<Page>pages;
    Page selectedPage;
    
    
public Eportfolio(String initStudentName, ArrayList<Page>initPages){
        studentName= initStudentName;
        pages=initPages;
  
}
public void setStudentName(String initStudentName){
     studentName= initStudentName;

}
public boolean isPageSelected(){
   return selectedPage!=null;
}
public void setSelectedPage(Page initSelectedPage){
   selectedPage = initSelectedPage;
}
public void reset(){


}
public void addPage(Page pageToAdd){
   pages.add(pageToAdd);

}
public void removePage(Page selectedPage){
pages.remove(selectedPage);

}

    
}
