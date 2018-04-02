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
        
    }

    public int hamming() {

    }
    public int manhattan() {

    }

    public boolean isGoal() {

    }

    public Board twin() {

    }

    public boolean equals(Object y) {

    }

    public Iterable<Board> neighbors() {

    }

    public String toString() {
        String result;
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