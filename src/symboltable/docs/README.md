####Symbol table applications

Application         Purpose of search       key         value
dictionary          find definition         word        definition
book index          find relevant pages     term        list of page numbers
file share          find song to download   song        computer id
financial account   process transactions    account #   transaction details
web search          find relevant web pages keyword     list of page names
compiler            find properties of vars var name    type and value
routing table       route Internet packets  destination best route
DNS                 find IP addr given URl  URL         IP address
reverse DNS         find URL given address  IP address  URL
genomics            find markers            DNA string  known positions
file system         find file on disk       filename    location on disk

Equality Test
-------------
All Java classes inherit a method equals().

Java requirements. For any references x, y and z
* Reflexive:    x.equals(x) is true
* Symmetric:    x.equals(y) iff y.equals(x).
* Transitive:   if x.equals(y) and y.equals(z), then x.equals(z).
* Non-null:     x.equals(null) is false.