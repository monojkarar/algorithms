package symboltable.applications.indexing_clients;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

/**
 * Concordance class.
 */
public final class Concordance {

    /** Constructor. Do not instantiate. */
    private Concordance() { }

    /**
     * Unit tests the Concordance data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        In in = new In(args[0]);
        String[] words = StdIn.readAll().split("\\s+");
        ST<String, SET<Integer>> st = new ST<>();
        // read text and build index
        for (String word : words) {
            if (!st.contains(word)) {
                st.put(word, new SET<>());
            }
            SET<Integer> pages = st.get(word);
            //st.put(i);
        }
        // process queries and print concordances
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            SET<Integer> set = st.get(query);
            for (int k : set) {
                int temp = 0;
                // print words[k-4] to words[k+4]
            }
        }
    }
}
