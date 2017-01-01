package analysisOfAlgorithms.week4;

import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

/**
 *  This class implements the board for the 8-puzzle problem.
 *
 *  The 8-puzzle problem is a puzzle invented by Noyes Palmer Chapman in the
 *  1870s. It is played on a 3-by-3 grid with 8 square blocks labeled 1
 *  through 8 and a blank square.  The goal is to rearrange the blocks so
 *  that they are in order, using tas few moves as possible. Blocks are
 *  permitted to slide horizontally or vertically into the blank square.
 *
 *  8 1 3   1   3   1 2 3   1 2 3   1 2 3
 *  4   2   4 2 5   4   5   4 5     4 5 6
 *  7 6 5   7 8 6   7 8 6   7 8 6   7 8
 *
 *  init    1 left  2 up    5 left  goal
 */
public class Board {
    /** An n-by-n array of blocks. */
    private final int[][] board;
    /** The size of the board. */
    private final int n;

    /**
     *  Construct a board from an n-by-n array of blocks.
     *  @param blocks the blocks of an n-by-n array of blocks
     */
    public Board(final int[][] blocks) {

        this.board = blocks;
        this.n = blocks.length;
    }

    /**
     *  analysisOfAlgorithms.week4.Board dimension.
     *  @return the board dimension
     */
    public int dimension() {

        return this.board.length;
    }

