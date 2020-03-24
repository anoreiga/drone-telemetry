package utility;

/**
 *
 * @author Alexandra
 */

import java.util.*;
import java.math.*;
import java.text.DecimalFormat;

/**
 * Statistics class performs statistical analysis on data columns:
 * Minimum, Maximum, Std Deviation, and Averages
 */
public class Statistics {
    
    //main test driver
    public static void main (String[] args) {
        
        //TODO: make dynamic
        //array of integers for equation testing
        Integer[] numArray = { 2, 4, 7, 5, 9 };
        
        //rounding to 3rd decimal place
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        
        //print out results 
        System.out.println("Minimum calc: " + minCalc(numArray));
        System.out.println("Maximum calc: " + maxCalc(numArray));
        System.out.println("Standard deviation: " + sdCalc(numArray));
        System.out.println("Average value: " + avgCalc(numArray));
       
    }
    
    //TODO: add comments   
    public static double minCalc(Integer[] numArray) {
       int min = Collections.min(Arrays.asList(numArray));
       
       return min;
    }
    
    //TODO: add comments   
    public static double maxCalc(Integer[] numArray) {
        int max = Collections.max(Arrays.asList(numArray));
   
        return max;
    }

    //TODO: add comments
    public static double sdCalc(Integer[] numArray) {
        double sum = 0.0;
        double stdDev = 0.0;
        
        int length = numArray.length;
        
        for(double num: numArray) {
            sum += num;
        }
        
        double mean = sum/length; 
        
        for(double num: numArray) {
            stdDev += Math.pow(num - mean, 2);
        }
    
    //performs Sample calculation
    //for populations: return Math.sqrt(stdDev/(length-1))
    
    //TODO: round to 3rd decimal place
    return Math.sqrt(stdDev/length);

    }
                
    //TODO: add averages calculation
    public static double avgCalc(Integer[] numArray) {
        
        int sum = 0; 
        double average = 0;
        
        for (int i = 0; i < numArray.length; i++) {
            sum = sum + numArray[i];
            
            //calculate the average value
            //double average = sum / numArray.length;
        }
        
        return average = sum / numArray.length;
    }
}