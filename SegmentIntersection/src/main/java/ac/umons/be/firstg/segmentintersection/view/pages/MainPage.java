package ac.umons.be.firstg.segmentintersection.view.pages;

import ac.umons.be.firstg.segmentintersection.controller.PlaneSweepIterable;
import ac.umons.be.firstg.segmentintersection.controller.utils.Generator;
import ac.umons.be.firstg.segmentintersection.model.ComparableSegment;
import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.view.components.*;
import ac.umons.be.firstg.segmentintersection.view.interfaces.ILambdaEvent;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IObjectGen;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IShapeGen;
import ac.umons.be.firstg.segmentintersection.view.utils.CustomConverter;
import ac.umons.be.firstg.segmentintersection.view.utils.Icon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ac.umons.be.firstg.segmentintersection.controller.utils.Parser;

public class MainPage extends HBox
{
    // Primary Stage to open windows
    private Stage primaryStage;

    private final VBox leftPane;
    private final ScrollPane graphPane;
    private final HBox timelinePane;
    private final TabPane tabPane;
    private Tab currentTab;


    private IntersectionsTable intersectionsTable;
    private SegmentsTable segmentsTable;

    // Graph Settings
    private GraphXY graph;
    private boolean hasChanged;

    // Inputs
    private int currentId = 1;
    private ObjectProperty<Double> xSizeInputs = new SimpleObjectProperty<>(600d);
    private ObjectProperty<Double> ySizeInputs = new SimpleObjectProperty<>(600d);
    private ObjectProperty<Double> xScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Double> yScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Integer> xNbLabelsInputs = new SimpleObjectProperty<>(10);
    private ObjectProperty<Integer> yNbLabelsInputs = new SimpleObjectProperty<>(10);

    // Sweep Line
    private Text currentX;
    private Text currentY;
    private Text currentIteration;
    private PlaneSweepIterable planeSweeps;
    private boolean isFastPlaying;
    private Timeline timeline;
    private KeyFrame keyFrame;
    private ChoiceBox<Double> speedChoice;
    private Button fastPlayButton;
    private Button nextStepButton;


    // Map Settings
    private Label fileNameLabel;
    private CheckBox showGridInput;
    private File inputFile;
    private boolean fileHasChanged = true;

    private Button addButton;
    private TextField x1PointInput = new TextField();
    private TextField y1PointInput = new TextField();
    private TextField x2PointInput = new TextField();
    private TextField y2PointInput = new TextField();

    private final int maxBfrWarningSegments = 200;


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
        timelinePane = new HBox();
        createTimeline(timelinePane);
        graphPane = new ScrollPane();
        graphPane.setFitToWidth(true);
        graphPane.setFitToHeight(true);
        leftPane.getChildren().addAll(graphPane, timelinePane);
        //  Resize the graph pane with the leftPane
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        graph = new GraphXY(new Point(50,25), 600, 600, 10, 10, 10, 10, true);
        //graph.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        graphPane.setContent(graph);

