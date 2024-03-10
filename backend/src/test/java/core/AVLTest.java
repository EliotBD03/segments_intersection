package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static core.AVLTestTools.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AVLTest \uD83C\uDF84")
class AVLTest {
    static AVL<Double> tree;

    @BeforeAll
    static void insert() {
        tree = new AVL<>();
        tree.insert(1d);
        tree.insert(2d);
        tree.insert(4d);
        tree.insert(-1.1d);
        tree.insert(10001d);
        tree.insert(701d);
        tree.insert(-10001d);
        assertTrue(orderIsRespected(tree.root));
    }

    @Test
    void remove() throws Exception
    {
        tree.remove(701d);
        assertTrue(orderIsRespected(tree.root));
        tree.display();
    }

}