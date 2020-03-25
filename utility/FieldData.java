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
public class FieldData {

    // Setting Variables
    // If the collection of data isString == true,
    //      then collection is names OR the data's type is undefined
    boolean isDistance;
    boolean isTemperature;
    boolean isVoltage;
    boolean isString;

    private ArrayList<String> data;

    // Default Constructor
    public FieldData() {
        data = new ArrayList<>();

        // Collection type is false until set
        isDistance = false;
        isTemperature = false;
        isVoltage = false;
        isString = false;
    }

    // Constructor if there is data to supply
    public FieldData(ArrayList<String> data) {
        this.data = data;

        // Collection type is false until set
        isDistance = false;
        isTemperature = false;
        isVoltage = false;
        isString = false;
    }

    // Add Data
    public void addData(String entry) {
        data.add(entry);
    }

    // Print Data
    public void printData() {
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                System.out.println(i + ": " + data.get(i));
            }
        } else {
            System.out.println("No data.\n");
        }
    }

    // Set the collection type
    public void setCollectionType() {
        String columnName = data.get(0);

        if (columnName.indexOf("[m") != -1) {
            isDistance = true;
            //System.out.println("isDistance set to true");
        } else if (columnName.indexOf("[v") != -1) {
            isVoltage = true;
            //System.out.println("isVoltage set to true");            
        } else if (columnName.indexOf("[c") != -1) {
            isTemperature = true;
            //System.out.println("isTemperature set to true");
        } else {
            isString = true;
            //System.out.println("isString set to true");
        }
    }

    // Convert temperature
    void convertTemperature(boolean isCelcius) {
        UnitConversion uc = new UnitConversion();
        float in;
        float out;

        if (isTemperature) {
            for (int i = 1; i < data.size(); i++) {
                if (data.get(i) != "") {
                    in = Float.parseFloat(data.get(i));

                    if (isCelcius) {
                        out = uc.ConvertToFahrenheit(in);
                    } else {
                        out = uc.ConvertToCelsius(in);
                    }

                    data.set(i, String.valueOf(out));
                }
            }
            System.out.println("Temperature was convereted.");
        } else {
            System.out.println("Temperature was not convereted.");
        }
    }

    // Convert distance
    void convertDistance(boolean isMeters) {
        UnitConversion uc = new UnitConversion();
        float in;
        float out;

        if (isDistance) {
            for (int i = 1; i < data.size(); i++) {
                if (data.get(i) != "") {
                    in = Float.parseFloat(data.get(i));
                    if (!isMeters) {
                        out = uc.ConvertToFeet(in);
                    } else {
                        out = uc.ConvertToMeters(in);
                    }

                    data.set(i, String.valueOf(out));
                }
            }
            System.out.println("Meters was convereted.");
        } else {
            System.out.println("Meters was not convereted.");
        }
    }

    // get data field column
    ArrayList<String> getFieldData(){
        return data;
    }
    
}
