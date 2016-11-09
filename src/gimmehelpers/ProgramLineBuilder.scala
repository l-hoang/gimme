package gimmehelpers

/* Builds each line that is "parsed" from a Gimme program. */
class ProgramLineBuilder {
  import OpEnums._
  import GimmeOpLines._

  // holds last operation
  var currentOp: GimmeOpEnum = G_NONE

  // current values
  var currentNumber: Int = -1
  var currentString: String = ""
  var currentBool: Boolean = false

  /* set the next op */
  def setOp(newOp: GimmeOpEnum) = {
    currentOp = newOp
  }

  /* set ints */
  def setGimmeValue(newValue: Int) {
    currentNumber = newValue
  }

  /* set strings */
  def setGimmeValue(newValue: String) {
    currentString = newValue
  }

  /* set bools */
  def setGimmeValue(newValue: Boolean) {
    currentBool = newValue
  }

  def saveLastLine = {
    currentOp match {
      case G_NUMBER =>
        // save num
        GimmeNum(currentNumber)
      case G_STRING =>
        GimmeString(currentString)
      case G_BOOL =>
        GimmeBool(currentBool)
      case G_NONE =>
        // complain/die
    }

    // reset everything in prep for next line

    // reset op
    currentOp = G_NONE

    // reset values
    currentNumber = -1
    currentString = ""
    currentBool = false
  }

}


