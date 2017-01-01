Assessment Summary
------------------
Compilation:  PASSED

Checkstyle:   FAILED (1 warning)

Findbugs:     FAILED (1 warning)

API:          PASSED

Correctness:  37/42 tests passed

Memory:       8/11 tests passed

Timing:       17/17 tests passed

Aggregate score: 89.53% 

[Correctness: 65%, Memory: 10%, Timing: 25%, Style: 0%]

###Assessment Details

####The following files were submitted:

9.5K Jan  1 11:43 Board.java
7.0K Jan  1 11:43 Solver.java
4.3K Jan  1 11:43 studentSubmission.zip

COMPILING

% javac Board.java

% javac Solver.java

% checkstyle *.java

Board.java:159:17: Do not use the 'instanceof' operator in this course. 
Use 'getClass()' to compare classes. [IllegalToken]
Checkstyle ends with 1 error.



% findbugs *.class

L P SIC_INNER_SHOULD_BE_STATIC_ANON SIC: 

Should the anonymous inner class 
'Solver$1' be refactored into a named static nested class? 

At Solver.java:[lines 140-146]
Warnings generated: 1

================================================================

Testing the APIs of your programs.
Board:

Solver:

================================================================

TESTING CORRECTNESS

Testing correctness of Board

Running 22 total tests.

Tests 5, 6, 13, and 14 rely upon toString() returning results in prescribed format.

Test 1a: Test hamming() with file inputs
  *  puzzle04.txt
  *  puzzle00.txt
  *  puzzle07.txt
  *  puzzle17.txt
  *  puzzle27.txt
  *  puzzle2x2-unsolvable1.txt
==> passed

Test 1b: Test hamming() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  9-by-9
  *  10-by-10
  *  127-by-127
==> passed

Test 2a: Test manhattan() with file inputs
  *  puzzle04.txt
  *  puzzle00.txt
  *  puzzle07.txt
  *  puzzle17.txt
  *  puzzle27.txt
  *  puzzle2x2-unsolvable1.txt
==> passed

Test 2b: Test manhattan() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  9-by-9
  *  10-by-10
  *  127-by-127
==> passed

Test 3: Test dimension() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
==> passed

Test 4a: Test toString() with file inputs
  *  puzzle04.txt
  *  puzzle00.txt
  *  puzzle06.txt
  *  puzzle09.txt
  *  puzzle23.txt
  *  puzzle2x2-unsolvable1.txt
==> passed

Test 4b: Test toString() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  9-by-9
  *  10-by-10
  *  127-by-127
==> passed

Test 5a: Test neighbors() with file inputs
  *  puzzle04.txt
  *  puzzle00.txt
  *  puzzle06.txt
  *  puzzle09.txt
  *  puzzle23.txt
  *  puzzle2x2-unsolvable1.txt
==> passed

Test 5b: Test neighbors() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  9-by-9
  *  10-by-10
  *  127-by-127
==> passed

Test 6a: Test neighbors() of neigbors() with file inputs
  *  puzzle04.txt
  *  puzzle00.txt
  *  puzzle06.txt
  *  puzzle09.txt
  *  puzzle23.txt
  *  puzzle2x2-unsolvable1.txt
==> passed

Test 6b: Test neighbors() of neighbors() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  9-by-9
  *  10-by-10
==> passed

Test 7a: Test twin() with file inputs
  *  puzzle04.txt
  *  puzzle00.txt
  *  puzzle06.txt
  *  puzzle09.txt
  *  puzzle23.txt
  *  puzzle2x2-unsolvable1.txt
==> passed

Test 7b: Test twin() with random N-by-N boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  9-by-9
  *  10-by-10
==> passed

Test 8a: Test isGoal() on file inputs
  *  puzzle00.txt
  *  puzzle04.txt
  *  puzzle16.txt
  *  puzzle06.txt
  *  puzzle09.txt
  *  puzzle23.txt
  *  puzzle2x2-unsolvable1.txt
  *  puzzle3x3-unsolvable1.txt
  *  puzzle3x3-00.txt
  *  puzzle4x4-00.txt
==> passed

Test 8b: Test isGoal() on N-by-N goal boards
  *  2-by-2
  *  3-by-3
  *  4-by-4
  *  5-by-5
  *  6-by-6
  *  100-by-100
