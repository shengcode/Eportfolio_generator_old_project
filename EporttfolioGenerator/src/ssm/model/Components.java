/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.model;

import javafx.scene.layout.VBox;
import javax.json.JsonObject;

/**
 *
 * @author shengchun
 */
public class Components {
    
    boolean updated;

    public boolean isUpdated() {
        return updated;
    }
    
   public Components(){
    
   }
   
   public VBox createUI() {
       //HBox newbox = new HBox();
       return null;
   }

    
   public JsonObject toJSON() {
       return null;
   } 
   
    public void showDialogBox() {}

}
