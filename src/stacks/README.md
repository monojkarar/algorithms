Stack
-----
* Operations: insert, remove, iterate, test if empty
* Intent is clear when we insert.
* Which item do we remove?
* Examine the item most recently added (Last In First Out)

Client, implementation, interface
Separate interface and implementation

Benefits.
* Client can't know details of implementation
  => client has many implementations from which to choose.
* Implementation can't know details of client needs 
  => many clients can re-use the same implementation.
* Design: creates modular, reusable libraries.
* Performance: use optimized implementation where it matters.

Client: Program using operations defined in interface.
Implementation: actual code implementing operations.
Interface: description of data tpe, basic operations.