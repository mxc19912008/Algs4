import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.ArrayList;
public class BruteCollinearPoints {
    private ArrayList<LineSegment> list; 
    private LineSegment[] segments;
    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 pointsCopy
        if (points.length == 0) throw new java.lang.NullPointerException();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        checkDuplicatedEntries(pointsCopy);
        list = new ArrayList<LineSegment>();
        for (int i = 0; i < pointsCopy.length-3; i++){
            for (int j = i+1; j < pointsCopy.length-2; j++){
                for (int k = j+1; k < pointsCopy.length-1; k++){
                    for (int n = k+1; n < pointsCopy.length; n++){
                        Point p = pointsCopy[i];
                        Point q = pointsCopy[j];
                        Point s = pointsCopy[k];
                        Point r = pointsCopy[n];
                        double pq = p.slopeTo(q);
                        double pr = p.slopeTo(r);
                        double ps = p.slopeTo(s);
                        if (pq == pr && pq == ps){
                            list.add(new LineSegment(p,r));
                        }
                    }
                }
            }
        }
        segments = list.toArray(new LineSegment[list.size()]);        
    }
    public           int numberOfSegments() {       // the number of line segments
        return segments.length;
    }
    public LineSegment[] segments()    {            // the line segments
        return segments;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if(segment != null){
                StdOut.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}