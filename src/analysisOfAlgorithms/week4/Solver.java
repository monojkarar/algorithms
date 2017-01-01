package analysisOfAlgorithms.week4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;

/**
 * The class Solver.
 */
public class Solver {
    /** Solution node. */
    private Node solutionNode;
    /** Is the initial board solvable? */
    private boolean initialSolvable = false;
    /** Queue for solution boards. */
    private Queue<Board> solutionBoards;

    /** Node class. */
    public static class Node implements Comparable<Node> {
        /** The game board. */
        private Board theBoard;
        /** The previous node. */
        private Node previousNode;
        /** The number of moves made. */
        private int numberOfMovesMade;

        /** Node constructor. */
        private Node() {
            this.numberOfMovesMade = 0;
        }

        /**
         * Compare number of moves of two nodes.
         *
         * @param that a node
         * @return int
         */
        public int compareTo(final Node that) {
            return Integer.compare(this.numberOfMovesMade,
                    that.numberOfMovesMade);
        }
    }

    /**
     *  Find a solution to the initial board (using the A* algorithm).
     *  1. Create first Node from initial Board
     *  2. Add initial Node to priority queue
     *  3. Dequeue Node with least priority
     *  4. Get back an list of all neighbor boards for dequeued Node
     *  5. Add each new Node to priority queue with the Nodes previous board set
     *  to last dequeued board
     *  6. Repeat until dequeued board is equal to goal board
     *
     *  @param initial the initial board
     */
    Solver(final Board initial) {

        Node solutionNodeTwin;  // Solution node twin
        MinPQ<Node> thePQ;  // Minimum priority queue
        MinPQ<Node> thePQTwin; // Minimum priority queue for the twin
        boolean initialSolvableTwin = false; // is initial twin board solvable

        solutionNode = new Node();
        solutionNodeTwin = new Node();

        solutionNode.theBoard = initial;
        solutionNodeTwin.theBoard = initial.twin();

        solutionNode.previousNode = null;
        solutionNodeTwin.previousNode = null;

        thePQ = new MinPQ<>(boardOrder());
        thePQTwin = new MinPQ<>(boardOrder());

        thePQ.insert(solutionNode);
        thePQTwin.insert(solutionNodeTwin);
        solutionBoards = new Queue<>();

        int count = 0;
        while (!solutionNode.theBoard.isGoal()
             | !solutionNodeTwin.theBoard.isGoal()) {

            solutionNode = thePQ.delMin();
            solutionBoards.enqueue(solutionNode.theBoard);

            if (solutionNode.theBoard.isGoal()) {
                initialSolvable = true;
                break;
            } else {
                solutionNode.numberOfMovesMade++;
                Iterable<Board> neighborBoards = solutionNode.theBoard.neighbors();
                Iterator<Board> itr = neighborBoards.iterator();
                while (itr.hasNext()) {
                    Node neighborNode = new Node();
                    neighborNode.theBoard = itr.next();
                    neighborNode.numberOfMovesMade = solutionNode.numberOfMovesMade;
                    neighborNode.previousNode = solutionNode;
                    if (count == 0) {
                        thePQ.insert((neighborNode));
                    } else if (!neighborNode.theBoard.equals(solutionNode
                            .previousNode.theBoard)) {
                        thePQ.insert(neighborNode);
                    }
                }
            }

            solutionNodeTwin = thePQTwin.delMin();
            if (solutionNodeTwin.theBoard.isGoal()) {
                initialSolvableTwin = true;
                break;
            } else {
                solutionNodeTwin.numberOfMovesMade++;
                Iterable<Board> neighborBoardsTwin = solutionNodeTwin.theBoard.neighbors();
                Iterator<Board> itr2 = neighborBoardsTwin.iterator();
                while (itr2.hasNext()) {
                    Node neighborNodeTwin = new Node();
                    neighborNodeTwin.theBoard = itr2.next();
                    neighborNodeTwin.numberOfMovesMade = solutionNodeTwin.numberOfMovesMade;
                    neighborNodeTwin.previousNode = solutionNodeTwin;
                    if (count == 0) {
                        thePQTwin.insert(neighborNodeTwin);
                    } else if (!neighborNodeTwin.theBoard.equals(solutionNodeTwin.previousNode.theBoard)) {
                        thePQTwin.insert(neighborNodeTwin);
                    }
                }
            }
            count++;
        }
    }

    /**
     * Compares two nodes by the number of moves added to the manhattan
     * result.
     *
     * @return the Comparator that defines this ordering on nodes.
     */
    private Comparator<Node> boardOrder() {
        return new Comparator<Node>() {
            @Override
            public int compare(final Node o1, final Node o2) {
                int a = o1.theBoard.manhattan() + o1.numberOfMovesMade;
                int b = o2.theBoard.manhattan() + o2.numberOfMovesMade;

                return Integer.compare(a, b);
            }
        };
    }
    /**
     * Is the initial board solvable?
     * @return true if the initial board is solvable; false otherwise
     */
    public boolean isSolvable() {

        return initialSolvable;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable.
     * @return minimum number of moves to solve initial board
     */
    public int moves() {

         return initialSolvable ? solutionNode.numberOfMovesMade : -1;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable.
     * @return sequence of boards in shortest solution
     */
    public Iterable<Board> solution() {

        return initialSolvable ? solutionBoards : null;
    }

    /**
     * Unit tests the Solver data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }
}
