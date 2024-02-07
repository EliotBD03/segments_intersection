package be.ac.umons.firstg.segmentintersector.Temp;

import java.util.Objects;

/**
 * This temporary class is supposed to be replaced by the EventPoint class or something like that
 */
public class Point {
    private double x,y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(x, point.x) == 0 && Double.compare(y, point.y) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }


}