==> passed

Test 9: Check whether two Board objects can be created at the same time
  *   random 3-by-3 and 3-by-3 boards
  *   random 4-by-4 and 4-by-4 boards
  *   random 2-by-2 and 2-by-2 boards
  *   random 3-by-3 and 4-by-4 boards
  *   random 4-by-4 and 3-by-3 boards
==> passed

Test 10a: Check equals()
  *  reflexive
  *  symmetric
  *  checks that individual entries of array are equal
  *  argument is object of type String
  *  argument is object of type Object
  *  argument is null
  *  argument is Board of different dimension
==> passed

Test 10b: Test equals() on M-by-M vs. N-by-N goal boards
  *  M = 2, N = 2
  *  M = 3, N = 3
  *  M = 4, N = 4
  *  M = 2, N = 5
  *  M = 5, N = 2
==> passed

Test 11: Check that Board is immutable by changing argument array after
         construction and making sure Board does not mutate
     -  hamming() different before and after changing 2d array
     -  before = 7
     -  after  = 6
==> FAILED

Test 12: Check that Board is immutable by testing whether methods
         return the same value, regardless of order in which called
  *  puzzle10.txt
  *  puzzle20.txt
  *  puzzle30.txt
  *  2-by-2
  *  3-by-3
  *  4-by-4
==> passed

Test 13: Call hamming() on a board that is kth-neighbor of a board
  * 0th neighbor of puzzle27.txt
  * 1th neighbor of puzzle27.txt
  * 2th neighbor of puzzle27.txt
  * 13th neighbor of puzzle27.txt
  * 13th neighbor of puzzle00.txt
  * 13th neighbor of puzzle2x2-unsolvable1.txt
==> passed

Test 14: Call manhattan() on a board that is a kth-neighbor of a board
  * 0th neighbor of puzzle27.txt
  * 1th neighbor of puzzle27.txt
  * 2th neighbor of puzzle27.txt
  * 13th neighbor of puzzle27.txt
  * 13th neighbor of puzzle00.txt
  * 13th neighbor of puzzle2x2-unsolvable1.txt
==> passed

Total: 21/22 tests passed!

================================================================

TESTING CORRECTNESS (substituting reference Board)

Testing correctness of Solver

Running 20 total tests.

Test 1: Call moves() with file inputs
  *  puzzle00.txt
  *  puzzle01.txt
  *  puzzle02.txt
  *  puzzle03.txt
  *  puzzle04.txt
  *  puzzle05.txt
  *  puzzle06.txt
  *  puzzle07.txt
  *  puzzle08.txt
  *  puzzle09.txt
  *  puzzle10.txt
  *  puzzle11.txt
  *  puzzle12.txt
  *  puzzle13.txt
==> passed

Test 2: Call solution() with file inputs
  *  puzzle00.txt
  *  puzzle01.txt
  *  puzzle02.txt
  *  puzzle03.txt
  *  puzzle04.txt
  *  puzzle05.txt
  *  puzzle06.txt
  *  puzzle07.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 16
     -  moves()              = 7
  *  puzzle08.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 10
     -  moves()              = 8
  *  puzzle10.txt
  *  puzzle15.txt
==> FAILED

Test 3: Create two Solver objects at the same time
  *  puzzle04.txt and puzzle04.txt
  *  puzzle00.txt and puzzle04.txt
  *  puzzle04.txt and puzzle00.txt
==> passed

Test 4a: Call isSolvable() with file inputs
  *  puzzle01.txt
  *  puzzle03.txt
  *  puzzle04.txt
  *  puzzle17.txt
  *  puzzle3x3-unsolvable1.txt
  *  puzzle3x3-unsolvable2.txt
  *  puzzle4x4-unsolvable.txt
==> passed

Test 4b: Call isSolvable() on random n-by-n boards
  *  100 random 2-by-2 boards
==> passed

Test 5: Call moves() on unsolvable puzzles
  *  puzzle2x2-unsolvable1.txt
  *  puzzle2x2-unsolvable2.txt
  *  puzzle3x3-unsolvable1.txt
  *  puzzle3x3-unsolvable2.txt
  *  puzzle4x4-unsolvable.txt
==> passed

