package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parser.Parser;
import static core.AVLTestTools.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PlaneSweepTest \uD83D\uDCC8")
class PlaneSweepTest
{
    private static PlaneSweep planeSweep;

    @BeforeAll
    static void initPlaneSweep() throws Exception
    {
        ArrayList<Segment> segments = new Parser(Parser.getPathFromResource("/test_tree/example_segments.txt")).getSegmentsFromFile();
        planeSweep = new PlaneSweep(segments);
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

        ArrayList<Segment> segments2 = new Parser(Parser.getPathFromResource("/test_tree/example_segments4.txt")).getSegmentsFromFile();
        PlaneSweep ps = new PlaneSweep(segments2);

        System.out.println(ps.getIntersections());



        Parser parser = new Parser(Parser.getPathFromResource("/cartes/fichier1.txt"));
        PlaneSweep ps2 = new PlaneSweep(parser.getSegmentsFromFile());
        ArrayList<Point> intersections = ps2.getIntersections();
        System.out.println(intersections);











    }

    @Test
    void union()
    {
        ComparableSegment s1 = new ComparableSegment(new Segment(new double[]{0, 0, 1, 1}, "s1"));
        ComparableSegment s2 = new ComparableSegment(new Segment(new double[]{1, 1, 1, 0}, "s2"));

        ArrayList<ComparableSegment> first = new ArrayList<>(List.of(s1));
        ArrayList<ComparableSegment> second = new ArrayList<>(List.of(s2));
        ArrayList<ComparableSegment> expected = new ArrayList<>(List.of(s1,s2));

        ArrayList<ComparableSegment> test = PlaneSweep.union(first, second);
        assertEquals(2, test.size());
        for(int i = 0; i < expected.size(); i++)
            assertEquals(expected.get(i), test.get(i));
    }
}