package ac.umons.be.firstg.segmentintersection.view.components;


import ac.umons.be.firstg.segmentintersection.controller.utils.CDouble;
import ac.umons.be.firstg.segmentintersection.model.ComparableSegment;
import ac.umons.be.firstg.segmentintersection.model.Point;
import ac.umons.be.firstg.segmentintersection.model.Segment;
import ac.umons.be.firstg.segmentintersection.view.interfaces.IShapeGen;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;


/**
 * Graph component that can display segments and his very customisable
 */
public class GraphXY extends AnchorPane
{
    private int markSize = 10;
    private int nbOfMarksX, nbOfMarksY;
    private double gapX,gapY;
    private ArrayList<Line> markLinesX;
    private ArrayList<Line> markLinesY;
    private ArrayList<Text> legendsX;
    private ArrayList<Text> legendsY;
    private IShapeGen<Point> textGen;
    private boolean showGrid;

    private double sizePixelAxisX, sizePixelAxisY;
    private Point origin;
    private Point maxAxisX;
    private Point maxAxisY;
    private Line xAxis,yAxis;

    // Used to scale the graph
    private double minScaleX, minScaleY;
    private double maxX,maxY;
    private double minX,minY;
    private double scalePixelX, scalePixelY;

    // Stores all segments contained inside the graph
    private HashMap<Segment, SegmentNode> segmentsShown;

    // Sweep Line settings
    private Line sweepLine;
    private EventPointNode currentSweepPoint;
    private Point sweepLinePosition;
    private Stack<SegmentNode> toSetInactive;
    private Stack<SegmentNode> toSetActive;



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
        resetScale();
        this.sizePixelAxisX = sizePixelAxisX;
        this.sizePixelAxisY = sizePixelAxisY;

        // Draw and set the origin and axes
        setAxes(start);

        // Text Legend settings
        legendsX = new ArrayList<>();
        legendsY = new ArrayList<>();
        markLinesX = new ArrayList<>();
        markLinesY = new ArrayList<>();

        // Add initial text marker
        textGen = x -> {
            Text text = new Text();
            text.setLayoutX(origin.x + x.x);
            text.setLayoutY(origin.y + x.y);
            text.toBack();
            return text;
        };

        // Scale definition
        this.minScaleX = minScaleX;
        this.minScaleY = minScaleY;
        // Scale marks
        // Draw Scale Marks
        drawScale(nbOfMarksX, nbOfMarksY, showGrid);

        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void resetScale()
    {
        // Infinity for both min values at start
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxX = Double.NEGATIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
    }

    private void setAxes(Point start)
    {
        // Removes previously drawn axis
        if(this.xAxis != null && this.yAxis != null)
        {
            getChildren().remove(xAxis);
            getChildren().remove(yAxis);
        }
        // Sets the origin pixel position and the axis lines
        this.maxAxisY = start;
        // Get points for the ends of the X and Y axis
        this.origin = new Point(this.maxAxisY.x, this.maxAxisY.y + sizePixelAxisY);
        this.maxAxisX = new Point(this.origin.x + sizePixelAxisX, this.origin.y);
        // Draw the axis using lines
        // X axis
        xAxis = new Line(this.maxAxisX.x, this.maxAxisX.y, this.origin.x, this.origin.y);
        xAxis.setStrokeWidth(3);
        this.getChildren().add(xAxis);
        // Y axis
        yAxis = new Line(this.maxAxisY.x, this.maxAxisY.y, this.origin.x, this.origin.y);
        yAxis.setStrokeWidth(3);
        this.getChildren().add(yAxis);

    }

    private void drawScale(int nbOfMarksX, int nbOfMarksY, boolean showGrid)
    {
        // Scale definition
        this.nbOfMarksX = nbOfMarksX;
        this.nbOfMarksY = nbOfMarksY;
        // Scale marks
        // Draw Scale Marks
        this.showGrid = showGrid;
        double distMarkX = this.sizePixelAxisX / nbOfMarksX;
        double distMarkY = this.sizePixelAxisY / nbOfMarksY;
        double markSize = showGrid ? this.sizePixelAxisY: this.markSize;

        // If they were already drawn, we remove them

        getChildren().removeAll(markLinesY);
        // also remove the text labels
        getChildren().removeAll(legendsY);
        legendsY.clear();
        markLinesY.clear();

        // If they were already drawn, we remove them

        getChildren().removeAll(markLinesX);
        getChildren().removeAll(legendsX);
        legendsX.clear();
        markLinesX.clear();


        for (int i = 0; i <= nbOfMarksX; i++)
        {
            drawScaleMarkAxisX(distMarkX * i, markSize);
        }
        markSize = showGrid ? this.sizePixelAxisX: this.markSize;
        for (int i = 0; i <= nbOfMarksY; i++)
        {
            drawScaleMarkAxisY(distMarkY * i, markSize);
        }
        updateLegend();

    }


