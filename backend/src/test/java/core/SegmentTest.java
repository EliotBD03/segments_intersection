package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {
    static Segment segment1;
    static Segment segment2;


    @BeforeAll
    static void init(){
        segment1 = new Segment(new Point(0, 0), new Point(1, 1), "s1");
        segment2 = new Segment(new Point(0.286, 0.286), new Point(0.8, 0.2), "s2");
    }
    @Test
    void findIntersection() {
        assertEquals(new Point(0.286, 0.286), Segment.findIntersection(segment1, segment2));
    }

    @Test
    void getPointOnXAxis() {
        assertEquals(Segment.getPointOnXAxis(new Point(0.286, 0.286), segment2).x, segment2.getUpperPoint().x, 0.0001);
    }

    @Test
    void testEquals() {
        assertFalse(segment1.equals(segment2));
        assertTrue(segment1.equals(segment1));
    }

    @Test
    void testHashCode() {
        assertEquals(segment1.hashCode(), segment1.hashCode());
        assertNotEquals(segment1.hashCode(), segment2.hashCode());
        assertNotEquals(segment1.hashCode(), new Segment(new Point(0, 0), new Point(1, 1), "s1"));
    }

    @Test
    void getLowerPoint() {
        assertEquals(new Point(0.8, 0.2), segment2.getLowerPoint());
    }

    @Test
    void getUpperPoint() {
        assertEquals(new Point(0.286, 0.286), segment2.getUpperPoint());
    }
}