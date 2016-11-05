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

    /* number case; set a random number */
    def A(n: NumberWord) = {
      currentState setNumber (rng nextInt 100)

      NumberContinue
    }

    /* string case; set a random string */
    def A(s: StringWord) = {
      val char1 = rng.nextPrintableChar toString
      val char2 = rng.nextPrintableChar toString
      val char3 = rng.nextPrintableChar toString
      val char4 = rng.nextPrintableChar toString
      val char5 = rng.nextPrintableChar toString
        
      val finalString = char1 + char2 + char3 + char4 + char5

      currentState setString finalString
    }

    /* bool case; set a random bool */
    def A(b: BoolWord) = {
      currentState setBool rng.nextBoolean
    }

    /* if given an int, save it */
    def A(num: Int) = {
      currentState setNumber num
    }

    /* if given a string, save it */
    def A(s: String) = {
      currentState setString s
    }

    /* if given a bool, save it */
    def A(b: Boolean) = {
      currentState setBool b
    }

    /* outputs the last thing that was gimme'd */
    def OUTPUT = {
      currentState.output
    }
  }

  /* continues the parse of a Gimme A Number line */
  object NumberContinue {
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
