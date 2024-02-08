package core;

import parser.Parser;

import java.io.IOException;
import java.util.ArrayList;

public class T extends AVL<ComparableSegment>
{
    private float currentYAxis;

    public T(float currentYAxis)
    {
        super();
        this.currentYAxis = currentYAxis;
    }

    public void add(Segment segment)
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis);
        super.insert(comparableSegment);
        super.insert(comparableSegment);
    }

    public void remove(Segment segment) throws Exception
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis);
        super.remove(comparableSegment);
        super.remove(comparableSegment);
    }


    public static void main(String[] args) throws IOException
    {
        Parser parser = new Parser("/Users/julienladeuze/Desktop/bac3Stuff/segments_intersection/backend/cartes/test.txt");
        ArrayList<Segment> segments = parser.getSegmentsFromFile();
        T t = new T(segments.getFirst().getUpperPoint().y);
        for(Segment segment : segments)
        {
            t.add(segment);
        }
        t.display();

    }
}
