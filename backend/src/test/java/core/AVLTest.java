package core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertTrue(orderIsRespected(tree.root));
    }

    @Test
    void remove() throws Exception
    {
        assertThrows(Exception.class, ()-> tree.remove(11212d));
        assertThrows(Exception.class, ()-> tree.remove(-11212d));

        assertDoesNotThrow(() -> tree.remove(2d));

        assertThrows(Exception.class, ()-> tree.remove(2d));
        assertTrue(orderIsRespected(tree.root));

        assertDoesNotThrow(() -> tree.remove(1d));
        assertTrue(orderIsRespected(tree.root));

        assertDoesNotThrow(() -> tree.remove(4d));
        assertTrue(orderIsRespected(tree.root));

        assertDoesNotThrow(() -> tree.remove(-1.1d));
        assertTrue(orderIsRespected(tree.root));

        // Check what happens when empty
        assertThrows(Exception.class, ()-> tree.remove(-11212d));
    }

    private static boolean orderIsRespected(AVL<Double>.Node<Double> node)
    {
        if(node == null || node.isLeaf())
            return true;

        boolean respected = (node.getLeft() != null && (node.getLeft().getData().compareTo(node.getData()) <= 0))
                            || (node.getRight() != null && (node.getRight().getData().compareTo(node.getData()) >= 0));

        return respected && orderIsRespected(node.getLeft()) && orderIsRespected(node.getRight());
    }


}