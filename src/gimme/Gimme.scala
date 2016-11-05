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
        val result = rng nextInt 100
        currentState setNumber result
    }

    /* gimme a random bool */
    def BOOL = {
      val result = rng nextInt 1

      // depending on 0 or 1, set true or false
      result match {
        case 0 =>
          currentState setBool false
        case 1 =>
          currentState setBool true
        case _ => 
          throw new RuntimeException("GIMME BOOL should only " +
                                     "generate 0 or 1")
      }
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
}
