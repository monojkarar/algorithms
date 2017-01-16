package unionfind;

import analysisOfAlgorithms.week1_union_find.Percolation;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Test class for Percolation class.
 */
public class TestPercolation {

    private static final String file = "src\\unionfind\\test\\wayne98.txt";

    /**
     * The entry point of Perocolation application.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {

        try {
            Scanner in = new Scanner(new FileReader(file));

            int n = in.nextInt();
            //int n = StdIn.readInt();
            Percolation percolation = new Percolation(n);

            StdOut.println(n + " x " + n + " grid created");

            //while (!StdIn.isEmpty()) {
            //int p = StdIn.readInt();
            //int q = StdIn.readInt();
            while (in.hasNextInt()) {
                int x = in.nextInt();
                int y = in.nextInt();
                percolation.open(x, y);
                StdOut.println("is " + x + " " + y + "  full: "
                        + percolation.isFull(x, y));
                StdOut.println("is " + x + " " + y + " percolated: "
                        + percolation.percolates());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
