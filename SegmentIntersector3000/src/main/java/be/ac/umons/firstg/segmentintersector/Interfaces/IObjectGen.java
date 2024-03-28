package be.ac.umons.firstg.segmentintersector.Interfaces;

/**
 * Interface that can be used to create lambda expressions to instantiate objects, helping with keeping the code generic
 * and clean.
 */
@FunctionalInterface
public interface IObjectGen<T, K> {
    /**
     * Creates an instance of a node
     * @return A new instance of a node
     */
    T createObject(K data);
}