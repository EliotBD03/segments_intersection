package be.ac.umons.firstg.segmentintersector.Pages;

import be.ac.umons.firstg.segmentintersector.Interfaces.ILambdaEvent;
import be.ac.umons.firstg.segmentintersector.Interfaces.IObjectGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import be.ac.umons.firstg.segmentintersector.components.SegmentsTable;
import be.ac.umons.firstg.segmentintersector.customUtil.CustomConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static be.ac.umons.firstg.segmentintersector.Temp.Parser.getSegmentsFromFile;

public class MainPage extends HBox
{
    // Primary Stage to open windows
    private Stage primaryStage;

    private final VBox leftPane;
    private final ScrollPane graphPane;
    private final HBox timelinePane;
    private final TabPane tabPane;


    // The currently shown tab
    private SegmentsTable segmentsTable;
    private GraphXY graph;
    private boolean tabClosed;
    private Tab currentTab;

    // Graph Settings
    private boolean hasChanged;

    private ObjectProperty<Double> xSizeInputs = new SimpleObjectProperty<>(600d);
    private ObjectProperty<Double> ySizeInputs = new SimpleObjectProperty<>(600d);
    private ObjectProperty<Double> xScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Double> yScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Integer> xNbLabelsInputs = new SimpleObjectProperty<>(10);
    private ObjectProperty<Integer> yNbLabelsInputs = new SimpleObjectProperty<>(10);


    // Map Settings
    private Label fileNameLabel;
    private CheckBox showGridInput;
    private File file;
    private boolean fileHasChanged = true;

    private Button addButton;
    private TextField x1PointInput = new TextField();
    private TextField y1PointInput = new TextField();
    private TextField x2PointInput = new TextField();
    private TextField y2PointInput = new TextField();

    public MainPage(Stage primaryStage)
    {
        // Create this HBox
        super();
        prefHeight(600);
        prefWidth(800);

        this.primaryStage = primaryStage;
        // Create children
        leftPane = new VBox();
        tabPane = new TabPane();
        // Resizes the left part with the main VBOX
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        getChildren().addAll(leftPane,tabPane);

        // Create leftPane Children
        timelinePane = new HBox(new Button("hiii"));
        graphPane = new ScrollPane();
        graphPane.setFitToWidth(true);
        graphPane.setFitToHeight(true);
        leftPane.getChildren().addAll(graphPane, timelinePane);
        //  Resize the graph pane with the leftPane
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        graph = new GraphXY(new Point(50,25), 600, 600, 10, 10, 10, 10, true);
        graphPane.setContent(graph);

        // Creating the TabPane
        createTabs();

    }

