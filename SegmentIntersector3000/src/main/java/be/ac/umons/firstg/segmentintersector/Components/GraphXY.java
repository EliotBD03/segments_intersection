package be.ac.umons.firstg.segmentintersector.Components;


import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.List;

public class GraphXY extends AnchorPane
{
    private int paddingX;
    private int paddingY;


    private double sizeAxisX,sizeAxisY;
    private Point origin;
    private Point maxAxisX;
    private Point maxAxisY;

    // Used to scale the graph
    private double maxX,maxY;
    private double minX,minY;
    private double scaleX,scaleY;

    public GraphXY(Point start, double sizeAxisX, double sizeAxisY)
    {
        // Infinity for both min values at start
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        this.sizeAxisX = sizeAxisX;
        this.sizeAxisY = sizeAxisY;

        this.maxAxisY = start;
        // Get points for the ends of the X and Y axis
        this.origin = new Point(this.maxAxisY.getX(), this.maxAxisY.getY() + sizeAxisY);
        this.maxAxisX = new Point(this.origin.getX() + sizeAxisX, this.origin.getY());
        // Draw the axis using lines
        // X axis
        Line line = new Line(this.maxAxisX.getX(), this.maxAxisX.getY(), this.origin.getX(), this.origin.getY());
        line.setStrokeWidth(2);
        this.getChildren().add(line);
        // Y axis
        line = new Line(this.maxAxisY.getX(), this.maxAxisY.getY(), this.origin.getX(), this.origin.getY());
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
        return new Point(this.origin.getX() + point.getX() , this.origin.getY() - point.getY());
    }

    public Point scalePoint(Point point){
        return new Point((point.getX() - minX)/ scaleX, (point.getY() - minY)/ scaleY);
    }


    public void addSegments(List<SegmentTMP> segments)
    {
        Segment segment;
        SegmentTMP segmentScaled;

        for(SegmentTMP segmentTMP: segments){
            // Update scale of the graph
            maxX = Math.max(Math.max(maxX, segmentTMP.getPoint1().getX()), segmentTMP.getPoint2().getX());
            maxY = Math.max(Math.max(maxY, segmentTMP.getPoint1().getY()), segmentTMP.getPoint2().getY());
            minX = Math.min(Math.min(minX, segmentTMP.getPoint1().getX()), segmentTMP.getPoint2().getX());
            minY = Math.min(Math.min(minY, segmentTMP.getPoint1().getY()), segmentTMP.getPoint2().getY());

            UpdateScale();
        }
        // Then when the scale has been fixed we can all segments
        for(SegmentTMP segmentTMP: segments){
            segmentScaled = new SegmentTMP(scalePoint(segmentTMP.getPoint1()),
                    scalePoint(segmentTMP.getPoint2()));
            addSegment(segmentScaled);
        }


    }

    public void UpdateScale(){
        // Round the number to the nearest mult of the size of the axisX and axisY
        // Also add padding if needed

        // Careful using ceil and floor, this might require more testing
        maxX = (Math.ceil(maxX/sizeAxisX) * sizeAxisX) + paddingX;
        minX = (Math.floor(minX/sizeAxisX) * sizeAxisX) - paddingX;
        maxY = (Math.ceil(maxY/sizeAxisY) * sizeAxisY) + paddingY;
        minY = (Math.floor(minY/sizeAxisY) * sizeAxisY) - paddingY;

        System.out.println("X: " + maxX + ": " + minX);
        System.out.println("Y: " + maxY + ": " + minY);

        scaleX = (maxX - minX) / sizeAxisX;
        scaleY = (maxY - minY) / sizeAxisY;
    }


    //_______________GETTER/SETTER


    public int getPaddingX() {
        return paddingX;
    }

    public int getPaddingY() {
        return paddingY;
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }
}
