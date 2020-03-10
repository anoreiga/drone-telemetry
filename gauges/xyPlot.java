/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandra
 */


//import java.security.acl.Group;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

public class xyPlot extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //defining the x axis 
        //TODO: make dynamic
        NumberAxis xAxis = new NumberAxis(2000, 2020, 10); 
        
        //TODO: make dynamic
        xAxis.setLabel("Years");
        
        //defining the y axis 
        //TODO: make dynamic
        NumberAxis yAxis = new NumberAxis (0, 250, 50);
        yAxis.setLabel("No. of schools");
        
        //creating the line chart 
        LineChart linechart = new LineChart(xAxis, yAxis);
        
        //Ready XYChart.Series objects by setting data
        XYChart.Series series = new XYChart.Series(); 
        
        //This will set the chart's overall name
        //TODO: decide if this will be static or a combination of data column selections
        series.setName("Number of schools in a year");
        
        //TODO: make dynamically retrieve data
        series.getData().add(new XYChart.Data(1970, 15));
        series.getData().add(new XYChart.Data(1980, 30));
        series.getData().add(new XYChart.Data(1990, 60));
        series.getData().add(new XYChart.Data(2000, 120));
        series.getData().add(new XYChart.Data(2013, 240));
        series.getData().add(new XYChart.Data(2014, 300));
        
        //adding the data to LineChart 
        linechart.getData().add(series);
        
        //creating group object 
        Group root = new Group(linechart);
        
        //creating scene object 
        Scene scene = new Scene(root, 600, 400);
        
        //setting title to the stage
        stage.setTitle("Line Chart");
        
        //adding scene to the stage
        stage.setScene(scene);
        
        //displaying the contents of the stage
        stage.show();
    }
    
    public static void main(String args[]) {
        launch(args);
    }
}