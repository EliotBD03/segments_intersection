package be.ac.umons.firstg.segmentintersector.Components;

import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.BinaryTree;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree Component
 */
public class Tree<T> extends AnchorPane
{
    private static final float minDistX = 50;
    private static final float minDistY = 100;
    private IShapeGen<T> leafShape;
    private IShapeGen<T> innerShape;

    /**
     * Creates a tree node using default circles as nodes
     * @param tree The binary tree to show
     * @param start The point of the root of the created tree
     */
    public Tree(BinaryTree<T> tree, Point start)
    {
        this(tree,start,x -> {
            Circle circle = new Circle();
            circle.setRadius(5f);
            return circle;
        },x -> {
            Circle circle = new Circle();
            circle.setRadius(5f);
            return circle;
        });
    }

    /**
     * Creates a tree node using custom shapes
     * @param tree The binary tree to show
     * @param start The point of the root of the created tree
     * @param leafShape The shape to use for leaves
     * @param innerShape The shape to use for internal nodes
     */
    public Tree(BinaryTree<T> tree, Point start, IShapeGen<T> leafShape, IShapeGen<T> innerShape)
    {
        this.leafShape = leafShape;
        this.innerShape = innerShape;
        //this.getChildren().addAll(getAllNodes(tree,start));
        ArrayList<Node> nodes = new ArrayList<>();
        recGetAllNodes(tree,start,nodes,tree.height,null);
        this.getChildren().addAll(nodes);
    }
    // TODO: When the real trees are implemented, there will be no need to actually store the currHeight
    private void recGetAllNodes(BinaryTree<T> tree, Point curr, ArrayList<Node> nodes, int currHeight, Point parent)
    {

        if(tree == null)
        {
            return;
        }
        // We continue to explore the tree recursively but we decrease the height
        Point rightPoint = new Point(curr.getX() + minDistX * currHeight, curr.getY() + minDistY);
        Point leftPoint = new Point(curr.getX() - minDistX * currHeight, curr.getY() + minDistY);
        recGetAllNodes(tree.right, rightPoint, nodes, currHeight - 1, curr);
        recGetAllNodes(tree.left, leftPoint, nodes, currHeight - 1, curr);
        // Then we can start drawing shapes ( so we can avoid lines getting on top of nodes )
        Node node;
        if(tree.isLeaf()){
            node = this.leafShape.createShape(tree.data);
        }else{
            node = this.innerShape.createShape(tree.data);
        }
        // If there is a parent, we add a line from that to the new node created
        if(parent != null){
            Line line = new Line(curr.getX(), curr.getY(), parent.getX(), parent.getY());
            line.toBack();
            line.setStrokeWidth(2);
            nodes.add(line);
        }
        nodes.add(node);
        // Set position of the node (and center it using its bound)
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        node.setLayoutX(curr.getX() - bounds.getWidth()/2);
        node.setLayoutY(curr.getY() - bounds.getHeight()/2);




    }
}
