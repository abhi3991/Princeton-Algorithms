package Assignment1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by abhin on 7/9/2017.
 */
public class PercolationStats {

    private final int n;
    private final int trials;
    private final double[] openSiteStats;

    public PercolationStats(final int n, final int trials) {
        this.n = n;
        this.trials = trials;
        this.openSiteStats = new double[trials];
        for (int i = 0; i < trials; i++) {
            openSiteStats[i] = runExperiment(n);
        }
    }

    public double confidenceHi() {
        return mean() + ((1.96 * Math.sqrt(stddev())) / Math.sqrt(trials));
    }

    public double confidenceLo() {
        return mean() - ((1.96 * Math.sqrt(stddev())) / Math.sqrt(trials));
    }

    public double mean() {
        return StdStats.mean(openSiteStats);
    }

    public double stddev() {
        return StdStats.stddev(openSiteStats);
    }

    private double runExperiment(final int n) {
        final Percolation perc = new Percolation(n);

        while (!perc.percolates()) {
            final int row = StdRandom.uniform(1, n + 1);
            final int col = StdRandom.uniform(1, n + 1);
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
            }
        }
        //System.out.println((double) perc.numberOfOpenSites()/(n*n));
        return (double) perc.numberOfOpenSites()/(n*n);
    }

    public static void main(String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int trails = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trails);
        System.out.println(String.format("mean                    = %g", stats.mean()));
        System.out.println(String.format("stddev                  = %g", stats.stddev()));
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
