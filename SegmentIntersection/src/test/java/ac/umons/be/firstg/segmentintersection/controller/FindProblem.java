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
        //System.out.println("size" + new PlaneSweepIterable(segments).getIntersections().size());
        //System.out.println("size" + NaiveIntersection.getIntersections(segments).size());

        while(true)
        {
            segments = generator.generate();
            // Save the file that will cause the error
            Parser.saveSegments(segments,"");
            //System.out.println("size" + new PlaneSweepIterable(segments).getIntersections().size());
            //System.out.println("size" + NaiveIntersection.getIntersections(segments).size());
        }
    }

    public static void main(String[] args) throws Exception
    {
        findProblem();
    }
}
