package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntersectionTable extends TableView
{
    @FXML
    private TableView<Map<String,Object>> table;

    @FXML
    private TableColumn<Map, Point> intersectionsColumn;

    @FXML
    private TableColumn<Map, String> segmentsColumn;

    @FXML
    private TableColumn<Map, Button> highlightColumn;

    private int current;


    /**
     * The constructor for the table component that will use the IntersectionTable.fxml file
     */
    public IntersectionTable()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IntersectionTable.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        current = 0;
        intersectionsColumn.setCellValueFactory(new MapValueFactory<>("Point"));

        segmentsColumn.setCellValueFactory(new MapValueFactory<>("Segments"));
        highlightColumn.setCellValueFactory(new MapValueFactory<>("Highlight"));
    }
    public void onClicked()
    {
        System.out.println(table.getItems().get(table.getSelectionModel().getSelectedIndex()).get("Segments"));
    }
    /**
     * Add a new line of information the table view
     * @param intersection  The intersection to add
     * @param segments      The list of segments involved in the intersection
     */
    public void addIntersection(Point intersection, List<SegmentTMP> segments)
    {
        int curr = current;
        Map<String, Object> item = new HashMap<>();
        String segmentToString = "";
        for (int i = 0; i < segments.size(); i++)
        {
            segmentToString = segmentToString.concat(segments.get(i).getName());
            if(i != segments.size()-1)
            {
                segmentToString = segmentToString.concat(" <-> ");
            }
        }
        item.put("Point", intersection.toString());
        item.put("Segments", segmentToString);
        Button highlightSegment = new Button("H");
        highlightSegment.setOnAction(e ->{
                    table.getSelectionModel().select(curr);
                    onClicked();
        });
        current ++;
        item.put("Highlight", highlightSegment);
        table.getItems().add(item);

    }
}
