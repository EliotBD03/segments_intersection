package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.util.*;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("QTest \uD83C\uDF33")
class PointQueueTest
{
    static PointQueue qTest;

    @BeforeAll
    static void initQ() throws Exception
    {
        qTest = PointQueue.initQ(new ArrayList<>(
                Arrays.asList(s1,s2,s3,s4,s5)
        ));
        //qTest.display();
        // Check for correct order
        qTest.display();
        assertTrue(checkInorder(qTest.root, List.of(E, C, G, A, H, B, D, F, I)));
    }


    @Test
    @Order(1)
    void UpperSegments()
    {
        // Check if segments got the correct upper segments

        assertEquals(qTest.getRootData().getStartOf(), Collections.singletonList(s1));
        assertEquals(qTest.root.getLeft().getData().getStartOf(), Collections.singletonList(s2));
        assertEquals(qTest.root.getRight().getData().getStartOf(), List.of());
        assertEquals(qTest.root.getRight().getLeft().getLeft().getData().getStartOf(), Collections.singletonList(s5));
        assertEquals(qTest.root.getLeft().getData().getStartOf(), List.of(s2));
        assertEquals(qTest.root.getLeft().getRight().getData().getStartOf(), List.of(s4));

    }

    @Test
    @Order(2)
    void dequeue()
    {
        // Check that the min node was really removed

        //assertEquals(qTest.dequeue(), qTest.);
    }

    @Test
    @Order(3)
    void enqueue()
    {
        // Check for no doubles
        qTest.enqueue(E);
        qTest.enqueue(I);
        assertFalse(checkForDoubles(qTest.root, new HashSet<>()));

    }
}