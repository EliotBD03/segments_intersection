package be.ac.umons.firstg.segmentintersector.Pages;

import be.ac.umons.firstg.segmentintersector.Interfaces.INodeGen;
import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainPage extends HBox
{
    private final VBox leftPane;
    private final ScrollPane graphPane;
    private final HBox timelinePane;
    private final TabPane tabPane;

    public MainPage()
    {
        // Create this HBox
        super();
        prefHeight(600);
        prefWidth(800);

        // Create children
        leftPane = new VBox();
        tabPane = new TabPane();
        // Resizes the left part with the main VBOX
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        getChildren().addAll(leftPane,tabPane);

        // Create leftPane Children
        timelinePane = new HBox(new Button("hiii"));
        graphPane = new ScrollPane();
        graphPane.setFitToWidth(true);
        graphPane.setFitToHeight(true);
        leftPane.getChildren().addAll(graphPane, timelinePane);
        //  Resize the graph pane with the leftPane
        VBox.setVgrow(graphPane, Priority.ALWAYS);
        GraphXY graph = new GraphXY(new Point(50,25), 600, 600, 10, 10, 10, 10, true);
        graphPane.setContent(graph);

        // Creating the TabPane
        createTabs();
        //
        //  Stops user from closing tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    }

    private void createTabs()
    {
        INodeGen<Tab, String> tabGen = data ->
        {
            Tab tab = new Tab();
            tab.setGraphic(getIcon(data));
            AnchorPane content = new AnchorPane();
            content.setPrefHeight(600);
            content.setPrefWidth(164);
            tab.setContent(content);
            return tab;
        };
        tabPane.getTabs().addAll(tabGen.createNode("icons/GraphSettingsIcon.png"),
                                 tabGen.createNode("icons/MapSettingsIcon.png"));
        tabPane.setTabMaxHeight(40);
        tabPane.setTabMinHeight(40);
        tabPane.setTabMaxWidth(40);
        tabPane.setTabMinWidth(40);
        tabPane.setSide(Side.LEFT);
    }
    private ImageView getIcon(String fromResourcePath)
    {
        ImageView imageView = new ImageView(String.valueOf(getClass().getResource(fromResourcePath)));
        imageView.setScaleX(0.05);
        imageView.setScaleY(0.05);
        return imageView;
    }
}