        // Creating the TabPane
        createTabs();
    }

    private void createTimeline(HBox timelinePane)
    {
        timelinePane.setAlignment(Pos.CENTER);
        timelinePane.setPadding(new Insets(10, 10, 10, 10));
        timelinePane.setSpacing(20);
        // Speed Button
        speedChoice = new ChoiceBox<>();

        speedChoice.getItems().addAll(0.25d, 0.5d, 1d, 2d, 4d, 6d, 8d, 10d);
        speedChoice.getSelectionModel().select(2);
        // Fast/ Pause play button
        fastPlayButton = createButton(50,"FastPlayIcon.png");
        fastPlayButton.setOnAction(e -> fastPlay());
        nextStepButton = createButton(50,"PlaybackButtonIcon.png");
        nextStepButton.setOnAction(e -> nextSL());
        Button restartButton = createButton(50,"RestartButtonIcon.png");
        restartButton.setOnAction(e -> resetGraphSweepLine());
        timelinePane.getChildren().addAll(speedChoice, fastPlayButton, nextStepButton, restartButton);
    }

    private void createTabs()
    {
        int tabSize = 50;
        IObjectGen<Tab, Pair<String, String>> tabGen = data ->
        {
            Tab tab = new Tab();
            if(data.getValue() != null)
                tab.setGraphic(Icon.getIcon(this.getClass(),data.getValue(), tabSize/1000f + 0.01f));
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

        Tab graphTab = tabGen.createObject(new Pair<>("Graph Settings", "GraphSettingsIcon.png"));
        setGraphSettings(graphTab);

        Tab mapTab = tabGen.createObject(new Pair<>("Map Settings","MapSettingsIcon.png"));
        setMapSettings(mapTab);

        Tab sweepLineTab = tabGen.createObject(new Pair<>("Sweep Line Algo. Info","SweepLineInfoIcon.png"));
        setSweepInfo(sweepLineTab);

        tabPane.getTabs().addAll(graphTab,
                                 mapTab,
                                 sweepLineTab,
                                 tabGen.createObject(new Pair<>("???? Settings","SpecialIcon.jpg")));
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



    /**
     * Toggles on and off the left tab to show the settings depending on the argument and the state of the tabView
     * @param closing   if false, opens the tab if it was closed or updates the content
     *                  if true, closes the tab if it was opened
     */
    private void handleTabInteraction(boolean closing)
    {
        //System.out.println("_-_-_");
        if(tabPane.getSelectionModel().getSelectedItem() == null)
        {
            //System.out.println("Nothing was selected");
            return;
        }
        if(currentTab == null && !closing)
        {
            //System.out.println("no tab is open and is opening: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab = tabPane.getSelectionModel().getSelectedItem();
            //System.out.println("Selected: " + tabPane.getSelectionModel().getSelectedIndex());
            if(currentTab == null)
                return;
            currentTab.getContent().setVisible(true);
        }// If a tab is open and is closing
        else if (currentTab != null && closing)
        {
            //System.out.println("a tab is open and is closing");
            currentTab.getContent().setVisible(false);
            //System.out.println("Just Cleared !");
            tabPane.getSelectionModel().clearSelection();
            graphPane.requestFocus();

            //System.out.println("What remains: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab = null;
        }
        // If a tab is open and is opening -> Close previous tab, show the content of the new one and change current tab
        else if (currentTab != null)
        {
            //System.out.println("a tab is open and is opening");
            //System.out.println("Selected: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab.getContent().setVisible(false);
            currentTab = tabPane.getSelectionModel().getSelectedItem();

            if(currentTab == null)
                return;
            currentTab.getContent().setVisible(true);
        }
        //System.out.println("current tab: " + currentTab + " after "  + closing);
        //System.out.println(xScaleInputs.get());
        // If no tab is open and is opening
        // If no tab is open and is closing -> nothing happens
    }


    //______________________________Formatters
    /**
     * Create a double formatter for the given textField and assigns the desired property
     * @param tf        The textField
     * @param property  The property to assign
     */
    private void  setDoubleFormatter(TextInputControl tf, ObjectProperty<Double> property, Double current, double min, double max, ILambdaEvent<Double> event)
    {
        //
        StringConverter<Double> stringConverter = new CustomConverter<>(new DoubleStringConverter(), current, min, max);
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

            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
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
        //box.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
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

    private Button createButton(int buttonSize, String iconName)
    {
        Button button = new Button();
        button.setPrefSize(buttonSize,buttonSize);
        button.setMinSize(buttonSize,buttonSize);
        button.setMaxSize(buttonSize,buttonSize);
        Icon.setButtonIcon(this.getClass(), iconName, button);
        return button;
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
        setDoubleFormatter(inputField, xSizeInputs, 100d, 100, 100000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Size Y");
        setDoubleFormatter(inputField, ySizeInputs, 100d, 100, 100000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));


        inputField = textFieldGen.createObject("Scale X");
        setDoubleFormatter(inputField, xScaleInputs, 100d, 1, 100000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));


        inputField = textFieldGen.createObject("Scale Y");
        setDoubleFormatter(inputField, yScaleInputs, 100d, 1, 100000, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Legends X");
        setIntegerFormatter(inputField, xNbLabelsInputs, event -> hasChanged = true);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Legends Y");
        setIntegerFormatter(inputField, yNbLabelsInputs, event -> hasChanged = true);
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
        drawButton.setOnAction(e ->
        {
            try
            {
                loadGraph();
            } catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        });
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
        loadMapButton.setOnAction(e ->
        {
            try
            {
                showFiles();
            } catch (Exception ex)
            {
                throw new RuntimeException(ex);
            }
        });

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
        addButton.setOnAction(e -> addSegment());

        HBox addSegmentsBox = new HBox();
        addSegmentsBox.setAlignment(Pos.CENTER);
        addSegmentsBox.getChildren().addAll(inputBox, addButton);
        currentMapContent.getChildren().add(addSegmentsBox);


        //      Segments table
        segmentsTable = new SegmentsTable(graph);
        segmentsTable.setRemoveSegmentEvent(this::removeSegment);
        currentMapContent.getChildren().add(segmentsTable);

        //      Export and Unload buttons
        Button exportButton = new Button("Export");
        Button unloadButton = new Button("Unload");
        unloadButton.setOnAction(e -> resetMap());
        exportButton.setOnAction(e -> exportSegments());
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

    private void setSweepInfo(Tab sweepLineTab)
    {
        VBox outer = (VBox) sweepLineTab.getContent();
        outer.setSpacing(20);
        // Add current iteration info
        VBox textInfoBox = new VBox();
        textInfoBox.setSpacing(20);
        textInfoBox.setPadding(new Insets(20, 10, 0, 0));

        currentIteration = new Text("/");
        textInfoBox.getChildren().add(encapsNode(new Text("Iteration: "), currentIteration));

        Text currPointText = new Text("Current Event-Point:");
        HBox currPointBox = new HBox(currPointText);
        //currPointBox.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
        currentX = new Text("x: /");
        currentY = new Text("y: /");
        VBox pointBox = new VBox(currentX, currentY);

        VBox.setVgrow(currPointText, Priority.ALWAYS);
        HBox.setHgrow(currPointText, Priority.ALWAYS);

        textInfoBox.getChildren().add(encapsNode(currPointBox, pointBox));
        outer.getChildren().add(textInfoBox);

        //      Segments table
        int maxSegmentsDisplayable = 200;
        intersectionsTable = new IntersectionsTable(graph);
        VBox.setVgrow(intersectionsTable, Priority.ALWAYS);
        outer.getChildren().add(intersectionsTable);
        //      Tree Buttons
        Button statusTreeButton = createButton(50,"BTreeIcon.png");
        statusTreeButton.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        statusTreeButton.setOnAction(e -> showStatusTree());
        Button pointQueueButton = createButton(50,"BTreeIcon.png");
        pointQueueButton.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        pointQueueButton.setOnAction(e -> showPointQueueTree());
        Button exportIntersections = new Button("Export \nIntersections");
        exportIntersections.setOnAction(e -> exportIntersections());

        HBox lastRowBox = new HBox();
        lastRowBox.setSpacing(20);

        lastRowBox.setAlignment(Pos.CENTER);
        lastRowBox.getChildren().addAll(exportIntersections, statusTreeButton, pointQueueButton);


        outer.getChildren().add(lastRowBox);



    }

    //______________Input Handlers__________________

    private void showFiles()
    {
        FileChooser fileChooser = new FileChooser();
        File selection = fileChooser.showOpenDialog(primaryStage);
        if(selection != null)
        {
            if(inputFile != null && inputFile.equals(selection))
                return;
            inputFile = selection;
            fileNameLabel.setText(inputFile.getName());
            fileHasChanged = true;
            loadMap();
        }
    }

    private String getOutputFile()
    {
        FileChooser fileChooser = new FileChooser();
        File selection = fileChooser.showSaveDialog(primaryStage);
        if(selection != null)
            return selection.getPath();
        return null;
    }
    private void exportSegments()
    {
        String outputPath = getOutputFile();
        if(outputPath != null)
            Parser.saveSegments(new ArrayList<>(graph.getSegments()), getOutputFile());
    }

    private void exportIntersections()
    {
        String outputPath = getOutputFile();
        if(outputPath != null)
            Parser.saveIntersections(intersectionsTable.getIntersections(), outputPath);
    }

    /**
     * Resets the graph and table
     */
    private void resetMap()
    {
        graph.resetGraph();
        segmentsTable.resetTable();
        currentId = 1;
        fileNameLabel.setText("No file selected");
    }

    private void loadGraph() throws Exception
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


    /**
     * Loads the segments contained in the selected file if it was changed since last call
     */
    private void loadMap()
    {
        if(fileHasChanged && inputFile != null)
        {
            fileHasChanged = false;
            try
            {
                resetMap();
                Parser parser = new Parser(inputFile.getPath(), currentId);
                ArrayList<Segment> segmentsList = parser.getSegmentsFromFile();

                if(!speedChoice.getItems().contains(10d))
                    speedChoice.getItems().add(10d);

                // Get next id
                currentId = parser.getCurrent();

                if(currentId > maxBfrWarningSegments)
                {
                    new Alert(Alert.AlertType.WARNING, "Beware that there are too many segments.\nThis will take a lot of memory").show();
                    speedChoice.getItems().removeLast();
                }
                segmentsTable.addAll(segmentsList);
                graph.addSegments(segmentsList);
                resetGraphSweepLine();

            }catch (Exception e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was a problem while trying to read the file: " + (inputFile != null ? inputFile.getName(): "\"\" ") + "\n\n"
                            + e);
                alert.setTitle("Invalid File");
                alert.show();
            }
        }
    }

    /**
     * Change the addButton state, the button will be enabled if all the input fields are correctly filled
     * @param newValue  The value received from the text field
     */
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

    /**
     * Add the segment from the textFields. Since this method is called when the add button is enabled,
     * we can suppose that all fields are filled with correct values. (i.e. no null inputs)
     */
    private void addSegment()
    {
        IObjectGen<Double,TextField> getValueGen = data -> (Double) data.getTextFormatter().getValue();
        Point point1 = new Point(getValueGen.createObject(x1PointInput), getValueGen.createObject(y1PointInput));
        Point point2 = new Point(getValueGen.createObject(x2PointInput), getValueGen.createObject(y2PointInput));

        Segment segmentTMP = new Segment(point1, point2, "s_" + currentId);
        currentId ++;
        // Add segment to the graph and the table
        graph.addSegments(List.of(segmentTMP));
        segmentsTable.addSegment(segmentTMP);

        // Reset Sweep line algo
        resetGraphSweepLine();

        x1PointInput.setText("");
        x2PointInput.setText("");
        y1PointInput.setText("");
        y2PointInput.setText("");
    }

    /**
     * Removes a segment from the graph
     * @param segment the segment to remove from the graph
     */
    private void removeSegment(Segment segment)
    {
        graph.removeSegmentFrom(segment);
        resetGraphSweepLine();
    }


    //______________Sweep Line Algo__________________

    private void createSweepLine() throws Exception
    {
        ArrayList<Segment> segments = new ArrayList<>(graph.getSegments());
        if(segments.isEmpty())
            return;
        planeSweeps = new PlaneSweepIterable(segments);
        currentIteration.setText("/");
        // Init Sweep
        graph.initializeSweep();
    }


    private void nextSL()
    {
        if(planeSweeps == null)
        {
            try
            {
                createSweepLine();
                intersectionsTable.resetTable();
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            if(planeSweeps.iterator().hasNext())
            {
                // Next Sweep iteration using U L and C
                try
                {
                    Point inter = planeSweeps.iterator().next().getIntersection();

                    if(inter != null)
                    {
                        System.out.println(inter.getIntersections());
                        intersectionsTable.addIntersection(inter);
                    }
                    graph.moveSweepLine(planeSweeps.getPlaneSweep().getCurrentPoint(), planeSweeps.getPlaneSweep().getUpper(), planeSweeps.getPlaneSweep().getLower(), planeSweeps.getPlaneSweep().getInner());
                    if(!isFastPlaying)
                    {
                        updateSweepInfo();
                    }
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "There was a problem during the sweepLine algorithm" + "\n\n"
                            + e);
                    alert.setTitle("Sweep Line Algorithm Error");
                    alert.show();
                    // Stop sweep line execution
                    resetGraphSweepLine();
                }
            }
            else
            {
                graph.moveSweepLine(planeSweeps.getPlaneSweep().getCurrentPoint(), null, null, null);
                planeSweeps = null;
            }
        }
    }

    private void updateSweepInfo()
    {
        if(planeSweeps != null && planeSweeps.getPlaneSweep().getCurrentPoint()!= null)
        {
            currentX.setText("x: " + String.format("%5f",planeSweeps.getPlaneSweep().getCurrentPoint().x));
            currentY.setText("y: " + String.format("%5f",planeSweeps.getPlaneSweep().getCurrentPoint().y));
            currentIteration.setText(planeSweeps.getIterationCount() + "");
        }else
        {
            currentX.setText("x: /");
            currentY.setText("y: /");
        }
    }

    private void fastPlay()
    {
        if(!isFastPlaying)
        {
            isFastPlaying = true;
            // Change icon to pauseIcon
            Icon.setButtonIcon(this.getClass(), "PauseIcon.png", fastPlayButton);
            if(timeline == null)
            {
                timeline = new Timeline();
                timeline.setCycleCount(Animation.INDEFINITE);
            }
            currentX.setText("x: ...");
            currentY.setText("y: ...");
            currentIteration.setText("...");
            keyFrame = new KeyFrame(Duration.seconds(0.5d / speedChoice.getValue()), event ->
            {
                nextSL();
                // If it's over, stop fast play
                if(planeSweeps == null || !planeSweeps.iterator().hasNext())
                {
                    nextSL();
                    stopFastPlay();
                }
            });
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
            // Disable speed choice during animation so to not confuse the user
            // And the regular next step button
            speedChoice.setDisable(true);
            nextStepButton.setDisable(true);
        }

        else
        {
            stopFastPlay();
        }
    }
    private void stopFastPlay()
    {
        if(timeline == null)
            return;
        isFastPlaying = false;
        timeline.stop();
        // Switch back icon to fastPlayIcon
        Icon.setButtonIcon(this.getClass(), "FastPlayIcon.png", fastPlayButton);
        speedChoice.setDisable(false);
        nextStepButton.setDisable(false);
        updateSweepInfo();
    }

    private void resetGraphSweepLine()
    {
        // Stop current animation if any
        stopFastPlay();
        graph.resetSweepLine();
        try
        {
            createSweepLine();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        // Reset intersection table
        intersectionsTable.resetTable();
        // Reset current X and Y info
        updateSweepInfo();

    }

    private void randomMap()
    {
        Generator generator = new Generator(10);
        ArrayList<Segment> segments = generator.generate();
        segmentsTable.addAll(segments);
        graph.addSegments(segments);
    }


    public void showStatusTree()
    {
        IShapeGen<ComparableSegment> genLeaf = x -> new TreeNode(x,false, Color.AQUA);
        IShapeGen<ComparableSegment> genNode = x -> new TreeNode(x,true);

        if(planeSweeps != null)
            Tree.showTree("Status Queue", planeSweeps.getPlaneSweep().getStatusQueue(), genNode, genLeaf, false);
    }

    public void showPointQueueTree()
    {
        IShapeGen<Point> genNode = x -> new TreeNode(x, Color.ORANGE);
        if(planeSweeps != null)
            Tree.showTree("Event Point Queue", planeSweeps.getPointQueue(), genNode, genNode, false);
    }

}
