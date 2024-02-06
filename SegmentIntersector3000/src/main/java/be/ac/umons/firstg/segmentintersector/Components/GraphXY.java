package be.ac.umons.firstg.segmentintersector.Components;


import be.ac.umons.firstg.segmentintersector.Interfaces.IShapeGen;
import be.ac.umons.firstg.segmentintersector.Temp.Point;
import be.ac.umons.firstg.segmentintersector.Temp.SegmentTMP;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


/**
 * Graph component that can display segments and his very customisable
 */
public class GraphXY extends AnchorPane
{
    private int paddingX;
    private int paddingY;


    private int markSize = 10;
    private int nbOfMarksX, nbOfMarksY;
    private double gapX,gapY;
    private ArrayList<Text> legendsX;
    private ArrayList<Text> legendsY;
    private IShapeGen<Point> textGen;

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

    // Sweep Line settings
    private Line sweepLine;
    private Stack<Segment> toSetInactive;
    private Stack<Segment> toSetActive;

    /**
     * Creates a graph using fixed values for his X and Y axis
     * @param start              The initial location of the graph, from the top left in the pane
     * @param start              The initial location of the graph, from the top left in the pane
     * @param sizePixelAxisX     The size of it's X axis in pixels
     * @param sizePixelAxisY     The size of it's Y axis in pixels
     * @param minScaleX         The minimum value of the scale on the X axis
     * @param minScaleY         The minimum value of the scale on the Y axis
     * @param nbOfMarksX        Number of columns of the grid
     * @param nbOfMarksY        Number of rows of the grid
     * @param showGrid          Draw the grid of the graph if true
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
        line.setStrokeWidth(3);
        this.getChildren().add(line);
        // Y axis
        line = new Line(this.maxAxisY.getX(), this.maxAxisY.getY(), this.origin.getX(), this.origin.getY());
        line.setStrokeWidth(3);
        this.getChildren().add(line);
        // Text Legend settings
        legendsX = new ArrayList<>();
        legendsY = new ArrayList<>();
        // Add initial text marker
        textGen = x -> {
            Text text = new Text();
            text.setLayoutX(origin.getX() + x.getX());
            text.setLayoutY(origin.getY() + x.getY());
            return text;
        };

        // Scale definition
        this.minScaleX = minScaleX;
        this.minScaleY = minScaleY;
        // Scale marks
        // Draw Scale Marks

        this.nbOfMarksX = nbOfMarksX;
        this.nbOfMarksY = nbOfMarksY;
        double distMarkX = sizePixelAxisX / nbOfMarksX;
        double distMarkY = sizePixelAxisY / nbOfMarksY;
        double markSize = showGrid ? sizePixelAxisY: this.markSize;


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
        segmentsShown.put(segment, segmentNode);
        this.getChildren().add(segmentNode);
    }

    /**
     * This method will reset the graph.
     * Add all segments to the graph, changing its scale to accommodate with the max and min values of the segments
     * @param segments  The segments to add
     */
    public void addSegments(List<SegmentTMP> segments)
    {
        ResetGraph();
        for(SegmentTMP segmentTMP: segments)
        {
            // Update scale of the graph
            updateMinMax(segmentTMP);

            updateScale();
        }
        // Then when the scale has been fixed we can add all segments
        for(SegmentTMP segmentTMP: segments)
        {
            addSegmentTo(segmentTMP);
        }
        // Update legend
        updateLegend();

    }

    /**
     * Give the values of a point in relation to the graph position in is group/ anchor pane (and not scale)
     * @param point The desired point (Already scaled to the graph)
     * @return The correct pixel location of the point on this graph
     */
    public Point translatePoint(Point point)
    {
        return new Point(this.origin.getX() + point.getX() , this.origin.getY() - point.getY());
    }

    /**
     * Gives the point coords scaled down to the graph proportions
     * @param point
     * @return The correct point scaled to the graph pixel size
     */
    public Point scalePoint(Point point)
    {
        return new Point(scaleOnX(point.getX()), scaleOnY(point.getY()));
    }

    private double scaleOnX(double x)
    {
        return  (x -  minX)/scalePixelX;
    }
    private double scaleOnY(double y)
    {
        return (y - minY)/scalePixelY;
    }

