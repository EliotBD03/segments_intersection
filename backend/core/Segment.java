package core;

public class Segment implements Comparable<Segment>
{
    private final Point lowerPoint, upperPoint;

    public Segment(float[] endpoints)
    {

        if(endpoints[1] <= endpoints[3] || (endpoints[1] == endpoints[3] && endpoints[0] <= endpoints[2]))
        {
            this.upperPoint = new Point(endpoints[2], endpoints[3]);
            this.lowerPoint = new Point(endpoints[0], endpoints[1]);
        }
        else
        {
            this.upperPoint = new Point(endpoints[0], endpoints[1]);
            this.lowerPoint = new Point(endpoints[2], endpoints[3]);
        }
    }

    public Point[] getEndpoints()
    {
        return new Point[]{lowerPoint, upperPoint};
    }

    public Point getLowerPoint()
    {
        return lowerPoint;
    }

    public Point getUpperPoint()
    {
        return upperPoint;
    }

    @Override
    public int compareTo(Segment otherSegment)
    {
        float y1 = upperPoint.y();
        float y2 = otherSegment.getEndpoints()[1].y();
        float x1 = upperPoint.x();
        float x2 = otherSegment.getEndpoints()[1].x();

        if(y1 < y2 || (y1 == y2 && x1 < x2))
            return 1;
        else if (y1 == y2 && x1 == x2)
            return 0;
        return -1;
    }

    @Override
    public String toString()
    {
        return lowerPoint +"--"+ upperPoint;
    }
}
