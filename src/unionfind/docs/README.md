###### Slides: 
> https://drive.google.com/open?id=0B4obpfq-GqEPTXJHZzVtbTVia2s

#####Applications involving manipulating objects of all types.
* pixels in a digital photo
* computers in a network
* friends in a social network
* transistors in a computer chip
* elements in a mathematical sett
* variables names in a program
* metallic sites in a composite system

When programming, convenient to name objects 0 to N-1.
* use integers as array index
* suppress details not relevant to union find (can use symbol talbe to 
translate from site names to integers).

We assume "is connected to" is an equivalence relation:
* reflexive: p is connected to p
* symmetric: if p is connected to q, then q is connected to p
* transitive: if p is connected to q and q is connected to r, then p is 
connected to r.

Connected components. Maximal set of objects that are mutually connected.

Implementing the  operations
* Find query. Check if two objects are in the same component.
* Union command. Replace components containing two objects with their union.

#####Union-find data type (API)
Goal. Design efficient data structure for union-find.
* Number of objects N can be huge.
* Number of operations M can be huge.
* Find queries and union commands may be intermixed.

####Union-find applications
* Percolation
* Games (Go, Hex).
* Dynamic connectivity
* least common ancestor
* Equivalence of finit state automata
* Hoshen-Kopelman algorithm in physics
* Hinley-Milner polymorphic type inference
* Kruskals minimum spanning tree algorithm
* Compiling equivalence statements in Fortran
* Morphological attribute openings and closings
* Matlab's bwlabel() function in image processing
