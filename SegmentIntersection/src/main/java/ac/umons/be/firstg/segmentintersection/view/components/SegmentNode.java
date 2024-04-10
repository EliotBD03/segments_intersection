package ac.umons.be.firstg.segmentintersection.view.components;

import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.view.interfaces.GraphShape;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IShapeGen;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * A component representing a segment by simply drawing 2 points and a line between them
 */
public class SegmentNode extends Group implements GraphShape
{
    private Segment segment;
    private final Line line;
    private final EventPointNode endPoint1;
    private final EventPointNode endPoint2;

    private Paint previousLineColor;
    private double previousLineWidth;

    /**
     * Creates a segment component
     * @param segment       The segment to represent
     */
    public SegmentNode(Segment segment)
    {
        this.segment = segment;
        // Draw segment
        IShapeGen<Point> getEventPoint = x ->
        {
            EventPointNode circle = new EventPointNode(x);
            circle.setLayoutX(x.x);
            circle.setLayoutY(x.y);
            return circle;
        };

        // Add Line
        this.line = new Line(segment.getUpperPoint().x, segment.getUpperPoint().y,
                             segment.getLowerPoint().x, segment.getLowerPoint().y);
        line.setStroke(Color.BLACK);
        this.getChildren().add(line);
        // Add the 2 end points
        endPoint1 = (EventPointNode) getEventPoint.createNode(segment.getUpperPoint());
        this.getChildren().add(endPoint1);
        endPoint2 = (EventPointNode) getEventPoint.createNode(segment.getLowerPoint());
        this.getChildren().add(endPoint2);
    }

    @Override
    public void setActive()
    {
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
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
        line.setStroke(Color.DARKSLATEGRAY);
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


    /**
     * Change the segment position
     * @param segment The segment to represent
     */
    public void setSegment(Segment segment)
    {
        this.segment = segment;
        // Change line position
        this.line.setStartX(segment.getUpperPoint().x);
        this.line.setStartY(segment.getUpperPoint().y);

        this.line.setEndX(segment.getLowerPoint().x);
        this.line.setEndY(segment.getLowerPoint().y);

        // Change endpoints location
        endPoint1.setLayoutX(segment.getUpperPoint().x);
        endPoint1.setLayoutY(segment.getUpperPoint().y);

        endPoint2.setLayoutX(segment.getLowerPoint().x);
        endPoint2.setLayoutY(segment.getLowerPoint().y);

    }



}
