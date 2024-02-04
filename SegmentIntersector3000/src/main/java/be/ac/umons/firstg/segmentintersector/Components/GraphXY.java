package be.ac.umons.firstg.segmentintersector.Components;


import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.HashMap;
import java.util.List;

public class GraphXY extends AnchorPane
{
    private int paddingX;
    private int paddingY;

    private int nbOfMarksX,nbOfMarksY;
    private int markSize = 10;

    private final double sizeAxisX,sizeAxisY;
    private final Point origin;
    private final Point maxAxisX;
    private final Point maxAxisY;

    // Used to scale the graph
    private double maxX,maxY;
    private double minX,minY;
    private double scaleX,scaleY;

    // Stores all segments contained inside the graph
    private HashMap<SegmentTMP, Segment> segmentsShown;

    /**
     * Creates a graph using fixed values for his X and Y axis
     * @param start         The initial location of the graph, from the top left
     * @param sizeAxisX     The size of it's X axis
     * @param sizeAxisY     Thz size of it's Y axis
     */
    public GraphXY(Point start, double sizeAxisX, double sizeAxisY, int nbOfMarksX, int nbOfMarksY, boolean showGrid)
    {
        segmentsShown = new HashMap<>();
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

        // Scale marks
        // Draw Scale Marks
        double distX = sizeAxisX / nbOfMarksX;
        double distY = sizeAxisY / nbOfMarksY;
        double markSize = showGrid ? sizeAxisY: this.markSize;

        for (int i = 1; i <= nbOfMarksX; i++)
        {
            drawScaleMarkAxisX(distX * i, markSize);
        }
        markSize = showGrid ? sizeAxisX: this.markSize;
        for (int i = 1; i <= nbOfMarksY; i++)
        {
            drawScaleMarkAxisY(distY * i, markSize);
        }
        //setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }


    private void addSegmentTo(SegmentTMP segment)
    {
        // Scales and then translates the point to the graph
        Point point1 = translatePoint(scalePoint(segment.getPoint1()));
        Point point2 = translatePoint(scalePoint(segment.getPoint2()));

        SegmentTMP segmentTMP = new SegmentTMP(point1,point2);
        Segment segmentNode = new Segment(segmentTMP);
        // Add segment so we can easily reference it
        segmentsShown.put(segmentTMP, segmentNode);
        this.getChildren().add(segmentNode);
    }

    /**
     * Give the values of a point in relation to the graph position in is group (and not scale)
     * @param point The desired point
     * @return The correct location of the point on this graph
     */
    public Point translatePoint(Point point)
    {
        return new Point(this.origin.getX() + point.getX() , this.origin.getY() - point.getY());
    }

    /**
     * Gives the point coords scaled down to the graph proportions
     * @param point
     * @return
     */
    public Point scalePoint(Point point)
    {
        return new Point((point.getX() - minX)/ scaleX, (point.getY() - minY)/ scaleY);
    }

    /**
     * This method should only be used once per graph.
     * Add all segments to the graph, changing its scale to accommodate with the max and min values of the segments
     * @param segments
     */
    public void addSegments(List<SegmentTMP> segments)
    {
        for(SegmentTMP segmentTMP: segments)
        {
            // Update scale of the graph
            updateMinMax(segmentTMP);

            UpdateScale();
        }
        // Then when the scale has been fixed we can add all segments
        for(SegmentTMP segmentTMP: segments)
        {
            addSegmentTo(segmentTMP);
        }
    }

    private void updateMinMax(SegmentTMP segment)
    {
        maxX = Math.max(Math.max(maxX, segment.getPoint1().getX()), segment.getPoint2().getX());
        maxY = Math.max(Math.max(maxY, segment.getPoint1().getY()), segment.getPoint2().getY());
        minX = Math.min(Math.min(minX, segment.getPoint1().getX()), segment.getPoint2().getX());
        minY = Math.min(Math.min(minY, segment.getPoint1().getY()), segment.getPoint2().getY());
    }

    /**
     * Update the scale of the graph, in relation to the min and max values of the points
     */
    public void UpdateScale()
    {
        // Round the number to the nearest mult of the size of the axisX and axisY (round up for max, round down for min)
        // Also add padding if needed

        // Careful using ceil and floor, this might require more testing
        maxX = (Math.ceil(maxX/sizeAxisX) * sizeAxisX) + paddingX;
        minX = (Math.floor(minX/sizeAxisX) * sizeAxisX) - paddingX;
        maxY = (Math.ceil(maxY/sizeAxisY) * sizeAxisY) + paddingY;
        minY = (Math.floor(minY/sizeAxisY) * sizeAxisY) - paddingY;

        scaleX = (maxX - minX) / sizeAxisX;
        scaleY = (maxY - minY) / sizeAxisY;
    }


    private void drawScaleMarkAxisX(double position, double size)
    {
        Line mark = new Line(origin.getX() + position, origin.getY(), origin.getX() + position, origin.getY() - size);
        mark.setStroke(Color.GRAY);
        getChildren().add(mark);
    }

    private void drawScaleMarkAxisY(double position, double size)
    {
        Line mark = new Line(origin.getX(), origin.getY() - position, origin.getX() + size, origin.getY() - position);
        mark.setStroke(Color.GRAY);
        getChildren().add(mark);
    }

    //_______________GETTER/SETTER


    public int getPaddingX()
    {
        return paddingX;
    }

    public int getPaddingY()
    {
        return paddingY;
    }

    public void setPaddingX(int paddingX)
    {
        this.paddingX = paddingX;
    }

    public void setPaddingY(int paddingY)
    {
        this.paddingY = paddingY;
    }
}
