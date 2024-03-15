package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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

    /**
     * Creates a SegmentsTable
     */
    public SegmentsTable()
    {
        // Create tableView
        tableView = new TableView<>();
        HBox.setHgrow(tableView, Priority.ALWAYS);
        //this.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
        // Create the three columns
        TableColumn<Map, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Map, String> upperPointColumn = new TableColumn<>("Upper");
        TableColumn<Map, String> lowerPointColumn = new TableColumn<>("Lower");
        TableColumn<Map, String> removeColumn = new TableColumn<>("");

        // Set cell value Factory
        nameColumn.setCellValueFactory(new MapValueFactory<>("name"));
        upperPointColumn.setCellValueFactory(new MapValueFactory<>("upper"));
        lowerPointColumn.setCellValueFactory(new MapValueFactory<>("lower"));
        removeColumn.setCellValueFactory(new MapValueFactory<>("remove"));

        removeColumn.setPrefWidth(40);

        tableView.getColumns().addAll(nameColumn,upperPointColumn,lowerPointColumn,removeColumn);
        for (TableColumn column: tableView.getColumns())
        {
            column.setEditable(false);
            column.setReorderable(false);
            column.setResizable(false);
            column.setSortable(false);
        }
        Label placeHolderText1 = new Label("This map is empty");
        Label placeHolderText2 = new Label("Load a file and/or add segments");
        VBox placeHolder = new VBox(10, placeHolderText1, placeHolderText2);
        placeHolder.setAlignment(Pos.CENTER);
        tableView.setPlaceholder(placeHolder);
        // So the columns can take all the space available
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        getChildren().add(tableView);
    }


    /**
     * Adds one segment as data to the table
     * @param segment   The segment to add
     */
    public void addSegment(SegmentTMP segment)
    {
        Map<String, Object> item = new HashMap<>();
        item.put("name", segment.getName());

        //TODO: Change with real upperPoint and lowerPoint

        // The '\' forces the text to print entirely
        item.put("upper", segment.getPoint1() + "\n");
        item.put("lower", segment.getPoint2() + "\n");

        Button button = new Button();
        item.put("remove",button);
        tableView.getItems().add(item);


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
}
