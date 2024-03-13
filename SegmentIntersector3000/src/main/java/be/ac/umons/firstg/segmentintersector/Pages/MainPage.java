package be.ac.umons.firstg.segmentintersector.Pages;

import be.ac.umons.firstg.segmentintersector.Interfaces.INodeGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.components.GraphXY;
import javafx.beans.Observable;
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

    // The currently shown tab
    private boolean tabClosed;
    private Tab currentTab;

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
        int tabSize = 100;
        INodeGen<Tab, String> tabGen = data ->
        {
            Tab tab = new Tab();
            if(data != null)
                tab.setGraphic(getIcon(data, tabSize/1000f + 0.01f));
            VBox content = new VBox();
            content.setVisible(false);
            content.managedProperty().bind(content.visibleProperty());
            content.setPrefHeight(600);
            content.setPrefWidth(300);
            Button button = new Button();
            button.setOnAction(e -> handleTabInteraction(true));
            content.getChildren().add(button);
            tab.setContent(content);
            return tab;
        };
        tabPane.getTabs().addAll(tabGen.createNode("icons/GraphSettingsIcon.png"),
                                 tabGen.createNode("icons/MapSettingsIcon.png"));
        tabPane.setTabMaxHeight(tabSize);
        tabPane.setTabMinHeight(tabSize);
        tabPane.setTabMaxWidth(tabSize);
        tabPane.setTabMinWidth(tabSize);
        tabPane.setSide(Side.LEFT);
        // Clear the starting selection
        tabPane.getSelectionModel().clearSelection();
        // Listen for interaction with the tabPane
        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> handleTabInteraction(false));

    }
    private ImageView getIcon(String fromResourcePath, float size)
    {
        ImageView imageView = new ImageView(String.valueOf(getClass().getResource(fromResourcePath)));
        imageView.setScaleX(size);
        imageView.setScaleY(size);
        return imageView;
    }

    private void handleTabInteraction(boolean closing)
    {
        if(tabPane.getSelectionModel().getSelectedItem() == null)
            return;
        System.out.println("_-_-_");
        if(currentTab == null && !closing)
        {
            System.out.println("no tab is open and is opening");
            currentTab = tabPane.getSelectionModel().getSelectedItem();
            System.out.println("Selected: " + tabPane.getSelectionModel().getSelectedIndex());
            if(currentTab == null)
                return;
            currentTab.getContent().setVisible(true);
        }// If a tab is open and is closing
        else if (currentTab != null && closing)
        {
            System.out.println("a tab is open and is closing");
            currentTab.getContent().setVisible(false);
            System.out.println("Just Cleared !");
            tabPane.getSelectionModel().clearSelection();
            System.out.println("What remains: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab = null;
        }
        // If a tab is open and is opening -> Close previous tab, show the content of the new one and change current tab
        else if (currentTab != null)
        {
            System.out.println("a tab is open and is opening");
            System.out.println("Selected: " + tabPane.getSelectionModel().getSelectedIndex());
            currentTab.getContent().setVisible(false);
            currentTab = tabPane.getSelectionModel().getSelectedItem();
            if(currentTab == null)
                return;
            currentTab.getContent().setVisible(true);
        }
        // If no tab is open and is opening
        // If no tab is open and is closing -> nothing happens
    }


}
