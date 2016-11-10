package gimmehelpers

/* This class holds the current runtime state of a Gimme program run; wraps
 * a program stack object. */
class ProgramState {
  // program stack
  val gimmeStack = new GimmeStack

  ///////////////////
  // State setters //
  ///////////////////

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

  /* Get the current bool (i.e. the last bool object that was gimme'd */
  def getBool = gimmeStack.getLastBool

  ////////////////
  // Binary Ops //
  ////////////////

  /* does stack addition */
  def stackAddition = {
    gimmeStack addElement 
     (gimmeStack.getNthNumber(0) + gimmeStack.getNthNumber(1))
  }

  /* does stack addition given an int */
  def stackAddition(num: Int) = {
    gimmeStack addElement (num + gimmeStack.getNthNumber(0))
  }

  /* does stack subtraction */
  def stackSubtraction = {
    gimmeStack addElement 
     (gimmeStack.getNthNumber(0) - gimmeStack.getNthNumber(1))
  }

  /* does stack subtraction given an int */
  def stackSubtraction(num: Int) = {
    gimmeStack addElement (num - gimmeStack.getNthNumber(0))
  }

  /* does stack multiplication */
  def stackMultiplication = {
    gimmeStack addElement 
     (gimmeStack.getNthNumber(0) * gimmeStack.getNthNumber(1))
  }

  /* does stack multiplication given an int */
  def stackMultiplication(num: Int) = {
    gimmeStack addElement (num * gimmeStack.getNthNumber(0))
  }

  /* does stack division */
  def stackDivision = {
    gimmeStack addElement 
     (gimmeStack.getNthNumber(0) / gimmeStack.getNthNumber(1))
  }

  /* does stack division given an int */
  def stackDivision(num: Int) = {
    gimmeStack addElement (num / gimmeStack.getNthNumber(0))
  }

  ////////////
  // Output //
  ////////////

  /* outputs the top thing on the stack (i.e. last gimme'd) */
  def output = {
    gimmeStack.outputTop
  }

}
