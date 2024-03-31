package ac.umons.be.firstg.segmentintersection.view.interfaces;


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
