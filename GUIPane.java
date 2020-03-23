/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipane;

//********************
//APACHE IMPORTS
//********************
import org.apache.commons.io.*;
import org.apache.commons.io.FileUtils.*;

//********************
//GOOGLE IMPORTS
//********************
import com.google.gson.Gson;
import com.google.gson.*;

//********************
//JAVA IMPORTS
//********************
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//********************
//JAVAFX IMPORTS
//********************
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author Alexandra
 * @author Brendan
 */
public class GUIPane extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

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
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //set the initial inventory to Documents 
                //TODO: is there any other directory that makes sense?
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

                //set the extension filters to only .xlsx and .csv files 
                //so only files with these extensions will be visible to the user
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel/CSV Files (*.xlsx/*.csv)", "*.xlsx", "*.csv");
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
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

                //set the initial inventory to Documents 
                //TODO: is there any other directory that makes sense?
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

                //set the extension filters to only .xlsx and .csv files 
                //so only files with these extensions will be visible to the user
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
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
                new MenuItem("Save File"), new MenuItem("Save As..."), new SeparatorMenuItem(),
                new MenuItem("Load File"),
                new SeparatorMenuItem(), exitItem);

        //creating the Edit menu
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(new MenuItem("Undo"), new MenuItem("Redo"));

        //creating About window 
        MenuItem aboutItem = new MenuItem("About...");
        aboutItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
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
                String text = "The drone telemetry software project will interpret information from a data file,"
                        + "and the user will be able to choose from the listed fields of telemetry data to be displayed.\n\n"
                        + "The user will select up to ten fields--eleven, including the timestamp--and"
                        + "the corresponding statistics will be displayed.\n\n Gauges can be selected and named, based"
                        + "on given choices, to display individual data elements.\n\n These gauges can be customized"
                        + "with regards to the ranges (blue, green, yellow, and red), and include an alarm for data"
                        + "entering/exceeding the red range.\n\n The user may also choose from a selection of playback"
                        + "speeds, standard playback or reverse, arrange and resize gauges prior to playback, and"
                        + "save gauge setup for later retrieval.";

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
        helpItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
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
                new CheckBoxTreeItem<>("Bar Plot"),
                new CheckBoxTreeItem<>("XY Plot"),
                new CheckBoxTreeItem<>("X Plot"),
                new CheckBoxTreeItem<>("Circle"),
                new CheckBoxTreeItem<>("Number/Single Character"),
                new CheckBoxTreeItem<>("Text"),
                new CheckBoxTreeItem<>("Clock"),
                new CheckBoxTreeItem<>("Stopwatch"),
                new CheckBoxTreeItem<>("Running time"),
                new CheckBoxTreeItem<>("On/Off Light"));

        TreeView<String> tv = new TreeView<String>(gaugeRootItem);

        final Callback<TreeItem<String>, ObservableValue<Boolean>> getSelectedProperty
                = (TreeItem<String> item) -> {
                    if (item instanceof CheckBoxTreeItem<?>) {
                        return ((CheckBoxTreeItem<?>) item).selectedProperty();
                    }
                    return null;
                };

        tv.setCellFactory(p -> new CheckBoxTreeCell<>(getSelectedProperty));
        tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //add tree items for data columns 
        //TODO: make dynamic 
        CheckBoxTreeItem<String> dataRootItem = new CheckBoxTreeItem<>("Data Columns");
        dataRootItem.setExpanded(true);

        dataRootItem.getChildren().addAll(
                new CheckBoxTreeItem<>("BATTERY"),
                new CheckBoxTreeItem<>("YAW"),
                new CheckBoxTreeItem<>("PITCH"),
                new CheckBoxTreeItem<>("TIMESTAMP"));

        TreeView<String> tv2 = new TreeView<>(dataRootItem);

        tv2.setCellFactory(p2 -> new CheckBoxTreeCell<>(getSelectedProperty));
        tv2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //create new tabpane on the left
        TabPane tabPaneLeft = new TabPane();

        //set the tabs 
        Tab gaugeTab = new Tab("Select Gauges");
        Tab dataTab = new Tab("Select Data Columns");

        //fill the tabs with content from the checkbox tree view item lists
        gaugeTab.setContent(tv);
        dataTab.setContent(tv2);

        //add the tab pane content to the left tab pane
        tabPaneLeft.getTabs().addAll(gaugeTab, dataTab);

        //Let's try and create a grid pane for the center
        GridPane gridPane = new GridPane();

        //Here's where we can set up adding images
        Image image = new Image("File:images/BarGaugeImageSelection.jpg");

        //Create event handler to insert gauge image into center
        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<String>("Root");
        rootItem.setExpanded(true);

        final TreeView<String> tree = new TreeView<String>(gaugeRootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        for (int i = 0; i < 8; i++) {

            gaugeRootItem.selectedProperty().addListener((obs, oldVal, newVal) -> {
                System.out.println(gaugeRootItem.getValue() + " selection state: " + newVal);
            });
        }

        //setting the dropshadow effect for the playback buttons
        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(5.0);
        dropshadow.setOffsetX(5.0);
        dropshadow.setColor(Color.WHITE);

        HBox playbackMenu = new HBox();

        playbackMenu.setPadding(new Insets(20));
        playbackMenu.setAlignment(Pos.CENTER);
        playbackMenu.alignmentProperty().isBound();
        playbackMenu.setSpacing(5);
        playbackMenu.setStyle("-fx-background-color: Black");

        Image playImage = new Image(getClass().getResourceAsStream("/guipane/icons/Play.png"));
        Button playButton = new Button();
        playButton.setGraphic(new ImageView(playImage));
        playButton.setStyle("-fx-background-color: Black");

        playButton.setOnAction((ActionEvent e) -> {
            //handle approprilately 
        });

        playButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            playButton.setStyle("-fx-background-color: Black");
            playButton.setStyle("-fx-body-color: Black");
        });

        playButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            playButton.setStyle("-fx-background-color: Black");
        });

        //pause button functionality
        Image pauseImage = new Image(getClass().getResourceAsStream("/guipane/icons/Pause.png"));

        Button pauseButton = new Button();

        pauseButton.setGraphic(new ImageView(pauseImage));
        pauseButton.setStyle("-fx-background-color: Black");

        pauseButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            pauseButton.setStyle("-fx-background-color: Black");
            pauseButton.setStyle("-fx-body-color: Black");
        });
        pauseButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            pauseButton.setStyle("-fx-background-color: Black");
        });

        //back button functionality 
        Image reverseImage = new Image(getClass().getResourceAsStream("/guipane/icons/1x.png"));

        Button reverseButton = new Button();
        reverseButton.setGraphic(new ImageView(reverseImage));
        reverseButton.setStyle("-fx-background-color: Black");

        reverseButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            reverseButton.setStyle("-fx-background-color: Black");
            reverseButton.setStyle("-fx-body-color: Black");
        });

        reverseButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            reverseButton.setStyle("-fx-background-color: Black");
        });

        //TODO: forward button functionality 
        Image tenXImage = new Image(getClass().getResourceAsStream("/guipane/icons/10x.png"));

        Button tenXButton = new Button();
        tenXButton.setGraphic(new ImageView(tenXImage));
        tenXButton.setStyle("-fx-background-color: Black");

        tenXButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            tenXButton.setStyle("-fx-background-color: Black");
            tenXButton.setStyle("-fx-body-color: Black");
        });

        tenXButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            tenXButton.setStyle("-fx-background-color: Black");
        });

        //TODO: last button functionality 
        Image fiveXImage = new Image(getClass().getResourceAsStream("/guipane/icons/5x.png"));

        Button fiveXButton = new Button();
        fiveXButton.setGraphic(new ImageView(fiveXImage));
        fiveXButton.setStyle("-fx-background-color: Black");

        fiveXButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            fiveXButton.setStyle("-fx-background-color: Black");
            fiveXButton.setStyle("-fx-body-color: Black");
        });

        fiveXButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            fiveXButton.setStyle("-fx-background-color: Black");
        });

        //TODO: first button functionality 
        Image oneXImage = new Image(getClass().getResourceAsStream("/guipane/icons/1x.png"));

        Button oneXButton = new Button();
        oneXButton.setGraphic(new ImageView(oneXImage));
        oneXButton.setStyle("-fx-background-color: Black");

        oneXButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            oneXButton.setStyle("-fx-background-color: Black");
            oneXButton.setStyle("-fx-body-color: Black");
        });

        oneXButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            oneXButton.setStyle("-fx-background-color: Black");
        });

        //add buttons to the playback menu
        playbackMenu.getChildren().addAll(reverseButton, playButton, pauseButton, oneXButton, fiveXButton, tenXButton);

        //setting up the border pane
        borderPane.setTop(vbox);
        borderPane.setLeft(tabPaneLeft);
        borderPane.setCenter(gridPane);
        //borderPane.setCenter(new TextArea());
        //borderPane.setRight(tabPaneRight);
        borderPane.setBottom(playbackMenu);

        return borderPane;
    }
}

//***********************
//  VENTURE BEHIND THIS POINT AND RISK THY SANITY
//  PROCEED AT THY OWN RISK
//***********************
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

   
private void Save(ObservableList<Node> children) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save file:");
    
    File file = fileChooser.showSaveDialog(new Stage());
    if (file != null) {
        Values values = new Values();
        children.stream().filter(child -> child.getId() != null)
                .forEach(child -> { 
                    if (child instanceof gaugeRootItem) {
                        gaugeItem gaugecheck = (CheckBoxTreeItem) child; 
                        values.setGaugeBox(gaugecheck.getSelected()); 
                    } else if (child instanceof dataRootItem) {
                        dataRootItem datacheck = (CheckBoxTreeItem) child; 
                        values.setDataBox(datacheck.getSelected());
                    }
                });
        try {
            org.apache.commons.io.FileUtils.write(file, new Gson().toJson(values), StandardCharsets.UTF_8);
        } catch (IOException e) {
            //handle properly 
        }
    }
}
 
private static class Values {
    private CheckBoxTreeItem<String> gaugeItem = new CheckBoxTreeItem<>("Gauge List");
    private CheckBoxTreeItem<String> dataItem = new CheckBoxTreeItem<>("Gauge List");

    public CheckBoxTreeItem<String> 
}
 */
