package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.util.ArrayList;

import static be.ac.umons.firstg.segmentintersector.Temp.Parser.getSegmentsFromFile;

public class MainPage
{
    @FXML
    public ScrollPane GraphGroup;
    @FXML
    public BorderPane TableGroup;
    public StackPane TreeT;
    public StackPane TreeQ;
    public TextField xNbLabelsInputField;
    public TextField yNbLabelsInputField;
    public CheckBox showGridInput;
    public Button loadFileButton;

    // Graph Inputs
    @FXML
    private TextField ySizeInputField;
    @FXML
    private TextField yScaleInputField;
    @FXML
    private Button loadButton;
    @FXML
    private TextField xSizeInputField;
    @FXML
    private TextField xScaleInputField;

    private Stage primaryStage;
    private File file;
    private boolean fileHasChanged = true;

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    private ObjectProperty<Double> xSizeInputs = new SimpleObjectProperty<>(300d);
    private ObjectProperty<Double> ySizeInputs = new SimpleObjectProperty<>(300d);
    private ObjectProperty<Double> xScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Double> yScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Integer> xNbLabelsInputs = new SimpleObjectProperty<>(5);
    private ObjectProperty<Integer> yNbLabelsInputs = new SimpleObjectProperty<>(5);


    // Components added
    private GraphXY graph;


    private boolean hasChanged;


    public MainPage()
    {

        //textFormatter.valueProperty().bindBidirectional(va);
        // When
    }

    @FXML
    public void initialize()
    {
        // Forces the textfields to only accept integers values
        // Doesn't stop negative values !!!

        setDoubleFormatter(xSizeInputField, xSizeInputs);
        setDoubleFormatter(ySizeInputField, ySizeInputs);
        setDoubleFormatter(xScaleInputField, xScaleInputs);
        setDoubleFormatter(yScaleInputField, yScaleInputs);

        setIntegerFormatter(xNbLabelsInputField, xNbLabelsInputs);
        setIntegerFormatter(yNbLabelsInputField, yNbLabelsInputs);
        showGridInput.setOnAction(e -> hasChanged = true);



    }

    @FXML
    private void loadGraph()
    {
        // Creates graph and adds it to the side
        // If the graph scales was changed, we need to redraw the entire graph
        if(graph == null){
            createGraph();
        }else
        {
            loadMap();
            if(hasChanged){
                hasChanged = false;
                graph.updateSize(xSizeInputs.get(), ySizeInputs.get(), xScaleInputs.get(), yScaleInputs.get(), xNbLabelsInputs.get(), yNbLabelsInputs.get(), showGridInput.isSelected());
            }
        }


    }

    private void createGraph()
    {
        graph = new GraphXY(new Point(50,25), xSizeInputs.get(), ySizeInputs.get(), xScaleInputs.get(), yScaleInputs.get(), xNbLabelsInputs.get(), yNbLabelsInputs.get(), showGridInput.isSelected());
        GraphGroup.setContent(graph);

        loadMap();
        //ArrayList<SegmentTMP> segmentTMPList = getSegmentsFromFile("/home/foucart/Bureau/Git/segments_intersection/SegmentIntersector3000/src/main/java/be/ac/umons/firstg/segmentintersector/Temp/cartes/nocode.txt");
        /*
        //107.56 190.65 186.97 121.64
        Point P = new Point(107.56d, 190.65d);
        //System.out.println(P);
        SegmentTMP s1 = new SegmentTMP(P, new Point( 186.97d, 121.64d));
        //236.77 88.23 266.09 97.46
        SegmentTMP s2 = new SegmentTMP(new Point(236.77d, 88.23d), new Point( 266.09d, 97.46d));
        System.out.println(s1.getPoint1().equals(new Point(107.55999755859375 , 190.64999389648438)));
        graph.initializeSweep();
        graph.moveSweepLine(s1.getPoint1(), List.of(s1, s2),null, null);
        graph.moveSweepLine(s1.getPoint2(), null, List.of(s1), null);

         */
    }

    private void loadMap()
    {
        if(fileHasChanged && file!= null)
        {
            fileHasChanged = false;
            ArrayList<SegmentTMP> segmentTMPList = getSegmentsFromFile(file.getPath());
            graph.addSegments(segmentTMPList);
        }
    }


    private void setDoubleFormatter(TextField tf, ObjectProperty<Double> property)
    {
        tf.setOnKeyTyped(e -> this.hasChanged = true);
        TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter());
        textFormatter.valueProperty().bindBidirectional(property);
        tf.setTextFormatter(textFormatter);
    }
    private void setIntegerFormatter(TextField tf, ObjectProperty<Integer> property)
    {
        tf.setOnKeyTyped(e -> this.hasChanged = true);
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter());
        textFormatter.valueProperty().bindBidirectional(property);
        tf.setTextFormatter(textFormatter);
    }

    public void showFiles()
    {
        FileChooser fileChooser = new FileChooser();
        File selection = fileChooser.showOpenDialog(primaryStage);
        if(file == null || !file.equals(selection))
        {
            file = selection;
            loadFileButton.setText(file.getName());
            fileHasChanged = true;
        }
    }
}
