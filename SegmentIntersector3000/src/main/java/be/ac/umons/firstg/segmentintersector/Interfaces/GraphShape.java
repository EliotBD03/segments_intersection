package be.ac.umons.firstg.segmentintersector.Interfaces;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * An interface for shapes that will change state during the sweep line algorithm
 */
public interface GraphShape
{
    /**
     * Set the current state as visited
     */
    void setVisited();

    /**
     * Set the current state as active
     */
    void setActive();

    /**
     * Set the current state as inactive
     */
    void setInactive();

    /**
     * Select the shape on the graph
     */
    void selectShape();

    /**
     * Deselect the shape on the graph
     */
    void deselectShape();

}
