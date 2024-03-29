package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Interfaces.ILambdaEvent;
import be.ac.umons.firstg.segmentintersector.Interfaces.IObjectGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple class that has uses a {@link TableView} to display segments data
 */
public class SegmentsTable extends HBox
{
    private TableView<Map> tableView;

    private int current;

    private ILambdaEvent<SegmentTMP> removeSegmentEvent;

    /**
     * Creates a SegmentsTable
     */
    public SegmentsTable()
    {
        tableView = TableGenerator.createTable(true,
                    new Pair<>("Name", "name"),
                    new Pair<>("Upper", "upper"),
                    new Pair<>("Lower", "lower"),
                    new Pair<>("","remove"));
        TableGenerator.addPlaceHolderText(tableView, "This map is empty", "Import a file and/or add segments");
        getChildren().add(tableView);
    }


    /**
     * Adds one segment as data to the table
     * @param segment   The segment to add
     */
    public void addSegment(SegmentTMP segment)
    {
        int curr = current;
        Map<String, Object> item = new HashMap<>();
        // We also store the segment inside the map for later use
        item.put("segment",segment);

        item.put("name", segment.getName());

        //TODO: Change with real upperPoint and lowerPoint

        IObjectGen<Label, Point> labelGen = data -> {
          Label label = new Label("x: " + data.getX() + "\ny: " + data.getY());
          label.setFont(new Font(15));
          return label;
        };

        item.put("upper", labelGen.createObject(segment.getPoint1()));
        item.put("lower", labelGen.createObject(segment.getPoint2()));

        Button button = new Button();
        item.put("remove",button);

        button.setOnAction(e -> {
            tableView.getSelectionModel().select(item);
            // Gets the segment stored in the hash

            if(removeSegmentEvent != null)
            {
                removeSegmentEvent.callMethod((SegmentTMP) tableView.getSelectionModel().getSelectedItem().get("segment"));
            }
            // Removes the segment from this table
            tableView.getItems().remove(item);


        });
        tableView.getItems().add(item);
        current++;
    }

    /**
     * Adds all segments to the table
     * @param segments  The segments to add
     */
    public void addAll(List<SegmentTMP> segments)
    {
        for (SegmentTMP segment: segments)
            addSegment(segment);
    }

    /**
     * Removes all stored data from the table
     */
    public void resetTable()
    {
        tableView.getItems().clear();
    }


    /**
     * Setter for the lambda function to call when the remove
     * segment button is pressed
     * @param event The event to call when the button is pressed
     */
    public void setRemoveSegmentEvent(ILambdaEvent<SegmentTMP> event)
    {
        removeSegmentEvent = event;
    }
}