Test 6: Call solution() on unsolvable puzzles
  *  puzzle2x2-unsolvable1.txt
  *  puzzle2x2-unsolvable2.txt
  *  puzzle3x3-unsolvable1.txt
  *  puzzle3x3-unsolvable2.txt
  *  puzzle4x4-unsolvable.txt
==> passed

Test 7a: Check that Solver is immutable by testing whether methods
         return the same value, regardless of order in which called
  *  puzzle3x3-00.txt
  *  puzzle3x3-01.txt
  *  puzzle3x3-05.txt
  *  puzzle3x3-10.txt
  *  random 2-by-2 solvable boards
==> passed

Test 7b: Check that Solver is immutable by testing whether methods
         return the same value, regardless of order in which called
  *  puzzle3x3-unsolvable1.txt
  *  puzzle3x3-unsolvable2.txt
  *  puzzle4x4-unsolvable.txt
  *  random 2-by-2 unsolvable boards
==> passed

Test 8: Call moves() with more file inputs
  *  puzzle14.txt
  *  puzzle15.txt
  *  puzzle16.txt
  *  puzzle17.txt
  *  puzzle18.txt
  *  puzzle19.txt
  *  puzzle20.txt
  *  puzzle21.txt
  *  puzzle22.txt
  *  puzzle23.txt
  *  puzzle24.txt
  *  puzzle25.txt
  *  puzzle26.txt
  *  puzzle27.txt
  *  puzzle28.txt
  *  puzzle29.txt
  *  puzzle30.txt
  *  puzzle31.txt
==> passed

Test 9: Check whether equals() method in Board is called
        with an argument of the wrong type
  *  puzzle00.txt
  *  puzzle05.txt
  *  puzzle10.txt
  *  puzzle15.txt
==> passed

Test 10: Check that constructor throws exception if board is null
==> passed

Test 11: Check for fragile dependence on toString()
  *  puzzle00.txt
  *  puzzle04.txt
  *  puzzle08.txt
  *  puzzle12.txt
==> passed

Test 12a: Call moves() with 2-by-2 file inputs
  *  puzzle2x2-00.txt
  *  puzzle2x2-01.txt
  *  puzzle2x2-02.txt
  *  puzzle2x2-03.txt
  *  puzzle2x2-04.txt
  *  puzzle2x2-05.txt
  *  puzzle2x2-06.txt
==> passed

Test 12b: Call solution() with 2-by-2 file inputs
  *  puzzle2x2-00.txt
  *  puzzle2x2-01.txt
  *  puzzle2x2-02.txt
  *  puzzle2x2-03.txt
  *  puzzle2x2-04.txt
  *  puzzle2x2-05.txt
  *  puzzle2x2-06.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 12
     -  moves()              = 6
==> FAILED

Test 13a: Call moves() with 3-by-3 file inputs
  *  puzzle3x3-00.txt
  *  puzzle3x3-01.txt
  *  puzzle3x3-02.txt
  *  puzzle3x3-03.txt
  *  puzzle3x3-04.txt
  *  puzzle3x3-05.txt
  *  puzzle3x3-06.txt
  *  puzzle3x3-07.txt
  *  puzzle3x3-08.txt
  *  puzzle3x3-09.txt
  *  puzzle3x3-10.txt
  *  puzzle3x3-11.txt
  *  puzzle3x3-12.txt
  *  puzzle3x3-13.txt
  *  puzzle3x3-14.txt
  *  puzzle3x3-15.txt
  *  puzzle3x3-16.txt
  *  puzzle3x3-17.txt
  *  puzzle3x3-18.txt
  *  puzzle3x3-19.txt
  *  puzzle3x3-20.txt
  *  puzzle3x3-21.txt
  *  puzzle3x3-22.txt
  *  puzzle3x3-23.txt
  *  puzzle3x3-24.txt
  *  puzzle3x3-25.txt
  *  puzzle3x3-26.txt
  *  puzzle3x3-27.txt
  *  puzzle3x3-28.txt
  *  puzzle3x3-29.txt
  *  puzzle3x3-30.txt
==> passed

