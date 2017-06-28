import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int numOfGrids;
    private int numOfTrails;
    private double[] collectionOfCounts;
    
    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0){
            throw new java.lang.IllegalArgumentException("number of grids and trials should be positive numbers");
        }
        numOfGrids = n;
        numOfTrails = trials;
        collectionOfCounts = new double[numOfTrails];
        for(int i=0;i<numOfTrails;i++){
            Percolation pl = new Percolation(numOfGrids);
            int openedSites = 0;
            while (!pl.percolates()) {
                int row = StdRandom.uniform(1, numOfGrids + 1);
                int col = StdRandom.uniform(1, numOfGrids + 1);
                //System.out.println(row+","+col);
                if(!pl.isOpen(row,col)){
                    pl.open(row,col);  
                    openedSites ++;
                }
                //StdOut.println("Row "+row+ ", col "+col+" opened.");
            }
            //StdOut.println("Sites opened                    = "+pl.numberOfOpenSites());
            collectionOfCounts[i] = (double) openedSites/numOfGrids/numOfGrids;
            //StdOut.println("The rite is                    = "+collectionOfCounts[i]);
        }
    }
    public double mean() {                         // sample mean of percolation threshold
        return StdStats.mean(collectionOfCounts);
    }
    public double stddev(){                        // sample standard deviation of percolation threshold
        return StdStats.stddev(collectionOfCounts);
    }
    public double confidenceLo()    {              // low  endpoint of 95% confidence interval
        double mean = mean();
        double std = stddev();
        int t = numOfTrails;
        return mean-(1.96*std)/Math.sqrt(t);
    }
    public double confidenceHi(){                  // high endpoint of 95% confidence interval
        double mean = mean();
        double std = stddev();
        int t = numOfTrails;
        return mean+(1.96*std)/Math.sqrt(t);
    }

   public static void main(String[] args) {       // test client (described below)
        //int n = Integer.parseInt(args[0]);
        //int trails = Integer.parseInt(args[1]);
         int n= StdIn.readInt(); 
        int trails= StdIn.readInt(); 
       
        PercolationStats ps = new PercolationStats(n,trails);
        
        double mean = ps.mean();
        double std = ps.stddev();
        double confidenceLo = ps.confidenceLo();
        double confidenceHi = ps.confidenceHi();
        
        StdOut.println("mean                    = "+mean);
        StdOut.println("stddev                  = "+std);
        StdOut.println("95% confidence interval = "+"["+confidenceLo+", "+confidenceHi+"]");
   }
}