    private void addSegmentTo(Segment segment)
    {
        // Scales and then translates the point to the graph
        Point point1 = translatePoint(scalePoint(segment.getUpperPoint()));
        Point point2 = translatePoint(scalePoint(segment.getLowerPoint()));

        Segment segmentTMP = new Segment(point1,point2);
        SegmentNode segmentNode = new SegmentNode(segmentTMP);
        // Add segment so we can easily reference it
        segmentsShown.put(segment, segmentNode);
        this.getChildren().add(segmentNode);
    }

    /**
     * Remove the segment, if it exists from the graph and if necessary, rescales the graph
     * @param segment The segment to remove
     */
    public void removeSegmentFrom(Segment segment)
    {
        SegmentNode segmentShown = segmentsShown.get(segment);
        // Removes segment from the pane
        if (segmentShown != null)
            getChildren().remove(segmentShown);
        // Remove the segment from storage
        segmentsShown.remove(segment);
        // Update the scale
        resetScale();
        rescaleSegments(true);
        updateLegend();
        drawScale(this.nbOfMarksX, this.nbOfMarksY, this.showGrid);
    }

    /**
     * Add all segments to the graph, changing its scale to accommodate with the max and min values of the segments (and the previously added ones)
     * @param segments  The segments to add
     */
    public void addSegments(List<Segment> segments)
    {
        // Set new boundaries
        setBoundaries(segments);
        // Resize previous segments to current scale
        rescaleSegments(false);
        // Then when the scale has been fixed we can add all segments
        for(Segment segmentTMP: segments)
        {
            addSegmentTo(segmentTMP);
        }
        // Update legend
        updateLegend();
    }

    /**
     * Returns all contained {@link Segment} contained in this graph
     * @return The segments contained in this graph
     */
    public Set<Segment> getSegments()
    {
        return segmentsShown.keySet();
    }

    private void setBoundaries(Iterable<Segment> segments)
    {
        for(Segment segmentTMP: segments)
        {
            // Update scale of the graph
            updateMinMax(segmentTMP);
            updateScale();
        }
        updateLegend();
    }
    /**
     * Rescale all segments of the graph
     */
    private void rescaleSegments(boolean updateBoundaries)
    {
        Segment newPosition;
        if(updateBoundaries)
            setBoundaries(segmentsShown.keySet());
        for(Segment segment : segmentsShown.keySet())
        {
            newPosition = new Segment(translatePoint(scalePoint(segment.getUpperPoint())),
                    translatePoint(scalePoint(segment.getLowerPoint())));
            segmentsShown.get(segment).setSegment(newPosition);
            segmentsShown.get(segment).toFront();
        }
    }

    /**
     * Give the values of a point in relation to the graph position in is group/ anchor pane (and not scale)
     * @param point The desired point (Already scaled to the graph)
     * @return The correct pixel location of the point on this graph
     */
    public Point translatePoint(Point point)
    {
        return new Point(this.origin.x + point.x , this.origin.y - point.y);
    }

    /**
     * Gives the point coords scaled down to the graph proportions
     * @param point
     * @return The correct point scaled to the graph pixel size
     */
    public Point scalePoint(Point point)
    {
        return new Point(scaleOnX(point.x), scaleOnY(point.y));
    }

    private double scaleOnX(double x)
    {
        return  (x -  minX)/scalePixelX;
    }
    private double scaleOnY(double y)
    {
        return (y - minY)/scalePixelY;
    }

    private void updateMinMax(Segment segment)
    {
        maxX = max(maxX, segment.getUpperPoint().x, segment.getLowerPoint().x, minScaleX);
        maxY = max(maxY, segment.getUpperPoint().y, segment.getLowerPoint().y, minScaleY);
        minX = min(minX, segment.getUpperPoint().x, segment.getLowerPoint().x);
        minY = min(minY, segment.getUpperPoint().y, segment.getLowerPoint().y);
    }
    private double max(double a, double b, double c, double d)
    {
        return Math.max(Math.max(Math.max(a, b), c), d);
    }
    private double min(double a, double b, double c)
    {
        return Math.min(Math.min(a, b), c);
    }


