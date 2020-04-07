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
//********************
//GOOGLE IMPORTS
//********************
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static com.microsoft.schemas.office.excel.STTrueFalseBlank.Factory.newValue;
//********************
//JAVA IMPORTS
//********************
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

//********************
//JAVAFX IMPORTS
//********************
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;

//********************
//MEDUSA IMPORTS
//********************
import eu.hansolo.medusa.*;
import eu.hansolo.medusa.skins.DashboardSkin;
import eu.hansolo.medusa.skins.GaugeSkin;
import eu.hansolo.medusa.skins.HSkin;
import eu.hansolo.medusa.skins.PlainClockSkin;
import eu.hansolo.medusa.skins.SimpleDigitalSkin;
import eu.hansolo.medusa.skins.SlimClockSkin;
import eu.hansolo.medusa.skins.SlimSkin;
import java.io.FileReader;
import java.io.Reader;

//********************
//JAVA IMPORTS
//********************
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.CheckBoxListCell;

/**
 *
 * @author Alexandra
 * @author Brendan
 */
public class GUIPane extends Application {
    
    //This HashMap is used to map the nodes in the GridPane to ids, 
    //so that they can be identified later and their locations within
    //the GridPane written to a file
    private HashMap<String, Object> nodes = new HashMap<>();
    //This global variable will allow the GridPane to be accessed outside 
    //of the createBorderPane method
    private GridPane gridPane = null;
    
    //global variable for data frequency
    private Label dataFreq = new Label();
                       
