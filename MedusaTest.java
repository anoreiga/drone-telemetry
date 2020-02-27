/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medusa.test;

//vanilla java imports 
import java.util.*;
import java.awt.*;
import java.text.*;

//javaFX imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent.*;
import javafx.scene.paint.Color;



//medusa imports
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import eu.hansolo.medusa.skins.SpaceXSkin;
import eu.hansolo.medusa.*;
import eu.hansolo.*;

/**
 *
 * @author Alexandra
 */
public class MedusaTest extends Application {
    
    @Override 
    public void start(Stage primaryStage) { 
        Gauge gauge = new Gauge();
        Button btn = new Button(); 
        btn.setText("Say 'Hello World!'");
        btn.setTranslateX(10);
        btn.setTranslateY(200);
        
        //button click action handler 
        btn.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override 
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                gauge.setAnimated(true);
                gauge.setValue(90.00);
            }   
    });
    
    gauge.setSkin(new ModernSkin(gauge)); //this is where the skin can be altered
    gauge.setTitle("MEDUSA TEST");
    gauge.setUnit("MPH");
    gauge.setUnitColor(Color.WHITE);
    gauge.setDecimals(0);
    gauge.setValue(50.00); 
    gauge.setAnimated(true);
    
    gauge.setValueColor(Color.WHITE);
    gauge.setTitleColor(Color.WHITE);
    gauge.setSubTitleColor(Color.WHITE);
    gauge.setBarColor(Color.WHITE);
    gauge.setNeedleColor(Color.WHITE);
    gauge.setThresholdColor(Color.WHITE);
    gauge.setThreshold(85);
    gauge.setThresholdVisible(true);
    
    gauge.setTickLabelColor(Color.rgb(151,151,151));
    gauge.setTickMarkColor(Color.WHITE);
    gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL); 
    
    StackPane root = new StackPane();
    root.getChildren().addAll(gauge);
    root.getChildren().addAll(btn);
    Scene scene = new Scene(root, 300, 250);
    
    primaryStage.setTitle("Please Work:");
    primaryStage.setScene(scene);
    primaryStage.show();
    }
}
