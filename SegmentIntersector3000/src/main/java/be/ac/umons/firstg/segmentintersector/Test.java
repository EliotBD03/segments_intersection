package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Temp.BinaryTree;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        ArrayList<Node> nodes = Tree.getAllPoints(binaryTree,new Point(scene.getWidth()/2,scene.getHeight()/2));
        group.getChildren().addAll(nodes);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}