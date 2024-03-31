package core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PlaneSweepTest \uD83D\uDCC8")
class PlaneSweepTest
{
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