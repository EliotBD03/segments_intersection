package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import static be.ac.umons.firstg.segmentintersector.Temp.Parser.getSegmentsFromFile;

public class MainPage
{
    @FXML
    public Button coolButton;
    public TabPane tabpane;

    public void closeTab()
    {
        System.out.println("closing tab :D ");
        tabpane.getTabs().removeFirst();
        tabpane.getTabs().removeFirst();
    }
    private ImageView getIcon(String resourcePath)
    {
        ImageView imageView = new ImageView(String.valueOf(getClass().getResource(resourcePath)));
        imageView.setScaleX(0.05);
        imageView.setScaleY(0.05);
        return imageView;
    }

    // Called on initialisation
    @FXML
    public void initialize()
    {
        tabpane.getTabs().getFirst().setText("");
        tabpane.getTabs().getFirst().setGraphic(getIcon("icons/GraphSettingsIcon.png"));
        tabpane.getTabs().getLast().setText("");
        tabpane.getTabs().getLast().setGraphic(getIcon("icons/MapSettingsIcon.png"));
    }
}
