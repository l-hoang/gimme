package gimme

// for the random number generator
import scala.util.Random
// for disabling compiler warnings about postfixes
import scala.language.postfixOps

/* The class that holds all of the keywords for Gimme */
class Gimme {
  // the random number generator we will use to generate things
  val rng = new Random()

  // holds the currently "given" number/string/bool
  var currentNumber: Int = -1
  var currentString: String = ""
  var currentBool: Boolean = false

  // tell you if the variable currently stored in the "current" vars are 
  // to be used
  var numberOn = false
  var stringOn = false
  var boolOn = false

  /* Sets the current number and nulls everything else */
  def setNumber(newNumber: Int) = {
    currentNumber = newNumber

    numberOn = true
    stringOn = false
    boolOn = false
  }

  /* Sets the current string and nulls everything else */
  def setString(newString: String) = {
    currentString = newString

    numberOn = false
    stringOn = true
    boolOn = false
  }

  /* Sets the current bool and nulls everything else */
  def setBool(newBool: Boolean) = {
    currentBool = newBool

    numberOn = false
    stringOn = false
    boolOn = true
  }

  /* The keyword GIMME which starts lines in this language */
  object GIMME {
    /* generates a random number from 0 to 100 inclusive and saves it*/
    def NUMBER = {
        val result = rng nextInt 100
        setNumber(result)
    }

    /* gimme a random bool */
    def BOOL = {
      val result = rng nextInt 1

      // depending on 0 or 1, set true or false
      result match {
        case 0 =>
          setBool(false)
        case 1 =>
          setBool(true)
        case _ => 
          throw new RuntimeException("GIMME BOOL should only " +
                                     "generate 0 or 1")
      }
    }

    /* gives me (at the moment) a 5 character string */
    def STRING = {
      val char1 = rng nextPrintableChar() toString
      val char2 = rng nextPrintableChar() toString
      val char3 = rng nextPrintableChar() toString
      val char4 = rng nextPrintableChar() toString
      val char5 = rng nextPrintableChar() toString
        
      val finalString = char1 + char2 + char3 + char4 + char5

      setString(finalString)
    }

    /* outputs the last thing that was gimme'd */
    def OUTPUT = {
      if (!numberOn && !stringOn && !boolOn) {
        throw new RuntimeException("Trying to output when nothing has been " +
                                   "gimme'd yet")
      }
        
      if (numberOn) println(currentNumber)
      else if (stringOn) println(currentString)
      else if (boolOn) println(currentBool)
      else throw new RuntimeException("Shouldn't get here if nothing is gimme'd")
    }
  }
}
