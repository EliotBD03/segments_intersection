package be.ac.umons.firstg.segmentintersector.Interfaces;


/**
 * Interface used to create a lambda function that takes one argument
 * @param <T>   The type of the argument
 */
@FunctionalInterface
public interface ILambdaEvent<T>
{
    /**
     * a Lambda method to call with a parameter
     * @param data  The parameter
     */
    void callMethod(T data);
}
