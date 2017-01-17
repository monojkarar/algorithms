package queues;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Evaluate class.
 */
public final class Evaluate {

    /** Constructor. */
    private Evaluate() { }

    /**
     * Unit tests the PostFix data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("(")) {
                ;
            } else if (s.equals("+")) {
                ops.push(s);
            } else if (s.equals("*")) {
                ops.push(s);
            } else if (s.equals(")")) {
                String op = ops.pop();
                if (op.equals("+")) {
                    vals.push(vals.pop() + vals.pop());
                } else if (op.equals("*")) {
                    vals.push(vals.pop() * vals.pop());
                }
            } else {
                vals.push(Double.parseDouble(s));
            }
        }
        StdOut.println(vals.pop());
    }
}