    private void updateMinMax(SegmentTMP segment)
    {
        maxX = Math.max(Math.max(maxX, segment.getPoint1().getX()), segment.getPoint2().getX());
        maxY = Math.max(Math.max(maxY, segment.getPoint1().getY()), segment.getPoint2().getY());
        minX = Math.min(Math.min(minX, segment.getPoint1().getX()), segment.getPoint2().getX());
        minY = Math.min(Math.min(minY, segment.getPoint1().getY()), segment.getPoint2().getY());
    }


    private void drawScaleMarkAxisX(double position, double size)
    {
        // Do not trace a line for origin
        if(position != 0)
        {
            Line mark = new Line(origin.getX() + position, origin.getY(), origin.getX() + position, origin.getY() - size);
            mark.setStroke(Color.GRAY);
            getChildren().add(mark);
        }
        Text text = (Text) textGen.createShape(new Point(position,20));
        getChildren().add(text);
        legendsX.add(text);
    }

    private void drawScaleMarkAxisY(double position, double size)
    {
        // Do not trace a line for origin
        if(position != 0)
        {
            Line mark = new Line(origin.getX(), origin.getY() - position, origin.getX() + size, origin.getY() - position);
            mark.setStroke(Color.GRAY);
            getChildren().add(mark);
        }
        Text text = (Text) textGen.createShape(new Point(-50,-position));
        getChildren().add(text);
        legendsY.add(text);
    }

    private void updateLegend()
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
    private void updateScale()
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
        updateScale();
        // And the labels too
        updateLegend();
    }


    //_______________SWEEPLINE and Visit Segments

    public void initializeSweepLine()
    {
        setSweepLinePosition(maxY);

        toSetActive = new Stack<>();
        toSetInactive = new Stack<>();
        toSetInactive.addAll(segmentsShown.values());
    }
    private void setSweepLinePosition(double y)
    {
        // If the sweepline wasn't added yet
        if(sweepLine == null)
        {
            sweepLine = new Line(maxAxisY.getX(), maxAxisY.getY(), maxAxisX.getX() + 5, maxAxisY.getY());
            sweepLine.setStroke(Color.DARKGREEN);
            sweepLine.setStrokeWidth(2);
            sweepLine.getStrokeDashArray().addAll(5d);
            getChildren().add(sweepLine);
        }
        // Move the sweep line (simply doing sweepLine.setLayoutY() doesn't work for some reason
        double newPosition = origin.getY() - scaleOnY(y);
        sweepLine.setStartY(newPosition);
        sweepLine.setEndY(newPosition);
    }

    public void moveSweepLine(Point P, List<SegmentTMP> U, List<SegmentTMP> L, List<SegmentTMP> C)
    {
        // Move SweepLine
        setSweepLinePosition(P.getY());
        Segment currSegment;
        // Set segments that were active last iteration to active
        while (! toSetActive.isEmpty())
        {
            currSegment = toSetActive.pop();
            currSegment.setActiveSegment();
        }
        // Set segments that became inactive since last iteration to inactive
        while (! toSetInactive.isEmpty())
        {
            currSegment = toSetInactive.pop();
            currSegment.setInactiveSegment();
        }
        if(L != null)
        {
            for(SegmentTMP segmentTMP: L)
            {
                segmentsShown.get(segmentTMP).setVisitedSegment();
                toSetInactive.add(segmentsShown.get(segmentTMP));
                segmentsShown.get(segmentTMP).toFront();
            }
        }
        if(C != null)
        {
            for(SegmentTMP segmentTMP: C)
            {
                segmentsShown.get(segmentTMP).setVisitedSegment();
                segmentsShown.get(segmentTMP).toFront();
                segmentsShown.get(segmentTMP).toFront();
            }
        }
        if(U != null){
            for(SegmentTMP segmentTMP : U)
            {
                segmentsShown.get(segmentTMP).setVisitedSegment();
                toSetActive.add(segmentsShown.get(segmentTMP));
                segmentsShown.get(segmentTMP).setVisitedPoint(translatePoint(scalePoint(P)));
                segmentsShown.get(segmentTMP).toFront();
            }

        }


    }
    //_______________GETTER/SETTER

/*
    //              TODO Check if this is salvageable or if this is a lost cause
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

 */
}
