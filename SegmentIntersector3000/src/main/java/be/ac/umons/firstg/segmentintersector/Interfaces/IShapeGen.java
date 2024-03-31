package be.ac.umons.firstg.segmentintersector.Interfaces;

import javafx.scene.Node;

/**
 * Interface that can be used to create lambda expressions to generate nodes, helping with keeping the code generic
 */
@FunctionalInterface
public interface IShapeGen<K> {
    /**
     * Creates an instance of a node
     * @return A new instance of a node
     */
    Node createNode(K data);
}
// TODO: Add a paramater to the lambda create shape expression to allow the use of data from binary trees
