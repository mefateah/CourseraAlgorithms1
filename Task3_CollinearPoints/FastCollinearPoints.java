import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

// http://coursera.cs.princeton.edu/algs4/assignments/collinear.html

public class FastCollinearPoints {
    private List<LineSegment> segments = new ArrayList<LineSegment>();
    
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
    
    public FastCollinearPoints(Point[] points) {
        
        checkForNullAndDuplicates(points);
        SegmentStorage segStorage = new SegmentStorage();
        Point[] copy = points.clone();
        
        for (int i = 0; i < copy.length; i++) {
            Point p = copy[i];
            Arrays.sort(points, p.slopeOrder());
            
            int counter = 0;
            // '-7' is used as an indicator of unset value
            // todo: consider using Double slope = null, and check for 'null'
            double slope = -7;
            Point min = p;
            Point max = p;
            for (int j = 1; j < points.length; j++) {
                Double currentSlope = p.slopeTo(points[j]);
                if (currentSlope != slope) {
                    if (counter >= 3 && slope != -7 && !segStorage.contains(min, max)) {
                        // check if segments already contais one of the ends
                        // add to segments
                        segments.add(new LineSegment(min, max));
                        segStorage.addSegment(min, max);
                    }
                    slope = currentSlope;
                    counter = 1;
                    min = p;
                    max = p;
                    if (points[j].compareTo(min) == -1) {
                        min = points[j];
                    }
                    if (points[j].compareTo(max) == 1) {
                        max = points[j];
                    }
                    
                } else {
                    counter++;
                    if (points[j].compareTo(min) == -1) {
                        min = points[j];
                    }
                    if (points[j].compareTo(max) == 1) {
                        max = points[j];
                    }
                    if (j == points.length - 1 && counter >= 3 && !segStorage.contains(min, max)) {
                        // check if segments already contais one of the ends
                        // add to segments
                        segments.add(new LineSegment(min, max));
                        segStorage.addSegment(min, max);
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
        Point[] arr = new Point[]{new Point(1, 0), null};
        FastCollinearPoints col = new FastCollinearPoints(arr);
        
        
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