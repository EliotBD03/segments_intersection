package be.ac.umons.firstg.segmentintersector.Interfaces;


@FunctionalInterface
public interface ILambdaEvent<T>
{
    void callMethod(T data);
}
