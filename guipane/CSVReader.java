/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guipane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Twon
 */
public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "C:\\Projects\\Senior-Design\\drone-telemetry\\guipane\\sample.csv";
        String line = "";
        String cvsSplitBy = ",";

        
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                for (int i = 0; data.length > i; i++){
                    System.out.println(data[i]);
                }
                

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}