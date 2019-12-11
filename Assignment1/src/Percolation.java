import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private int[][] grid;
    private static int BLOCK = 0, OPEN = 1;
    private int openSites, top, bottom;
    private WeightedQuickUnionUF unionFind;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        grid = new int[n][n];
        unionFind = new WeightedQuickUnionUF(n*n+2);
        top = n*n;
        bottom = n*n+1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if(isOpen(row, col)) return;
        grid[row][col] = OPEN;
        openSites++;
        connectNeighbors(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        return grid[row][col] == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        return unionFind.connected(getId(row, col), top);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return unionFind.connected(top, bottom);
    }

    // get id form 0 ~ (n*n)-1
    private int getId(int row, int col)
    {
        return row*grid[0].length + col;
    }

    // connect
    private void connectNeighbors(int row, int col)
    {
        if(row == 0) unionFind.union(getId(row, col), top);
        if(row == grid.length-1) unionFind.union(getId(row, col), bottom);
        if(row-1 >= 0 && isOpen(row-1, col))
            unionFind.union(getId(row-1, col), getId(row, col));
        if(row+1 <= grid.length-1 && isOpen(row+1, col))
            unionFind.union(getId(row+1, col), getId(row, col));
        if(col-1 >= 0 && isOpen(row, col-1))
            unionFind.union(getId(row, col-1), getId(row, col));
        if(col+1 <= grid[0].length-1 && isOpen(row, col+1))
            unionFind.union(getId(row, col+1), getId(row, col));
    }

    // debug
    private void printGrid()
    {
        for(int[] row : grid)
        {
            for(int e : row) System.out.print( e + " ");
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(3);
        percolation.open(0, 0);
        percolation.open(0, 1);
        percolation.open(0, 2);
        percolation.open(1, 0);
        percolation.open(2, 0);
        percolation.printGrid();
        System.out.println(percolation.isOpen(0, 0));
    }
}