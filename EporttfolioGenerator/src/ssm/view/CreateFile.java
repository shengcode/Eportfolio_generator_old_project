/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssm.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author shengchun
 */
public class CreateFile {
    public static void main(String[] args){    

String path1="file:///C:/Users/shengchun/Documents/NetBeansProjects/SlideshowMaker/Sites/";
//String path2="title of the slideshow/"; // which I am going to get from getTitle
String path3="index.html";            // a new slide show
File myHtml = new File(path1+path3);

try {
       myHtml.createNewFile();
}

catch(Exception e){
    e.printStackTrace();    
    }

try {
FileWriter myWriter = new FileWriter(myHtml);
BufferedWriter buffw = new BufferedWriter(myWriter);
buffw.write("this is going to be my new Html");
 } 
catch(Exception e){
e .printStackTrace();
      }
  }
}
