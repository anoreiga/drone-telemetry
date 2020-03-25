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
import com.thoughtworks.xstream.XStream;

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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
import eu.hansolo.medusa.skins.GaugeSkin;
import eu.hansolo.medusa.skins.PlainClockSkin;
import eu.hansolo.medusa.skins.SlimSkin;

//********************
//JAVA IMPORTS
//********************
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Alexandra
 * @author Brendan
 */
public class GUIPane extends Application {
    
    private String[] cells = new String[9];
    private HashMap<String, Object> nodes = new HashMap<>();
    private GridPane gridPane = null;

    public static void main(String[] args) { 

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane borderPane = new BorderPane();

        ToggleSwitch button = new ToggleSwitch();
        
        Scene scene = new Scene(createBorderPane(borderPane.getChildren()), 1000, 1000);
        scene.getStylesheets().add("/guipane/css/styles.css");

        stage.setTitle("Gauge/Data Column Selection");

        stage.setScene(scene);
        stage.show();

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
                new CheckBoxTreeItem<String>("BATTERY"),
                new CheckBoxTreeItem<String>("YAW"),
                new CheckBoxTreeItem<String>("PITCH"),
                new CheckBoxTreeItem<String>("TIMESTAMP"));

        //TreeView<String> tv2 = new TreeView<>(dataRootItem);
        //tv2.setCellFactory(p2 -> new CheckBoxTreeCell<>(getSelectedProperty));
        //tv2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dataRootItem.addEventHandler(
                CheckBoxTreeItem.<Path>checkBoxSelectionChangedEvent(),
                (TreeModificationEvent<Path> e) -> {
                    CheckBoxTreeItem<Path> item = e.getTreeItem();
                    if (item.isSelected() || item.isIndeterminate()) {
                        System.out.println("Some items are checked");
                    } else {
                        System.out.println("Some items are unchecked");
                    }
                });

        TreeView<String> tv2 = new TreeView<>(dataRootItem);
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

        //********************************
        //GRID PANE 
        //********************************
        //Let's try and create a grid pane for the center
        gridPane = new GridPane();
        //setting grid pane lines visible 
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

        //adding text area to the grid pane
        TextArea ta = new TextArea();

        //adding the BarGauge to the grid display 
        //x and y axis
        final String A = "Height [h]";
        final String B = "Horizontal Speed [m/h]";
        final String C = "Vertical Speed [m/v]";

        final NumberAxis x = new NumberAxis();
        final CategoryAxis y = new CategoryAxis();
//create bar chart
        final BarChart<Number, String> b
                = new BarChart<Number, String>(x, y);
        b.setTitle("Flight Data (Units: Metric)");
//set title for x axis
        x.setLabel("Category");
        x.setTickLabelRotation(90);
//set title for y axis
        y.setLabel("Measurement");
//dataset on 1999
        XYChart.Series s1 = new XYChart.Series();
        s1.setName("Height [h]");
        s1.getData().add(new XYChart.Data(10, A));
        s1.getData().add(new XYChart.Data(60, B));
        s1.getData().add(new XYChart.Data(30, C));
//dataset on 2009
        XYChart.Series s2 = new XYChart.Series();
        s2.setName("Horizontal Speed [m/h]");
        s2.getData().add(new XYChart.Data(50, A));
        s2.getData().add(new XYChart.Data(30, C));
        s2.getData().add(new XYChart.Data(20, B));
