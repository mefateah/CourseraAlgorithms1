import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

// http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html

public class Board {
    // todo: rename to tiles
    private int[][] board;
    
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        this.board = clone2dArray(blocks);
    }
    
    private int abs(int n) {
        if (n > 0) {
            return n;
        } else {
            return -n;
        }
    }
    
    private class Point {
        private int x;
        private int y;
        
        public Point() {};
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
    
    private Point findPosition(int n) {
        if (n == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        
        Point res = new Point();
        n--;
        res.x = n / dimension();
        res.y = (n % dimension());
            
        return res;
    }
    
    private Point findBlankSquare() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    return new Point(i, j);
                }
            }
        }
        throw new java.lang.IllegalArgumentException("can't find blank square ('0' element)");
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int num = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int expectedNum = (i * dimension()) + (j+1);
                if (board[i][j] != expectedNum) {
                    // skip last element because it should be blank square
                    if (i == dimension() - 1 && j == dimension() - 1) {
                        continue;
                    }
                    num++;
                }
            }
        }
        return num;
    }
    
    public int manhattan() {
        int num = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) continue;
                
                int expectedNum = (i * dimension()) + (j+1);
                if (board[i][j] != expectedNum) {
                    Point p = findPosition(board[i][j]);
                    num += abs(p.x - i) + abs(p.y - j);
                }
            }
        }
        return num;
    }

    public boolean isGoal() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int expectedNum = (i * dimension()) + (j+1);
                // last element should be '0' - blank square
                if (i == dimension() - 1 && j == dimension() - 1) {
                    expectedNum = 0;
                }
                if (board[i][j] != expectedNum) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        Point a = null;
        int[][] twin = clone2dArray(board);
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (twin[i][j] != 0) {
                    if (a == null) {
                        a = new Point(i, j);
                    } else {
                        int tmp = twin[a.x][a.y];
                        twin[a.x][a.y] = twin[i][j];
                        twin[i][j] = tmp;
                        return new Board(twin);
                    }
                }
            }
        }
        throw new java.lang.IllegalArgumentException("can't exchange 2 elements to get twin");
    }

    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        
        Board b = (Board) y;
        return Arrays.deepEquals(this.board, b.board);
    }
    
    // todo: need to override getHashCode()
    
    private int[][] clone2dArray(int[][] arr){
        int [][] result = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].clone();
        }
        return result;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<Board>();
        //Board b = new Board(this.board.clone());
        int n = dimension();
        Point blank = findBlankSquare();
        int x = blank.x;
        int y = blank.y;
        
        if (x < n-1) {
            int[][] b = clone2dArray(board);
            b[x][y] = b[x+1][y];
            b[x+1][y] = 0;
            neighbors.add(new Board(b));
        }
        if (x > 0) {
            int[][] b = clone2dArray(board);
            b[x][y] = b[x-1][y];
            b[x-1][y] = 0;
            neighbors.add(new Board(b));
        }
        
        if (y < n-1) {
            int[][] b = clone2dArray(board);
            b[x][y] = b[x][y+1];
            b[x][y+1] = 0;
            neighbors.add(new Board(b));
        }
        if (y > 0) {
            int[][] b = clone2dArray(board);
            b[x][y] = b[x][y-1];
            b[x][y-1] = 0;
            neighbors.add(new Board(b));
        }
        
        return neighbors;
    }
    
    public String toString() {
        int n = dimension();
        StringBuilder s = new StringBuilder();
        s.append(n + System.lineSeparator());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append(System.lineSeparator());
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 1;
        blocks[0][1] = 0;
        blocks[0][2] = 2;
        blocks[1][0] = 3;
        blocks[1][1] = 4;
        blocks[1][2] = 5;
        blocks[2][0] = 6;
        blocks[2][1] = 7;
        blocks[2][2] = 8;
        
        Board b = new Board(blocks);
        StdOut.println(b.toString());
        StdOut.println(b.twin());
        StdOut.println(b.isGoal());
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        /*
        StdOut.println(b.findPosition(1));
        StdOut.println(b.findPosition(2));
        StdOut.println(b.findPosition(3));
        StdOut.println(b.findPosition(4));
        StdOut.println(b.findPosition(5));
        StdOut.println(b.findPosition(6));
        StdOut.println(b.findPosition(7));
        StdOut.println(b.findPosition(8));
        StdOut.println(b.findPosition(9));
        */
        for (Board i : b.neighbors()) {
            StdOut.println(i.toString());
        }
    }
}