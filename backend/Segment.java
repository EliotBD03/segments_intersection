public class Segment implements Comparable<Segment>
{
    private final Point[] endpoints;

    public Segment(float[] endpoints)
    {
        this.endpoints = new Point[2];
        if(endpoints[0] < endpoints[2] || (endpoints[0] == endpoints[2] && endpoints[1] < endpoints[3]))
        {
            this.endpoints[0] = new Point(endpoints[0], endpoints[1]);
            this.endpoints[1] = new Point(endpoints[2], endpoints[3]);
        }
        else
        {
            this.endpoints[1] = new Point(endpoints[0], endpoints[1]);
            this.endpoints[0] = new Point(endpoints[2], endpoints[3]);
        }
    }

    public Point[] getEndpoints()
    {
        return endpoints;
    }

    @Override
    public int compareTo(Segment otherSegment)
    {
        float y1 = endpoints[1].getY();
        float y2 = otherSegment.getEndpoints()[1].getY();
        float x1 = endpoints[1].getX();
        float x2 = otherSegment.getEndpoints()[1].getX();

        if(y1 < y2 || (y1 == y2 && x1 < x2))
            return 1;
        else if (y1 == y2 && x1 == x2)
            return 0;
        return -1;
    }

    @Override
    public String toString()
    {
        return endpoints[0]+"--"+ endpoints[1];
    }
}