//dataset on 2019
        XYChart.Series S3 = new XYChart.Series();
        S3.setName("Vertical Speed [m/v]");
        S3.getData().add(new XYChart.Data(70, A));
        S3.getData().add(new XYChart.Data(25, B));
        S3.getData().add(new XYChart.Data(5, C));

        b.getData().addAll(s1, s2, S3);

        gridPane.add(b, 0, 2);
        cells[2] = "bar";
        nodes.put("bar", b);

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

        //adding the chart to grid pane 
        gridPane.add(linePlot, 1, 0);
        cells[3] = "line";
        nodes.put("line", linePlot);
        //creating a new clock
        Clock clock = new Clock();

        clock.setSkin(new PlainClockSkin(clock));

        //TODO: make dynamic
        clock.setTitle("Speedometer");
        //gauge.setUnit("MPH");

        //adding the clock to grid pane 
        gridPane.add(clock, 1, 1);
        cells[4] = "clock";
        nodes.put("clock", clock);
        
        //adding a toggle switch to the grid pane 
        ToggleSwitch button = new ToggleSwitch();
        gridPane.add(button, 2, 1);
        cells[7] = "button";
        nodes.put("button", button);
        
        //adding a circle gauge
        Gauge circle = new Gauge();
        circle.setSkin(new SlimSkin(circle));

        gridPane.add(circle, 2, 0);
        cells[6] = "circle";
        nodes.put("circle", circle);

        //creating a new speedometer 
        Gauge gauge = new Gauge();
        gauge.setSkin(new GaugeSkin(gauge));

        gridPane.add(gauge, 1, 2);
        cells[5] = "gauge";
        nodes.put("gauge", gauge);

        //adding a single character display to the grid pane
        TextArea tf = new TextArea();
        PseudoClass centered = PseudoClass.getPseudoClass("centered");

        Pattern validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");

        TextFormatter<Double> textFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0,
                change -> {
                    String newText = change.getControlNewText();
                    if (validDoubleText.matcher(newText).matches()) {
                        return change;
                    } else {
                        return null;
                    }
                });

        tf.setTextFormatter(textFormatter);

        textFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("New double value " + newValue);
        });

        tf.setFont(Font.font("times new roman", 70)); //setting the font and font size

        ToggleButton centerText = new ToggleButton("Center All Text");

        centerText.selectedProperty().addListener((obs, wasCentered, isNowCentered)
                -> tf.pseudoClassStateChanged(centered, isNowCentered));

        centerText.selectedProperty().addListener((obs, wasCentered, isNowCentered)
                -> ta.pseudoClassStateChanged(centered, isNowCentered));

        gridPane.add(tf, 0, 1);
        cells[1] = "tf";
        nodes.put("tf", tf);
        
        gridPane.add(ta, 0, 0);
        cells[0] = "ta";
        nodes.put("ta", ta);
        
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

        //display the current time property
        //TODO: add time listener to timestamp cell in spreadsheet
        //TODO: add updateValues() method 
        //add buttons to the playback menu
        playbackMenu.getChildren().addAll(reverseButton, playButton, pauseButton, oneXButton, fiveXButton, tenXButton, timeStamp, centerText);

        //************************
        //BORDER PANE SETUP 
        //************************
        //setting up the border pane
        borderPane.setTop(vbox);
        borderPane.setLeft(tabPaneLeft);
        borderPane.setCenter(gridPane);
        //borderPane.setCenter(new TextArea());
        //borderPane.setRight(tabPaneRight);
        borderPane.setBottom(playbackMenu);

        return borderPane;
    }

//***********************
//  VENTURE BEHIND THIS POINT AND RISK THY SANITY
//  PROCEED AT THY OWN RISK
//***********************
//saving function 
    
    
    private void saveFile(ObservableList<Node> children) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Current Layout");

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            Values values = new Values();
            
            String charsetAsString = String.valueOf(StandardCharsets.UTF_8);
            try {
                XStream xstream = new XStream();
                String output = xstream.toXML(values.getCells());
                FileUtils.writeStringToFile(file, output, charsetAsString);
            } catch (IOException ex) {
                Logger.getLogger(GUIPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
       private void loadFile(ObservableList<Node> children) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Previous Layout");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            // handle properly
            
            String charsetAsString = String.valueOf(StandardCharsets.UTF_8);
            
            XStream xstream = new XStream();
                        
            String[] arr = (String[]) xstream.fromXML(file);
            
            Iterator mapIter = nodes.entrySet().iterator();
            while(mapIter.hasNext()){
                Map.Entry i = (Map.Entry) mapIter.next();
                gridPane.getChildren().remove(i.getValue());
            }
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

        String[] locations = cells;

        public String[] getCells() {
            return locations;
        }
    }
}
