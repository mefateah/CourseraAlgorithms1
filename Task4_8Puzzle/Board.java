import edu.princeton.cs.algs4.StdOut;

// http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html

public class Board {
    private int[][] board;
    
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        this.board = blocks;
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int num = 0;
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                int expectedNum = (i * dimension()) + (j+1);
                if (board[i][j] != expectedNum) {
                    num++;
                }
            }
        }
        return num;
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
    }
    
    private Point findPosition(int n) {
        Point res = new Point();
        res.x = n / dimension();
        res.y = n % dimension();
        
        return res;
    }
    
    public int manhattan() {
        int num = 0;
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                // todo: consider 0 element as 'empty'
                // to introduce some flag to indicate wether 'empty' is passed and if it is then consider than 'switch'
                if (block[i][j] = 0) continue;
                
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
        for (int i = 0; i < dimension(); i++){
            for (int j = 0; j < dimension(); j++){
                // todo: consider 'empty' block = 0
                //if (board[i][j] == 0) continue;
                
                int expectedNum = (i * dimension()) + (j+1);
                if (board[i][j] != expectedNum) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin() {
        
    }

    public boolean equals(Object y) {

    }

    public Iterable<Board> neighbors() {

    }

    public String toString() {
        String result = "";
        // pring dimention (i.e. 3)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                result += board[i][j] + " ";
            }
            result += System.lineSeparator();
        }
        return result;
    }

    public static void main(String[] args) {

    }
}