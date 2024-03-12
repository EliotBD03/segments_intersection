package be.ac.umons.firstg.segmentintersector;

import be.ac.umons.firstg.segmentintersector.Pages.MainPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Test.class.getResource("MainPage.fxml"));


        //Scene scene = new Scene(fxmlLoader.load());
        Scene scene = new Scene(new MainPage());
        /*
        MainPage mainPage = fxmlLoader.getController();
        mainPage.setPrimaryStage(stage);

        IntersectionTable intersectionTable = new IntersectionTable();

        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s1"),
                new SegmentTMP("s1")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1")
        ));

        mainPage.TableGroup.setCenter(intersectionTable);
        mainPage.TableGroup.setPadding(new Insets(25,10,0,10));

        */

        // Tree Windows
        //Stage TWindow = new Stage();
        //TWindow.setTitle("T Tree");
        //TWindow.show();

        //ScrollPane scrollPane = new ScrollPane(tree);
        //HBox borderPane = new HBox(scrollPane);
        //borderPane.setFillHeight(true);

        //borderPane.setAlignment(Pos.CENTER);




        //Scene sceneT = new Scene(borderPane, 600, 600);
        //TWindow.setResizable(true);
        //TWindow.setMinHeight(400);
        //TWindow.setMinWidth(400);
        //TWindow.setScene(sceneT);

        /*
        String css=  this.getClass().getResource("test.css").toExternalForm();
        scene.getStylesheets().add(css);
        HelloController helloController = fxmlLoader.getController();


        IntersectionTable intersectionTable = helloController.getInterTable();

        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));
        intersectionTable.addIntersection(new Point(3,1), List.of(
                new SegmentTMP("s1"),
                new SegmentTMP("s2")
        ));

        intersectionTable.addIntersection(new Point(10,9), List.of(
                new SegmentTMP("s5"),
                new SegmentTMP("s0"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9"),
                new SegmentTMP("s9")
        ));
         */
        //Group group = new Group();
        //Scene scene = new Scene(group, 600,600);

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
        //GraphXY graph = new GraphXY(new Point(90,90), 400, 400, 50, 50,  5, 5, true);

        //group.getChildren().add(graph);


        /*
        ArrayList<SegmentTMP> segmentTMPList = new ArrayList<>(
                List.of(new SegmentTMP(new Point(0,0), new Point(25, 25))));

        graph.addSegments(segmentTMPList);
        */


        //ArrayList<SegmentTMP> segmentTMPList = getSegmentsFromFile("/home/foucart/Bureau/Git/segments_intersection/SegmentIntersector3000/src/main/java/be/ac/umons/firstg/segmentintersector/Temp/cartes/fichier1.txt");
        //graph.addSegments(segmentTMPList);
        //graph.resetGraph();
        //graph.moveSweepLine(new Point(51.48,94.67), null,List.of(new SegmentTMP(new Point(39.99, 188.38), new Point(99.78, 42.37))),null);
        //graph.moveSweepLine(new Point(51.48,94.67), null,null,null);

        // TODO EventPoint Component Class WITH A SPECIAL METHOD: HIGHLIGHT
        //segmentTMPList.add(segmentTMP4);
        //graph.ResetGraph();
        //graph.addSegment(new SegmentTMP(new Point(0,0), new Point(200, 200)));




        // Prevent window being too small
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("Only pain and suffering are awaiting!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}