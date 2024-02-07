package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * A component representing a segment by simply drawing 2 points and a line between them
 */
public class Segment extends Group {
    private final SegmentTMP segment;
    private final Line line;
    private final EventPointC endPoint1;
    private final EventPointC endPoint2;

    /**
     * Creates a segment component
     * @param segment       The segment to represent
     */
    public Segment(SegmentTMP segment)
    {
        this.segment = segment;
        // Draw segment
        IShapeGen<Point> getEventPoint = x ->
        {
            EventPointC circle = new EventPointC(x);
            circle.setLayoutX(x.getX());
            circle.setLayoutY(x.getY());
            return circle;
        };

        // Add Line
        this.line = new Line(segment.getPoint1().getX(), segment.getPoint1().getY(),
                             segment.getPoint2().getX(), segment.getPoint2().getY());
        line.setStroke(Color.BLACK);
        this.getChildren().add(line);
        // Add the 2 end points
        endPoint1 = (EventPointC) getEventPoint.createShape(segment.getPoint1());
        this.getChildren().add(endPoint1);
        endPoint2 = (EventPointC) getEventPoint.createShape(segment.getPoint2());
        this.getChildren().add(endPoint2);
    }


    public void setVisitedSegment()
    {
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        endPoint1.isActive();
        endPoint2.isActive();
    }

    public void setInactiveSegment()
    {
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(1);
        endPoint1.isInactive();
        endPoint2.isInactive();
    }
    public void setActiveSegment()
    {
        line.setStroke(Color.BLACK);
        endPoint2.isActive();
        endPoint1.isActive();
    }

    public void setVisitedPoint(Point point)
    {
        if(segment.getPoint1().equals(point))
        {
            endPoint1.isVisited();
        }
        else if (segment.getPoint2().equals(point))
        {
            endPoint2.isVisited();
        }

    }

}
