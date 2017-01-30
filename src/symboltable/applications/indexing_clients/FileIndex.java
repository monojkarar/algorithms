/******************************************************************************
 *  Compilation:  javac FileIndex.java
 *  Execution:    java FileIndex file1.txt file2.txt file3.txt ...
 *  Dependencies: ST.java SET.java In.java StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/ex1.txt
 *                http://algs4.cs.princeton.edu/35applications/ex2.txt
 *                http://algs4.cs.princeton.edu/35applications/ex3.txt
 *                http://algs4.cs.princeton.edu/35applications/ex4.txt
 *
 *  % java FileIndex ex*.txt
 *  age
 *   ex3.txt
 *   ex4.txt 
 * best
 *   ex1.txt 
 * was
 *   ex1.txt
 *   ex2.txt
 *   ex3.txt
 *   ex4.txt 
 *
 *  % java FileIndex *.txt
 *
 *  % java FileIndex *.java
 *
 ******************************************************************************/

package symboltable.applications.indexing_clients;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;

/**
 *  The FileIndex class provides a client for indexing a set of files,
 *  specified as command-line arguments. It takes queries from standard input
 *  and prints each file that contains the given query.
 *
 *  ls *.java
 *  java FileIndex *.java (create the index)
 *  import (print out files in index that contains the key).
 *
 *  For additional documentation, see
 *  <a href="http://algs4.cs.princeton.edu/35applications">Section 3.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public final class FileIndex {

    /** Constructor. Do not instantiate. */
    private FileIndex() { }

    /**
     * Unit tests the FileIndex data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        // key = word, value = set of files containing that word (symbol table)
        ST<String, SET<File>> st = new ST<>();      // symbol table

        // create inverted index of all files from command line
        StdOut.println("Indexing files");
        for (String filename : args) {
            StdOut.println("  " + filename);
            File file = new File(filename);         // list of file names
            In in = new In(file);
            // for each word in file, add file to corresponding set
            while (!in.isEmpty()) {
                // for each word in file, add file to corresponding list
                String word = in.readString();
                if (!st.contains(word)) {
                    st.put(word, new SET<>());
                }
                SET<File> set = st.get(word);
                set.add(file);
            }
        }


        // read queries from standard input, one per line
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();      // process queries
            if (st.contains(query)) {
                SET<File> set = st.get(query);
                for (File file : set) {
                    StdOut.println("  " + file.getName());
                }
            }
        }

    }

}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
