/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandra
 */

import javafx.application.Application;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;


//THE TEA: Most of these variables need to be made dynamic
//So hold on and learn about car stuffs for a sec 
//This is me bored in 499 

public class BarGauge extends Application {
    @Override 
    public void start(Stage stage) throws Exception {
        
        //define the axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Speed", "User Rating", "Milage", "Safety")));
        xAxis.setLabel("category");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("score");
        
        //creating the bar chart 
        BarChart<String, Number> barChart = new BarChart<>(xAxis,yAxis); 
        
        //TODO: decide if dynamic or static title
        //if dynamic, make dynamic
        barChart.setTitle("Comparison between various cars.");
        
        //prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        
        //Yeah, all of these are gonna be dynamic sorry
        series1.setName("Fiat");
        
        series1.getData().add(new XYChart.Data<>("Speed", 1.0));
        series1.getData().add(new XYChart.Data<>("User rating", 3.0));
        series1.getData().add(new XYChart.Data<>("Milage", 5.0));
        series1.getData().add(new XYChart.Data<>("Safety", 5.0));
        
        //Yep, still dynamic
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        
        series2.setName("Audi");
        series2.getData().add(new XYChart.Data<>("Speed", 5.0));
        series2.getData().add(new XYChart.Data<>("User rating", 2.0));
        series2.getData().add(new XYChart.Data<>("Milage", 3.0));
        series2.getData().add(new XYChart.Data<>("Safety", 6.0));
        
        //Also dynamic
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        
        series3.setName("Ford");
        series3.getData().add(new XYChart.Data<>("Speed", 4.0));
        series3.getData().add(new XYChart.Data<>("User rating", 2.0));
        series3.getData().add(new XYChart.Data<>("Milage", 1.0));
        series3.getData().add(new XYChart.Data<>("Safety", 6.0));
        
        //setting the data to bar chart 
        //also dynamic woo
        barChart.getData().addAll(series1, series2, series3);
        
        //creating a group object 
        Group root = new Group(barChart);
        
        //creating a scene object 
        Scene scene = new Scene(root, 600, 400);
        
        //setting title to the stage 
        stage.setTitle("Bar Chart");
        
        //adding scene to the stage 
        stage.setScene(scene);
        
        //displaying the contents of the stage 
        stage.show();
        
    }
    
    public static void main(String args[]){
        launch(args);
    }
}