    public static void main(String[] args) { 

        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();

        ToggleSwitch button = new ToggleSwitch();
        
        Scene scene = new Scene(createBorderPane(borderPane.getChildren()), 1000, 1000);
        scene.getStylesheets().add("/guipane/css/styles.css");

        primaryStage.setTitle("Gauge/Data Column Selection");

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public BorderPane createBorderPane(ObservableList<Node> children) {

        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderpane");

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
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
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

        //creating the save file initial functionality 
        MenuItem saveItem = new MenuItem("Save File");

        saveItem.setOnAction(e -> {
            try {
                saveFile(children);
            } catch (IOException ex) {
                Logger.getLogger(GUIPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //creating the load file initial functionality 
        MenuItem loadItem = new MenuItem("Load File");
        
        loadItem.setOnAction(e -> {
            try {
                loadFile(children);
            } catch (IOException ex) {
                Logger.getLogger(GUIPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        fileMenu.getItems().addAll(newItem, openItem,
                new SeparatorMenuItem(), saveItem, loadItem,
                new SeparatorMenuItem(), exitItem);

        //creating the Edit menu
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(new MenuItem("Undo"), new MenuItem("Redo"));

        //creating About window 
        MenuItem aboutItem = new MenuItem("About...");
        aboutItem.setOnAction((ActionEvent event) -> {
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
        });

        MenuItem helpItem = new MenuItem("Help Contents");
        helpItem.setOnAction((ActionEvent event) -> {
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
        });

        //creating the Help menu
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().addAll(aboutItem, new SeparatorMenuItem(), helpItem);

        //add all the menus to the menu bar
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        //ToolBar toolBar = new ToolBar(new Button("New"), new Button("Open"));
        VBox vbox = new VBox();

        //add toolbar here if wanted
        vbox.getChildren().addAll(menuBar);
        
        //*******************************
        //CREATING GAUGE LISTS + LISTENERS
        //*******************************
            
        ListView <Item> gaugeView = new ListView<>();
                
        Item toggleGauge = new Item("Toggle Gauge ", false);
        Item singleGauge = new Item("Single Character Gauge ", false);
        Item textGauge = new Item("Text Gauge ", false);
        Item barGauge = new Item("XY Plot Gauge ", false);
        Item speedGauge = new Item("Speedometer Gauge ", false);
        Item timeGauge = new Item("Timestamp Gauge ", false);            
        
        gaugeView.getItems().addAll(toggleGauge, singleGauge, textGauge, barGauge, speedGauge, timeGauge);        
                
            for (Item item : gaugeView.getItems()) 
            {    
                //observe item's on property and display message when true 
                toggleGauge.onProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(toggleGauge.getName() + "changed on state from "+wasOn+" to "+isNowOn);

                    ToggleSwitch toggleSwitch = new ToggleSwitch();
                    
                    if(isNowOn == true) {
                        if(!gridPane.getChildren().contains(toggleSwitch)){
                            //adding a toggle switch to the grid pane 
                            gridPane.add(toggleSwitch, 0, 0);
                            nodes.put("toggle", toggleSwitch);    

                            System.out.println("Adding toggle switch..." + gridPane.getChildren().contains(toggleSwitch));
                    
                    }
                    } else {
                        
                        gridPane.getChildren().forEach(node -> {
                            if(node instanceof ToggleSwitch) {
                                gridPane.getChildren().remove(node);
                            }
                        });
                        
                    }                       
                    
                });
                
                //observe item's on property and display message when true 
                timeGauge.onProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(timeGauge.getName() + "changed on state from "+wasOn+" to "+isNowOn);
                    //adding a toggle switch to the grid pane 
                    
                    Clock timestamp = new Clock();
                    timestamp.setSkin(new SlimClockSkin(timestamp));
                     
                    if(isNowOn == true) {
                        if(!gridPane.getChildren().contains(timestamp)){
                            gridPane.add(timestamp, 1, 0);
                            nodes.put("timestamp", timestamp);

                            System.out.println("Adding timestamp..." + gridPane.getChildren().contains(timestamp));

                        }
                        
                    } else {
                            gridPane.getChildren().forEach(node ->{
                                if(node instanceof Clock){
                                    gridPane.getChildren().remove(node);
                                }
                            
                            });
                    }                   
                    
                });    

                singleGauge.onProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(singleGauge.getName() + "changed on state from "+wasOn+" to "+isNowOn);
                    
                    //adding a single character display to the grid pane
                    TextField tf = new TextField();
                    PseudoClass centered = PseudoClass.getPseudoClass("centered");

                    Pattern validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");

                    TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter(), 0.0,
                            change -> {
                                String newText = change.getControlNewText();
                                if (validDoubleText.matcher(newText).matches()) {
                                    return change;
                                } else {
                                    return null;
                                }
                            });

                    tf.setTextFormatter(textFormatter);

                    tf.setFont(Font.font("times new roman", 70)); //setting the font and font size
                    
                    if(isNowOn == true) {
                        if(!gridPane.getChildren().contains(tf)) {
                            gridPane.add(tf, 1, 2);
                            nodes.put("tf", tf);                           
                        }
                    } else {
                            gridPane.getChildren().forEach(node ->{
                                if(node instanceof TextField){
                                    gridPane.getChildren().remove(node);
                                }
                            
                            });                    
                    }                   
                });

                textGauge.onProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(textGauge.getName() + "changed on state from "+wasOn+" to "+isNowOn);
                    
                    //adding text area to the grid pane
                    TextArea ta = new TextArea();  
                    
                    if(isNowOn == true) {
                        if(!gridPane.getChildren().contains(ta)) {
                            gridPane.add(ta, 0, 2);
                            nodes.put("ta", ta);                         
                        }
                    } else {
                        gridPane.getChildren().forEach(node -> {
                            if(node instanceof TextArea) {
                                gridPane.getChildren().remove(node);
                            }
                        });
                    }
                    
                });

                barGauge.onProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(barGauge.getName() + "changed on state from "+wasOn+" to "+isNowOn);
                    
                    //adding a line chart display to the grid pane 
                    //defining the x axis
                    NumberAxis lineX = new NumberAxis(1960, 2020, 10);
                    lineX.setLabel("Years");

                    //defining the y axis 
                    NumberAxis lineY = new NumberAxis(0, 350, 50);
                    lineY.setLabel("No. of schools");

                    //creating the line chart 
                    LineChart linePlot = new LineChart(lineX, lineY);

                    //setting the chart data 
                    //TODO: make dynamic 
                    XYChart.Series series = new XYChart.Series();
                    series.setName("No of schools in a year");

                    series.getData().add(new XYChart.Data(1970, 15));
                    series.getData().add(new XYChart.Data(1980, 30));
                    series.getData().add(new XYChart.Data(1990, 60));
                    series.getData().add(new XYChart.Data(2000, 120));
                    series.getData().add(new XYChart.Data(2013, 240));
                    series.getData().add(new XYChart.Data(2014, 300));

                    //setting the data to line chart 
                    linePlot.getData().add(series);

                    if(isNowOn == true) {
                      if (!gridPane.getChildren().contains(linePlot)) {
                        //adding the chart to grid pane 
                        gridPane.add(linePlot, 2, 0);
                        nodes.put("line", linePlot);                     
                      }  
                    } else {
                            gridPane.getChildren().forEach(node ->{
                                if(node instanceof LineChart){
                                    gridPane.getChildren().remove(node);
                                }
                            
                            });                    
                    }
                });  

