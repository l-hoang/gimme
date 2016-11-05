# Gimme: A Stupid DSL for Scala

## Purposes

* To act as a somewhat easy example for people wanting to build internal DSLs in Scala.
* For the fun that comes out of defining an insane language that will proably serve no pratical purpose
* To see how far I can take this :V

## Language Specification

Gimme works by giving you things whenever you ask for them. The thing you
asked for will then be stored in program state.
When you request something else after you've already asked it to give you
something, the last thing it "gave" you will be overwritten.

`GIMME NUMBER`

Gives you a random number between 0 and 100 that is then stored in program state.

`GIMME STRING`

Gives you a random string of 5 characters that is then stored in program state.

`GIMME BOOL`

Gives you true or false that is then stored in program state.

`GIMME OUTPUT`

Prints to stdout the last thing you requested the program to give you.

**Note that all lines should end with a semi-colon or bad things might happen**. The
purpose of the semi-colon is the stop the runtime from trying to parse the next
thing as an argument of the thing that is possibly returned at the end of each
line. Another way to get around this would be to put a new line between each
line, but that makes the code ugly.
