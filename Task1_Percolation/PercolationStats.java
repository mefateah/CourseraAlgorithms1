import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final double[] fractions;
    private final int t;
    private double mean;
    private double stddev;
  
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n or trials <= 0");
        }
        
        t = trials;
        fractions = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            //StdOut.printf("number of open sites: %d\n", p.numberOfOpenSites());
            fractions[i] = p.numberOfOpenSites() / (double) (n * n);
        }
        // calculate mean
        mean = StdStats.mean(fractions);
        // calculate standard deviation
        stddev = StdStats.stddev(fractions);
    }
    
    public double mean() {
        return mean;
    }
    
    public double stddev() {
        return stddev;
    }
    
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(t);
    }
    
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(t);
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int repeats = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, repeats);
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
}