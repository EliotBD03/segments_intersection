package core;


/**
 * Custom Double Class used to compare doubles using a threshold comparison method
 */
public class CDouble
{
    public static final double epsilon = 0.000001d;

    /**
     * A threshold comparison method using {@link CDouble#epsilon}
     * @param d1    One of the value to compare
     * @param d2    The other value to compare
     * @return true if d1 and d2 are close enough
     */
    public static boolean almostEqual(double d1, double d2)
    {
        return Math.abs(d1 - d2) <= epsilon;
    }
}
