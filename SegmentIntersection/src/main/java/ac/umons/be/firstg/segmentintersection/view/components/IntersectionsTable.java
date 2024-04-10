package ac.umons.be.firstg.segmentintersection.view.components;

import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.view.interfaces.ILambdaEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IntersectionsTable extends HBox
{
    private TableView<Map> tableView;

    private GraphXY graph;

    private int current;

    private ArrayList<Point> intersections;


    /**
     * Default constructor for the IntersectionTable
     * @param graphXY   The graph linked to the table
     */
    public IntersectionsTable(GraphXY graphXY)
    {
        // Create tableView
        tableView = TableGenerator.createTable(false,
                                                new Pair<>("Intersections Found","inter"),
                                                new Pair<>("Segments", "segments"));
        TableGenerator.addPlaceHolderText(tableView, "No intersections found yet",
                                                    "Try the next iteration");
        getChildren().add(tableView);
        intersections = new ArrayList<>();
        this.graph = graphXY;

    }

    /**
     * Add an intersection in the table
     * @param intersection  The point of the intersection to add
     */
    public void addIntersection(Point intersection)
    {
        intersections.add(intersection);
        Map<String, Object> item = new HashMap<>();
        String segmentToString = "{";

        ArrayList<Segment> segments = intersection.getIntersections();
        for (int i = 0; i < segments.size(); i++)
        {
            segmentToString = segmentToString.concat(segments.get(i).getId());
            if(i != segments.size()-1)
            {
                // After n segments, skip to next line
                segmentToString = segmentToString.concat(", ");
                if((i+1) % 4 == 0)
                    segmentToString = segmentToString.concat("\n");
            }
        }
        segmentToString = segmentToString.concat("}");
        item.put("inter", "x: " + intersection.x + "\ny: " + intersection.y);
        item.put("segments", segmentToString);
        current ++;
        tableView.getItems().add(item);

    }


    /**
     * Clears the table from all intersections
     */
    public void resetTable()
    {
        tableView.getItems().clear();
        intersections.clear();
    }

    public ArrayList<Point> getIntersections()
    {
        return intersections;
    }

}
