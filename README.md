Simple_Compiler
===============

This was an assignment done for for one of my undergraduate studies course module - Compiler Design(CS4542. This is
a simple compiler which can parse the given grammar,

P → D L

D → B N ; D | B N ;

B → int | float

N → N , id | id

L → S ; L | S ;

S → id = E | E

E → E + T | T

T → T × F | F

F → ( E ) | num | id

To run this compiler pass the input file as an argument.

This compiler was developed using the example compiler given in the book Compilers: Principles, Techniques, and Tools by
Alfred V. Aho, Monica S. Lam, Ravi Sethi, and Jeffrey D. Ullman.
