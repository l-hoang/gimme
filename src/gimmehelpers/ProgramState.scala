package gimmehelpers

/* This class holds the current runtime state of a Gimme program run */
class ProgramState {
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

  /* Sets the current number */
  def setNumber(newNumber: Int) = {
    currentNumber = newNumber
    currentSetting = NumType
  }

  /* Sets the current string */
  def setString(newString: String) = {
    currentString = newString
    currentSetting = StringType
  }

  /* Sets the current bool */
  def setBool(newBool: Boolean) = {
    currentBool = newBool
    currentSetting = BoolType
  }

  /* outputs the last thing that was gimme'd */
  def output = {
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
