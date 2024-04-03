package ac.umons.be.firstg.segmentintersection.controller;

import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.model.Point;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ac.umons.be.firstg.segmentintersection.controller.utils.Parser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlaneSweepIterableTest {

    private static PlaneSweepIterable planeSweepIterable;

    @BeforeAll
    static void initPlaneSweepIterable() throws Exception
    {
        ArrayList<Segment> segments = new Parser(Parser.getPathFromResource("/test2.txt")).getSegmentsFromFile();
        planeSweepIterable = new PlaneSweepIterable(segments);
    }

    @Test
    void getIntersections() throws Exception
    {
        /*
        ArrayList<Point> intersections = planeSweep.getIntersections();
        for(Point point : intersections)
            System.out.println(point);
        assertEquals(2, intersections.size());
         */
        /*
        ArrayList<Segment> segments2 = new Parser(Parser.getPathFromResource("/test_tree/example_segments5.txt")).getSegmentsFromFile();
        PlaneSweep ps = new PlaneSweep(segments2);

        System.out.println(ps.getIntersections());
         */


        Parser parser = new Parser(Parser.getPathFromResource("/problem6.txt"));
        PlaneSweepIterable ps2 = new PlaneSweepIterable(parser.getSegmentsFromFile());
        ArrayList<Point> intersections = new ArrayList<>();
        for(PlaneSweep planeSweep : ps2)
        {
            System.out.println(planeSweep.getStatusQueue());
            System.out.println(planeSweep.getUpper());
            System.out.println(planeSweep.getLower());
            System.out.println(planeSweep.getInner());
            System.out.println(planeSweep.getIntersection());
            if (planeSweep.getIntersection() != null)
                intersections.add(planeSweep.getIntersection());
        }
        for(Point point : intersections)
        {
            System.out.println(point);
        }
        //assertEquals(NaiveIntersection.getIntersections(parser.getSegmentsFromFile()).size(), intersections.size());

    }

}
