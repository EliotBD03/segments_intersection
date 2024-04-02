package ac.umons.be.firstg.segmentintersection.controller.utils;

import ac.umons.be.firstg.segmentintersection.model.Segment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class ParserTest
{
    @Test
    void saveSegments()
    {
        ArrayList<Segment> segments = new ArrayList<Segment>()
        {
            {
                add(new Segment(new double[]{0, 0, 1, 1}, "s1"));
                add(new Segment(new double[]{0, 1, 1, 0}, "s2"));
            }
        };
        assertDoesNotThrow(() -> Parser.saveSegments(segments, ("/Users/julienladeuze/Desktop/bac3Stuff/segments_intersection/SegmentIntersection/src/test/resources/testsave.txt")));

    }
}
