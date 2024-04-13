package ac.umons.be.firstg.segmentintersection.view.components;

import ac.umons.be.firstg.segmentintersection.model.AVL;
import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IShapeGen;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Tree Component
 */
public class Tree<T extends Comparable<T>> extends Group
{
    private static final float minDistX = 200;
    private static final float biasX = 10;
    private final float minBiasX;
    private final float maxDistX;
    private static final float minDistY = 115;
    private final int initHeight;
    private IShapeGen<T> leafShape;
    private IShapeGen<T> innerShape;
    private final boolean scaleDown;

    /**
     * Creates a tree node using default circles as nodes
     * @param tree The binary tree to show
     * @param start The point of the root of the created tree
     */
    public Tree(AVL<T> tree, Point start, boolean scaleDown)
    {
        this(tree,start,x -> {
            Circle circle = new Circle();
            circle.setRadius(5f);
            return circle;
        },x -> {
            Circle circle = new Circle();
            circle.setRadius(5f);
            return circle;
        }, scaleDown);
    }

    /**
     * Creates a tree node using custom shapes
     * @param tree The binary tree to show
     * @param start The point of the root of the created tree
     * @param leafShape The shape to use for leaves
     * @param innerShape The shape to use for internal nodes
     */
    public Tree(AVL<T> tree, Point start, IShapeGen<T> leafShape, IShapeGen<T> innerShape, boolean scaleDown)
    {
        this.leafShape = leafShape;
        this.innerShape = innerShape;
        this.scaleDown = scaleDown;
        //this.getChildren().addAll(getAllNodes(tree,start));
        ArrayList<Node> nodes = new ArrayList<>();
        initHeight = tree.getRoot().getHeight();
        maxDistX = (minDistX * tree.getRoot().getHeight());
        minBiasX = (biasX / tree.getRoot().getHeight());
        recGetAllNodes(tree.getRoot(), start, nodes, null, maxDistX);
        this.getChildren().addAll(nodes);
    }
    private void recGetAllNodes(AVL<T>.Node<T> tree, Point curr, ArrayList<Node> nodes, Point parent, double xDist)
    {
        if(tree == null)
        {
            return;
        }
        double newDist = xDist / 2;
        Point rightPoint= new Point(curr.x + newDist, curr.y + minDistY);
        Point leftPoint = new Point(curr.x - newDist, curr.y + minDistY);
        recGetAllNodes(tree.getRight(), rightPoint, nodes, curr, newDist);
        recGetAllNodes(tree.getLeft(), leftPoint, nodes, curr, newDist);
        // Then we can start drawing shapes ( so we can avoid lines getting on top of nodes )
        Node node;
        if(tree.isLeaf()){
            node = this.leafShape.createNode(tree.getData());
        }else{
            node = this.innerShape.createNode(tree.getData());
        }
        if(scaleDown)
        {
            int divBy = initHeight - tree.getHeight() + 1;
            node.setScaleX(node.getScaleX() / divBy);
            node.setScaleY(node.getScaleY() / divBy);
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
    public static <T extends Comparable<T>> void showTree(String title, AVL<T> data, IShapeGen<T> genNode, IShapeGen<T> genLeaf, boolean scaleDown)
    {
        if(data == null || data.getRoot() == null)
            return;
        Stage TWindow = new Stage();
        TWindow.setTitle(title);
        TWindow.show();
        TWindow.setResizable(true);
        TWindow.setMinWidth(400);
        TWindow.setMinHeight(400);

        Tree<T> tree = new Tree<>(data,
                new Point(300, 300),
                genLeaf,
                genNode,
                scaleDown);
        ScrollPane scrollPane = new ScrollPane(tree);
        HBox box = new HBox(scrollPane);
        box.setFillHeight(true);
        box.setAlignment(Pos.CENTER);

        Scene sceneT = new Scene(box, 600, 600);
        TWindow.setScene(sceneT);
    }
}