    /**
     *  Hamming priority function. The number of blocks in the wrong
     *  position, plus the number of moves made so far to get to the search
     *  node. Intuitively, a search node with a small number of blocks in the
     *  wrong position is close to the goal, and we prefer a search node that
     *  have been reached using a small number of moves.
     *
     *  8   1   3       1   2   3       1   2   3   4   5   6   7   8
     *  4       2       4   5   6   ---------------------------------
     *  7   6   5       7   8           1   1   0   0   1   1   0   1
     *   initial           goal             Manhattan = 5 + 0
     * @return the number of blocks out of place
     */
    public int hamming() {

        int count = 0;
        int goalNum = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == n - 1) && (j == n - 1)) {
                    break;
                }
                if (board[i][j] == goalNum) {
                    count++;
                }
                goalNum++;
            }
        }
        return ((n * n) - 1) - count;
    }

    /**
     *  Sum of Manhattan distances between blocks and goal.
     *
     *  Manhattan priority function. The sum of the Manhattan distances(sum
     *  of the vertical and horizontal distance) from the blocks to their
     *  goal positions, plus the number of moves made so far to get to the
     *  search node.
     *
     *  8   1   3       1   2   3       1   2   3   4   5   6   7   8
     *  4       2       4   5   6   ---------------------------------
     *  7   6   5       7   8           1   2   0   0   2   2   0   3
     *   initial           goal             Manhattan = 10 + 0
     *
     *  @return the sum
     */
    public int manhattan() {

        int total = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int goalRow;
                int goalCol;
                if (board[i][j] != 0) {
                    goalRow = (board[i][j] - 1) / n;
                    goalCol = (board[i][j] - 1) % n;
                    total += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return total;
    }

    /**
     *  Is this board the goal board?
     *  @return true if board is the goal board; false otherwise
     */
    public boolean isGoal() {

        return this.hamming() == 0;
    }

    /**
     * A board that is obtained by exchanging any pair of blocks.
     * @return the board obtained by exchanging any pair of blocks
     */
    public Board twin() {

        int block1;
        int block2;

        int[][] twin = new int[n][n];
        int zeroRow = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    zeroRow = i;
                }
                twin[i][j] = board[i][j];
            }
        }
        if (zeroRow == (n - 1)) {
            block1 = board[zeroRow - 1][0];
            block2 = board[zeroRow - 1][1];
            twin[zeroRow - 1][0] = block2;
            twin[zeroRow - 1][1] = block1;
        } else {
            block1 = board[zeroRow + 1][0];
            block2 = board[zeroRow + 1][1];
            twin[zeroRow + 1][0] = block2;
            twin[zeroRow + 1][1] = block1;
        }
        return new Board(twin);
    }

    /**
     * Does this board equal y?
     * @param y the board to check against
     * @return true if board equals y; false otherwise
     */
    public boolean equals(final Object y) {

        int count = 0;
        if (!(y instanceof Board)) {

            return false;
        }
        Board yBoard = (Board) y;
        // Does this board equal y?
        if (this.n != yBoard.n) {

            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == yBoard.board[i][j]) {
                    count++;
                }
            }
        }

        return (count == n * n);
    }

    /**
     * String representation of this board (in teh output specified format).
     * @return the string representation of this board
     */
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    /**
     * Copy the board.
     * @param x the board
     * @return a copy of the board.
     */
    private Board copy(final Board x)
    {
        int[][] twin = new int[n][n];

        for (int i = 0; i < n; i++) {
                twin[i] = x.board[i].clone();
        }

        Board boardCopy = new Board(twin);
        return boardCopy;
    }

    /**
     * All neighboring boards.
     * @return all neighboring boards.
     */
    public Iterable<Board> neighbors() {

        int x = 0;
        int y = 0;
        // Find out where the zero is.
        outerloop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                    break outerloop;
                }
            }
        }

        Queue<Board> queue = new Queue<>();

        if (x != n - 1) {
            Board newBoard = copy(this);
            newBoard.board[x][y] = newBoard.board[x + 1][y];
            newBoard.board[x + 1][y] = 0;
            queue.enqueue(newBoard);
        }

        if (x != 0) {
            Board newBoard2 = copy(this);
            newBoard2.board[x][y] = this.board[x - 1][y];
            newBoard2.board[x - 1][y] = 0;
            queue.enqueue(newBoard2);
        }

        if (y != n - 1) {
            Board newBoard3 = copy(this);
            newBoard3.board[x][y] = this.board[x][y + 1];
            newBoard3.board[x][y + 1] = 0;
            queue.enqueue(newBoard3);
        }

        if (y != 0){
            Board newBoard4 = copy(this);
            newBoard4.board[x][y] = this.board[x][y - 1];
            newBoard4.board[x][y - 1] = 0;
            queue.enqueue(newBoard4);
        }

        return queue;
    }

    /**
     * Unit tests the BinaryHeap data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        int[][] arr = new int[3][3];
        arr[0][0] = 8;
        arr[0][1] = 1;
        arr[0][2] = 3;
        arr[1][0] = 4;
        arr[1][1] = 0;
        arr[1][2] = 2;
        arr[2][0] = 7;
        arr[2][1] = 6;
        arr[2][2] = 5;

        Board board = new Board(arr);
        System.out.println("Current board: " + board + "\n");
        System.out.println("Manhattan = " + board.manhattan());
        System.out.println("Hamming = " + board.hamming());
        System.out.println("analysisOfAlgorithms.week4.Board dimension: " + board.dimension() + "\n");

        System.out.println("Is this board the goal board? " + board.isGoal());

        int[][] arr2 = new int[3][3];
        arr2[0][0] = 1;
        arr2[0][1] = 2;
        arr2[0][2] = 3;
        arr2[1][0] = 4;
        arr2[1][1] = 5;
        arr2[1][2] = 6;
        arr2[2][0] = 7;
        arr2[2][1] = 8;
        arr2[2][2] = 0;

        Board newBoard = new Board(arr2);
        System.out.println("\nFinal board: " + newBoard + "\n");
        System.out.println("Is this the goal board? " + newBoard.isGoal());
        System.out.println();
        Board twin = board.twin();
        System.out.println("Twin:\n" + twin.toString() + "\n");
        System.out.println("Neightbors: ");

        Iterable<Board> neighbors = board.neighbors();
        Iterator<Board> itr = neighbors.iterator();

        while (itr.hasNext()) {
            Board output = itr.next();
            System.out.println(output);
            System.out.println();
        }
    }
}
