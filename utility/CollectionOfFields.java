/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlyParser;

import java.util.ArrayList;

/**
 *
 * @author jasmi
 */
public class CollectionOfFields {

    boolean isCelcius;
    boolean isMeters;

    ArrayList<FieldData> CollectionData;
    int NumberOfColumns;
    int SizeOfColumn;

    // Constructor
    public CollectionOfFields(ArrayList<String> ParsedData, int NumberOfColumns) {
        // Default information
        isCelcius = true;
        isMeters = true;

        // Setting up collection list
        CollectionData = new ArrayList<>();
        this.NumberOfColumns = NumberOfColumns;

        // Create Columns 
        for (int i = 0; i < NumberOfColumns; i++) {
            FieldData entry = new FieldData();
            CollectionData.add(entry);
        }

        //System.out.println("\n\nBreaking data into " + NumberOfColumns + " coluumns");
        // Break the parsed data into columns              
        int count = 0;
        for (int i = 0; i < ParsedData.size(); i++) {
            if (count == ParsedData.size()) {
                continue;
            }
            for (int j = 0; j < NumberOfColumns; j++) {
                CollectionData.get(j).addData(ParsedData.get(count));
                count++;
            }
        }

        //System.out.println("\n\nDone Breaking\n");
        //System.out.println("Starting to set collection type...");
        for (int i = 0; i < CollectionData.size(); i++) {
            CollectionData.get(i).setCollectionType();
        }

        //System.out.println("\nDone setting collection type...\n\n");        
    }

    // Get the list of data
    ArrayList<FieldData> getCollection() {
        return CollectionData;
    }

    // Swap between Temperature
    void setTemperature() {
        for (int i = 0; i < CollectionData.size(); i++) {
            CollectionData.get(i).convertTemperature(isCelcius);
        }

        if (isCelcius) {
            isCelcius = false;
            System.out.println("The collection is set to Fahr");
        } else {
            isCelcius = true;
            System.out.print("The collection is set to Celcius");
        }
    }

    // Swap between Distance
    void setDistance() {
        for (int i = 0; i < CollectionData.size(); i++) {
            CollectionData.get(i).convertDistance(isMeters);
        }

        if (isMeters) {
            isMeters = false;
            System.out.println("The collection is set to Feet");
        } else {
            isMeters = true;
            System.out.print("The collection is set to Meters");
        }
    }

    // Print out Collection
    void printCollection() {
        for (int i = 0; i < CollectionData.size(); i++) {
            System.out.println("\nColumn: " + i);
            CollectionData.get(i).printData();
        }
    }

    // Get the number of columns
    int getNumberOfColumns() {
        return NumberOfColumns;
    }

}
