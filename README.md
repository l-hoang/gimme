# Gimme: A Stupid DSL for Scala

## Purposes

* To act as a somewhat easy example for people wanting to build internal DSLs in Scala.
* For the fun that comes out of defining an insane language that will proably 
serve no practical purpose
* To see how far I can take this 

## How To Compile

1) cd to the src directory.

2) Make a "bin" directory in the directory above src. 

3) Run the following in order (the order can be mixed up a bit, but I'm pretty sure this order will work)

```
scalac -d ../bin/ -cp ../bin gimme/KeywordObjects.scala 
scalac -d ../bin/ -cp ../bin gimmehelpers/OpEnums.scala 
scalac -d ../bin/ -cp ../bin gimmehelpers/GimmeOpLines.scala 
scalac -d ../bin/ -cp ../bin gimmehelpers/ProgramLineBuilders.scala 
scalac -d ../bin/ -cp ../bin gimmehelpers/GimmeStack.scala 
scalac -d ../bin/ -cp ../bin gimmehelpers/ProgramState.scala 
scalac -d ../bin/ -cp ../bin gimmehelpers/ProgramText.scala 
scalac -d ../bin/ -cp ../bin gimme/Gimme.scala 
scalac -d ../bin/ -cp ../bin tests/SanityTest.scala 
```

4) To run the Sanity Test, cd over to the bin directory and run the following

`scala tests.SanityTest`

## How Gimme Works as a DSL

The following section will explain what the purpose of each file is in this system.

### gimme/Gimme.scala

This file contains the Gimme class, which is what one wants to extend when 
writing a program in Gimme.

The GIMME object acts as the beginning of a Gimme line: it contains method
calls that correspond to the next valid word that could come after the word
GIMME in a Gimme program. There are also apply methods that take certain
arguments and do things with them as well.

The class contains a Program Text instance (explained below) that stores
the program as it is parsed by the Gimme class. The Gimme class will call 
into this function to set lines as they are being read, and at the beginning
of each Gimme line, the last line parsed will be stored.

### gimme/KeywordObjects.scala

This file contains some keywords of the language instantiated as objects that
inherit from classes of that word.

The purpose of these is to act as something that the Scala parser can take
as arguments in order to continue the parse of a Gimme line.

### gimmehelpers/GimmeOpLines.scala

This file contains the case classes for the possible Gimme lines. They are used
by the program to represent lines in a Gimme program so that they can be
easily run later through matching case classes.

### gimmehelpers/GimmeStack.scala

This file contains the implementation of the Gimme Stack: Gimme has a stack
that contains the most recent things Gimme'd.

There is an implementation of an element in the stack and the stack itself.
It wraps a Java ArrayDeque (the underlying implementation of the stack).

### gimmehelpers/OpEnums.scala

This file holds the "enums" used by the program. These enums are set
to designate what kind of line is currently being parsed so that the LineBuilder
knows what kind of line to return.

### gimmehelpers/ProgramLineBuilder.scala

This file contains the Gimme Line Builder which is responsible for returning
Gimme lines (as case classes) for storage by the program text. The main Gimme
program is able to set things in this class so that it knows what kind of
line it needs to return when the method to finish a line is called on it.

### gimmehelpers/ProgramState.scala

This file contains the program state of a Gimme program. It is changed
as a Gimme program is executed. The main thing it holds is the program
stack (the Gimme stack mentioned above), and it is passed into
the Program Text class so that the text can change the state of the program
as it runs it.

### gimmehelpers/ProgramText.scala

This class holds the lines that are parsed by the main Gimme class (and
a Line Builder that is responsible for making those lines). The main Gimme
class calls into this one to create lines and save them. It also contains
the runtime handler, as in it is responsible for running a program since
it has access to the lines. It runs a program by going through the stored
Gimme lines and taking the appropriate action on a passed in program
state.

## Language Specification

