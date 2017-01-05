package analysisOfAlgorithms.week2_stacks_and_queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *  analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2_stacks_and_queues.Subset class takes a
 *  command-line integer k; reads in a sequence of N strings from standard
 *  input using StdIn.readString(); and prints out exactly k of them,
 *  uniformly at random. Each item from the sequence can be printed out at
 *  most once. You may assume that 0 ≤ k ≤ n, where n is the
 *  number of string on standard input.
 *
 *  % echo A B C D E F G H I | java .Subset 3      % echo AA BB BB BB BB BB CC CC | java Subset 8
 *  C                                              BB
 *  G                                              AA
 *  A                                              BB
 *  CC
 *  % echo A B C D E F G H I | java Subset 3       BB
 *  E                                              BB
 *  F                                              CC
 *  G                                              BB
 *
 *  Requirements:
 *  The running time of Subset must be linear in the size of the input. You may
 *  use only a constant amount of memory plus either one Deque or
 *  RandomizedQueue object of maximum size at most n, where n is the number of
 *  strings on standard input. (For an extra challenge, use only one Deque or
 *  RandomizedQueue object of maximum size at most k.)
 */
public class Subset {

    /**
     * Subset class takes a command-line integer k; reads in a sequence of N
     * strings from standard input using StdIn.readString(); and prints out
     * exactly k of them, uniformly at random.
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        int k = Integer.parseInt(args[0]);
        int count = 0;

        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
            count++;
        }
        while (count - k > 0) {
            randomizedQueue.dequeue();
            count--;
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
