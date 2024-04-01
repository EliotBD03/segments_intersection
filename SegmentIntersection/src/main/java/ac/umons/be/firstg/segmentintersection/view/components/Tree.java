package ac.umons.be.firstg.segmentintersection.view.components;

import ac.umons.be.firstg.segmentintersection.model.AVL;
import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IShapeGen;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Tree Component
 */
public class Tree<T extends Comparable<T>> extends Group
{
    private static final float maxDistX = 600;
    private static final float minDistY = 115;
    private final int initHeigh;
    private IShapeGen<T> leafShape;
    private IShapeGen<T> innerShape;

    /**
     * Creates a tree node using default circles as nodes
     * @param tree The binary tree to show
     * @param start The point of the root of the created tree
     */
    public Tree(AVL<T> tree, Point start)
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
    public Tree(AVL<T> tree, Point start, IShapeGen<T> leafShape, IShapeGen<T> innerShape)
    {
        this.leafShape = leafShape;
        this.innerShape = innerShape;
        //this.getChildren().addAll(getAllNodes(tree,start));
        ArrayList<Node> nodes = new ArrayList<>();
        initHeigh = tree.getRoot().getHeight();
        recGetAllNodes(tree.getRoot(), start, nodes, null);
        this.getChildren().addAll(nodes);
    }
    // TODO: When the real trees are implemented, there will be no need to actually store the currHeight
    private void recGetAllNodes(AVL<T>.Node<T> tree, Point curr, ArrayList<Node> nodes, Point parent)
    {

        if(tree == null)
        {
            return;
        }
        int divBy = 2 * (initHeigh - tree.getHeight());
        if(divBy == 0)
        {
            divBy = 1;
        }
        System.out.println((maxDistX / divBy));
        Point rightPoint= new Point(curr.x + (maxDistX / divBy), curr.y + minDistY);
        Point leftPoint = new Point(curr.x - (maxDistX / divBy), curr.y + minDistY);
        recGetAllNodes(tree.getRight(), rightPoint, nodes, curr);
        recGetAllNodes(tree.getLeft(), leftPoint, nodes, curr);
        // Then we can start drawing shapes ( so we can avoid lines getting on top of nodes )
        Node node;
        if(tree.isLeaf()){
            node = this.leafShape.createNode(tree.getData());
        }else{
            node = this.innerShape.createNode(tree.getData());
        }
        // If there is a parent, we add a line from that to the new node created
        if(parent != null){
            Line line = new Line(curr.x, curr.y, parent.x, parent.y);
            line.toBack();
            line.setStrokeWidth(2);
            nodes.add(line);
        }
        nodes.add(node);
        // Set position of the node (and center it using its bound)
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        node.setLayoutX(curr.x - bounds.getWidth()/2);
        node.setLayoutY(curr.y - bounds.getHeight()/2);

        //setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));


    }
}
