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

  /* The keyword GIMME which starts lines in this language */
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

    /////////////////
    // Conditional //
    /////////////////

    /* the beginning of a conditional */
    def THE(b: BelowWord) = {
      programText finishLine lineBuilder
      lineBuilder.beginConditional

      // find out if looking for a true or a false
      CondContinue
    }

    /* the end of a conditional */
    def THE(a: AboveWord) = {
      programText finishLine lineBuilder
      lineBuilder.endConditional
      lineBuilder setOp OpEnums.G_COND_END
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

  /* continues the parse of a conditional */
  object CondContinue {
    /* conditional looking for a true */
    def TRUE = {
      lineBuilder.conditionalTrueSet
      lineBuilder setOp OpEnums.G_COND_BEGIN
    }

    /* conditional looking for a false */
    def FALSE = {
      lineBuilder.conditionalFalseSet
      lineBuilder setOp OpEnums.G_COND_BEGIN
    }
  }

  def RUN = {
    // finish last line
    programText finishLine lineBuilder
    programText runProgram currentState
  }
}
