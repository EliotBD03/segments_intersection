package ac.umons.be.firstg.segmentintersection.view.components;


import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class TreeNode extends StackPane
{
    public TreeNode(Segment segmentTMP, boolean ringShaped)
    {
        this(segmentTMP, ringShaped, Color.GREENYELLOW);
    }

    public TreeNode(Segment segmentTMP, boolean ringShaped, Color color)
    {

        // Create ring
        //    v and v1: Position, v2 and 3: size, v4 and v5: starting and ending radius
        if(ringShaped)
        {
            Arc arc = new Arc(0,0,30,30,0,360);
            arc.setFill(color);
            arc.setType(ArcType.OPEN);
            arc.setStroke(Color.BLACK);
            getChildren().add(arc);
        }else
        {
            Rectangle rectangle = new Rectangle(60,40);
            Rectangle clip = new Rectangle(58,38);
            clip.setFill(color);
            rectangle.setFill(Color.BLACK);
            getChildren().add(rectangle);
            getChildren().add(clip);
        }

        // Add text
        Text text = new Text(segmentTMP.getId());
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        getChildren().add(text);
    }

    public TreeNode(Point point, Color color)
    {
        Rectangle rectangle = new Rectangle(70,40);
        Rectangle clip = new Rectangle(68,38);
        clip.setFill(color);
        rectangle.setFill(Color.BLACK);
        getChildren().add(rectangle);
        getChildren().add(clip);


        // Add text
        Text text = new Text("x: " + String.format("%.4f", point.x) + "\ny: " + String.format("%.4f", point.y));
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,  10));
        getChildren().add(text);
    }
}