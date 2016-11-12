package gimmehelpers

/* Builds each line that is "parsed" from a Gimme program. */
class ProgramLineBuilder {
  import OpEnums._
  import GimmeOpLines._

  /////////////////////////////////
  // Metadata for building lines //
  /////////////////////////////////

  // holds last operation
  var currentOp: GimmeOpEnum = G_NONE

  // marker for first line
  var firstLine = true

  /* set the next op */
  def setOp(newOp: GimmeOpEnum) = {
    currentOp = newOp
  }


  ///////////////////////////////////////
  // Setters for what has been gimme'd //
  ///////////////////////////////////////

  // current values (not necessarily reset every line)
  var currentNumber = -1
  var currentString = ""
  var currentBool = false

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

  // these are used by gimme number range
  var low = -1
  var high = -1

  /* set the range */
  def setGimmeRange(l:Int, h:Int) {
    low = l
    high = h
  }

  var condBool = false 

  /* for setting true or false for conditionals */
  def conditionalTrueSet = {
    condBool = true
  }

  def conditionalFalseSet = {
    condBool = false
  }


  //////////////////////////////////////
  // Line builder returns a line here //
  //////////////////////////////////////

  /* Given the metadata that should be set by the DSL "parser", return a line */
  def returnLine = {
    var lineToReturn: GimmeOp = GimmeNone

    currentOp match {
      case G_NUMBER => lineToReturn = GimmeNum(currentNumber)
      case G_NUMBER_RANDOM => lineToReturn = GimmeNumRandom()
      case G_NUMBER_RANGE => lineToReturn = GimmeNumRange(low, high)
      case G_STRING => lineToReturn = GimmeString(currentString)
      case G_STRING_RANDOM => lineToReturn = GimmeStringRandom()
      case G_STRING_OUTPUT => lineToReturn = GimmeStringOutput(currentString)
      case G_BOOL => lineToReturn = GimmeBool(currentBool)
      case G_BOOL_RANDOM => lineToReturn = GimmeBoolRandom()
      case G_OUTPUT => lineToReturn = GimmeOutput()
      case G_COND_BEGIN => lineToReturn = GimmeCondBegin(-1, condBool)
      case G_COND_END => lineToReturn = GimmeCondEnd()
      case G_LOOP_BEGIN => lineToReturn = GimmeLoopBegin(-1)
      case G_LOOP_END => lineToReturn = GimmeLoopEnd(-1)
      case G_BREAK => lineToReturn = GimmeBreak(-1)
      case G_ADDITION => lineToReturn = GimmeAddition()
      case G_ADDITION_WITH => lineToReturn = GimmeAdditionWith(currentNumber)
      case G_SUBTRACTION => lineToReturn = GimmeSubtraction()
      case G_SUBTRACTION_WITH => 
        lineToReturn = GimmeSubtractionWith(currentNumber)
      case G_MULTIPLICATION => lineToReturn = GimmeMultiplication()
      case G_MULTIPLICATION_WITH => 
        lineToReturn = GimmeMultiplicationWith(currentNumber)
      case G_DIVISION => lineToReturn = GimmeDivision()
      case G_DIVISION_WITH => lineToReturn = GimmeDivisionWith(currentNumber)
      case G_NONE =>
        if (!firstLine)
          throw new RuntimeException("Adding an empty line")
        else
          // first line meaning nothing supposed to be there
          firstLine = false
    }

    // reset everything in prep for next line
    // reset op
    currentOp = G_NONE

    lineToReturn
  }

}


