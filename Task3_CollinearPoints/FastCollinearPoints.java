import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

// http://coursera.cs.princeton.edu/algs4/assignments/collinear.html

public class FastCollinearPoints {
    
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points, naturalOrder());
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] copy = points.clone();
            Arrays.sort(points, p.slopeOrder());
            StdOut.println("test");
            
            int counter;
            double slope;
            for (int j = 1; j < points.length; j++) {
                if (p.slopeTo(points[j] != slope && counter >= 3) {
                    // add to segments
                    
                    slope = p.slopeTo(points[j]);
                }
            }
        }
    }
    
    private Comparator<Point> naturalOrder() {
        return new Comparator<Point>() {
            
            public int compare(Point p1, Point p2) {
                return p1.compareTo(p2);
            }
        };
    }
    
    public int numberOfSegments() {
        throw new java.lang.UnsupportedOperationException();
        //return segments.size();
    }
    
    public LineSegment[] segments() {
        throw new java.lang.UnsupportedOperationException();
        //return segments.toArray(new LineSegment[0]);
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
        
        // draw the points
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
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}