package analysisOfAlgorithms.week1_union_find;
/******************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size n of the percolation system.
 *    - Creates an n-by-n grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * The type Percolation visualizer.
 */
public final class PercolationVisualizer {

    /** delay in miliseconds (controls animation speed). */
    private static final int DELAY = 100;
    /** Test file. */
    private static final String file = "src\\unionfind\\test\\wayne98.txt";

    /** The constructor. */
    private PercolationVisualizer() { }
    /**
     * Draw.
     *
     * @param perc the perc
     * @param n    the n
     */
// draw n-by-n percolation system
    private static void draw(final Percolation perc, final int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05 * n, 1.05 * n);
        StdDraw.setYscale(-0.05 * n, 1.05 * n);  // leave a border to write text
        StdDraw.filledSquare(n / 2.0, n / 2.0, n / 2.0);

        // draw n-by-n grid
        int opened = 0;
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                } else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                } else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(col - 0.5,  n - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(0.25 * n, -0.025 * n, opened + " open sites");
        if (perc.percolates()) {
            StdDraw.text(0.75 * n, -0.025 * n, "percolates");
        } else {
            StdDraw.text(0.75 * n, -0.025 * n, "does not percolate");
        }

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        try {
            Scanner in = new Scanner(new FileReader(file));
            int n = in.nextInt();        // n-by-n percolation system

            // turn on animation mode
            StdDraw.enableDoubleBuffering();

            // repeatedly read in sites to open and draw resulting system
            Percolation perc = new Percolation(n);
            draw(perc, n);
            StdDraw.show();
            StdDraw.pause(DELAY);
            while (in.hasNextInt()) {
                int x = in.nextInt();
                int y = in.nextInt();
                perc.open(x, y);
                draw(perc, n);
                StdDraw.show();
                StdDraw.pause(DELAY);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