    private void drawScaleMarkAxisX(double position, double size)
    {
        // Do not trace a line for origin
        if(position != 0)
        {
            Line mark = new Line(origin.x + position, origin.y, origin.x + position, origin.y - size);
            mark.setStroke(Color.GRAY);
            mark.setOpacity(0.5);
            mark.toBack();
            getChildren().add(mark);
            markLinesX.add(mark);
        }
        Text text = (Text) textGen.createNode(new Point(position - 25,20));
        text.setRotate(-30);
        text.toBack();
        getChildren().add(text);
        legendsX.add(text);
    }

    private void drawScaleMarkAxisY(double position, double size)
    {
        // Do not trace a line for origin
        if(position != 0)
        {
            Line mark = new Line(origin.x, origin.y - position, origin.x + size, origin.y - position);
            mark.setStroke(Color.GRAY);
            mark.toBack();
            getChildren().add(mark);
            markLinesY.add(mark);
        }
        Text text = (Text) textGen.createNode(new Point(-50,-position));
        text.toBack();
        getChildren().add(text);
        legendsY.add(text);
    }


    private void updateLegend()
    {
        double tmpMinX = minX;
        double tmpMinY = minY;
        if(minX == Double.POSITIVE_INFINITY)
        {
            tmpMinX = 0d;
            gapX = minScaleX / nbOfMarksX;
        }
        else
            gapX = (maxX-tmpMinX) / nbOfMarksX;

        if(minY == Double.POSITIVE_INFINITY)
        {
            tmpMinY = 0d;
            gapY = minScaleY / nbOfMarksY;
        }
        else
            gapY = (maxY-tmpMinY) / nbOfMarksY;

        for (int i = 0; i < legendsX.size(); i++)
        {
            legendsX.get(i).setText(String.format("%.2f",tmpMinX + (gapX * i)));
        }
        for (int i = 0; i < legendsY.size(); i++)
        {
            legendsY.get(i).setText(String.format("%.2f",tmpMinY + (gapY * i)));
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
        maxX = (Math.ceil(maxX/ minScaleX) * minScaleX);
        minX = (Math.floor(minX/ minScaleX) * minScaleX);
        maxY = (Math.ceil(maxY/ minScaleY) * minScaleY);
        minY = (Math.floor(minY/ minScaleY) * minScaleY);
        scalePixelX = (maxX - minX) / sizePixelAxisX;
        scalePixelY = (maxY - minY) / sizePixelAxisY;
    }

    /**
     * Removes all segments displayed in the graph.
     * Also resets the scale
     */
    public void resetGraph()
    {
        for (Segment segmentTMP: segmentsShown.keySet())
        {
            getChildren().remove(segmentsShown.get(segmentTMP));
        }
        segmentsShown.clear();
        // Reset the scale
        resetScale();
        updateScale();
        // And the labels too
        updateLegend();
    }

    /**
     *
     * Updates the size of the graph
     * @param sizePixelAxisX    The new size of the X axis in pixels
     * @param sizePixelAxisY    The new size of the Y axis in pixels
     * @param minScaleX
     * @param minScaleY
     * @param nbOfMarksX
     * @param nbOfMarksY
     * @param showGrid
     */
    public void updateSize(double sizePixelAxisX, double sizePixelAxisY, double minScaleX, double minScaleY,  int nbOfMarksX, int nbOfMarksY, boolean showGrid)
    {
        // Change Scale
        resetScale();
        // Change nbOf Marks
        this.nbOfMarksX = nbOfMarksX;
        this.nbOfMarksY = nbOfMarksY;
        // And grid
        this.showGrid = showGrid;

        this.minScaleX = minScaleX;
        this.minScaleY = minScaleY;
        // We have to change all the computed values
        this.sizePixelAxisX = sizePixelAxisX;
        this.sizePixelAxisY = sizePixelAxisY;
        // And the origin position and axis size
        setAxes(this.maxAxisY);
        // But also the scales labels and lines
        drawScale(this.nbOfMarksX, this.nbOfMarksY, this.showGrid);
        // Update the segments using the keyset of the dictionary
        // We cannot simply, re add them, because we need to maintain the sweep line state !
        //addSegments(this.segmentsShown.keySet().stream().toList()); (wouldn't work)
        rescaleSegments(true);
        // Redraw SweepLine
        if(sweepLine != null && sweepLinePosition != null)
            setSweepLinePosition(sweepLinePosition);
    }



    //_______________SWEEPLINE and Visit Segments

    /**
     * Initialises the sweep line and set all segment to be inactive at the next iteration
     */
    public void initializeSweep()
    {
        // Initialises the sweepLine
        if(sweepLine != null)
        {
            getChildren().removeAll(sweepLine, currentSweepPoint);
            currentSweepPoint = null;
            sweepLine = null;
        }
        setSweepLinePosition(null);
        // Initialise the stacks that will be used to change the status of segments during
        // the exploration
        toSetActive = new Stack<>();
        toSetInactive = new Stack<>();
        // When starting the exploration, all segments are inactive
        toSetInactive.addAll(segmentsShown.values());
    }
    private void setSweepLinePosition(Point p)
    {
        // Save the SweepLine position in case of resize
        sweepLinePosition = p;

        // If the sweepline wasn't added yet
        if(sweepLine == null)
        {
            sweepLine = new Line(maxAxisY.x, maxAxisY.y, maxAxisX.x + 5, maxAxisY.y);
            sweepLine.setStroke(Color.DARKGREEN);
            sweepLine.setStrokeWidth(2);
            sweepLine.getStrokeDashArray().addAll(5d);
            currentSweepPoint = new EventPointNode(null, 5, Color.DARKMAGENTA);
            currentSweepPoint.setLayoutX(maxAxisX.x + 5);
            currentSweepPoint.setLayoutY(maxAxisY.y);
            getChildren().addAll(sweepLine, currentSweepPoint);
        }
        else
        {

            // Move the sweep line (simply doing sweepLine.setLayoutY() doesn't work
            double newPosition = origin.y - scaleOnY(p.y);
            sweepLine.setStartY(newPosition);
            sweepLine.setStartX(maxAxisY.x);
            sweepLine.setEndX(maxAxisX.x + 5);
            sweepLine.setEndY(newPosition);
            currentSweepPoint.setLayoutX(origin.x + scaleOnX(p.x));
            currentSweepPoint.setLayoutY(origin.y - scaleOnY(p.y));
            currentSweepPoint.toFront();
        }
    }

    /**
     * Moves the sweep line at the Y value of the given point
     * @param P         The point being explored
     * @param U         Contains all segments that have P as their upper point
     * @param L         Contains all segments that have P as their lower point
     * @param C         Contains all segments that have P as an inner point
     */
    public void moveSweepLine(Point P, List<ComparableSegment> U, List<ComparableSegment> L, List<ComparableSegment> C)
    {
        if(sweepLine == null)
            initializeSweep();

        // Move SweepLine
        setSweepLinePosition(P);
        SegmentNode currSegment = null;
        // Show current point

        // Set segments that were active last iteration to active
        while (! toSetActive.isEmpty())
        {
            currSegment = toSetActive.pop();
            currSegment.setActive();
        }
        // Set segments that became inactive since last iteration to inactive
        while (! toSetInactive.isEmpty())
        {
            currSegment = toSetInactive.pop();
            currSegment.setInactive();
        }
        // Visits all segments impacted by P (i.e. contained into U, L and C)
        if(L != null)
        {
            for(Segment segmentTMP: L)
            {
                currSegment = segmentsShown.get(segmentTMP);
                currSegment.setVisited();
                // Segments contained in L, are going to be on top of the sweep line
                // So they become inactive

                toSetInactive.add(segmentsShown.get(segmentTMP));
                segmentsShown.get(segmentTMP).toFront();
            }
        }
        if(C != null)
        {
            for(Segment segmentTMP: C)
            {
                currSegment = segmentsShown.get(segmentTMP);
                toSetActive.add(currSegment);
                currSegment.setVisited();
                currSegment.toFront();
            }
        }
        if(U != null){
            for(Segment segmentTMP : U)
            {
                currSegment = segmentsShown.get(segmentTMP);
                currSegment.setVisited();
                // Segments contained in U, are going to be touching the sweep line from now on
                toSetActive.add(currSegment);
                // We highlight the point P
                currSegment.toFront();
            }
        }
        currentSweepPoint.toFront();
    }

    public void resetSweepLine()
    {
        segmentsShown.keySet().forEach(e ->  segmentsShown.get(e).setActive());
        initializeSweep();
    }

    /**
     * Select the segment on the graph. Nothing happens if it isn't found on the graph.
     * @param segment   The segment to highlight
     */
    public void selectSegment(Segment segment)
    {
        SegmentNode segmentNode = segmentsShown.get(segment);
        if(segmentNode == null)
            return;
        segmentNode.toFront();
        segmentNode.selectShape();
    }

    /**
     * Deselect the segment on the graph. Nothing happens if it isn't found on the graph.
     * @param segment   The segment to deselect
     */
    public void deselectSegment(Segment segment)
    {
        SegmentNode segmentNode = segmentsShown.get(segment);
        if(segmentNode == null)
            return;
        segmentNode.deselectShape();
    }


}
