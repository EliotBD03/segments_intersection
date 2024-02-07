package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import be.ac.umons.firstg.segmentintersector.components.IntersectionTable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Map;

public class HelloController {
    @FXML
    private IntersectionTable interTable;

    public HelloController()
    {
    }

    public IntersectionTable getInterTable()
    {
        return interTable;
    }
}
