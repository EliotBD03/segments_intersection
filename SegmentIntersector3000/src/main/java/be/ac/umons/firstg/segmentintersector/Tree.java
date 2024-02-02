package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.BinaryTree;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Simple class designed to help draw a tree
 */
public class Tree{
    public static float minDistX = 20;
    public static float minDistY = 50;

    public static ArrayList<Node> getAllPoints(BinaryTree tree, Point start){
        System.out.println(start);
        ArrayList<Pair<Point, Point>> points = new ArrayList<>();
        recGetAllPoints(tree,start, points, tree.height, null);
        ArrayList<Node> nodes = new ArrayList<>();
        Circle circle;
        Line line;
        for (Pair<Point, Point> pair : points){
            circle = new Circle();
            circle.setRadius(5f);
            circle.setCenterX(pair.getKey().getX());
            circle.setCenterY(pair.getKey().getY());
            if(pair.getValue() != null){
                // print line between them
                line = new Line(pair.getKey().getX(), pair.getKey().getY(), pair.getValue().getX(), pair.getValue().getY());
                nodes.add(line);
            }
            nodes.add(circle);
        }

        return nodes;
    }
    private static void recGetAllPoints(BinaryTree tree, Point curr, ArrayList<Pair<Point, Point>> points, int currHeight, Point parent){
        if(tree == null){
            return;
        }
        Pair<Point,Point> point = new Pair<>(curr, parent);
        points.add(point);
        Point rightPoint = new Point(curr.getX() + minDistX * currHeight, curr.getY() + minDistY);
        Point leftPoint = new Point(curr.getX() - minDistX * currHeight, curr.getY() + minDistY);
        recGetAllPoints(tree.right, rightPoint, points, currHeight - 1, curr);
        recGetAllPoints(tree.left, leftPoint, points, currHeight - 1, curr);
    }
}
