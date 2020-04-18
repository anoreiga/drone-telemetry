/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CSVReader {

    public void ReadCSV(String path, ListView<String> view) {
        
        String csvFile = path;
        String line = "";
        String DELIMITER = ",";
        

      try {
          
         File file = new File(csvFile);
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);

         String[] tempArr;
         String[] dataArr;
         
         String headerRow = null;
         
         while((line = br.readLine()) != null) {
            tempArr = line.split(DELIMITER);
            for(String tempStr : tempArr) {
                view.getItems().add(tempStr);
            }
            break;
            
            }
        
         br.close();
         
         } catch(IOException ioe) {
            ioe.printStackTrace();
         }
    }
         
    public void ParseTextAreaCSV (TextArea ta, String path, String currentSelection) throws FileNotFoundException, IOException, InterruptedException {
        
        String csvFile = path;
        String line = "";
        String DELIMITER = ",";
        
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        int count = 0;
        int colNum = 0;
        while((line = br.readLine()) != null) {
            //using commas as seperators 
            String[] cols = line.split(DELIMITER); 
            if(count==0){
                for(int i = 0; i < cols.length; i++){
                    if(cols[i].equals(currentSelection)){
                        colNum = i;
                    }
                }
            }
            //print out the requested column  
            else {
                //ta.deleteText(0, ta.getText().length()-1);
                //ta.insertText(0, cols[colNum]);
                ta.setText(cols[colNum]);
                System.out.println(cols[colNum]);
                //Thread.sleep(500);
            }
            count++;
        }
    }
    
    public void ParseSingleCharacterCSV (TextField tf, String path, String currentSelection) throws FileNotFoundException, IOException, InterruptedException {
        
        String csvFile = path;
        String line = "";
        String DELIMITER = ",";
        
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        int count = 0;
        int colNum = 0;
        while((line = br.readLine()) != null) {
            //using commas as seperators 
            String[] cols = line.split(DELIMITER); 
            if(count==0){
                for(int i = 0; i < cols.length; i++){
                    if(cols[i].equals(currentSelection)){
                        colNum = i;
                    }
                }
            }
            //print out the requested column  
            else {
                //ta.deleteText(0, ta.getText().length()-1);
                //ta.insertText(0, cols[colNum]);
                tf.setText(cols[colNum]);
                System.out.println(cols[colNum]);
                //Thread.sleep(500);
            }
            count++;
        }
    }   
}
    