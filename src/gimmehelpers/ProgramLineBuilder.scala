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

  // says if this line (result) should be printed
  var shouldPrint = false

  // says if binary op with construction is done
  var withDone = false


  /* set the next op */
  def setOp(newOp: GimmeOpEnum) = {
    currentOp = newOp
  }

  /* returns the currently set op */
  def getOp = currentOp

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

  /* say this line should be printed */
  def setOutput = shouldPrint = true

  /* with construction complete */
  def withComplete = withDone = true

  //////////////////////////////////////
  // Line builder returns a line here //
  //////////////////////////////////////

  /* Given the metadata that should be set by the DSL "parser", return a line
   * as a case class. */
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
      
      case G_FUNCTION_BEGIN => lineToReturn = GimmeFunctionBegin(currentString,
                                                                 -1)
      case G_FUNCTION_END => lineToReturn = GimmeFunctionEnd(currentString)

      case G_FUNCTION_CALL => lineToReturn = GimmeFunctionCall(currentString)

      case G_GREATER => lineToReturn = GimmeGreater()
      case G_GREATER_EQUAL => lineToReturn = GimmeGreaterEqual()
      case G_LESS => lineToReturn = GimmeLess()
      case G_LESS_EQUAL => lineToReturn = GimmeLessEqual()
      case G_EQUAL => lineToReturn = GimmeEqual()

      case G_GREATER_N => lineToReturn = GimmeGreaterNum(currentNumber)
      case G_GREATER_EQUAL_N => lineToReturn = GimmeGreaterEqualNum(currentNumber)
      case G_LESS_N => lineToReturn = GimmeLessNum(currentNumber)
      case G_LESS_EQUAL_N => lineToReturn = GimmeLessEqualNum(currentNumber)
      case G_EQUAL_N => lineToReturn = GimmeEqualNum(currentNumber)

      case G_LOOP_BEGIN => lineToReturn = GimmeLoopBegin(-1)
      case G_LOOP_END => lineToReturn = GimmeLoopEnd(-1)

      case G_BREAK => lineToReturn = GimmeBreak(-1)
      case G_BREAK_TRUE => lineToReturn = GimmeBreakTrue(-1)
      case G_BREAK_FALSE => lineToReturn = GimmeBreakFalse(-1)

      case G_ADDITION => lineToReturn = GimmeAddition()
      case G_ADDITION_WITH => 
        if (!withDone) {
          throw new RuntimeException("With construct didn't specify a number")
        }
        lineToReturn = GimmeAdditionWith(currentNumber)
      case G_SUBTRACTION => lineToReturn = GimmeSubtraction()
      case G_SUBTRACTION_WITH => 
        if (!withDone) {
          throw new RuntimeException("With construct didn't specify a number")
        }
        lineToReturn = GimmeSubtractionWith(currentNumber)
      case G_MULTIPLICATION => lineToReturn = GimmeMultiplication()
      case G_MULTIPLICATION_WITH => 
        if (!withDone) {
          throw new RuntimeException("With construct didn't specify a number")
        }
        lineToReturn = GimmeMultiplicationWith(currentNumber)
      case G_DIVISION => lineToReturn = GimmeDivision()
      case G_DIVISION_WITH => 
        if (!withDone) {
          throw new RuntimeException("With construct didn't specify a number")
        }
        lineToReturn = GimmeDivisionWith(currentNumber)

      case G_NEGATION => lineToReturn = GimmeNegation()

      case G_NONE =>
        if (!firstLine)
          throw new RuntimeException("Adding an empty line")
        else
          // first line meaning nothing supposed to be there
          firstLine = false
    }

    // if AND OUTPUT was found, this will be true, so set the line to a print
    // line
    if (shouldPrint) {
      lineToReturn.doPrint
    }

    /* reset everything in prep for next line */
    // reset op
    currentOp = G_NONE
    // reset print
    shouldPrint = false
    // reset with done
    withDone = false

    lineToReturn
  }

}