                speedGauge.onProperty().addListener((obs, wasOn, isNowOn) -> {
                    System.out.println(speedGauge.getName() + " changed on state from "+wasOn+" to "+isNowOn);
                    
                    //creating a new speedometer 
                    Gauge gauge = new Gauge();
                    gauge.setSkin(new HSkin(gauge));
                    
                    if(isNowOn == true) {
                        if (!gridPane.getChildren().contains(gauge)) {
                            //adding the gauge to the grid pane 
                            gridPane.add(gauge, 2, 2);
                            nodes.put("gauge", gauge);                          
                        }
                    } else {
                        gridPane.getChildren().forEach(node -> {
                            if(node instanceof Gauge) {
                                gridPane.getChildren().remove(node);
                            }
                        });
                    }
              
                });            
            }
        
        gaugeView.setCellFactory(CheckBoxListCell.forListView(new Callback<Item, ObservableValue<Boolean>>() {
            @Override 
            public ObservableValue<Boolean> call(Item item) {
                    return item.onProperty();
        }
                }));
            
        //*******************************
        //CREATING DATA LISTS + LISTENERS
        //*******************************
        ListView <String> dataView = new ListView<>();
                
        //TODO: make dynamic
        String battery = "BATTERY";
        String pitch = "PITCH";
        String yaw = "YAW";
        String timestamp = "TIMESTAMP";
                    
        dataView.getItems().addAll(timestamp, pitch, yaw, battery);        
                  
        //create new tabpane on the left
        TabPane tbLeft = new TabPane();

        //set the tabs 
        Tab dataTab = new Tab("View Data Columns");
        Tab gaugeTab = new Tab("Select Gauges");

        //fill the tabs with content from the checkbox tree view item lists
        gaugeTab.setContent(gaugeView);
        dataTab.setContent(dataView);

        //add the tab pane content to the left tab pane
        tbLeft.getTabs().addAll(gaugeTab, dataTab);

        //********************************
        //GRID PANE 
        //********************************
        //Let's try and create a grid pane for the center
        gridPane = new GridPane();
        
        gridPane.setLayoutX(100);
        gridPane.setLayoutY(100);

        gridPane.setPrefSize(500, 250);
        gridPane.setGridLinesVisible(true);

        //setting the column constraints
        ColumnConstraints column1width = new ColumnConstraints(250);
        ColumnConstraints column2width = new ColumnConstraints(250);
        ColumnConstraints column3width = new ColumnConstraints(250);

        //setting the row constraints
        RowConstraints row1Height = new RowConstraints(285);
        RowConstraints row2Height = new RowConstraints(285);
        RowConstraints row3Height = new RowConstraints(285);

        gridPane.getColumnConstraints().addAll(column1width, column2width, column3width);
        gridPane.getRowConstraints().addAll(row1Height, row2Height, row3Height);
                
        //add text fields to hboxes 
        TextField speedField = new TextField("Speedometer Gauge");
        TextField areaField = new TextField("TextArea Gauge");
        TextField singleField = new TextField("Single Character Gauge");
        TextField toggleField = new TextField("On/Off Switch Gauge");
        TextField xyField = new TextField("XY Plot Gauge");
        TextField timeField = new TextField("Timestamp Gauge");
        
        //adding comboboxes to hboxes
        final ComboBox speedCombo = new ComboBox(dataView.getItems());
        final ComboBox areaCombo = new ComboBox(dataView.getItems());
        final ComboBox singleCombo = new ComboBox(dataView.getItems());
        final ComboBox toggleCombo = new ComboBox(dataView.getItems());
        final ComboBox xy1Combo = new ComboBox(dataView.getItems());
        final ComboBox timeCombo = new ComboBox(dataView.getItems());
        
        //whoo lad here we go 
        
        speedCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });  
        
        areaCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });
        
        singleCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });  
        
        toggleCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });      
        
        areaCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });
        
        xy1Combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });
        
        timeCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue obs, String t, String t1) {
                System.out.println(t1);
            }
        });

        
        HBox hbox1 = new HBox(); 
        HBox hbox2 = new HBox();
        
        hbox1.getChildren().addAll(toggleField, toggleCombo);
        hbox2.getChildren().addAll(areaField, areaCombo);
        
        HBox hbox3 = new HBox(); 
        HBox hbox4 = new HBox();
        
        hbox3.getChildren().addAll(timeField, timeCombo);
        hbox4.getChildren().addAll(singleField, singleCombo);
        
        HBox hbox5 = new HBox();
        HBox hbox6 = new HBox();
        
        hbox5.getChildren().addAll(xyField, xy1Combo);
        hbox6.getChildren().addAll(speedField, speedCombo);
        
        VBox vbox1 = new VBox();
        vbox1.setSpacing(220);
        vbox1.getChildren().addAll(hbox1, hbox2);
        
        VBox vbox2 = new VBox(); 
        vbox2.setSpacing(220);
        vbox2.getChildren().addAll(hbox3, hbox4);
        
        VBox vbox3 = new VBox();
        vbox3.setSpacing(220);
        vbox3.getChildren().addAll(hbox5, hbox6);
        
        gridPane.add(vbox1, 0, 1);  
        gridPane.add(vbox2, 1, 1);
        gridPane.add(vbox3, 2, 1);
        
        //ToggleButton centerText = new ToggleButton("Center All Text");

        //*****************************************
        //PLAYBACK BUTTONS 
        //TODO: place into seperate function 
        //*****************************************
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

        //time label 
        long endTime = 100000;

        Label timeStamp = new Label();
        timeStamp.setTextFill(Color.YELLOW);
        timeStamp.setPrefWidth(80);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(500),
                        event -> {
                            final long diff = endTime - System.currentTimeMillis();
                            if (diff < 0) {
                                //  timeLabel.setText( "00:00:00" );
                                timeStamp.setText(timeFormat.format(0));
                            } else {
                                timeStamp.setText(timeFormat.format(diff));
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
 
        //************************
        //DATA FREQUENCY 
        //************************        

        //create data frequency button 
        Button dataButton = new Button("Set Data Frequency");
        
        //open a new window when the toggle button is pressed
        dataButton.setOnAction((ActionEvent event) -> {
            Label dataLabel = new Label("Select data frequency:");
            
            HBox dataMenu = new HBox();
            
            dataMenu.setAlignment(Pos.CENTER);
            dataMenu.alignmentProperty().isBound();
            dataMenu.setSpacing(5);
            
            final ToggleGroup group = new ToggleGroup();
            
            RadioButton data1 = new RadioButton("1");
            data1.setToggleGroup(group);
            data1.setSelected(true);
            data1.requestFocus();
            
            RadioButton data2 = new RadioButton("1.5");
            data2.setToggleGroup(group);
            data2.setSelected(true);            
            data2.requestFocus();
            
            RadioButton data3 = new RadioButton("2");
            data3.setToggleGroup(group);
            data3.setSelected(true);            
            data3.requestFocus();
            
            RadioButton data4 = new RadioButton("-1");
            data4.setToggleGroup(group);
            data4.setSelected(true);            
            data4.requestFocus();
                                    
            Label labelInfo = new Label();
            labelInfo.setTextFill(Color.BLUE);
            
            dataFreq.setTextFill(Color.WHITE);
            dataFreq.setFont(new Font(15));
            dataFreq.getBorder();
            
            group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                    //has selection
                    if (group.getSelectedToggle() != null) {
                        RadioButton button = (RadioButton) group.getSelectedToggle();
                        System.out.println("Button: " + button.getText());
                        labelInfo.setText("Your new data frequency is: " + button.getText() + " update(s) per second");
                        dataFreq.setText("Current Data Frequency: " + button.getText() + " update(s) per second");
                        
                        
                    }
                }
            });
            
            dataMenu.getChildren().addAll(data4, data1, data2, data3);
            
            VBox secondaryLayout = new VBox();
            secondaryLayout.setSpacing(10);
            secondaryLayout.getChildren().addAll(dataLabel, dataMenu, labelInfo);
            
            Scene secondScene = new Scene(secondaryLayout, 330, 150);
            
            //new window
            Stage newWindow = new Stage();
            newWindow.setTitle("Data Frequency");
            newWindow.setScene(secondScene);
            
            //specify modality for the new window
            newWindow.initModality(Modality.WINDOW_MODAL);
            
            newWindow.show();
        });
                
        //display the current time property
        //TODO: add time listener to timestamp cell in spreadsheet
        //TODO: add updateValues() method 
        
        
        //add buttons to the playback menu
        //add centertext button here when fix
        playbackMenu.getChildren().addAll(reverseButton, playButton, pauseButton, oneXButton, fiveXButton, tenXButton, timeStamp, dataButton, dataFreq);

        //************************
        //BORDER PANE SETUP 
        //************************
        
        //setting up the border pane
        borderPane.setTop(vbox);
        borderPane.setLeft(tbLeft);
        borderPane.setCenter(gridPane);
        //borderPane.setCenter(new TextArea());
        //borderPane.setRight(tabPaneRight);
        borderPane.setBottom(playbackMenu);

        return borderPane;
    }


    //************************
    //SAVING FUNCTION 
    //************************
    
    private void saveFile(ObservableList<Node> children) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Current Layout");
        
        //setting the initial file chooser directory 
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

        //set the extension filters to only .xlsx and .csv files 
        //so only files with these extensions will be visible to the user
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File file = fileChooser.showSaveDialog(new Stage());
        
        //open extension filtered files
        HostServices hostServices = getHostServices();
        hostServices.showDocument(file.getAbsolutePath());        
        
        if (file != null) {
            
            //Create a new values object, in order to store and write the current
            //state of the GridPane's cells to a file
            Values values = new Values(gridPane);
            
            String charsetAsString = String.valueOf(StandardCharsets.UTF_8);
            
            //Write the array of node ids to a file using Gson
            try {
                Gson gson = new Gson();
                String output = gson.toJson(values.getCells());
                FileUtils.writeStringToFile(file, output, charsetAsString);
            } catch (IOException ex) {
                Logger.getLogger(GUIPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //************************
    //LOADING FUNCTION 
    //************************       
    
    private void loadFile(ObservableList<Node> children) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Previous Layout");
        
        //setting the initial file chooser directory 
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Documents"));

        //set the extension filters to only .xlsx and .csv files 
        //so only files with these extensions will be visible to the user
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File file = fileChooser.showOpenDialog(new Stage());
        
        //open extension filtered files
        HostServices hostServices = getHostServices();
        hostServices.showDocument(file.getAbsolutePath());         
        
        if (file != null) {
            
            String charsetAsString = String.valueOf(StandardCharsets.UTF_8);
            
            //Use Gson to read file into a string array
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<String[]>(){}.getType();
            String[] arr = null;
            try(Reader input = new FileReader(file)){
                arr = (String[]) gson.fromJson(input, String[].class);
            }
            
            //Clear the gridpane
            Iterator mapIter = nodes.entrySet().iterator();
            while(mapIter.hasNext()){
                Map.Entry i = (Map.Entry) mapIter.next();
                gridPane.getChildren().remove(i.getValue());
            }
            
            //Put the nodes into their appropriate positions within the GridPane
            //based on the locations of their ids within the array
            for(int i = 0; i < arr.length; i++){
                if(arr[i] != null){
                    int x = (i+1)/3;
                    int y = (i+1)%3;
                    if(y == 0){
                        gridPane.add((Node) nodes.get(arr[i]), x-1, 2);
                    }
                    else{
                        gridPane.add((Node) nodes.get(arr[i]), x, y-1);
                    }
                }
            }
        }
    }

    private class Values {
        
        String[] locations = new String[9];
        
        public Values(GridPane pane){
            //Get the current location of each node in the gridpane, and store its
            //corresponding id from the HashMap in the appropriate location in a string array
            pane.getChildren().forEach(node ->{
            Iterator mapIter = nodes.entrySet().iterator();
            while(mapIter.hasNext()){
                Map.Entry i = (Map.Entry) mapIter.next();
                if(i.getValue().equals(node)){
                    locations[(GridPane.getColumnIndex(node) * 3) + GridPane.getRowIndex(node)] = (String) i.getKey();
                }
            }
            });
        }
        
        //Return the array of node ids
        public String[] getCells() {
            return locations;
        }
    }
}
