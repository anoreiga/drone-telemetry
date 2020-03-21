/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipane;

import java.io.File;
import java.nio.charset.StandardCharsets;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
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
   public void start(final Stage stage) throws Exception {

      Scene scene = new Scene(createBorderPane(), 1000, 1000);
      stage.setTitle("Gauge/Data Column Selection");
      stage.setScene(scene);
      stage.show();
      
   }
      
   public BorderPane createBorderPane() {

      BorderPane borderPane = new BorderPane();

      //creating the menubar
      MenuBar menuBar = new MenuBar();
      
      //creating the File menu
      Menu fileMenu = new Menu("File");
      
      //telling the New menu item to load a new excel file
      MenuItem newItem = new MenuItem("New File");
      newItem.setOnAction(new EventHandler<ActionEvent>()
      {
          public void handle(ActionEvent event)
          {              
              FileChooser fileChooser = new FileChooser();
              
              //set the initial inventory to Documents 
              //TODO: is there any other directory that makes sense?
              fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

              //set the extension filters to only .xlsx and .csv files 
              //so only files with these extensions will be visible to the user
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)");
              fileChooser.getExtensionFilters().add(extFilter);
              
              Stage stage = new Stage();
              
              //showing "open file" dialog
              File file = fileChooser.showOpenDialog(stage);
              
              //open extension filtered files 
              HostServices hostServices = getHostServices();
              hostServices.showDocument(file.getAbsolutePath());
              
          }
          
      });
      
      //telling the Open menu item to load another .JSCON file 
      MenuItem openItem = new MenuItem("Open Existing File");
      openItem.setOnAction(new EventHandler<ActionEvent>()
      {
          public void handle(ActionEvent event)
          {              
              FileChooser fileChooser = new FileChooser();
              
              //set the initial inventory to Documents 
              //TODO: is there any other directory that makes sense?
              fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

              //set the extension filters to only .xlsx and .csv files 
              //so only files with these extensions will be visible to the user
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", "Comma seperated files (*.csv)", "*.csv");
              fileChooser.getExtensionFilters().add(extFilter);
              
              Stage stage = new Stage();
              
              //showing "open file" dialog
              File file = fileChooser.showOpenDialog(stage);
              
              //open extension filtered files 
              HostServices hostServices = getHostServices();
              hostServices.showDocument(file.getAbsolutePath());
              
          }
          
      });
      
      //telling the Exit menu item to exit application
      MenuItem exitItem = new MenuItem("Exit");
      exitItem.setOnAction(e -> Platform.exit());
 
      fileMenu.getItems().addAll(newItem,
         new SeparatorMenuItem(), openItem,
         new MenuItem("Save File"), new MenuItem("Save As..."),
         new SeparatorMenuItem(), exitItem);
      
      //creating the Edit menu
      Menu editMenu = new Menu("Edit");
      editMenu.getItems().addAll(new MenuItem("Undo"), new MenuItem("Redo"));
      
      //creating About window 
      MenuItem aboutItem = new MenuItem("About...");
      aboutItem.setOnAction(new EventHandler<ActionEvent>()
      {
          public void handle(ActionEvent event)
          {              
             Stage popupAbout = new Stage();
             //popupAbout.initModality(Modality.APPLICATION_MODAL);
             popupAbout.initStyle(StageStyle.UTILITY);
             popupAbout.setTitle("About the Project");
             
             //creating a text area
             Text aboutText = new Text();
             
             //setting the text font 
             aboutText.setFont(Font.font("times new roman", FontPosture.REGULAR, 15));
             
             //setting the position of the text 
             //aboutText.setTextAlignment(TextAlignment.CENTER);
             
             //setting the About text
             String text = "The drone telemetry software project will interpret information from a data file," +
"and the user will be able to choose from the listed fields of telemetry data to be displayed.\n\n" +
"The user will select up to ten fields--eleven, including the timestamp--and" +
"the corresponding statistics will be displayed.\n\n Gauges can be selected and named, based" +
"on given choices, to display individual data elements.\n\n These gauges can be customized" +
"with regards to the ranges (blue, green, yellow, and red), and include an alarm for data" +
"entering/exceeding the red range.\n\n The user may also choose from a selection of playback" +
"speeds, standard playback or reverse, arrange and resize gauges prior to playback, and" +
"save gauge setup for later retrieval.";
             
             aboutText.setText(text);
             
             //creating a layout for the text area 
             VBox vbox = new VBox(aboutText);
             
             //creating the popup scene 
             Scene popScene = new Scene(vbox, 1200, 300);
             popupAbout.setScene(popScene);
             popupAbout.showAndWait();
             
          }
          
      });
      
      MenuItem helpItem = new MenuItem("Help Contents");
      helpItem.setOnAction(new EventHandler<ActionEvent>()
      {
          public void handle(ActionEvent event)
          {              
             Stage popupHelp = new Stage();
             //popupAbout.initStyle(StageStyle.UTILITY);
             popupHelp.initModality(Modality.APPLICATION_MODAL);
             popupHelp.setTitle("Help Contents");
            
             //creating a help menu 
             Menu infoMenu = new Menu("Info");
             //infoMenu.getItems().addAll(new MenuItem("Creating a Gauge", new MenuItem());
             
             //creating a menu bar
             MenuBar infoBar = new MenuBar();
             infoBar.getMenus().addAll(infoMenu);
             
             //adding the menu bar to the popup window 
             
             //creating a layout for the text area 
             VBox vbox = new VBox(infoBar);
             
             //creating the popup scene 
             Scene helpScene = new Scene(vbox, 500, 500);
             popupHelp.setScene(helpScene);
             popupHelp.showAndWait();
             
          }
          
      });
      
      //creating the Help menu
      Menu helpMenu = new Menu("Help");
      helpMenu.getItems().addAll(aboutItem, new SeparatorMenuItem(), helpItem);
      
      
      //add all the menus to the menu bar
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

//loading function 
   
  /* 
private void load(ObservableList<Node> children)
{
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load File:");
    
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
        
        try {
            Values values = new Gson().fromJson(org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8), Values.class);
            children.stream().filter(child -> child.getId() != null)
                    .forEach(child -> { 
                        TextField field = (TextField) child; 
                        field.setText(values.getFieldTest());
                    } else if (child instanceof CheckBoxTreeItem) {
                            CheckBoxTreeItem area = (CheckBoxTreeItem) child; 
                            area.setText(values.getAreaText());
                            }
        });
    } catch (IOException e) {
            //handle exception 
            }
        }
    }
*/
   
   //TODO: create a save function
   
   //TODO: create a values function to store the UI element values
}