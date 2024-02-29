package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.util.*;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("QTest \uD83C\uDF33")
class QTest
{
    static Q qTest;

    @BeforeAll
    static void initQ()
    {
        qTest = Q.initQ(new ArrayList<>(
                Arrays.asList(s1,s2,s3,s4,s5)
        ));
        //qTest.display();
        // Check for correct order
        assertEquals(qTest.root.getData(), A);
        assertEquals(qTest.root.getRight().getData(), D);
        assertEquals(qTest.root.getLeft().getData(), C);
        assertEquals(qTest.root.getLeft().getLeft().getData(), E);
        assertEquals(qTest.root.getLeft().getRight().getData(), G);
        assertEquals(qTest.root.getRight().getLeft().getData(), B);
        assertEquals(qTest.root.getRight().getLeft().getLeft().getData(), H);
        assertEquals(qTest.root.getRight().getRight().getData(), F);
        assertEquals(qTest.root.getRight().getRight().getRight().getData(), I);
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