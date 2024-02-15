package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Temp.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * EventPoint component that helps to represent an event point to the graph
 */
public class EventPointC extends Pane
{
    // In pixels
    private final Point location;
    private final Circle circle;

    public EventPointC(Point location)
    {
        circle = new Circle();
        circle.setFill(Color.BLACK);
        circle.setRadius(3f);
        this.location = location;
        getChildren().add(circle);
    }

    public void isVisited()
    {
        circle.toFront();
        circle.setRadius(5f);
        circle.setFill(Color.BLUEVIOLET);
    }
    public void isInactive()
    {
        circle.setRadius(3f);
        circle.setFill(Color.GRAY);
    }
    public void isActive()
    {
        circle.setRadius(3f);
        circle.setFill(Color.BLACK);
    }
}
