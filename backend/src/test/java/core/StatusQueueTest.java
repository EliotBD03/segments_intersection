package core;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StatusQueueTest \uD83C\uDF32 ")
class StatusQueueTest
{
    static StatusQueue testTree1;


    @BeforeEach
    @DisplayName("\uD83C\uDF32 StatusQueue test starting")
    void add()
    {
        testTree1 =  new StatusQueue();

        // Test that the graph's segments are correctly placed in T
        // Sweep line is at point E
        //      Insert s3
        testTree1.add(s3, E);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s3, s3))));

        // Sweep line is at point C
        //      Insert s2
        testTree1.add(s2, C);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s3,s3))));
        // Sweep line is at point G
        //      Insert s4
        testTree1.add(s4, G);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s3,s3,s4,s4))));
        // Sweep line is at point A
        //      Insert s1
        testTree1.add(s1, A);
        //testTree1.display();
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s1,s1,s2,s2,s3,s3,s4,s4))));
        //testTree1.display();
        //testTree1.display();
    }
    @Test
    @Order(2)
    void remove()
    {
        // Try to remove from empty tree
        StatusQueue empty = new StatusQueue();
        assertThrows(Exception.class,() -> empty.remove((ComparableSegment) s3));
    }

    @Test
    @Order(3)
    void orderSwapped() throws Exception
    {
        // Sweep line is at point P
        //      Remove s1 then s2
        testTree1.remove(s1);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s3,s3,s4,s4))));

        testTree1.remove(s2);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s3,s3,s4,s4))));

        //      Add s1 then s2
        //      Check if they were correctly exchanged

        testTree1.add(s1,P);

        assertTrue(checkInorder(testTree1.root, convertList(List.of(s1,s1,s3,s3,s4,s4))));
        testTree1.add(s2,P);

        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s1,s1,s3,s3,s4,s4))));




        // Same with on point H, but with s3, s4 and s1
        // s4 will end and s5 will be added
        testTree1.remove(s1);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s3,s3,s4,s4))));


        testTree1.remove(s3);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s4,s4))));


        testTree1.remove(s4);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2))));


        testTree1.add(s1,H);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2,s1,s1))));


        testTree1.add(s3,H);

        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2, s3, s3, s1, s1))));
        testTree1.add(s5,H);
        assertTrue(checkInorder(testTree1.root, convertList(List.of(s2,s2, s5, s5, s3, s3, s1, s1))));


/*
         */

    }



    @Test
    void getNeighborsTest()
    {
        testTree1.display();

        //degenerated case -> rightmost
        Pair<ComparableSegment, ComparableSegment> tested = testTree1.getNeighbours(s4,P);
        assertEquals("s3", tested.getItem1().id);
        assertNull(tested.getItem2());

        //middle case
        tested = testTree1.getNeighbours(s3, P);
        assertEquals("s2", tested.getItem1().id);
        assertEquals("s4", tested.getItem2().id);

        //degenerated case -> leftmost
        tested = testTree1.getNeighbours(s1, P);
        assertNull(tested.getItem1());
        assertEquals("s2", tested.getItem2().id);
    }

    private static List<ComparableSegment> convertList(List<Segment> segments)
    {
        List<ComparableSegment> res = new ArrayList<>();
        for(Segment s : segments)
        {
            res.add(new ComparableSegment(s));
        }
        return res;
    }

}
