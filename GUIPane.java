/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * @author Alexandra
 */

public class GUIPane extends Application {

   public static void main(String[] args) {
      Application.launch(args);
   }

   @Override
   public void start(Stage stage) throws Exception {

      Scene scene = new Scene(createBorderPane(), 250, 200);
      stage.setTitle("Gauge/Data Column Selection");
      stage.setScene(scene);
      stage.show();
   }


   public BorderPane createBorderPane() {

      BorderPane borderPane = new BorderPane();

      MenuBar menuBar = new MenuBar();
      Menu fileMenu = new Menu("File");
      fileMenu.getItems().addAll(new MenuItem("New"),
         new SeparatorMenuItem(), new MenuItem("Open"),
         new MenuItem("Save"), new MenuItem("Save As..."),
         new SeparatorMenuItem(), new MenuItem("Exit"));
      Menu editMenu = new Menu("Edit");
      editMenu.getItems().addAll(new MenuItem("Undo"),
         new MenuItem("Redo"), new MenuItem("Cut"),
         new MenuItem("Copy"), new MenuItem("Paste"),
         new SeparatorMenuItem(), new MenuItem("Search/Replace"));
      Menu helpMenu = new Menu("Help");
      helpMenu.getItems().addAll(new MenuItem("Help Contents"),
         new SeparatorMenuItem(), new MenuItem("About..."));
      menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

      //ToolBar toolbar = new ToolBar(new Button("New"), new Button("Open"));

      VBox vbox = new VBox();
      
      //add toolbar here if wanted
      vbox.getChildren().addAll(menuBar);

      CheckBoxTreeItem<String> gaugeRootItem = new CheckBoxTreeItem<>("Gauge List");
     
      
      gaugeRootItem.getChildren().addAll(
         new CheckBoxTreeItem<String>("Bar Plot"),
         new CheckBoxTreeItem<String>("XY Plot"),
         new CheckBoxTreeItem<String>("X Plot"),
         new CheckBoxTreeItem<String>("Circle"),
         new CheckBoxTreeItem<String>("Number/Single Character"),
         new CheckBoxTreeItem<String>("Text"),
         new CheckBoxTreeItem<String>("Clock"),
         new CheckBoxTreeItem<String>("Stopwatch"),
         new CheckBoxTreeItem<String>("Running time"),
         new CheckBoxTreeItem<String>("On/Off Light"));
         
                 
      final Callback<TreeItem<String>, ObservableValue<Boolean>> getSelectedProperty = 
              (TreeItem<String> item) -> {
                  if (item instanceof CheckBoxTreeItem<?>) {
                      return ((CheckBoxTreeItem<?>)item).selectedProperty();
                  }
                  return null;
              };
      
      TreeView<String> tv = new TreeView<String>(gaugeRootItem);
      
      tv.setCellFactory(p -> new CheckBoxTreeCell<>(getSelectedProperty));
      tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      
      TabPane tabPaneLeft = new TabPane();
      Tab tab1 = new Tab("Select Gauges");
      tab1.setContent(tv);
      tabPaneLeft.getTabs().addAll(tab1);
      
      //add tree items for data columns 
      //TODO: make dynamic 
      CheckBoxTreeItem<String> dataRootItem = new CheckBoxTreeItem<>("Data Columns");
      dataRootItem.setExpanded(true);
      
      dataRootItem.getChildren().addAll(
         new CheckBoxTreeItem<String>("BATTERY"),
         new CheckBoxTreeItem<String>("YAW"),
         new CheckBoxTreeItem<String>("PITCH"),
         new CheckBoxTreeItem<String>("TIMESTAMP"));
      
      TreeView<String> tv2 = new TreeView<String>(dataRootItem);

      TabPane tabPaneRight = new TabPane();
      Tab tab2 = new Tab("Select Data Columns");
      tab2.setContent(tv2);
      tabPaneRight.getTabs().addAll(tab2);
      
      borderPane.setTop(vbox);
      borderPane.setLeft(tabPaneLeft);
      borderPane.setCenter(new TextArea());
      borderPane.setRight(tabPaneRight);
      borderPane.setBottom(new Label("Status text: Gauge/Data Column Selection"));

     return borderPane;
   }
}