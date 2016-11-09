package gimmehelpers

import java.util.ArrayDeque

/* Represents a scala stack object */
class GimmeStackObject {
  ///////////////////
  // Element types //
  ///////////////////

  abstract sealed class CurrentType
  case object NullType extends CurrentType
  case object NumType extends CurrentType
  case object StringType extends CurrentType
  case object BoolType extends CurrentType

  //////////////
  // Metadata //
  //////////////

  var objectInit = false
  var objectType: CurrentType = NullType

  var num = -1
  var string = ""
  var bool = false

  ////////////////
  // Assertions //
  ////////////////

  def assertInit = {
    if (!objectInit) {
      throw new RuntimeException("trying to call an op that needs an "+
                                 "initialized object")
    }
  }

  def assertNotInit = {
    if (objectInit) {
      throw new RuntimeException("trying to overwrite an already init'd " +
                                 "stack object")
    }
  }

  /////////////////////////
  // Object initializers //
  /////////////////////////

  def initObject(element: Int) {
    num = element
    objectType = NumType
    objectInit = true
  }

  def initObject(element: String) {
    string = element
    objectType = StringType
    objectInit = true
  }

  def initObject(element: Boolean) {
    bool = element
    objectType = BoolType
    objectInit = true
  }

  ////////////////////////
  // "Public" functions //
  ////////////////////////

  /* returns the type of this object */
  def getType = {
    assertInit
    objectType
  }

  /* outputs to stdout the element represented by this object */
  def outputElement = {
    assertInit
    
    objectType match {
      case NumType => println(num)
      case StringType => println(string)
      case BoolType => println(bool)
      case NullType => throw new RuntimeException("Shouldn't get here if " +
                                                  "assertInit worked")
    }
  }
}

/* Represents the stack of GimmeObjects (i.e. runtime memory of things that
have been gimme'd. Wrapper for Java's ArrayDeque */
class GimmeStack {
  //////////////
  // Metadata //
  //////////////
  val StackSize = 20
  val programStack = new ArrayDeque[GimmeStackObject]
  // easy access to last bool object that was added (well, the value)
  var lastBool = false

  ////////////////////
  // Element adders //
  ////////////////////

  /* the following addElement calls create a stack object then add it to the 
   * stack */

  def addElement(num: Int) = {
    val newStackObject = new GimmeStackObject
    newStackObject initObject num
    this addToStack newStackObject
  }

  def addElement(str: String) = {
    val newStackObject = new GimmeStackObject
    newStackObject initObject str
    this addToStack newStackObject
  }

  def addElement(bool: Boolean) = {
    val newStackObject = new GimmeStackObject
    newStackObject initObject bool
    this addToStack newStackObject
    // track the last bool as well
    lastBool = bool
  }

  /* add a stack object to our stack; first checks to see if stack is full: if
  so, clear up space */
  def addToStack(newStackObject: GimmeStackObject) = {
    // if deque has certain amount of elements, then boot last element so
    // we have a finite stack
    if (programStack.size >= StackSize) {
      // last = bottom of the stack
      programStack.removeLast
    }
    
    programStack push newStackObject
  }

  ///////////////
  // Accessors //
  ///////////////

  /* prints the element at the top of the stack, i.e. the most recent 
   * element */
  def outputTop = {
    if (programStack.isEmpty()) {
      throw new RuntimeException("trying to output when nothing gimme'd")
    }

    programStack.peekFirst.outputElement
  }

  /* returns the last bool that was added to this stack */
  def getLastBool = lastBool
}
