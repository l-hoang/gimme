package gimme

// gimme helpers
import gimmehelpers._

// for the random number generator
import scala.util.Random
// for disabling compiler warnings about postfixes
import scala.language.postfixOps

/* The class that holds all of the keywords for Gimme + the DSL itself. This
class when extended is what does the "parsing". */
class Gimme {
  // the random number generator we will use to generate things
  val rng = new Random()

  /* helper classes that are used to create program data + run it */
  val currentState = new ProgramState
  val lineBuilder = new ProgramLineBuilder
  val programText = new ProgramText

  /* The keyword GIMME which starts lines in this language; has method
   * calls that correspond to the valid continuations of a Gimme line */
  object GIMME {
    /////////////
    // Numbers //
    /////////////

    /* number case; set a random number */
    def A(n: NumberWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_NUMBER_RANDOM

      NumberContinue
    }

    /* if given an int after A, save it */
    def A(num: Int) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue num
      lineBuilder setOp OpEnums.G_NUMBER
    }

    /* simple apply for expressions (i.e. wrapped in parens) 
     * e.g. GIMME (100 + 3) */
    def apply(num: Int) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue num
      lineBuilder setOp OpEnums.G_NUMBER
    }

    /* continues the parse of a Gimme A Number line */
    object NumberContinue {
      def BETWEEN(lowerBound: Int) = {
        // continue the parse
        new AndContinue(lowerBound)
      }
    }
  
    /* continues the Between/And construction for numbers */
    class AndContinue(low: Int) {
      def AND(upperBound: Int) = {
        lineBuilder.setGimmeRange(low, upperBound)
        lineBuilder setOp OpEnums.G_NUMBER_RANGE
      }
    }

    /////////////
    // Strings //
    /////////////

    /* string case; set a random string */
    def A(s: StringWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_STRING_RANDOM
    }

    /* if given a string, save it */
    def A(s: String) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue s
      lineBuilder setOp OpEnums.G_STRING

      StringContinue
    }

    /* if an AND is found, that means we should also print the string
     * out after adding it to program state */
    object StringContinue {
      def AND(o: OutputWord) = {
        lineBuilder setOp OpEnums.G_STRING_OUTPUT
      }
    }

    //////////////
    // Booleans //
    //////////////

    /* bool case; set a random bool */
    def A(b: BoolWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_BOOL_RANDOM
    }

    /* if given a bool, save it */
    def A(b: Boolean) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue b
      lineBuilder setOp OpEnums.G_BOOL
    }

    /* handles the 2 cases where people may put TRUE or FALSE instead of
     * Scala recognized true and false */
    def A(t: TrueWord) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue true
      lineBuilder setOp OpEnums.G_BOOL
    }

    def A(f: FalseWord) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue false
      lineBuilder setOp OpEnums.G_BOOL
    }

    /* allows for GIMME (1 > 3) or any other parenthesized Scala expression */
    def apply(b: Boolean) = {
      programText finishLine lineBuilder
      lineBuilder setGimmeValue b
      lineBuilder setOp OpEnums.G_BOOL
    }

    /* TODO add TRUE/FALSE? */

    ///////////////////////////
    // Conditional/Functions //
    ///////////////////////////

    /* the beginning of a conditional/functions */
    def THE(b: BelowWord) = {
      programText finishLine lineBuilder

      // find out if looking for a true or a false (or a function...)
      TheContinue
    }

    /* the end of a conditional */
    def THE(a: AboveWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_COND_END
    }

    def THE(e: EndWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_COND_END
      
      EndContinue
    }

    /* continues the parse of a conditional */
    object TheContinue {
      /* conditional looking for a true */
      def IF(t: TrueWord) {
        lineBuilder.conditionalTrueSet
        lineBuilder setOp OpEnums.G_COND_BEGIN
      }
  
      /* conditional looking for a false */
      def IF(t: FalseWord) {
        lineBuilder.conditionalFalseSet
        lineBuilder setOp OpEnums.G_COND_BEGIN
      }

      /* save the function name to the line builder and tell it
       * the next line is a line builder */
      def AS(functionName: String) {
        lineBuilder setGimmeValue functionName
        lineBuilder setOp OpEnums.G_FUNCTION_BEGIN
      }
    }

    object EndContinue {
      /* end of function construction */
      def OF(functionName: String) {
        lineBuilder setGimmeValue functionName
        lineBuilder setOp OpEnums.G_FUNCTION_END
      }
    }

    /////////////////
    // Comparators //
    /////////////////

    /* The comparison commands for the language */

    def GREATER(t: ThanWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_GREATER

      compareContinue
    }

    def LESS(t: ThanWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_LESS

      compareContinue
    }

    def EQUAL(t: ToWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_EQUAL

      compareContinue
    }

    object compareContinue {
      def EQUAL(t: ToWord) = {
        lineBuilder.getOp match {
          case OpEnums.G_GREATER => lineBuilder setOp OpEnums.G_GREATER_EQUAL
          case OpEnums.G_LESS => lineBuilder setOp OpEnums.G_LESS_EQUAL
          case _ =>
            throw new RuntimeException("comparator EQUAL TO continues greater " +
                                       "than or less than only")
        }

        compareContinue
      }

      def NUMBER(num: Int) = {
        lineBuilder setGimmeValue num

        lineBuilder.getOp match {
          case OpEnums.G_GREATER => lineBuilder setOp OpEnums.G_GREATER_N
          case OpEnums.G_LESS => lineBuilder setOp OpEnums.G_LESS_N
          case OpEnums.G_GREATER_EQUAL => 
            lineBuilder setOp OpEnums.G_GREATER_EQUAL_N
          case OpEnums.G_LESS_EQUAL => lineBuilder setOp OpEnums.G_LESS_EQUAL_N
          case OpEnums.G_EQUAL => lineBuilder setOp OpEnums.G_EQUAL_N
          case _ =>
            throw new RuntimeException("in comparator parse; only takes compare "+
                                       "ops")
        }
      }
    }

    //////////////////
    // Loop (While) //
    //////////////////

    /* beginning of loop end or beginning */
    def CONTINUOUSLY(t: TheWord) = {
      programText finishLine lineBuilder
      LoopBelowOrAbove
    }

    object LoopBelowOrAbove {
      /* loop beginning */
      def BELOW = {
        lineBuilder setOp OpEnums.G_LOOP_BEGIN
      }

      /* loop end */
      def ABOVE = {
        lineBuilder setOp OpEnums.G_LOOP_END
      }
    }

    //////////////////
    // Loop (Break) //
    //////////////////

    /* parses a break statement construct */
    def AN(e: ExitWord) = {
        programText finishLine lineBuilder
        lineBuilder setOp OpEnums.G_BREAK

        BreakContinue
    }

    /* continuation for the break statement for true/false variants */
    object BreakContinue {
      def IF(t: TrueWord) = lineBuilder setOp OpEnums.G_BREAK_TRUE
      def IF(t: FalseWord) = lineBuilder setOp OpEnums.G_BREAK_FALSE
    }

    ///////////////////////
    // Binary operations //
    ///////////////////////

    /* What follows are binary operations that are done using the last 2
     * elements that were gimme'd unless the WITH variant is used in which
     * case 1 of the numbers used will be user provided. */

    def ADDITION = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_ADDITION
    }

    def ADDITION(w: WithWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_ADDITION_WITH

      BinContinue
    }

    def SUBTRACTION = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_SUBTRACTION
    }

    def SUBTRACTION(w: WithWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_SUBTRACTION_WITH

      BinContinue
    }

    def MULTIPLICATION = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_MULTIPLICATION
    }

    def MULTIPLICATION(w: WithWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_MULTIPLICATION_WITH

      BinContinue
    }

    def DIVISION = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_DIVISION
    }

    def DIVISION(w: WithWord) = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_DIVISION_WITH

      BinContinue
    }

    /* continues parsing the OP WITH <number> line */
    object BinContinue {
      def NUMBER(num: Int) {
        lineBuilder setGimmeValue num
      }
    }

    //////////////
    // Negation //
    //////////////

    /* This will negate the last thing stored on the stack */

    def NEGATION = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_NEGATION
    }

    ////////////
    // Output //
    ////////////

    /* outputs the last thing that was gimme'd */
    def OUTPUT = {
      programText finishLine lineBuilder
      lineBuilder setOp OpEnums.G_OUTPUT
    }
  }

  def RUN = {
    // finish last line
    programText finishLine lineBuilder
    // run the program starting from the first GIMME line parsed
    programText runProgram currentState
  }
}
