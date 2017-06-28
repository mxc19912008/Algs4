import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;
public class FastCollinearPoints {
    private HashMap<Double, List<Point>> foundSegments;
    private List<LineSegment> segments;


    public FastCollinearPoints(Point[] points) {
        foundSegments = new HashMap<Double, List<Point>>();
        segments = new ArrayList<LineSegment>();
        
        if (points.length == 0) throw new java.lang.NullPointerException();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);
        for (Point startPoint : points) {
            Arrays.sort(pointsCopy, startPoint.slopeOrder());

            List<Point> slopePoints = new ArrayList<Point>();
            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
            //double previousSlope = startPoint.slopeTo(pointsCopy[0]);

            for (int i = 0; i < pointsCopy.length; i++) {
                slope = startPoint.slopeTo(pointsCopy[i]);
                if (slope == previousSlope) {
                    slopePoints.add(pointsCopy[i]);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegmentIfNew(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                previousSlope = slope;
            }

            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(slopePoints, slope);
            }
        }
    }

    private void addSegmentIfNew(List<Point> slopePoints, double slope) {
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<Point>();
            endPoints.add(endPoint);
            foundSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }

    public           int numberOfSegments(){        // the number of line segments
        return segments.size();
    }
    public LineSegment[] segments(){                // the line segments
        return segments.toArray(new LineSegment[segments.size()]);
    }
    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
                if (points[i].compareTo(points[i+1]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
        }
     }
     public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the pointsCopy
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if(segment != null){
                StdOut.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}