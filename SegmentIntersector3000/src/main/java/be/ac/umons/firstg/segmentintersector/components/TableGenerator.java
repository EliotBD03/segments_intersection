package be.ac.umons.firstg.segmentintersector.components;

import be.ac.umons.firstg.segmentintersector.Interfaces.IObjectGen;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;


/**
 * A class used to help quickly generate tables
 */
public class TableGenerator
{
    /**
     * This method will return a table in a format good for this project.
     * @param hasButton if true, the last column will be considered has a smaller column
     * @param pairs An array of {@link Pair} containing for each column,
     *              the name as the key and the map key of the column
     *              as the value of the pair.
     * @return The table fully constructed with the desired columns
     */
    @SafeVarargs
    public static TableView<Map> createTable(boolean hasButton, Pair<String, String>... pairs)
    {
        TableView<Map> tableView = new TableView<>();
        HBox.setHgrow(tableView, Priority.ALWAYS);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        for (Pair<String, String> pair : pairs)
        {
            TableColumn<Map, String> column = new TableColumn<>(pair.getKey());
            column.setEditable(false);
            column.setReorderable(false);
            column.setResizable(false);
            column.setSortable(false);
            column.setCellValueFactory(new MapValueFactory<>(pair.getValue()));
            tableView.getColumns().add(column);
        }
        // if the last columns is a button
        if(hasButton)
        {
            TableColumn<Map, ?> column = tableView.getColumns().get(pairs.length-1);
            column.setPrefWidth(25);
        }

        return tableView;
    }

    /**
     * Changes the placeholder value of the tableview by label nodes
     * @param tableView The tableview to change
     * @param texts     The text to show when the tableview is empty
     */
    public static void addPlaceHolderText(TableView<Map> tableView, String... texts)
    {
        VBox placeHolderBox = new VBox(10);
        placeHolderBox.setAlignment(Pos.CENTER);
        for (String text: texts)
            placeHolderBox.getChildren().add(new Label(text));
        tableView.setPlaceholder(placeHolderBox);
    }
}
