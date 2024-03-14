package be.ac.umons.firstg.segmentintersector.Pages;

import be.ac.umons.firstg.segmentintersector.Interfaces.IObjectGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import be.ac.umons.firstg.segmentintersector.customUtil.CustomConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.Pair;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class MainPage extends HBox
{
    private final VBox leftPane;
    private final ScrollPane graphPane;
    private final HBox timelinePane;
    private final TabPane tabPane;

    // The currently shown tab
    private boolean tabClosed;
    private Tab currentTab;

    // Graph Settings
    private boolean hasChanged;
    private ObjectProperty<Double> xSizeInputs = new SimpleObjectProperty<>(300d);
    private ObjectProperty<Double> ySizeInputs = new SimpleObjectProperty<>(300d);
    private ObjectProperty<Double> xScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Double> yScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Integer> xNbLabelsInputs = new SimpleObjectProperty<>(5);
    private ObjectProperty<Integer> yNbLabelsInputs = new SimpleObjectProperty<>(5);

    public MainPage()
    {
        // Create this HBox
        super();
        prefHeight(600);
        prefWidth(800);

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
        GraphXY graph = new GraphXY(new Point(50,25), 600, 600, 10, 10, 10, 10, true);
        graphPane.setContent(graph);

        // Creating the TabPane
        createTabs();

        tabPane.getSelectionModel().select(1);

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

            //HBox.setHgrow(text, Priority.ALWAYS);
            content.getChildren().addAll(text,button);
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
        tabPane.getSelectionModel().clearSelection(3);

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
    private void  setDoubleFormatter(TextInputControl tf, ObjectProperty<Double> property)
    {
        //
        StringConverter<Double> stringConverter = new CustomConverter<Double>(new DoubleStringConverter(), 100d, 1000d);
        TextFormatter<Double> textFormatter = new TextFormatter<>(stringConverter);
        setFormatter(tf, property, textFormatter);
    }

    /**
     * Create an integer formatter for the given textField and assigns the desired property
     * @param tf        The textField
     * @param property  The property to assign
     */
    private void setIntegerFormatter(TextInputControl tf, ObjectProperty<Integer> property)
    {
        StringConverter<Integer> stringConverter = new CustomConverter<Integer>(new IntegerStringConverter(), 1, 20);
        TextFormatter<Integer> textFormatter = new TextFormatter<>(stringConverter);
        setFormatter(tf, property, textFormatter);
    }

    /**
     * Assigns a formatter for the given textField and assigns the desired property
     * @param tf            The textField
     * @param property      The property to assign
     * @param textFormat    The format of the textField
     * @param <E>           The class of the formatter
     */
    private <E> void setFormatter(TextInputControl tf, ObjectProperty<E> property, TextFormatter<E>  textFormat)
    {
        tf.setOnKeyTyped(e ->
        {
            this.hasChanged = true;
            System.out.println(e.getText());
        });
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            //tf.setText(newValue);
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });
        textFormat.valueProperty().bindBidirectional(property);
        tf.setTextFormatter(textFormat);


    }

    /**
     * Encapsulates the textfield into an HBox in a way that the label and the textField are taking all the line space
     * @param tf    The textfield to encapsulate, with the label coming from the textField prompt text
     * @return      The promised HBox
     */
    private HBox encapsTextField(TextField tf)
    {
        HBox box = new HBox();
        box.setSpacing(10);
        Label label = new Label(tf.getPromptText());
        AnchorPane left = new AnchorPane();
        HBox.setHgrow(left, Priority.ALWAYS);
        AnchorPane right = new AnchorPane();
        box.getChildren().addAll(left,right);
        left.getChildren().add(label);
        right.getChildren().add(tf);
        return box;
    }


    //______________________________Tabs
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
        setDoubleFormatter(inputField, xSizeInputs);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Size Y");
        setDoubleFormatter(inputField, ySizeInputs);
        content.getChildren().add(encapsTextField(inputField));


        inputField = textFieldGen.createObject("Scale X");
        setDoubleFormatter(inputField, xScaleInputs);
        content.getChildren().add(encapsTextField(inputField));


        inputField = textFieldGen.createObject("Scale Y");
        setDoubleFormatter(inputField, yScaleInputs);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Legends X");
        setIntegerFormatter(inputField, xNbLabelsInputs);
        content.getChildren().add(encapsTextField(inputField));

        inputField = textFieldGen.createObject("Legends Y");
        setIntegerFormatter(inputField, yNbLabelsInputs);
        content.getChildren().add(encapsTextField(inputField));

        HBox box = new HBox();
        box.setSpacing(20);
        CheckBox cb = new CheckBox();
        cb.setOnAction(e -> hasChanged = true);
        Label text = new Label("Show Grid");
        box.getChildren().addAll(text, cb);

        content.getChildren().add(box);
    }

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
        // No map Loaded Content
        VBox noMapContent = new VBox();
        noMapContent.setAlignment(Pos.CENTER);

        Text noMapText = new Text("No map loaded");
        noMapText.setFont(new Font(20));



        Button importButton = new Button("import");
        Button createButton = new Button("create");


        noMapContent.getChildren().addAll(noMapText, importButton, createButton);

        // Shared Content
        VBox shareContent = new VBox();

        VBox addSegments = new VBox();
        //shareContent.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        shareContent.setSpacing(10);
        HBox point1 = new HBox();
        point1.setSpacing(10);
        TextField x1 = textFieldGen.createObject("X1");
        TextField y1 = textFieldGen.createObject("Y1");
        HBox point2 = new HBox();
        TextField x2 = textFieldGen.createObject("X2");
        TextField y2 = textFieldGen.createObject("Y2");
        point2.setSpacing(10);

        //VBox.setVgrow(shareContent,Priority.ALWAYS);

        addSegments.setAlignment(Pos.CENTER);
        point1.getChildren().addAll(encapsTextField(x1), encapsTextField(y1));
        point2.getChildren().addAll(encapsTextField(x2), encapsTextField(y2));

        addSegments.getChildren().addAll(point1, point2);
        addSegments.setSpacing(20);
        HBox.setHgrow(addSegments, Priority.ALWAYS);


        //HBox.setHgrow(point2, Priority.ALWAYS);

        shareContent.getChildren().add(addSegments);
        outer.getChildren().add(shareContent);
    }
}
