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

        IShapeGen<SegmentTMP> genLeaf = x -> new TreeSegmentNode(x,false, Color.AQUA);
        IShapeGen<SegmentTMP> genNode = x -> new TreeSegmentNode(x,true);

        Tree<SegmentTMP> tree = new Tree<>(bTree, new Point(300,300),genLeaf,genNode);

        //group.getChildren().add(segmentNode);
        group.getChildren().add(tree);

         */

        // Graph Testing
        GraphXY graph = new GraphXY(new Point(90,90), 400, 400, 100, 100,  10, 10, false);

        group.getChildren().add(graph);


        //SegmentTMP segmentTMP2 = new SegmentTMP(new Point(1000,1500), new Point(4500, 4000));

        ArrayList<SegmentTMP> segmentTMPList = new ArrayList<>(
                List.of(new SegmentTMP(new Point(51.48,94.67), new Point(33.34, 94.67)),
                        new SegmentTMP(new Point(39.99, 188.38), new Point(99.78, 42.37)),
                        new SegmentTMP(new Point(115.8, 45.6), new Point(69.65, 45.6)),
                        new SegmentTMP(new Point(139.22, 134.68), new Point(83.03, 165.42))));

        graph.addSegments(segmentTMPList);
        graph.initializeSweepLine();
        graph.moveSweepLine(new Point(39.99, 188.38),
                List.of(new SegmentTMP(new Point(39.99, 188.38),
                        new Point(99.78, 42.37))),null,null);
        graph.moveSweepLine(new Point(83.03, 165.42),
                List.of(new SegmentTMP(new Point(139.22, 134.68), new Point(83.03, 165.42))),null,null);

        //graph.moveSweepLine(new Point(51.48,94.67), null,List.of(new SegmentTMP(new Point(39.99, 188.38), new Point(99.78, 42.37))),null);
        //graph.moveSweepLine(new Point(51.48,94.67), null,null,null);

        // TODO EventPoint Component Class WITH A SPECIAL METHOD: HIGHLIGHT
        //segmentTMPList.add(segmentTMP4);
        //graph.ResetGraph();
        //graph.addSegment(new SegmentTMP(new Point(0,0), new Point(200, 200)));





        stage.setTitle("Only pain and suffering are awaiting!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}