import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

// http://coursera.cs.princeton.edu/algs4/checklists/percolation.html

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF unionFind;
    private final WeightedQuickUnionUF percolationChecks;
    private boolean[][] grid;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n should be greater than 0");
        }
        this.n = n;
        unionFind = new WeightedQuickUnionUF(n * n);
        percolationChecks = new WeightedQuickUnionUF(n * n);
        createVirtualTopRowComponent(unionFind);
        createVirtualTopRowComponent(percolationChecks);
        createVirtualBottomRowComponent(percolationChecks);
        grid = new boolean[n][n];
    }
    
    // create virtual site which connects nodes on a top row (used in isFull())
    private void createVirtualTopRowComponent(WeightedQuickUnionUF uf) {
        for (int i = 1; i < n; i++) {
            uf.union(convertTo1D(1, i), convertTo1D(1, n));
        }
    }
    
    // create virtual site which connects nodes on a bottom row (used in percolates())
    private void createVirtualBottomRowComponent(WeightedQuickUnionUF uf) {
        for (int i = 1; i < n; i++) {
            uf.union(convertTo1D(n, i), convertTo1D(n, n));
        }
    }
    
    private void verifyBounds(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new java.lang.IllegalArgumentException("row index is outside of bounds");
        }
        if (col < 1 || col > grid.length) {
            throw new java.lang.IllegalArgumentException("column index is outside of bounds");
        }
    }
    
    private int convertTo1D(int row, int col) {
        return ((row - 1) * n) + (col - 1);
    }
    
    private void connectNeighbours(int row, int col) {
        // top
        if (row > 1 && isOpen(row - 1, col)) {
            unionFind.union(convertTo1D(row, col), convertTo1D(row - 1, col));
            percolationChecks.union(convertTo1D(row, col), convertTo1D(row - 1, col));
        }
        // bottom
        if (row < n && isOpen(row + 1, col)) {
            unionFind.union(convertTo1D(row, col), convertTo1D(row + 1, col));
            percolationChecks.union(convertTo1D(row, col), convertTo1D(row + 1, col));
        }
        // left
        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(convertTo1D(row, col), convertTo1D(row, col - 1));
            percolationChecks.union(convertTo1D(row, col), convertTo1D(row, col - 1));
        }
        // right
        if (col < n && isOpen(row, col + 1)) {
            unionFind.union(convertTo1D(row, col), convertTo1D(row, col + 1));
            percolationChecks.union(convertTo1D(row, col), convertTo1D(row, col + 1));
        }
    }
    
    public void open(int row, int col) {
        verifyBounds(row, col);
        if (!isOpen(row, col)) {
            //StdOut.printf("open site (%d, %d)\n", row, col);
            grid[row - 1][col - 1] = true;
            connectNeighbours(row, col);
        }
    }
    
    public boolean isOpen(int row, int col) {
        verifyBounds(row, col);
        return grid[row - 1][col - 1];
    }
    
    public boolean isFull(int row, int col) {
        verifyBounds(row, col);
        if (isOpen(row, col)) {
            return unionFind.connected(0, convertTo1D(row, col));
        }
        return false;
    }
    
    public int numberOfOpenSites() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }
    
    public boolean percolates() {
        if (n == 1 && grid[0][0] == false) {
            return false;
        } else {
            return percolationChecks.connected(0, n * n - 1);
        }
    }
    
    public static void main(String[] args) { 
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);
        StdOut.println(p.percolates());
        /*
        StdOut.println(p.isOpen(1, 1));
        p.open(1, 1);
        StdOut.println(p.isOpen(1, 1));
        StdOut.println(p.isOpen(1, 2));
        StdOut.println(p.isOpen(1, 3));
        StdOut.println(p.isOpen(1, 4));
        StdOut.println(p.isOpen(1, 5));
        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.percolates());
        StdOut.println(p.numberOfOpenSites());
        */
    }
}
