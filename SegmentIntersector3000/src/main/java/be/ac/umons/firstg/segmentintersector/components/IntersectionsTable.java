package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Interfaces.ILambdaEvent;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.customUtil.Icon;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntersectionsTable extends HBox
{
    private TableView<Map> tableView;

    private GraphXY graph;

    private int current;

    private ILambdaEvent<Pair<Point, List<SegmentTMP>>> highlightSegmentsEvent;


    /**
     * Default constructor for the IntersectionTable
     * @param graphXY   The graph linked to the table
     */
    public IntersectionsTable(GraphXY graphXY)
    {
        // Create tableView
        tableView = TableGenerator.createTable(true,
                                                new Pair<>("Intersections Found","inter"),
                                                new Pair<>("Segments", "segments"),
                                                new Pair<>("", "highlight"));
        TableGenerator.addPlaceHolderText(tableView, "No intersections found yet",
                                                    "Try the next iteration");
        getChildren().add(tableView);

        this.graph = graphXY;

    }

    /**
     * Add an intersection in the table
     * @param intersection  The point of the intersection to add
     * @param segments      The segment that are intersecting at that point
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
        item.put("inter", intersection.toString());
        item.put("segments", segmentToString);
        Button highlightSegment = new Button("");
        //ImageView buttonIcon = Icon.getIcon(this.getClass(), "highlightButtonIcon.png", 1);
        //buttonIcon.setScaleX(1);
        //buttonIcon.setScaleY(1);
        //highlightSegment.setGraphic(buttonIcon);
        //buttonIcon.fitHeightProperty().bind(highlightSegment.heightProperty());
        //buttonIcon.fitWidthProperty().bind(highlightSegment.widthProperty());
        highlightSegment.setOnAction(e ->{
            tableView.getSelectionModel().select(item);
            if(highlightSegmentsEvent != null)
                highlightSegmentsEvent.callMethod(
                        new Pair<>((Point) tableView.getSelectionModel().getSelectedItem().get("inter"),
                                (List<SegmentTMP>) tableView.getSelectionModel().getSelectedItem().get(segments)));

        });
        current ++;
        item.put("highlight", highlightSegment);
        tableView.getItems().add(item);

    }



}
