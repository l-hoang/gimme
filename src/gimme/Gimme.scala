package gimme

// for the random number generator
import scala.util.Random
// for disabling compiler warnings about postfixes
import scala.language.postfixOps

/* The class that holds all of the keywords for Gimme */
class Gimme {
  // the random number generator we will use to generate things
  val rng = new Random()

  val currentState = new ProgramState

  /* The keyword GIMME which starts lines in this language */
  object GIMME {
    /* generates a random number from 0 to 100 inclusive and saves it*/
    def NUMBER = {
      currentState setNumber (rng nextInt 100)
    }

    // class that represents the NUMBER keyword
    abstract sealed class NumberWord
    object NUMBER extends NumberWord

    /* the A keyword marks the beginning of a gimme/between construct */
    def A(n: NumberWord) = {
      new NumberContinue
    }

    /* gimme a random bool */
    def BOOL = {
      currentState setBool rng.nextBoolean
    }

    /* gives me (at the moment) a 5 character string */
    def STRING = {
      val char1 = rng.nextPrintableChar toString
      val char2 = rng.nextPrintableChar toString
      val char3 = rng.nextPrintableChar toString
      val char4 = rng.nextPrintableChar toString
      val char5 = rng.nextPrintableChar toString
        
      val finalString = char1 + char2 + char3 + char4 + char5

      currentState setString finalString
    }

    /* outputs the last thing that was gimme'd */
    def OUTPUT = {
      currentState.output
    }
  }


  /* continues the parse of a Gimme A Number line */
  class NumberContinue {
    def BETWEEN(lowerBound: Int) = {
      currentState setLowerBound lowerBound

      // continue the parse
      AndContinue
    }
  }

  /* continues the Between/And construction for numbers */
  object AndContinue {
    def AND(upperBound: Int) = {
      currentState setUpperBound upperBound

      // finalize by setting number within our bounds
      currentState setNumberWithBounds
    }
  }
}
