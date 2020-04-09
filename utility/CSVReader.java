/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.control.ListView;

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
}
    