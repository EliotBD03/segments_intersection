package ac.umons.be.firstg.segmentintersection.controller;


import ac.umons.be.firstg.segmentintersection.controller.utils.Generator;
import ac.umons.be.firstg.segmentintersection.controller.utils.Parser;
import ac.umons.be.firstg.segmentintersection.model.Segment;

import java.util.ArrayList;

/**
 * Used to see if a set of segments makes some problems to our app
 */
public class FindProblem
{
    public static void findProblem() throws Exception
    {
        Generator generator = new Generator(10);
        ArrayList<Segment> segments = generator.generate();
        System.out.println("size" + new PlaneSweepIterable(segments).getIntersections().size());
        System.out.println("size" + NaiveIntersection.getIntersections(segments).size());

        while(new PlaneSweepIterable(segments).getIntersections().size() == NaiveIntersection.getIntersections(segments).size())
        {
            segments = generator.generate();
            System.out.println("size" + new PlaneSweepIterable(segments).getIntersections().size());
            System.out.println("size" + NaiveIntersection.getIntersections(segments).size());
        }
        Parser.saveSegments(segments,"/Users/julienladeuze/Desktop/bac3Stuff/segments_intersection/SegmentIntersection/src/test/resources/problem.txt");
    }

    public static void main(String[] args) throws Exception
    {
        findProblem();
    }
}
