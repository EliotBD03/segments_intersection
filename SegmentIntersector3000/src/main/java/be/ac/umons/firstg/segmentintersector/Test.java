package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Components.Tree;
import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.BinaryTree;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Group group = new Group();
        Scene scene = new Scene(group, 600,600);

        BinaryTree<Integer> binaryTree = new BinaryTree<>(3);
        binaryTree.left = new BinaryTree<>(6);
        binaryTree.left.left = new BinaryTree<>(7);
        binaryTree.right = new BinaryTree<>(8);
        binaryTree.right.right = new BinaryTree<>(9);
        binaryTree.right.left = new BinaryTree<>(99);
        binaryTree.left.left.left = new BinaryTree<>(0);
        binaryTree.height = 3;
        IShapeGen circleShape = () -> {
            Circle circle = new Circle();
            circle.setRadius(5f);
            return circle;
        };
        IShapeGen rectangleShape = () -> {
            Rectangle rectangle = new Rectangle(30,20);

            rectangle.setX(- rectangle.getWidth() /2);
            rectangle.setY(- rectangle.getHeight() /2);
            return rectangle;
        };
        group.getChildren().add(new Tree(binaryTree, new Point(300,200), rectangleShape, circleShape));

        stage.setTitle("Only pain and suffering are awaiting!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}