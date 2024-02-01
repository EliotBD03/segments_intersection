package be.ac.umons.firstg.segmentintersector.Interfaces;

import javafx.scene.shape.Shape;

/**
 * Interface that can be used to create lambda expressions to generate shapes, helping with keeping the code generic
 */
@FunctionalInterface
public interface IShapeGen {
    /**
     * Creates an instance of a shape
     * @return A new instance of a shape
     */
    Shape createShape();
}
// TODO: Add a paramater to the lambda create shape expression to allow the use of data from binary trees
