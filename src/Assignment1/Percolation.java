package Assignment1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author abhin on 7/8/2017.
 */
public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int[] openSite;
    private int openSiteCount;
    private final int matrixSize;
    private static final int BLOCKED = 0;
    private static final int OPEN = 4;
    private static final int OPEN_CONNECTED_TO_TOP = 6;
    private static final int OPEN_CONNECTED_TO_BOTTOM = 5;
    private static final int OPEN_CONNECTED_TO_TOP_BOTTOM = 7;
    private boolean PERCOLATES;

    /**
     * create n-by-n grid, with all sites blocked
     *
     * @param n
     */
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("Minimum grid size 1");
        }
        this.openSiteCount = 0;
        this.PERCOLATES = false;
        this.matrixSize = n;
        this.openSite = new int[n * n];
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
    }

    /**
     * open site (row, col) if it is not open already
     *
     * @param row
     * @param col
     */
    public void open(final int row, final int col) {

        isValidIndex(row, col);

        final int p = getIndex(row, col);

        if (!isOpen(row, col)) {
            this.openSiteCount++;
            this.openSite[p] = OPEN;
        } else {
            return;
        }

        if (row == 1) {
            this.openSite[p] = openSite[p] | OPEN_CONNECTED_TO_TOP;
        }

        if (row == matrixSize) {
            this.openSite[p] = openSite[p] | OPEN_CONNECTED_TO_BOTTOM;
        }

        int topNeighborStatus = BLOCKED;
        {
            // connect to above-site if valid index and is open
            int qRow = row - 1;
            int qCol = col;
            if (isValidNeighbor(qRow, qCol)) {
                if (this.isOpen(qRow, qCol)) {
                    int qIndex = getIndex(qRow, qCol);
                    int neighborRoot = weightedQuickUnionUF.find(qIndex);
                    topNeighborStatus = openSite[neighborRoot];
                    weightedQuickUnionUF.union(p, qIndex);

                }
            }
        }

        int bottomNeighborStatus = BLOCKED;
        {
            // connect to below-site if valid index and is open
            int qRow = row + 1;
            int qCol = col;
            if (isValidNeighbor(qRow, qCol)) {
                if (this.isOpen(qRow, qCol)) {
                    int qIndex = getIndex(qRow, qCol);
                    int neighborRoot = weightedQuickUnionUF.find(qIndex);
                    bottomNeighborStatus = openSite[neighborRoot];
                    weightedQuickUnionUF.union(p, qIndex);
                }
            }
        }

        int leftNeighborStatus = BLOCKED;
        {
            // connect to left-site if valid index and is open
            int qRow = row;
            int qCol = col - 1;
            if (isValidNeighbor(qRow, qCol)) {
                if (this.isOpen(qRow, qCol)) {
                    int qIndex = getIndex(qRow, qCol);
                    int neighborRoot = weightedQuickUnionUF.find(qIndex);
                    leftNeighborStatus = openSite[neighborRoot];
                    weightedQuickUnionUF.union(p, qIndex);
                }
            }
        }

        int rightNeighborStatus = BLOCKED;
        {
            // connect to right-site if valid index and is open
            int qRow = row;
            int qCol = col + 1;
            if (isValidNeighbor(qRow, qCol)) {
                if (this.isOpen(qRow, qCol)) {
                    int qIndex = getIndex(qRow, qCol);
                    int neighborRoot = weightedQuickUnionUF.find(qIndex);
                    rightNeighborStatus = openSite[neighborRoot];
                    weightedQuickUnionUF.union(p, qIndex);
                }
            }
        }

        int currentSiteStatus = openSite[p];
        int newConnectedComponentRoot = weightedQuickUnionUF.find(p);

        openSite[newConnectedComponentRoot] = topNeighborStatus | bottomNeighborStatus
                | leftNeighborStatus | rightNeighborStatus | currentSiteStatus;

        if (openSite[newConnectedComponentRoot] == OPEN_CONNECTED_TO_TOP_BOTTOM)
           this.PERCOLATES = true;

        assert isOpen(row, col) == true;
    }

    /**
     * is site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {

        isValidIndex(row, col);
        int index = getIndex(row, col);
        return openSite[index] != 0;
    }

    /**
     * is site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(final int row, final int col) {

        isValidIndex(row, col);

        final int index = getIndex(row, col);

        return openSite[weightedQuickUnionUF.find(index)] == OPEN_CONNECTED_TO_TOP ||
                openSite[weightedQuickUnionUF.find(index)] == OPEN_CONNECTED_TO_TOP_BOTTOM;
    }

    /**
     * number of open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    /**
     * does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return PERCOLATES;
    }

    /**
     * converts two dimensional index into one dimensional index.
     *
     * @param row
     * @param col
     * @return uni-dimensional index.
     */
    private int getIndex(int row, int col) {
        return ((row - 1) * matrixSize) + (col - 1);
    }

    private boolean isValidIndex(int row, int col) {

        if (row < 1 || col < 1 || row > matrixSize || col > matrixSize) {
            throw new IllegalArgumentException("Index out of range");
        }
        return true;
    }

    private boolean isValidNeighbor(int row, int col) {
        if (row < 1 || col < 1 || row > matrixSize || col > matrixSize) {
            return false;
        }
        return true;
    }

    /**
     * test client (optional)
     *
     * @param args
     */
    public static void main(String[] args) {

    }

}
