package core;


/**
 * Custom Double Class used to compare doubles using a threshold comparison method
 */
public class CDouble
{
    /**
     * The threshold value to fight against floating point errors
     */
    public static final double epsilon = 0.0001d;

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

    /**
     * Compare to double like for {@link Double#compare(double, double)} but
     * returns 0 if {@link  CDouble#almostEqual(double, double)} is verified
     * @param d1    One of the value to compare
     * @param d2    The other value to compare
     * @return 0 if d1 and d2 are "equal", 1 if d1 is greater than d2, -1 otherwise
     */
    public static int compare(double d1, double d2)
    {
        if (almostEqual(d1, d2))
            return 0;
        Double comp = d1 - d2;
        if (comp > 0)
            return 1;
        else
            return -1;
    }

    /**
     * Checks to see if a value is greater or equal than another
     * @param d1    One of the value to compare
     * @param d2    The other value to compare
     * @return true if d1 is bigger or "equal" than d2
     */
    public static boolean almostGreaterEqual(double d1, double d2)
    {
        return compare(d1, d2) >= 0;
    }
    /**
     * Checks to see if a value is smaller or equal than another
     * @param d1    One of the value to compare
     * @param d2    The other value to compare
     * @return true if d1 is smaller or "equal" than d2
     */
    public static boolean almostLessEqual(double d1, double d2)
    {
        return compare(d1, d2) <= 0;
    }

    /**
     * Checks to see if a value is greater than another
     * @param d1    One of the value to compare
     * @param d2    The other value to compare
     * @return true if d1 is bigger than d2
     */
    public static boolean greater(double d1, double d2)
    {
        return ! almostLessEqual(d1, d2);
    }
    /**
     * Checks to see if a value is smaller than another
     * @param d1    One of the value to compare
     * @param d2    The other value to compare
     * @return true if d1 is smaller than d2
     */
    public static boolean lesser(double d1, double d2)
    {
        return ! almostGreaterEqual(d1, d2);
    }
}
