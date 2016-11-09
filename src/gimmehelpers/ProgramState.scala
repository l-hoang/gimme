package gimmehelpers

// for the random number generator
import scala.util.Random

class ProgramState {
  val rng = new Random()

  // designates type of object the program state is currently holding
  abstract sealed class CurrentType
  case object NullType extends CurrentType
  case object NumType extends CurrentType
  case object StringType extends CurrentType
  case object BoolType extends CurrentType

  var currentSetting: CurrentType = NullType

  // holds the currently "given" number/string/bool
  var currentNumber: Int = -1
  var currentString: String = ""
  var currentBool: Boolean = false

  // tells you if the program is ready to accept a new "line"; only set to 
  // false if it's the case that the program state is in the middle of
  // parsing a Gimme line e.g. a BETWEEN construction
  var ready = true

  def assertReady = {
    if (!ready) {
      throw new RuntimeException("Program in inconsistent state " +
                                 "(line parse unfinshed?)")
    }
  }

  /* Sets the current number */
  def setNumber(newNumber: Int) = {
    assertReady
    currentNumber = newNumber
    currentSetting = NumType
  }

  /* Sets the current string */
  def setString(newString: String) = {
    assertReady
    currentString = newString
    currentSetting = StringType
  }

  /* Sets the current bool */
  def setBool(newBool: Boolean) = {
    assertReady
    currentBool = newBool
    currentSetting = BoolType
  }

  var lowerBound: Int = 0
  var upperBound: Int = 0
  var lowerBoundSet = false
  var upperBoundSet = false

  def setLowerBound(lb: Int) = {
    ready = false

    lowerBound = lb
    lowerBoundSet = true
  }

  def setUpperBound(ub: Int) = {
    if (!lowerBoundSet) {
      throw new RuntimeException("Setting upper bound without lower bound")
    }

    if (ub < lowerBound) {
      throw new RuntimeException("Setting upper bound that is lower than " +
                                 "lower bound")
    }

    upperBound = ub
    upperBoundSet = true
  }

  def setNumberWithBounds = {
    if (!lowerBoundSet && !upperBoundSet) {
      throw new RuntimeException("Upper/lower bounds not set for bounded num")
    }

    // create a range then select a random number from it
    val chosenRange = (lowerBound to upperBound)

    // between/and construct read, make ready again
    ready = true

    // choose a random number in the range then set it
    this setNumber chosenRange(rng nextInt chosenRange.length)

    // reset vars
    lowerBoundSet = false
    upperBoundSet = false
  }

  /* outputs the last thing that was gimme'd */
  def output = {
    assertReady
    if (currentSetting == NullType) {
      throw new RuntimeException("Trying to output when nothing has been " +
                                 "gimme'd yet")
    }
        
    currentSetting match {
      case NumType => println(currentNumber)
      case StringType => println(currentString)
      case BoolType => println(currentBool)
      case NullType => throw new RuntimeException("Shouldn't get here if nothing is gimme'd")
    }
  }

}