Test 13b: Call solution() with 3-by-3 file inputs
  *  puzzle3x3-00.txt
  *  puzzle3x3-01.txt
  *  puzzle3x3-02.txt
  *  puzzle3x3-03.txt
  *  puzzle3x3-04.txt
  *  puzzle3x3-05.txt
  *  puzzle3x3-06.txt
  *  puzzle3x3-07.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 13
     -  moves()              = 7
  *  puzzle3x3-08.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 18
     -  moves()              = 8
  *  puzzle3x3-09.txt
  *  puzzle3x3-10.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 17
     -  moves()              = 10
  *  puzzle3x3-11.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 19
     -  moves()              = 11
  *  puzzle3x3-12.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 38
     -  moves()              = 12
  *  puzzle3x3-13.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 48
     -  moves()              = 13
  *  puzzle3x3-14.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 30
     -  moves()              = 14
  *  puzzle3x3-15.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 50
     -  moves()              = 15
  *  puzzle3x3-16.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 197
     -  moves()              = 16
  *  puzzle3x3-17.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 328
     -  moves()              = 17
  *  puzzle3x3-18.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 406
     -  moves()              = 18
  *  puzzle3x3-19.txt
  *  puzzle3x3-20.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 383
     -  moves()              = 20
  *  puzzle3x3-21.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 769
     -  moves()              = 21
  *  puzzle3x3-22.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 1164
     -  moves()              = 22
  *  puzzle3x3-23.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 871
     -  moves()              = 23
  *  puzzle3x3-24.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 3603
     -  moves()              = 24
  *  puzzle3x3-25.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 3011
     -  moves()              = 25
  *  puzzle3x3-26.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 1866
     -  moves()              = 26
  *  puzzle3x3-27.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 12794
     -  moves()              = 27
  *  puzzle3x3-28.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 12540
     -  moves()              = 28
  *  puzzle3x3-29.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 12420
     -  moves()              = 29
  *  puzzle3x3-30.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 13503
     -  moves()              = 30
==> FAILED

Test 14a: Call moves() with 4-by-4 file inputs
  *  puzzle4x4-00.txt
  *  puzzle4x4-01.txt
  *  puzzle4x4-02.txt
  *  puzzle4x4-03.txt
  *  puzzle4x4-04.txt
  *  puzzle4x4-05.txt
  *  puzzle4x4-06.txt
  *  puzzle4x4-07.txt
  *  puzzle4x4-08.txt
  *  puzzle4x4-09.txt
  *  puzzle4x4-10.txt
  *  puzzle4x4-11.txt
  *  puzzle4x4-12.txt
  *  puzzle4x4-13.txt
  *  puzzle4x4-14.txt
  *  puzzle4x4-15.txt
  *  puzzle4x4-16.txt
  *  puzzle4x4-17.txt
  *  puzzle4x4-18.txt
  *  puzzle4x4-19.txt
  *  puzzle4x4-20.txt
  *  puzzle4x4-21.txt
  *  puzzle4x4-22.txt
  *  puzzle4x4-23.txt
  *  puzzle4x4-24.txt
  *  puzzle4x4-25.txt
  *  puzzle4x4-26.txt
  *  puzzle4x4-27.txt
  *  puzzle4x4-28.txt
  *  puzzle4x4-29.txt
  *  puzzle4x4-30.txt
==> passed

Test 14b: Call solution() with 4-by-4 file inputs
  *  puzzle4x4-00.txt
  *  puzzle4x4-01.txt
  *  puzzle4x4-02.txt
  *  puzzle4x4-03.txt
  *  puzzle4x4-04.txt
  *  puzzle4x4-05.txt
  *  puzzle4x4-06.txt
  *  puzzle4x4-07.txt
  *  puzzle4x4-08.txt
  *  puzzle4x4-09.txt
  *  puzzle4x4-10.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 17
     -  moves()              = 10
  *  puzzle4x4-11.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 15
     -  moves()              = 11
  *  puzzle4x4-12.txt
  *  puzzle4x4-13.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 18
     -  moves()              = 13
  *  puzzle4x4-14.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 36
     -  moves()              = 14
  *  puzzle4x4-15.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 46
     -  moves()              = 15
  *  puzzle4x4-16.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 33
     -  moves()              = 16
  *  puzzle4x4-17.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 66
     -  moves()              = 17
  *  puzzle4x4-18.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 521
     -  moves()              = 18
  *  puzzle4x4-19.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 138
     -  moves()              = 19
  *  puzzle4x4-20.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 33
     -  moves()              = 20
  *  puzzle4x4-21.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 245
     -  moves()              = 21
  *  puzzle4x4-22.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 183
     -  moves()              = 22
  *  puzzle4x4-23.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 1621
     -  moves()              = 23
  *  puzzle4x4-24.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 290
     -  moves()              = 24
  *  puzzle4x4-25.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 658
     -  moves()              = 25
  *  puzzle4x4-26.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 1940
     -  moves()              = 26
  *  puzzle4x4-27.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 700
     -  moves()              = 27
  *  puzzle4x4-28.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 6287
     -  moves()              = 28
  *  puzzle4x4-29.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 7993
     -  moves()              = 29
  *  puzzle4x4-30.txt
     -  number of boards in solution() does not equal to 1 + moves()
        (it should be 1 greater because solution() starts with the inital board)
     -  length of solution() = 699
     -  moves()              = 30
