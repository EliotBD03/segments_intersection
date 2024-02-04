package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Components.GraphXY;
import be.ac.umons.firstg.segmentintersector.Components.Tree;
import be.ac.umons.firstg.segmentintersector.Components.TreeSegmentNode;
import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.BinaryTree;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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

        // Tree Testing
        /*
        BinaryTree<SegmentTMP> bTree = new BinaryTree<>(new SegmentTMP("S1"));
        bTree.left = new BinaryTree<>(new SegmentTMP("S2"));
        bTree.right = new BinaryTree<>(new SegmentTMP("S3"));
        bTree.right.right = new BinaryTree<>(new SegmentTMP("S4"));
        bTree.left.right = new BinaryTree<>(new SegmentTMP("S5"));
        bTree.left.left = new BinaryTree<>(new SegmentTMP("S6"));
        bTree.right.left = new BinaryTree<>(new SegmentTMP("S7"));
        bTree.height = 2;

        // Create method to display Circle with text(the name of the segment) inside
        IShapeGen<SegmentTMP> coolCircle = X ->
        {
            Circle circle = new Circle();

            return circle;
        };
        IShapeGen<SegmentTMP> genLeaf = x -> new TreeSegmentNode(x,false, Color.AQUA);
        IShapeGen<SegmentTMP> genNode = x -> new TreeSegmentNode(x,true);

        Tree<SegmentTMP> tree = new Tree<SegmentTMP>(bTree, new Point(300,300),genLeaf,genNode);
        TreeSegmentNode segmentNode = new TreeSegmentNode(new SegmentTMP("S6"), true);


        segmentNode.setLayoutX(300);
        segmentNode.setLayoutY(300);
        //group.getChildren().add(segmentNode);
        group.getChildren().add(tree);
         */
        // Graph Testing

        GraphXY graph = new GraphXY(new Point(90,90), 400, 400,  10, 10, true);
        graph.setPaddingX(0);
        graph.setPaddingY(0);
        group.getChildren().add(graph);
        //group.getChildren().add(new Segment(new SegmentTMP(new Point(300,300), new Point(300, 400))));
        SegmentTMP segmentTMP = new SegmentTMP(new Point(1500,1500), new Point(4500, 4500));
        SegmentTMP segmentTMP2 = new SegmentTMP(new Point(1000,1500), new Point(4500, 4000));

        ArrayList<SegmentTMP> segmentTMPList = new ArrayList<>();
        segmentTMPList.add(segmentTMP);
        segmentTMPList.add(segmentTMP2);
        graph.addSegments(segmentTMPList);
        //graph.addSegment(new SegmentTMP(new Point(0,0), new Point(200, 200)));



        stage.setTitle("Only pain and suffering are awaiting!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}