package be.ac.umons.firstg.segmentintersector.Components;


import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class GraphXY extends AnchorPane
{
    private int paddingX;
    private int paddingY;


    private int markSize = 10;
    private int nbOfMarksX, nbOfMarksY;
    private double gapX,gapY;
    private ArrayList<Text> legendsX;
    private ArrayList<Text> legendsY;

    private final double sizePixelAxisX, sizePixelAxisY;
    private final Point origin;
    private final Point maxAxisX;
    private final Point maxAxisY;

    // Used to scale the graph
    private double minScaleX, minScaleY;
    private double maxX,maxY;
    private double minX,minY;
    private double scalePixelX, scalePixelY;

    // Stores all segments contained inside the graph
    private HashMap<SegmentTMP, Segment> segmentsShown;

    /**
     * Creates a graph using fixed values for his X and Y axis
     * @param start         The initial location of the graph, from the top left
     * @param sizePixelAxisX     The size of it's X axis
     * @param sizePixelAxisY     Thz size of it's Y axis
     */
    public GraphXY(Point start, double sizePixelAxisX, double sizePixelAxisY, double minScaleX, double minScaleY,  int nbOfMarksX, int nbOfMarksY, boolean showGrid)
    {
        segmentsShown = new HashMap<>();
        // Infinity for both min values at start
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        this.sizePixelAxisX = sizePixelAxisX;
        this.sizePixelAxisY = sizePixelAxisY;

        this.maxAxisY = start;
        // Get points for the ends of the X and Y axis
        this.origin = new Point(this.maxAxisY.getX(), this.maxAxisY.getY() + sizePixelAxisY);
        this.maxAxisX = new Point(this.origin.getX() + sizePixelAxisX, this.origin.getY());
        // Draw the axis using lines
        // X axis
        Line line = new Line(this.maxAxisX.getX(), this.maxAxisX.getY(), this.origin.getX(), this.origin.getY());
        line.setStrokeWidth(2);
        this.getChildren().add(line);
        // Y axis
        line = new Line(this.maxAxisY.getX(), this.maxAxisY.getY(), this.origin.getX(), this.origin.getY());
        line.setStrokeWidth(2);
        this.getChildren().add(line);


        this.minScaleX = minScaleX;
        this.minScaleY = minScaleY;
        // Scale marks
        // Draw Scale Marks

        this.nbOfMarksX = nbOfMarksX;
        this.nbOfMarksY = nbOfMarksY;
        double distMarkX = sizePixelAxisX / nbOfMarksX;
        double distMarkY = sizePixelAxisY / nbOfMarksY;
        double markSize = showGrid ? sizePixelAxisY: this.markSize;
        legendsX = new ArrayList<>();
        legendsY = new ArrayList<>();
        for (int i = 0; i <= nbOfMarksX; i++)
        {
            drawScaleMarkAxisX(distMarkX * i, markSize);
        }
        markSize = showGrid ? sizePixelAxisX: this.markSize;
        for (int i = 0; i <= nbOfMarksY; i++)
        {
            drawScaleMarkAxisY(distMarkY * i, markSize);
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
        return new Point((point.getX() - minX)/ scalePixelX, (point.getY() - minY)/ scalePixelY);
    }

    private void updateMinMax(SegmentTMP segment)
    {
        maxX = Math.max(Math.max(maxX, segment.getPoint1().getX()), segment.getPoint2().getX());
        maxY = Math.max(Math.max(maxY, segment.getPoint1().getY()), segment.getPoint2().getY());
        minX = Math.min(Math.min(minX, segment.getPoint1().getX()), segment.getPoint2().getX());
        minY = Math.min(Math.min(minY, segment.getPoint1().getY()), segment.getPoint2().getY());
    }

    /**
     * This method will reset the graph.
     * Add all segments to the graph, changing its scale to accommodate with the max and min values of the segments
     * @param segments
     */
    public void addSegments(List<SegmentTMP> segments)
    {
        ResetGraph();
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
        // Update legend
        UpdateLegend();
        System.out.println("Min X: " + minX);
        System.out.println("Min Y:" + minY);

    }


    private void drawScaleMarkAxisX(double position, double size)
    {
        Line mark = new Line(origin.getX() + position, origin.getY(), origin.getX() + position, origin.getY() - size);
        mark.setStroke(Color.GRAY);
        Text text = new Text();
        //Double.toString(minX + position)

        text.setLayoutX(origin.getX() + position);
        text.setLayoutY(origin.getY() + 20);
        getChildren().add(text);
        legendsX.add(text);
        getChildren().add(mark);
    }

    private void drawScaleMarkAxisY(double position, double size)
    {
        Line mark = new Line(origin.getX(), origin.getY() - position, origin.getX() + size, origin.getY() - position);
        mark.setStroke(Color.GRAY);
        Text text = new Text();
        text.setLayoutX(origin.getX() - 50);
        text.setLayoutY(origin.getY() - position);
        getChildren().add(text);
        legendsY.add(text);
        getChildren().add(mark);
    }

    private void UpdateLegend()
    {
        gapX = (maxX-minX) / nbOfMarksX;
        gapY = (maxY-minY) / nbOfMarksY;
        for (int i = 0; i < legendsX.size(); i++)
        {
            legendsX.get(i).setText(Double.toString(minX + (gapX * i)));
        }
        for (int i = 0; i < legendsY.size(); i++)
        {
            legendsY.get(i).setText(Double.toString(minY + (gapY * i)));
        }

    }

    /**
     * Update the scale of the graph, in relation to the min and max values of the points
     */
    private void UpdateScale()
    {
        // Round the number to the nearest mult of the size of the axisX and axisY (round up for max, round down for min)
        // Also add padding if needed

        // Careful using ceil and floor, this might require more testing
        maxX = (Math.ceil(maxX/ minScaleX) * minScaleX) + paddingX;
        minX = (Math.floor(minX/ minScaleX) * minScaleX) - paddingX;
        maxY = (Math.ceil(maxY/ minScaleY) * minScaleY) + paddingY;
        minY = (Math.floor(minY/ minScaleY) * minScaleY) - paddingY;
        scalePixelX = (maxX - minX) / sizePixelAxisX;
        scalePixelY = (maxY - minY) / sizePixelAxisY;

    }

    /**
     * Removes all segments displayed in the graph.
     * Also resets the scale
     */
    public void ResetGraph()
    {
        for (SegmentTMP segmentTMP: segmentsShown.keySet())
        {
            getChildren().remove(segmentsShown.get(segmentTMP));
        }
        // Reset the scale
        maxX = minScaleX;
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxY = minScaleY;
        UpdateScale();
        // And the labels too
        UpdateLegend();
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
