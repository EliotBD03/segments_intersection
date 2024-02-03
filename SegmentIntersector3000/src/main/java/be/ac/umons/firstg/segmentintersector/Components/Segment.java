package be.ac.umons.firstg.segmentintersector.Components;

import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * A component representing a segment by simply drawing 2 points and a line between them
 */
public class Segment extends Group {
    private final SegmentTMP segment;
    private final Line line;
    private final Circle endPoint1;
    private final Circle endPoint2;

    /**
     * Creates a segment component
     * @param segment       The segment to represent
     */
    public Segment(SegmentTMP segment)
    {
        this.segment = segment;
        // Draw segment
        IShapeGen<Point> getCircle = x ->
        {
            Circle circle = new Circle();
            circle.setRadius(3f);
            circle.setLayoutX(x.getX());
            circle.setCenterY(x.getY());
            return circle;
        };
        // Add the 2 end points
        endPoint1 = (Circle) getCircle.createShape(segment.getPoint1());
        this.getChildren().add(endPoint1);

        endPoint2 = (Circle) getCircle.createShape(segment.getPoint2());
        this.getChildren().add(endPoint2);
        // Add Line
        this.line = new Line(segment.getPoint1().getX(), segment.getPoint1().getY(),
                             segment.getPoint2().getX(), segment.getPoint2().getY());
        this.getChildren().add(line);
    }
}
