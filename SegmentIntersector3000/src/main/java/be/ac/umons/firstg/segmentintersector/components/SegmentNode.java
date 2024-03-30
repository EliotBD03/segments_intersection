package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Interfaces.GraphShape;
import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * A component representing a segment by simply drawing 2 points and a line between them
 */
public class SegmentNode extends Group implements GraphShape
{
    private SegmentTMP segment;
    private final Line line;
    private final EventPointNode endPoint1;
    private final EventPointNode endPoint2;

    private Paint previousLineColor;
    private double previousLineWidth;

    /**
     * Creates a segment component
     * @param segment       The segment to represent
     */
    public SegmentNode(SegmentTMP segment)
    {
        this.segment = segment;
        // Draw segment
        IShapeGen<Point> getEventPoint = x ->
        {
            EventPointNode circle = new EventPointNode(x);
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
        endPoint1 = (EventPointNode) getEventPoint.createNode(segment.getPoint1());
        this.getChildren().add(endPoint1);
        endPoint2 = (EventPointNode) getEventPoint.createNode(segment.getPoint2());
        this.getChildren().add(endPoint2);
    }

    @Override
    public void setActive()
    {
        line.setStroke(Color.BLACK);
        endPoint2.setActive();
        endPoint1.setActive();
    }


    @Override
    public void setVisited()
    {
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        endPoint1.setActive();
        endPoint2.setActive();
    }

    @Override
    public void setInactive()
    {
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(1);
        endPoint1.setInactive();
        endPoint2.setInactive();
    }

    @Override
    public void selectShape()
    {
        previousLineColor = line.getStroke();
        previousLineWidth = line.getStrokeWidth();
        endPoint1.selectShape();
        endPoint2.selectShape();
        line.setStrokeWidth(3);
        line.setStroke(Color.ORANGE);
    }

    @Override
    public void deselectShape()
    {
        endPoint1.deselectShape();
        endPoint2.deselectShape();
        line.setStroke(previousLineColor);
        line.setStrokeWidth(previousLineWidth);
    }

    public void setVisitedPoint(Point point)
    {
        if(segment.getPoint1().equals(point))
        {
            endPoint1.setVisited();
        }
        else if (segment.getPoint2().equals(point))
        {
            endPoint2.setVisited();
        }

    }


    /**
     * Change the segment position
     * @param segment The segment to represent
     */
    public void setSegment(SegmentTMP segment)
    {
        this.segment = segment;
        // Change line position
        this.line.setStartX(segment.getPoint1().getX());
        this.line.setStartY(segment.getPoint1().getY());

        this.line.setEndX(segment.getPoint2().getX());
        this.line.setEndY(segment.getPoint2().getY());

        // Change endpoints location
        endPoint1.setLayoutX(segment.getPoint1().getX());
        endPoint1.setLayoutY(segment.getPoint1().getY());

        endPoint2.setLayoutX(segment.getPoint2().getX());
        endPoint2.setLayoutY(segment.getPoint2().getY());

    }



}
