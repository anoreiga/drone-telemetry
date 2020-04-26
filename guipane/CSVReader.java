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
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CSVReader {

    int count = 0;
    int colNum = 0;
    String[] cols = null;
    String line = "";
    
    public void ReadCSV(String path, ListView<String> view) {
        
        String csvFile = path;
        String line2 = "";
        String DELIMITER = ",";

      try {
          
         File file = new File(csvFile);
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);

         String[] tempArr;
         String[] dataArr;
         
         String headerRow = null;
         
         while((line2 = br.readLine()) != null) {
            tempArr = line2.split(DELIMITER);
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
        line = "";
        String DELIMITER = ",";
        
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        count = 0;
        colNum = 0;
            //using commas as seperators 
            line = br.readLine();
            cols = line.split(DELIMITER); 
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    Runnable updater = new Runnable(){
                        @Override
                        public void run() {
                           if(count==0){
                                for(int i = 0; i < cols.length; i++){
                                    if(cols[i].equals(currentSelection)){
                                        System.out.println("here");
                                        colNum = i;
                                    }
                                }
                            }
                            //print out the requested column  
                            else {
                               try {
                                   line = br.readLine();
                               } catch (IOException ex) {
                                   Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
                               }
                                cols = line.split(DELIMITER); 
                                ta.setText(cols[colNum]);
                                System.out.println("element: " + cols[colNum]);
                                //Thread.sleep(500);
                            }
                            count++;
                        }
                    };
                    
                    while (line != null) {
                        try {
                            System.out.println("count: " + count);
                            Thread.sleep(100);
                        } 
                        catch (InterruptedException ex) {
                        }

                        // UI update is run on the Application thread
                        Platform.runLater(updater);
                    }
    
                }
                
                
            });
            thread.setDaemon(true);
            thread.start();
            
        
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
            //ObservableList<String> cols = FXCollections.observableArrayList();
            String[] cols = line.split(DELIMITER);
            cols = line.split(DELIMITER);
            if(count==0){
                for(int i = 0; i < cols.length; i++){
                    if(cols[i].equals(currentSelection)){
                        colNum = i;
                        //parsedCols.add(cols[colNum]);
                    }
                }
            }
            //print out the requested column  
            else {
                //ta.deleteText(0, ta.getText().length()-1);
                //ta.insertText(0, cols[colNum]);
                tf.setText(cols[colNum]);
                                
                System.out.println(cols[colNum]);
            }
            count++;
        }
    }   
}
    
    
