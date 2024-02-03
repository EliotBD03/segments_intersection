package be.ac.umons.firstg.segmentintersector.Components;


import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class TreeSegmentNode extends StackPane
{
    private double height;
    private double width;
    public TreeSegmentNode(SegmentTMP segmentTMP, boolean ringShaped)
    {
        // Create ring
        //    v and v1: Position, v2 and 3: size, v4 and v5: starting and ending radius
        if(ringShaped)
        {
            Arc arc = new Arc(0,0,30,30,0,360);
            arc.setFill(Color.GREENYELLOW);
            arc.setType(ArcType.OPEN);
            arc.setStroke(Color.BLACK);
            getChildren().add(arc);
        }else
        {
            Rectangle rectangle = new Rectangle(60,40);
            Rectangle clip = new Rectangle(58,38);
            clip.setFill(Color.GREENYELLOW);
            rectangle.setFill(Color.BLACK);
            getChildren().add(rectangle);
            getChildren().add(clip);
        }

        // Add text
        Text text = new Text(segmentTMP.getName());
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        getChildren().add(text);
        //setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        //Bounds bounds = localToScene(getBoundsInLocal());
        //System.out.println(bounds.getWidth());
        Bounds bounds = localToScene(getBoundsInLocal());
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
    }


    public double getNodeHeight()
    {
        return height;
    }

    public double getNodeWidth()
    {
        return width;
    }
}