==> FAILED

Test 15: Call moves() with random solvable n-by-n boards
  *  100 random 2-by-2 boards
  *  200 random 3-by-3 boards that are <= 20 moves from goal
  *  200 random 4-by-4 boards that are <= 20 moves from goal
  *  200 random 5-by-5 boards that are <= 20 moves from goal
==> passed

Total: 16/20 tests passed!

================================================================

MEMORY

Computing memory of Board

Running 8 total tests.

Memory usage of an n-by-n board

              n       student (bytes)    reference (bytes)
----------------------------------------------------------
=> passed     4           248                  240
=> passed     8           568                  560
=> passed    12          1016                 1008
=> passed    16          1592                 1584
=> passed    20          2296                 2288
=> passed    36          6392                 6384
=> passed    72         23096                23088
=> passed   120         61496                61488
==> 8/8 tests passed

Total: 8/8 tests passed!

Student   memory = 4.00 n^2 + 32.00 n + 56.00   (R^2 = 1.000)
Reference memory = 4.00 n^2 + 32.00 n + 48.00   (R^2 = 1.000)

================================================================



Computing memory of Solver

Running 3 total tests.

Test 1: memory with puzzle20.txt (must be <= 2.0x reference solution)
  - memory of student   Solver = 118016 bytes
  - memory of reference Solver = 4896 bytes
  - student / reference        = 24.10
==> FAILED

Test 2: memory with puzzle25.txt (must be <= 2.0x reference solution)
  - memory of student   Solver = 726792 bytes
  - memory of reference Solver = 6056 bytes
  - student / reference        = 120.01
==> FAILED

Test 3: memory with puzzle30.txt (must be <= 2.0x reference solution)
  - memory of student   Solver = 2926856 bytes
  - memory of reference Solver = 7216 bytes
  - student / reference        = 405.61
==> FAILED


Total: 0/3 tests passed!

================================================================

TIMING


Timing Solver

Running 17 total tests.

Timing tests use your implementation of Board.java and Solver.java.
Maximum time allowed per puzzle is 10 seconds.

               filename   N    seconds    insert()            delMin()         max PQ size
---------------------------------------------------------------------------------------------
=> passed  puzzle20.txt   3     0.02       1708                1007                 702         
=> passed  puzzle21.txt   3     0.02       3950                2347                1604         
=> passed  puzzle22.txt   3     0.02       3532                2077                1456         
=> passed  puzzle23.txt   3     0.02       5785                3437                2349         
=> passed  puzzle24.txt   3     0.02       6219                3751                2469         
=> passed  puzzle25.txt   3     0.03      10512                6253                4260         
=> passed  puzzle26.txt   3     0.03      10483                6203                4281         
=> passed  puzzle27.txt   3     0.05      15734                9501                6234         
=> passed  puzzle28.txt   3     0.07      20260               12151                8110         
=> passed  puzzle29.txt   3     0.05      22182               13331                8852         
=> passed  puzzle30.txt   3     0.11      42131               25217               16915         
=> passed  puzzle31.txt   3     0.20      82441               49205               33237         
=> passed  puzzle34.txt   4     0.35     129761               62559               67203         
=> passed  puzzle37.txt   4     0.52     197143               94497              102647         
=> passed  puzzle39.txt   4     0.37     142538               69493               73046         
=> passed  puzzle41.txt   5     0.19      60140               25483               34658         
=> passed  puzzle44.txt   5     0.93     249912              109925              139988         
==> 17/17 tests passed

Total: 17/17 tests passed!
