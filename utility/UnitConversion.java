/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlyParser;

/**
 *
 * @author jasmi
 */
public class UnitConversion {
    
    float distance;
    float temperature;
    
    UnitConversion(){
    }
    
    float ConvertToMeters(float distance){
        // Convert Feet to Meters
        // M = F * 3.281
        this.distance = (float) (distance * 3.281);
        
        return this.distance;
    }
    
    float ConvertToFeet(float distance){
        // Convert Meters to Feet
        // F = M / 3.281
        this.distance = (float) (distance / 3.281);
        
        return this.distance;
    }
    
    float ConvertToFahrenheit(float temperature){
        // Convert Celsius to Fahrenheit
        // C = (F - 32) * (5/9)
        this.temperature = (temperature - 32) * 5/9;
                
        return this.temperature;
    }
    
    float ConvertToCelsius(float temperature){
        // Convert Celsius to Fahrenheit
        // F = (C * 9/5) + 32
        this.temperature = (temperature * 9/5) + 32;
        
        return this.temperature;
    }
    
}
