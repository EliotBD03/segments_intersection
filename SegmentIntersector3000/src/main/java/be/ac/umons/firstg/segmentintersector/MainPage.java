package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;
import java.util.List;

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
        }else
        {
            graph.updateSize(xSizeInputs.get(), ySizeInputs.get(), xScaleInputs.get(), yScaleInputs.get(), 10, 10, true);
        }
        // TODO add a way to load files

    }

    private void createGraph()
    {
        graph = new GraphXY(new Point(50,25), xSizeInputs.get(), ySizeInputs.get(), xScaleInputs.get(), yScaleInputs.get(),  5, 5, true);
        GraphGroup.setContent(graph);
        ArrayList<SegmentTMP> segmentTMPList = getSegmentsFromFile("/home/foucart/Bureau/Git/segments_intersection/SegmentIntersector3000/src/main/java/be/ac/umons/firstg/segmentintersector/Temp/cartes/nocode.txt");
        graph.addSegments(segmentTMPList);

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


    private static void setTextFormatter(TextField tf, ObjectProperty<Double> property)
    {
        TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter());
        textFormatter.valueProperty().bindBidirectional(property);
        tf.setTextFormatter(textFormatter);
    }
}
