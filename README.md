# Gimme: A Stupid DSL for Scala

## Purposes

* To act as a somewhat easy example for people wanting to build internal DSLs in Scala.
* For the fun that comes out of defining an insane language that will proably 
serve no practical purpose
* To see how far I can take this 

## Language Specification

Gimme is very similar to assembly language programming. 
Currently it can only store 1 thing in memory, but this will soon change
with the addition of a stack of sorts.  There is no concept of variables.

Gimme works by giving you things whenever you ask for them. The thing you
asked for will then be stored in program state.
When you request something else after you've already asked it to give you
something, the last thing it "gave" you will be overwritten.

`GIMME A NUMBER`

Gives you a random number between 0 and 99 that is then stored in program state.

`GIMME A NUMBER BETWEEN <x> and <y>`

Gives you a random number between x and y inclusive that is then stored in
program state.

`GIMME A STRING`

Gives you a random string of 5 characters that is then stored in program state.

`GIMME A BOOL`

Gives you true or false that is then stored in program state.

`GIMME A <x>`

Gives you whatever you give it (as long as it's an int, string, or bool) by
saving it to program state. Note you can also have an expression wrapped
in parens (as long as Scala can read it). For example, (1 + 3).

`GIMME <x>`

x should be a numeric expression or (TODO) a boolean comparison. Will give
you it.

`GIMME THE BELOW IF <TRUE/FALSE>`

Beginning of Gimme's conditional construct. Gimme checks the last boolean
that was gimme'd and will go through the code below if it meets the
condition specified (true/false).

`GIMME THE ABOVE`

End of Gimme's conditional construct. Marks the end of a conditional. 

`GIMME ADDITION`

`GIMME SUBTRACTION`

`GIMME MULTIPLICATION`

`GIMME DIVISION`

These operations will take the last two things that have been gimme'd and
apply the operation. The result will be what is gimme'd, i.e. placed in
the program state. Both should be numbers; otherwise you will get an error.

The order is Last Thing OP Thing Before That.

`GIMME OUTPUT`

Prints to stdout the last thing you requested the program to give you.

**Note that all lines should end with a semi-colon or bad things might happen**. The
purpose of the semi-colon is the stop the runtime from trying to parse the next
thing as an argument of the thing that is possibly returned at the end of each
line. Another way to get around this would be to put a new line between each
line, but that makes the code ugly.