    private void createTabs()
    {
        int tabSize = 50;
        IObjectGen<Tab, Pair<String, String>> tabGen = data ->
        {
            Tab tab = new Tab();
            if(data.getValue() != null)
                tab.setGraphic(getIcon(data.getValue(), tabSize/1000f + 0.01f));
            // Create the content of the tab
            // Title
            HBox titleBox = new HBox();
            VBox content = new VBox();
            content.setPadding(new Insets(10,10,10,10));
            content.setSpacing(20);
            content.setVisible(false);
            content.managedProperty().bind(content.visibleProperty());
            content.setPrefHeight(600);
            content.setPrefWidth(300);
            content.setAlignment(Pos.TOP_CENTER);
            // Content of content
            //  Title as a text node
            Text text = new Text(data.getKey());
            text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

            text.setTextAlignment(TextAlignment.CENTER);
            Button button = new Button();
            button.setOnAction(e -> handleTabInteraction(true));
            titleBox.getChildren().addAll(text);

            //HBox.setHgrow(text, Priority.ALWAYS);
            content.getChildren().addAll(encapsNode(titleBox, button));
            tab.setContent(content);
            return tab;
        };




        // Create Graph Tab

        Tab graphTab = tabGen.createObject(new Pair<>("Graph Settings", "icons/GraphSettingsIcon.png"));
        setGraphSettings(graphTab);

        Tab mapTab = tabGen.createObject(new Pair<>("Map Settings","icons/MapSettingsIcon.png"));
        setMapSettings(mapTab);


        tabPane.getTabs().addAll(graphTab,
                                 mapTab,
                                 tabGen.createObject(new Pair<>("Gay Settings","icons/importantIcon.jpg")),
                                 tabGen.createObject(new Pair<>("???? Settings","icons/SpecialIcon.jpg")));
        tabPane.setTabMaxHeight(tabSize);
        tabPane.setTabMinHeight(tabSize);
        tabPane.setTabMaxWidth(tabSize);
        tabPane.setTabMinWidth(tabSize);
        tabPane.setSide(Side.LEFT);
        // Clear the starting selection
        tabPane.getSelectionModel().clearSelection();
        // Listen for interaction with the tabPane
        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> handleTabInteraction(false));
        //  Stops user from closing tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


    }

    private ImageView getIcon(String fromResourcePath, float size)
    {
        ImageView imageView = new ImageView(String.valueOf(getClass().getResource(fromResourcePath)));
        imageView.setScaleX(size);
        imageView.setScaleY(size);
        return imageView;
    }

    /**
     * Toggles on and off the left tab to show the settings depending on the argument and the state of the tabView
     * @param closing   if false, opens the tab if it was closed or updates the content
     *                  if true, closes the tab if it was opened
     */
    private void handleTabInteraction(boolean closing)
    {
        System.out.println("_-_-_");
        if(tabPane.getSelectionModel().getSelectedItem() == null)
        {
            System.out.println("Nothing was selected");
            return;
        }
        if(currentTab == null && !closing)
        {
            System.out.println("no tab is open and is opening: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab = tabPane.getSelectionModel().getSelectedItem();
            System.out.println("Selected: " + tabPane.getSelectionModel().getSelectedIndex());
            if(currentTab == null)
                return;
            currentTab.getContent().setVisible(true);
        }// If a tab is open and is closing
        else if (currentTab != null && closing)
        {
            System.out.println("a tab is open and is closing");
            currentTab.getContent().setVisible(false);
            System.out.println("Just Cleared !");
            tabPane.getSelectionModel().clearSelection();
            graphPane.requestFocus();

            System.out.println("What remains: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab = null;
        }
        // If a tab is open and is opening -> Close previous tab, show the content of the new one and change current tab
        else if (currentTab != null)
        {
            System.out.println("a tab is open and is opening");
            System.out.println("Selected: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab.getContent().setVisible(false);
            currentTab = tabPane.getSelectionModel().getSelectedItem();

            if(currentTab == null)
                return;
            currentTab.getContent().setVisible(true);
        }
        System.out.println("current tab: " + currentTab + " after "  + closing);
        System.out.println(xScaleInputs.get());
        // If no tab is open and is opening
        // If no tab is open and is closing -> nothing happens
    }


    /**
     * Create a double formatter for the given textField and assigns the desired property
     * @param tf        The textField
     * @param property  The property to assign
     */
    private void  setDoubleFormatter(TextInputControl tf, ObjectProperty<Double> property, Double current, double min, double max, ILambdaEvent<Double> event)
    {
        //
        StringConverter<Double> stringConverter = new CustomConverter<Double>(new DoubleStringConverter(), current, min, max);
        TextFormatter<Double> textFormatter = new TextFormatter<>(stringConverter);
        setFormatter(tf, property, textFormatter, event);
    }

    /**
     * Create an integer formatter for the given textField and assigns the desired property
     * @param tf        The textField
     * @param property  The property to assign
     */
    private void setIntegerFormatter(TextInputControl tf, ObjectProperty<Integer> property, ILambdaEvent<Integer> event)
    {
        StringConverter<Integer> stringConverter = new CustomConverter<Integer>(new IntegerStringConverter(), 1, 20);
        TextFormatter<Integer> textFormatter = new TextFormatter<>(stringConverter);
        setFormatter(tf, property, textFormatter, event);
    }

    /**
     * Assigns a formatter for the given textField and assigns the desired property
     * @param tf            The textField
     * @param property      The property to assign
     * @param textFormat    The format of the textField
     * @param <E>           The class of the formatter
     */
    private <E> void setFormatter(TextInputControl tf, ObjectProperty<E> property, TextFormatter<E>  textFormat, ILambdaEvent<E> event)
    {
        //tf.setOnKeyTyped(event);
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            //tf.setText(newValue);
            if (!Objects.equals(oldValue, newValue) && event != null)
                event.callMethod(textFormat.getValueConverter().fromString(newValue));

            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        textFormat.valueProperty().bindBidirectional(property);
        tf.setTextFormatter(textFormat);


    }

    /**
     * Calls the {@link MainPage#encapsNode(Node...)} method with a label and the textField
     * @param tf    The textfield to encapsulate, with the label coming from the textField prompt text
     * @return      The promised HBox
     */
    private HBox encapsTextField(TextField tf)
    {
        Label label = new Label(tf.getPromptText());
        return encapsNode(label, tf);
    }

    /**
     * Encapsulates nodes in a way that they are all placed uniformly on a row using
     * an {@link HBox}
     * @param nodes The nodes to encapsulate
     * @return  The promised HBox
     */
    private HBox encapsNode(Node... nodes)
    {
        HBox box = new HBox();
        box.setSpacing(10);
        if(nodes.length == 0)
            return box;
        AnchorPane left = new AnchorPane();
        AnchorPane other;
        HBox.setHgrow(left, Priority.ALWAYS);
        left.getChildren().add(nodes[0]);
        box.getChildren().add(left);
        for (int i = 1; i < nodes.length; i++)
        {
            other = new AnchorPane(nodes[i]);
            box.getChildren().add(other);
            if(i < nodes.length -1)
                HBox.setHgrow(other, Priority.ALWAYS);

        }
        return box;
    }


    //______________________________Tabs

    /**
     * Fills the graphTab with all the necessary input for the user
     * @param graphTab  The tab to fill
     */
    private void setGraphSettings(Tab graphTab)
    {
        IObjectGen<TextField, String> textFieldGen = data ->
        {
            TextField inputField = new TextField();
            inputField.setPromptText(data);
            return inputField;
        };

        VBox content = (VBox) graphTab.getContent();


        TextField inputField = textFieldGen.createObject("Size X");
        setDoubleFormatter(inputField, xSizeInputs, 100d, 100, 1000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Size Y");
        setDoubleFormatter(inputField, ySizeInputs, 100d, 100, 1000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));


        inputField = textFieldGen.createObject("Scale X");
        setDoubleFormatter(inputField, xScaleInputs, 100d, 100, 1000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));


        inputField = textFieldGen.createObject("Scale Y");
        setDoubleFormatter(inputField, yScaleInputs, 100d, 100, 1000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Legends X");
        setIntegerFormatter(inputField, xNbLabelsInputs, null);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Legends Y");
        setIntegerFormatter(inputField, yNbLabelsInputs, null);
        content.getChildren().add(encapsTextField(inputField));

        HBox box = new HBox();
        box.setSpacing(20);
        showGridInput = new CheckBox();
        showGridInput.setSelected(true);
        showGridInput.setOnAction(e -> hasChanged = true);
        Label text = new Label("Show Grid");
        box.getChildren().addAll(text, showGridInput);

        content.getChildren().add(box);

        // Draw graph button
        Button drawButton = new Button("Draw Graph");
        hasChanged =true;
        drawButton.setOnAction(e -> loadGraph());
        content.getChildren().add(drawButton);


    }

    /**
     * Fills the mapTab with all the necessary inputs for the user
     * @param mapTab The tab to fill
     */
    private void setMapSettings(Tab mapTab)
    {
        IObjectGen<TextField, String> textFieldGen = data ->
        {
            TextField inputField = new TextField();
            inputField.setPromptText(data);
            inputField.setPrefWidth(80);
            return inputField;
        };
        VBox outer = (VBox) mapTab.getContent();
        // Load File Content
        HBox loadFileContent = new HBox(20);
        loadFileContent.setAlignment(Pos.CENTER_LEFT);
        //loadFileContent.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        Button loadMapButton = new Button("Import");

        fileNameLabel = new Label("No file selected");
        loadMapButton.setOnAction(e -> showFiles());

        loadFileContent.getChildren().addAll(loadMapButton, fileNameLabel);

        // Current Map Content

        // Shared Content
        //      Add Segment
        VBox currentMapContent = new VBox();
        currentMapContent.setAlignment(Pos.CENTER);

        VBox inputBox = new VBox();
        currentMapContent.setSpacing(10);
        HBox point1 = new HBox();
        point1.setSpacing(10);
        x1PointInput = textFieldGen.createObject("X1");
        y1PointInput = textFieldGen.createObject("Y1");
        HBox point2 = new HBox();
        x2PointInput = textFieldGen.createObject("X2");
        y2PointInput = textFieldGen.createObject("Y2");
        point2.setSpacing(10);

        // Set Formatters -> current will be null
        setDoubleFormatter(x1PointInput, new SimpleObjectProperty<>(),null, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, this::changeAddButtonState);
        setDoubleFormatter(y1PointInput, new SimpleObjectProperty<>(), null, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, this::changeAddButtonState);

        setDoubleFormatter(x2PointInput, new SimpleObjectProperty<>(),null, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, this::changeAddButtonState);
        setDoubleFormatter(y2PointInput, new SimpleObjectProperty<>(),null, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, this::changeAddButtonState);

        point1.getChildren().addAll(encapsTextField(x1PointInput), encapsTextField(y1PointInput));
        point2.getChildren().addAll(encapsTextField(x2PointInput), encapsTextField(y2PointInput));

        inputBox.getChildren().addAll(point1, point2);
        inputBox.setSpacing(20);
        inputBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(inputBox, Priority.ALWAYS);

        addButton = new Button();
        addButton.setText("Add");
        //      The add button must be disabled at first
        addButton.setDisable(true);

        HBox addSegmentsBox = new HBox();
        addSegmentsBox.setAlignment(Pos.CENTER);
        addSegmentsBox.getChildren().addAll(inputBox, addButton);
        currentMapContent.getChildren().add(addSegmentsBox);


        //      Segments table
        segmentsTable = new SegmentsTable();

        currentMapContent.getChildren().add(segmentsTable);

        //      Export and Unload buttons
        Button exportButton = new Button("Export");
        Button unloadButton = new Button("Unload");
        unloadButton.setOnAction(e -> resetMap());
        HBox buttons = encapsNode(exportButton, unloadButton);
        buttons.setPadding(new Insets(0,20,0,20));
        currentMapContent.getChildren().add(buttons);


        VBox.setVgrow(outer,Priority.ALWAYS);
        VBox.setVgrow(segmentsTable,Priority.ALWAYS);
        outer.setAlignment(Pos.CENTER);
        VBox.setVgrow(currentMapContent, Priority.ALWAYS);
        //currentMapContent.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        outer.getChildren().addAll(loadFileContent, currentMapContent);
    }

    //______________Input Handlers__________________
    private void showFiles()
    {
        FileChooser fileChooser = new FileChooser();
        File selection = fileChooser.showOpenDialog(primaryStage);
        if(file == null || !file.equals(selection))
        {
            file = selection;
            fileNameLabel.setText(file.getName());
            fileHasChanged = true;
            loadMap();
        }
    }


    /**
     * Resets the graph and table
     */
    private void resetMap()
    {
        graph.resetGraph();
        segmentsTable.resetTable();
        fileNameLabel.setText("No file selected");
    }

    private void loadGraph()
    {
        // If the graph scales was changed, we need to redraw the entire graph
        if(hasChanged){
            loadMap();
            if(hasChanged){
                hasChanged = false;
                graph.updateSize(xSizeInputs.get(), ySizeInputs.get(),
                                xScaleInputs.get(), yScaleInputs.get(),
                                xNbLabelsInputs.get(), yNbLabelsInputs.get(),
                                showGridInput.isSelected());
            }
        }

    }

    private void loadMap()
    {
        if(fileHasChanged && file!= null)
        {
            // Reset
            resetMap();
            fileHasChanged = false;
            ArrayList<SegmentTMP> segmentTMPList = getSegmentsFromFile(file.getPath());
            segmentsTable.addAll(segmentTMPList);
            graph.addSegments(segmentTMPList);
        }
    }

    private void changeAddButtonState(Double newValue)
    {
        // If the new value is null we can directly show the button as turned off
        if (newValue == null)
        {
            addButton.setDisable(true);
            return;
        }
        // Cast the actual text data into a double value
        // If we get null, this means the string entered was incorrect or unfinished
        IObjectGen<Double,TextField> inputValGen = data-> (Double) data.getTextFormatter().getValueConverter().fromString(data.getText());
        // Button is disabled if one or more input is empty
        addButton.setDisable(inputValGen.createObject(x1PointInput) == null ||
                inputValGen.createObject(y1PointInput) == null ||
                inputValGen.createObject(x2PointInput) == null ||
                inputValGen.createObject(y2PointInput) == null) ;


    }
    private void addSegment()
    {

    }

}
