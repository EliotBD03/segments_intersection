package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntersectionTable extends TableView
{
    @FXML
    private TableView<Map> table;

    @FXML
    private TableColumn<Map, Point> intersectionsColumn;

    @FXML
    private TableColumn<Map, String> segmentsColumn;

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
        intersectionsColumn.setCellValueFactory(new MapValueFactory<>("Point"));
        segmentsColumn.setCellValueFactory(new MapValueFactory<>("Segments"));
    }

    /**
     * Add a new line of information the table view
     * @param intersection  The intersection to add
     * @param segments      The list of segments involved in the intersection
     */
    public void addIntersection(Point intersection, List<SegmentTMP> segments)
    {

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
        table.getItems().add(item);

    }
}
