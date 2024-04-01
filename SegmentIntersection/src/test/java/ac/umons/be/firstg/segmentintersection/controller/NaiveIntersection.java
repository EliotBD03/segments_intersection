package ac.umons.be.firstg.segmentintersection.controller;

import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;

import java.util.ArrayList;

public class NaiveIntersection
{
    public static ArrayList<Point> getIntersections(ArrayList<Segment> testSet)
    {
        ArrayList<Point> result = new ArrayList<>();
        for(int i = 0; i < testSet.size(); i++)
        {
            for(int j = i + 1; j < testSet.size(); j++)
            {
                if(!testSet.get(i).equals(testSet.get(j)))
                {
                    Point intersection = Segment.findIntersection(testSet.get(i), testSet.get(j));
                    if (intersection != null && !result.contains(intersection))
                    {
                        System.out.println(intersection);
                        result.add(intersection);
                    }
                }
            }
        }
        return result;
    }
}
