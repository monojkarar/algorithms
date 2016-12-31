package analysisOfAlgorithms.week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;

/**
 *
 */
public final class PuzzleVisualizer {
    /**
     *
     * @param board the board
     * @param numberOfMoves the number of moves
     */
    private static void draw(final Board board, final int numberOfMoves) {
        String boardString = board.toString();
        String[] blockStrings = boardString.split("\\s+");
        int dimension = Integer.parseInt(blockStrings[0]);

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.square(1 / 2.0, 1 / 2.0, 1 / 2.0);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int block = Integer.parseInt(blockStrings[i * dimension + j + 1]);
                double x = (2 * j + 1) / (2.0 * dimension);
                double y = 1 - (2 * i + 1) / (2.0 * dimension);
                double halfSideLength = 1 / (2.0 * dimension);
                if (block == 0) {
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    StdDraw.filledSquare(x, y, halfSideLength);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.square(x, y, halfSideLength);
                } else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.square(x, y, halfSideLength);
                    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 40));
                    StdDraw.text(x, y - 0.01, "" + block);
                }
            }
        }

        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 15));
        StdDraw.text(0.05, -0.02, "Move: " + numberOfMoves);
    }

    /**
     *
     * @param message the message
     */
    private static void displayMessage(final String message) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 30));
        StdDraw.text(1 / 2.0, 1 / 2.0, message);
    }

    /**
     * Unit tests the PuzzleVisualizer data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        StdDraw.show(0);

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board board = new Board(blocks);

        Solver solver = new Solver(board);

        int delay = 1000;
        if (args.length > 1) {
            delay = Integer.parseInt(args[1]);
        }
        if (solver.isSolvable()) {
            int numberOfMoves = 0;
            for (Board move : solver.solution()) {
                draw(move, numberOfMoves);
                StdDraw.show(delay);
                numberOfMoves++;
            }
        } else {
            displayMessage("Not solvable");
            StdDraw.show(0);
        }
    }
}