/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
      stage.setTitle("Layout Demo");
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

      ToolBar toolbar = new ToolBar(new Button("New"),
         new Button("Open"), new Separator(), new Button("Cut"),
         new Button("Copy"), new Button("Paste"));

      VBox vbox = new VBox();
      vbox.getChildren().addAll(menuBar, toolbar);

      TreeItem<String> ti = new TreeItem<>("Gauge List");
      ti.getChildren().addAll(
         new TreeItem<String>("Bar Plot"),
         new TreeItem<String>("XY Plot"),
         new TreeItem<String>("X Plot"),
         new TreeItem<String>("Circle"),
         new TreeItem<String>("Number/Single Character"),
         new TreeItem<String>("Text"),
         new TreeItem<String>("Clock"),
         new TreeItem<String>("Stopwatch"),
         new TreeItem<String>("Running time"),
         new TreeItem<String>("On/Off Light"));
                 
      
      TreeView<String> tv = new TreeView<String>(ti);

      TabPane tabPaneLeft = new TabPane();
      Tab tab1 = new Tab("Gauge List");
      tab1.setContent(tv);
      tabPaneLeft.getTabs().addAll(tab1, new Tab("Explorer"));

      TabPane tabPaneRight = new TabPane();
      tabPaneRight.getTabs().addAll(new Tab("Outline"),
         new Tab("Task List"));

      borderPane.setTop(vbox);
      borderPane.setLeft(tabPaneLeft);
      borderPane.setCenter(new TextArea());
      borderPane.setRight(tabPaneRight);
      borderPane.setBottom(new Label("Status text: Gauge/Data Column Selection"));

     return borderPane;
   }
}