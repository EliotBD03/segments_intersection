package ac.umons.be.firstg.segmentintersection.model;


import java.util.Objects;

/**
 * Simple class that act as a pair of two objects to store
 * @param <T>   The type of the first item
 * @param <P>   The type of the second item
 */
public class Pair<T,P>
{
    private final T item1;
    private final P item2;

    /**
     * Basic constructor to create a pair containing two values
     * @param item1 // The value of the first item
     * @param item2 // The value of the second item
     */
    public Pair(T item1, P item2)
    {
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Gets the first item of the tuple
     * @return  the first item of the tuple
     */
    public T getItem1()
    {
        return item1;
    }
    /**
     * Gets the second item of the tuple
     * @return  the second item of the tuple
     */
    public P getItem2()
    {
        return item2;
    }

    @Override
    public String toString()
    {
        return "Pair{" +
                "item1=" + item1 +
                ", item2=" + item2 +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(item1, pair.item1) && Objects.equals(item2, pair.item2);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(item1, item2);
    }
}
