package ac.umons.be.firstg.segmentintersection.view.components;

import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.view.interfaces.GraphShape;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * EventPoint component that helps to represent an event point to the graph
 */
public class EventPointNode extends Pane implements GraphShape
{
    // In pixels
    private final Point location;
    private final Circle circle;

    private Paint previousPointColor;
    private double previousRadius;

    public EventPointNode(Point location)
    {
        this(location, 3f, Color.BLACK);
    }
    public EventPointNode(Point location, double radius, Paint color)
    {
        circle = new Circle();
        circle.setFill(color);
        circle.setRadius(radius);
        this.location = location;
        getChildren().add(circle);
    }

    @Override
    public void setVisited()
    {
        circle.toFront();
        circle.setRadius(5f);
        circle.setFill(Color.BLUEVIOLET);
    }
    @Override
    public void setInactive()
    {
        circle.setRadius(3f);
        circle.setFill(Color.GRAY);
    }

    @Override
    public void setActive()
    {
        circle.setRadius(3f);
        circle.setFill(Color.BLACK);
    }

    @Override
    public void selectShape()
    {
        // Save current color
        previousPointColor = circle.getFill();
        previousRadius = circle.getRadius();
        circle.setFill(Color.ORANGE);
        circle.setRadius(5);
    }

    @Override
    public void deselectShape()
    {
        circle.setFill(previousPointColor);
        circle.setRadius(previousRadius);
    }

}