Gimme is very similar to assembly language programming. 
Currently it can only store 1 thing in memory, but this will soon change
with the addition of a stack of sorts.  There is no concept of variables.

Gimme works by giving you things whenever you ask for them. The thing you
asked for will then be stored in program state.
When you request something else after you've already asked it to give you
something, the last thing it "gave" you will be overwritten.

### Basic Gimmes

`GIMME A NUMBER`

Gives you a random number between 0 and 100(?) that is then stored in program state.

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

`GIMME A <x> WITH OUTPUT`

Gives you a *string* and outputs it. *Will not work with booleans or numbers*.

`GIMME <x>`

x should be a numeric expression or a boolean comparison. Will give
you the result on the stack.

### Conditionals and Loops

`GIMME THE BELOW IF <TRUE/FALSE>`

Beginning of Gimme's conditional construct. Gimme checks the last boolean
that was gimme'd and will go through the code below if it meets the
condition specified (true/false).

`GIMME THE ABOVE`

End of Gimme's conditional construct. Marks the end of a conditional. 

`GIMME CONTINUOUSLY THE BELOW`

Marks the beginning of a while loop. Note that loops are infinite in Gimme
unless you break out of them.

`GIMME CONTINUOUSLY THE ABOVE`

Marks the end of a while loop. Note that loops are infinite in Gimme
unless you break out of them.

`GIMME A BREAK`

Breaks out of a loop that the break is contained in.

`GIMME A BREAK IF <TRUE/FALSE>`

Breaks out of a loop that the break is contained in if the last boolean
was true/false.


### Comparators

`GIMME GREATER THAN`

`GIMME GREATER THAN EQUAL TO`

`GIMME LESS THAN`

`GIMME LESS THAN EQUAL TO`

`GIMME EQUAL TO`

Takes the last 2 numbers on the stack and compares them. The top number x1 is on
the left, the latter number x2 (i.e. below x1 on stack) is on the right, 
e.g. x1 > x2.

`GIMME GREATER THAN NUMBER <n>`

`GIMME GREATER THAN EQUAL TO NUMBER <n>`

`GIMME LESS THAN NUMBER <n>`

`GIMME LESS THAN EQUAL TO NUMBER <n>`

`GIMME EQUAL TO NUMBER <n>`

Compares the number you give it with the top number on the stack. Note
the number you give it will be on the left, e.g. n > x1. (x1 is the 
top number on the stack)

### Operations on Gimme'd Things

`GIMME ADDITION`

`GIMME SUBTRACTION`

`GIMME MULTIPLICATION`

`GIMME DIVISION`

These operations will take the last two things that have been gimme'd and
apply the operation. The result will be what is gimme'd, i.e. placed in
the program state. Both should be numbers; otherwise you will get an error.

The order is Last Thing OP Thing Before That. For example, if I gimme a 10 *then*
a 20, the division operation will do 20 divided by 10.


`GIMME ADDITION WITH <n>`

`GIMME SUBTRACTION WITH <n>`

`GIMME MULTIPLICATION WITH <n>`

`GIMME DIVISION WITH <n>`

Does the requested binary op with n as the first operand and the last number
that was gimme'd as the second operand.

### Other

`GIMME NEGATION`

Negates the last thing on the stack as long as it's not a string and pushes
the result onto the stack. For example, a boolean true on the top of the
stack will cause a false to be pushed, while a number 10 would caused a -10
to be pushed.

`GIMME OUTPUT`

Prints to stdout the last thing you requested the program to give you.

## Other Notes


**Note that all lines should end with a semi-colon or bad things might happen**. The
purpose of the semi-colon is the stop the runtime from trying to parse the next
thing as an argument of the thing that is possibly returned at the end of each
line. Another way to get around this would be to put a new line between each
line, but that makes the code ugly. There are some cases where you do not need to put
a semi-colon, but I recommend you put one anyways.

## Possible Extensions
Maybe use APIs to make it able to GIMME more things like Tweets.
