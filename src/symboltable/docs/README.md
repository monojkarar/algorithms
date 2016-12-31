 Immutability: implementing in Java
 ----------------------------------
 Data type. Set values and operations on those values.
 Immutable data type. Can't change teh data type value once created.
 
 Immutable 
 * String
 * Integer
 * Double 
 * Color 
 * Vector 
 * Transaction 
 * Point2D
 * java.io.File
 
 Mutable 
 * StringBuilder
 * Stack
 * Counter
 * Java array
 * java.net.URL 
 
 Advantages.
 * Simplifies debugging.
 * Safer in presence of hostile code.
 * Simplifies concurrent programming.
 * Safe to use as key in priority queue or symbol table.
 
 Disadvantage 
* Must create new object for each data type value.

Equality Test
-------------
All Java classes inherit a method equals().

Java requirements. For any references x, y and z
* Reflexive:    x.equals(x) is true
* Symmetric:    x.equals(y) iff y.equals(x).
* Transitive:   if x.equals(y) and y.equals(z), then x.equals(z).
* Non-null:     x.equals(null) is false.