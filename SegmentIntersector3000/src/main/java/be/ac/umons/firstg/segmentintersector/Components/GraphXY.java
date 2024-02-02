package be.ac.umons.firstg.segmentintersector.Components;


import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class GraphXY extends AnchorPane
{

    private Point start;
    private Point maxAxisX;
    private Point maxAxisY;

    public GraphXY(Point start, float maxX, float maxY)
    {
        this.start = start;
        // Get points for the ends of the X and Y axis
        this.maxAxisX  = new Point(this.start.getX() + maxX, this.start.getY());
        this.maxAxisY = new Point(this.start.getX(), this.start.getY() - maxY);
        // Draw the axis using lines
        // X axis
        Line line = new Line(this.maxAxisX.getX(), this.maxAxisX.getY(), this.start.getX(), this.start.getY());
        line.setStrokeWidth(2);
        this.getChildren().add(line);
        // Y axis
        line = new Line(this.maxAxisY.getX(), this.maxAxisY.getY(), this.start.getX(), this.start.getY());
        line.setStrokeWidth(2);
        this.getChildren().add(line);
    }

    public void addSegment(SegmentTMP segment){

        Point point1 = translatePoint(segment.getPoint1());
        Point point2 = translatePoint(segment.getPoint2());

        SegmentTMP segmentTMP = new SegmentTMP(point1,point2);
        Segment segmentNode = new Segment(segmentTMP);
        this.getChildren().add(segmentNode);
    }

    /**
     * Give the values of a point in relation to the graph position (and not scale)
     * @param point The desired point
     * @return The correct location of the point on this graph
     */
    public Point translatePoint(Point point){
        return new Point(this.start.getX() + point.getX() , this.start.getY() - point.getY());
    }


}
