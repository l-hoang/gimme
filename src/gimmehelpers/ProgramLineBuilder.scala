package gimmehelpers

/* Builds each line that is "parsed" from a Gimme program. */
class ProgramLineBuilder {
  import OpEnums._
  import GimmeOpLines._

  // holds last operation
  var currentOp: GimmeOpEnum = G_NONE

  // current values (not necessarily reset every line)
  var currentNumber = -1
  var currentString = ""
  var currentBool = false

  // these are used by gimme number range
  var low = -1
  var high = -1

  // marker for first line
  var firstLine = true

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

  /* set the range */
  def setGimmeRange(l:Int, h:Int) {
    low = l
    high = h
  }

  def returnLine = {
    var lineToReturn: GimmeOp = GimmeNone

    currentOp match {
      case G_NUMBER =>
        lineToReturn = GimmeNum(currentNumber)

      case G_NUMBER_RANDOM =>
        lineToReturn = GimmeNumRandom()

      case G_NUMBER_RANGE =>
        lineToReturn = GimmeNumRange(low, high)

      case G_STRING =>
        lineToReturn = GimmeString(currentString)

      case G_STRING_RANDOM =>
        lineToReturn = GimmeStringRandom()

      case G_BOOL =>
        lineToReturn = GimmeBool(currentBool)

      case G_BOOL_RANDOM =>
        lineToReturn = GimmeBoolRandom()

      case G_OUTPUT =>
        lineToReturn = GimmeOutput()

      case G_NONE =>
        if (!firstLine) {
          throw new RuntimeException("Adding an empty line")
        } else {
          // first line meaning nothing supposed to be there
          firstLine = false
        }
    }

    // reset everything in prep for next line
    // reset op
    currentOp = G_NONE

    lineToReturn
  }

}


