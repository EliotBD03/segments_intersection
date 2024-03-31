package ac.umons.be.firstg.segmentintersection.view.components;

import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.view.interfaces.ILambdaEvent;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IObjectGen;
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

    private Segment currentSelection;

    private ILambdaEvent<Segment> removeSegmentEvent;


    /**
     * Creates a SegmentsTable
     */
    public SegmentsTable(GraphXY graph)
    {
        tableView = TableGenerator.createTable(true,
                    new Pair<>("Name", "name"),
                    new Pair<>("Upper", "upper"),
                    new Pair<>("Lower", "lower"),
                    new Pair<>("","remove"));
        TableGenerator.addPlaceHolderText(tableView, "This map is empty", "Import a file and/or add segments");
        getChildren().add(tableView);


        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (oldSelection != null)
            {
                graph.deselectSegment((Segment) oldSelection.get("segment"));
            }
            if (newSelection != null)
            {
                Segment newSel = (Segment) newSelection.get("segment");
                graph.selectSegment(newSel);
                currentSelection = newSel;
            }
        });
        tableView.focusedProperty().addListener((obs, oldState, newState) -> {
            if(!newState){
                graph.deselectSegment(currentSelection);
                tableView.getSelectionModel().clearSelection();
                currentSelection = null;
            }
        });


    }


    /**
     * Adds one segment as data to the table
     * @param segment   The segment to add
     */
    public void addSegment(Segment segment)
    {
        Map<String, Object> item = new HashMap<>();
        // We also store the segment inside the map for later use
        item.put("segment",segment);

        item.put("name", segment.getId());

        //TODO: Change with real upperPoint and lowerPoint

        IObjectGen<Label, Point> labelGen = data -> {
          Label label = new Label("x: " + data.x + "\ny: " + data.y);
          label.setFont(new Font(15));
          return label;
        };

        item.put("upper", labelGen.createObject(segment.getUpperPoint()));
        item.put("lower", labelGen.createObject(segment.getLowerPoint()));

        Button button = new Button();
        item.put("remove",button);

        button.setOnAction(e -> {
            tableView.getSelectionModel().select(item);
            // Gets the segment stored in the hash

            if(removeSegmentEvent != null)
            {
                removeSegmentEvent.callMethod((Segment) tableView.getSelectionModel().getSelectedItem().get("segment"));
            }
            // Removes the segment from this table
            tableView.getItems().remove(item);


        });
        tableView.getItems().add(item);
    }

    /**
     * Adds all segments to the table
     * @param segments  The segments to add
     */
    public void addAll(List<Segment> segments)
    {
        for (Segment segment: segments)
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
    public void setRemoveSegmentEvent(ILambdaEvent<Segment> event)
    {
        removeSegmentEvent = event;
    }
}
