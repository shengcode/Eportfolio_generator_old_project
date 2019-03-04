
package ssm.view;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author shengchun
 */
public class MyTest {   
public static void main(String[] arg){        
  File fileName = new File("file:///C:/Users/shengchun/Documents/NetBeansProjects/SlideshowMaker/SlideshowMaker/data/slide_shows/index.html");  
  Scanner scan = null;
  try{
  scan = new Scanner(fileName);
  while (scan.hasNextLine()){
      System.out.println(scan);
        scan.close();
     }
 }
  catch(FileNotFoundException e){
      e.printStackTrace();
       }
   }
}

