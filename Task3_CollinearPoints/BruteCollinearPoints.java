import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;

// http://coursera.cs.princeton.edu/algs4/assignments/collinear.html

public class BruteCollinearPoints {
    
    private class SegmentStorage {
        private class Segment {
            private Point a;
            private Point b;
            
            public Segment(Point a, Point b) {
                this.a = a;
                this.b = b;
            }
        }
        private List<Segment> segs = new ArrayList<Segment>();
        
        public void addSegment(Point start, Point end) {
            segs.add(new Segment(start, end));
        }
        
        public boolean contains(Point a, Point b) {
            for (Segment s : segs) {
                if (s.a == a && s.b == b) {
                    return true;
                }
                if (s.a == b && s.b == a) {
                    return true;
                }
            }
            return false;
        }
    }
    
    private List<LineSegment> segments = new ArrayList<LineSegment>();
    
    public BruteCollinearPoints(Point[] points) {
        checkForNullAndDuplicates(points);
        SegmentStorage segs = new SegmentStorage();
        
        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        Point first = points[p];
                        double slopeQ = first.slopeTo(points[q]);
                        double slopeR = first.slopeTo(points[r]);
                        double slopeS = first.slopeTo(points[s]);
                        if (slopeQ == slopeR && slopeQ == slopeS) {
                            Point[] seg = new Point[] {points[p], points[q], points[r], points[s]};
                            Arrays.sort(seg, naturalOrder());
                            if (!segs.contains(seg[0], seg[3])) {
                                segments.add(new LineSegment(seg[0], seg[3]));
                                segs.addSegment(seg[0], seg[3]);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void checkForNullAndDuplicates(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
        
        Arrays.sort(points, naturalOrder());
        Point last = points[0];
        for (int i = 1; i < points.length; i++) {
            if (last.compareTo(points[i]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
            last = points[i];
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
        return segments.size();
    }
    
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
    
    public static void main(String[] args) {
        if (new Point(1,0) == new Point(1,0)) {StdOut.println("the same");} else {StdOut.println("not");}
        Point[] arr = new Point[]{new Point(1, 0), new Point(55, 44), new Point(100, 40), new Point(55, 44)};
        BruteCollinearPoints test = new BruteCollinearPoints(arr);
        
        /*
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
        */
    }
}