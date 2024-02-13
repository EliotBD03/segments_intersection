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

    public void add(Segment segment, int id)
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis, id);
        super.insert(comparableSegment);
        super.insert(comparableSegment);
    }

    public void remove(Segment segment, int id) throws Exception
    {
        ComparableSegment comparableSegment = new ComparableSegment(segment, currentYAxis, id);
        super.remove(comparableSegment);
        super.remove(comparableSegment);
    }


    public static void main(String[] args) throws IOException
    {
        Parser parser = new Parser("/Users/julienladeuze/Desktop/bac3Stuff/segments_intersection/backend/cartes/test.txt");
        ArrayList<Segment> segments = parser.getSegmentsFromFile();
        T t = new T(segments.getFirst().getUpperPoint().y);
        for(int i = 0; i < segments.size(); i++)
        {
            t.add(segments.get(i), i+1);
        }
        t.display();

    }
}
