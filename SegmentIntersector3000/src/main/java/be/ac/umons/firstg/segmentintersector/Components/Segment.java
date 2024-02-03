package be.ac.umons.firstg.segmentintersector.Components;

import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Segment extends Group {
    private final SegmentTMP segment;
    private final Line line;
    private final Circle endPoint1;
    private final Circle endPoint2;


    public Segment(SegmentTMP segment){
        this.segment = segment;
        // Draw segment
        IShapeGen getCircle = () -> {
            Circle circle = new Circle();
            circle.setRadius(3f);
            return circle;
        };
        // Add the 2 end points
        endPoint1 = (Circle) getCircle.createShape();
        endPoint1.setLayoutX(segment.getPoint1().getX());
        endPoint1.setLayoutY(segment.getPoint1().getY());
        this.getChildren().add(endPoint1);

        endPoint2 = (Circle) getCircle.createShape();
        endPoint2.setLayoutX(segment.getPoint2().getX());
        endPoint2.setLayoutY(segment.getPoint2().getY());
        this.getChildren().add(endPoint2);
        // Add Line
        this.line = new Line(segment.getPoint1().getX(), segment.getPoint1().getY(),
                             segment.getPoint2().getX(), segment.getPoint2().getY());
        this.getChildren().add(line);
    }
}
