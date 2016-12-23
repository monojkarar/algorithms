##Sorting Applications

####Sorting algorithms are essential in a broad varitiety of applications.
Obvious Applications
* Sort a list of names.
* Organize an mp3 library.
* Display Google PageRank results.
* List RSS feed in reverse chronological order.

Problems become easy once items are in sorted order
* Find the median.
* Binary search in a database.
* Identify statistical outliers.
* Find duplicates in a mailing list.

Non-obvious applications
* Data compression.
* Computer graphics.
* Computational biologoy.
* Load balancing on a computer.

#### Java system sorts
Arrays.sorts(). Import java.util.Arrays.
* Has different method for each primitive type.
* Has a method for data types that implements Comparable.
* Has a method that uses a Comparator.
* Uses tuned quicksort for primitive types; tuned mergesort for object.

####System Sort: Which algorithm to use?
Appliations have diverse attributes.
* Stable?
* Parallel?
* Deterministic?
* Keys all distinct?
* Multiple key types?
* Linked list or arrays?
* Large or small items?
* Is your array randomly ordered?
* Need guaranteed performance?

Elementary sort may be method of choice for some combination.
Cannot cover all combinations of attributes.

Q. Is the system sort good enough?
A. Usually.