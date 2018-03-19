import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

// http://coursera.cs.princeton.edu/algs4/assignments/collinear.html

public class BruteCollinearPoints {
    private List<LineSegment> segments = new ArrayList<LineSegment>();
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        Point first = points[p];
                        double slopeQ = first.slopeTo(points[q]);
                        double slopeR = first.slopeTo(points[r]);
                        double slopeS = first.slopeTo(points[s]);
                        if (slopeQ == slopeR && slopeQ == slopeS) {
                            StdOut.printf("Slopes: Q: %f, R: %f, S: %s", slopeQ, slopeR, slopeS);
                            //StdOut.printf("found one: %s, %s, %s, %s", points[p].toString(), points[q].toString(), points[r].toString(), points[s].toString());
                            segments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return segments.size();
    }
    
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}