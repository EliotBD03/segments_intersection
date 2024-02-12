package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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


    private ObjectProperty<Double> xSizeInputs = new SimpleObjectProperty<>(300d);
    private ObjectProperty<Double> ySizeInputs = new SimpleObjectProperty<>(300d);
    private ObjectProperty<Double> xScaleInputs = new SimpleObjectProperty<>(10d);
    private ObjectProperty<Double> yScaleInputs = new SimpleObjectProperty<>(10d);

    // Components added
    private GraphXY graph;


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

        setTextFormatter(xSizeInputField, xSizeInputs);
        setTextFormatter(ySizeInputField, ySizeInputs);
        setTextFormatter(xScaleInputField, xScaleInputs);
        setTextFormatter(yScaleInputField, yScaleInputs);


    }

    @FXML
    private void loadGraph()
    {
        // Creates graph and adds it to the side
        // If the graph scales was changed, we need to redraw the entire graph
        if(graph == null){
            createGraph();
        }else if (graph.getSizePixelAxisX() != xSizeInputs.get() || graph.getSizePixelAxisY() != ySizeInputs.get())
        {
            graph.updateSize(xSizeInputs.get(), ySizeInputs.get());
        }
        // TODO add a way to load files

    }

    private void createGraph()
    {
        graph = new GraphXY(new Point(50,25), xSizeInputs.get(), ySizeInputs.get(), xScaleInputs.get(), yScaleInputs.get(),  5, 5, true);
        GraphGroup.setContent(graph);
        ArrayList<SegmentTMP> segmentTMPList = getSegmentsFromFile("/home/foucart/Bureau/Git/segments_intersection/SegmentIntersector3000/src/main/java/be/ac/umons/firstg/segmentintersector/Temp/cartes/fichier2.txt");
        graph.addSegments(segmentTMPList);
    }


    private static void setTextFormatter(TextField tf, ObjectProperty<Double> property)
    {
        TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter());
        textFormatter.valueProperty().bindBidirectional(property);
        tf.setTextFormatter(textFormatter);
    }
}
