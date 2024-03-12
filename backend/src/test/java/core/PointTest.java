package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    static Point point;

    @BeforeAll
    static void init(){
        point = new Point(1d, 2d);
    }

    @Test
    void addSegment() {
        assertEquals(0, point.getStartOf().size());
        Segment segment = new Segment(point, new Point(0d, 0d), "tutu");
        point.addSegment(segment);
        assertEquals(point.getStartOf().getFirst(), segment);
    }

    @Test
    void compareTo() {
        Point point1 = new Point(2d, 1d);
        Point point2 = new Point(2.00011d, 1d);
        assertEquals(-1, point1.compareTo(point2));
    }

    @Test
    void testEquals() {
        Point point = new Point(2d, 2d);
        assertTrue(point.equals(point));
        assertTrue(new Point(1d, 1d).equals(new Point(1d, 1d)));
        assertFalse(new Point(1d, 1d).equals(new Point(1.00001, 1d)));
    }

    @Test
    void testHashCode() {
        Point point1 = new Point(1d, 1d);
        Point point2 = new Point(1d, 1d);
        Point point3 = new Point(2d, 3d);
        assertNotEquals(point3.hashCode(), point2.hashCode());
        assertEquals(point2.hashCode(), point1.hashCode());
    }

}