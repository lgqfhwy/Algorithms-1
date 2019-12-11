import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats
{
    private int n, trials;
    private double[] fractions;
    double x, T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if(n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.n = n;
        this.trials = trials;
        fractions = new double[trials];

        for(int i = 0; i < trials; i++)
        {
            Percolation percolation = new Percolation(n);
            int[] frequency = new int[n*n-1];
            for(int j = 0; j < frequency.length; j++) frequency[j] = 1;
            while(!percolation.percolates())
            {
                int id = StdRandom.discrete(frequency);
                frequency[id]--;
                int row = getRow(id), col = getCol(id);
                percolation.open(row, col);
            }
            fractions[i] = percolation.numberOfOpenSites()/1.0/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        if(x == 0) x = StdStats.mean(fractions);
        return x;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        if(T == 0) T = StdStats.stddev(fractions);
        return T;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    private int getRow(int id) { return id / n; }

    private int getCol(int id) { return id % n; }

    // test client (see below)
    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(N, T);

        String confidence = pStats.confidenceLo() + ", "
                + pStats.confidenceHi();
        StdOut.println("mean                    = " + pStats.mean());
        StdOut.println("stddev                  = " + pStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}