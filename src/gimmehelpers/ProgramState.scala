package gimmehelpers

/* This class holds the current runtime state of a Gimme program run; wraps
 * a program stack object. */
class ProgramState {
  // program stack
  val gimmeStack = new GimmeStack

  /* Sets the current number */
  def setNumber(newNumber: Int) = {
    gimmeStack addElement newNumber
  }

  /* Sets the current string */
  def setString(newString: String) = {
    gimmeStack addElement newString
  }

  /* Sets the current bool */
  def setBool(newBool: Boolean) = {
    gimmeStack addElement newBool
  }

  /* Get the current bool */
  def getBool = gimmeStack.getLastBool

  /* outputs the top thing on the stack (i.e. last gimme'd) */
  def output = {
    gimmeStack.outputTop
  }
}
