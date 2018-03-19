import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

// http://coursera.cs.princeton.edu/algs4/assignments/collinear.html

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }
    
    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        } else if (this.y < that.y) {
            return -1;
        } else {
            if (this.x > that.x) {
                return 1;
            } else if (this.x < that.x) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    
    public double slopeTo(Point that) {
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.x == this.x) {
            return 0.0;
        }
        if (that.y == this.y) {
            return Double.POSITIVE_INFINITY;
        }
        
        return (that.y - this.y) / (that.x - this.x);
    }
    
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            
            public int compare(Point p1, Point p2) {
                if (slopeTo(p1) > slopeTo(p2)) {
                    return 1;
                } else if (slopeTo(p1) < slopeTo(p2)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
    }
    
    public static void main(String[] args) {
        Point a = new Point(2, 1);
        Point b = new Point(1, 1);
        StdOut.println(a.slopeTo(b));
    }
}