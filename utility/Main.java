/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlyParser;

import java.util.ArrayList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author jasmi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("hello world!");

        ExcelReader er = new ExcelReader();
        try {
            er.Read();
            CollectionOfFields Cof = er.getCollection();
            Cof.setDistance();  // to feet
            //Cof.setDistance();  // to meters
            //Cof.printCollection();
            ExcelWriter ew = new ExcelWriter(Cof);
            ew.Write();

        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
