package gimmehelpers


// import the case classes that represent gimme operations
// located in program line builder
import GimmeOpLines._
import OpEnums._

import scala.collection.mutable.HashMap
import java.util.ArrayDeque
import scala.util.Random

/* This class holds the "text" of the program, i.e. the actual program split
 * into lines. It also has the runtime method for running the program. */
class ProgramText {
  /////////////////////////
  // ProramText Metadata //
  /////////////////////////

  // current line number
  var currentLineNumber = 1;

  // random number generator used for various purposes
  val rng = new Random()
  // map of gimme lines 
  val gimmeLines = new HashMap[Int, GimmeOp]
  // map from function names to the beginning of the function (line number)
  val gimmeFunctions = new HashMap[String, Int]

  // holds current conditional beginning line numbers
  val conditionalStack = new ArrayDeque[Int]
  // holds current loop beginning line numbers
  val loopStack = new ArrayDeque[Int]
  // holds all breaks (line numbers) that exist in a loop (for later update)
  val breakStack = new HashMap[Int, ArrayDeque[Int]]
  // function stack that tells us which functions aren't closed yet
  val functionStack = new ArrayDeque[String]
  // function stack that holds functiong beginning line number
  val functionStackNumbers = new ArrayDeque[Int]

  ///////////////////////////
  // Line adding functions //
  ///////////////////////////

  /* add a line to our current lines, then increment the line counter by 1 */
  def addLine(line: GimmeOp) = {
    gimmeLines += Tuple2(currentLineNumber, line)
    currentLineNumber += 1
  }

  def finishLine(lineBuilder: ProgramLineBuilder) = {
    // grab the line from the builder
    val line = lineBuilder.returnLine

    line match {
      case GimmeNone => // do nothing

      ///////////////////////
      // Conditional lines //
      ///////////////////////

      case GimmeCondBegin(_, _) => 
        // push current line onto cond stack, then add the line
        conditionalStack push currentLineNumber
        addLine(line)

      case GimmeCondEnd() => 
        // create an updated cond beginning, add into the map
        val condBegin = conditionalStack.pop
        val oldCondLine = gimmeLines(condBegin).asInstanceOf[GimmeCondBegin]

        gimmeLines.put(condBegin, GimmeCondBegin(currentLineNumber,
                                    oldCondLine.trueOrFalse))
        
        // add the cond end
        addLine(line)

      ////////////////////
      // Function lines //
      ////////////////////

      case GimmeFunctionBegin(functionName, _) =>
        // push function onto unclosed functions, check if undeclared,
        // then add to lines after saving this line number
        functionStack push functionName

        if (gimmeFunctions contains functionName) {
          throw new RuntimeException("Redefining an existing function")
        }

        functionStackNumbers push currentLineNumber
        // save function binding to map
        gimmeFunctions.put(functionName, currentLineNumber + 1)

        addLine(line)

      case GimmeFunctionEnd(functionName) =>
        // see if this closes the currently "active" function; if so,
        // then add else die
        if (functionStack.peek == functionName) {
          functionStack.pop

          // revise function beginning line with the end of the function
          val functionBegin = functionStackNumbers.pop 
          gimmeLines.put(functionBegin, GimmeFunctionBegin(functionName,
                                                         currentLineNumber + 1))
          addLine(line)
        } else {
          throw new RuntimeException("Ending the wrong function/invalid func end")
        }

      ////////////////
      // Loop lines //
      ////////////////

      case GimmeLoopBegin(_) => 
        // push current line onto loop stack
        loopStack push currentLineNumber

        // create a "queue" for breaks
        breakStack.put(currentLineNumber, new ArrayDeque) 

        // add the line
        addLine(line)

      case GimmeLoopEnd(_) => 
        val loopBegin = loopStack.pop
        val jumpPoint = currentLineNumber + 1

        // replace old loop begin with updated version that has the
        // line after the end of the loop
        gimmeLines.put(loopBegin, GimmeLoopBegin(jumpPoint))

        // go back and update all the break lines on the queue
        val queueBreaks = breakStack(loopBegin).iterator 
        // replace the break line we added before with an updated one that
        // has the line to jump to on break
        while (queueBreaks.hasNext) {
          val lineToCheck = queueBreaks.next
          val oldLine = gimmeLines(lineToCheck)

          oldLine match {
            case GimmeBreak(_) => 
              gimmeLines.put(lineToCheck, GimmeBreak(jumpPoint))
            case GimmeBreakTrue(_) =>
              gimmeLines.put(lineToCheck, GimmeBreakTrue(jumpPoint))
            case GimmeBreakFalse(_) =>
              gimmeLines.put(lineToCheck, GimmeBreakFalse(jumpPoint))
            case _ =>
              throw new RuntimeException("line in break stack should be a " +
                                         "break line")
          }
        }

        // save loop end with beginning of loop 
        addLine(GimmeLoopEnd(loopBegin))

      ///////////
      // Break //
      ///////////

      case GimmeBreak(_) =>
        // push our line onto the "to handle" stack for this loop (we don't
        // currently know where to go on break; to be updated when we hit
        // the loop end
        breakStack(loopStack.peek) push currentLineNumber
        // add line (will be changed later)
        addLine(line)

      /* following 2 same as above for breaks (just adding diff line type) */
      case GimmeBreakTrue(_) =>
        breakStack(loopStack.peek) push currentLineNumber
        addLine(line)

      case GimmeBreakFalse(_) =>
        breakStack(loopStack.peek) push currentLineNumber
        addLine(line)

      /////////////////////
      // Everything else //
      /////////////////////

      // no special handling is required for other lines 
      case _ => addLine(line)
    }
  }

  ///////////////////////////////
  // Program running functions //
  ///////////////////////////////

  /* make sure there isn't anything malformed that may exist
   * from the program parse (e.g. an incomplete conditional) */
  def validateConsistency = {
    // make sure the conditional stack is empty
    if (!conditionalStack.isEmpty) {
      throw new RuntimeException("There are unclosed conditionals\n")
    }
    if (!loopStack.isEmpty) {
      throw new RuntimeException("There are unclosed loops\n")
    }
    if (!functionStack.isEmpty) {
      throw new RuntimeException("There are functions not closed\n")
    }
    if (!functionStackNumbers.isEmpty) {
      throw new RuntimeException("There are functions not closed (numbers)\n")
    }


    // TODO add more checks as I make the program more complex
  }

  /* runs the program starting from line 1 */
  def runProgram(currentState: ProgramState) = {
    // make sure program parse was consistent
    validateConsistency

    var runtimeLineNumber = 1

    // loop as long as we are at a valid line number
    while (runtimeLineNumber < currentLineNumber) {
      // grab the line
      val currentLine = gimmeLines(runtimeLineNumber)
      var lineJump = false

      /* based on what kind of line we are currently on, take the appropriate
       * action and apply it to the passed in program state */
      currentLine match {
        //////////////////
        // Basic Gimmes //
        //////////////////
        case GimmeNum(num) => currentState setNumber num

        case GimmeNumRandom() => currentState setNumber (rng nextInt 100)

        case GimmeNumRange(low, high) => 
          // create a range then select a random number from it
          val chosenRange = (low to high)
          currentState setNumber chosenRange(rng nextInt chosenRange.length)

        case GimmeString(str) => currentState setString str

        case GimmeStringRandom() => currentState setString 
          ((rng.nextPrintableChar.toString) + (rng.nextPrintableChar.toString) + 
          (rng.nextPrintableChar.toString) + (rng.nextPrintableChar.toString) + 
          (rng.nextPrintableChar.toString))

        case GimmeStringOutput(str) => 
          currentState setString str
          currentState.output

        case GimmeBool(bool) => currentState setBool bool

        case GimmeBoolRandom() => currentState setBool rng.nextBoolean

        /////////////////
        // Conditional //
        /////////////////

        case GimmeCondBegin(lineEnd, tOrF) =>
          // if condition is met, then we just go to the next line
          // otherwise we jump to the end of the line
          if (currentState.getBool != tOrF) {
            // jump to end of condition
            runtimeLineNumber = lineEnd
            // set this so we don't increment the line
            lineJump = true
          }

        case GimmeCondEnd() => // do nothing, just a cond end line

        ///////////////////
        // Function Defs //
        ///////////////////

        // if a function declaration, just skip over it
        case GimmeFunctionBegin(_, jumpLocation) =>
          runtimeLineNumber = jumpLocation
          lineJump = true

        case GimmeFunctionEnd(_) =>
          // make sure the func call stack is non-empty
          if (currentState.funcStackEmpty) {
            throw new RuntimeException("Returning from function when none called")
          }

          runtimeLineNumber = currentState.popFuncStack
          lineJump = true

        ///////////////////
        // Function Call //
        ///////////////////

        case GimmeFunctionCall(functionName) => 
          // grab the line to jump to
          if (!(gimmeFunctions contains functionName)) {
            throw new RuntimeException("calling an undefined function")
          }

          // save next line number to jump back to later
          currentState pushFuncStack (runtimeLineNumber + 1)

          // jump to beginning of function
          runtimeLineNumber = gimmeFunctions(functionName)
          lineJump = true

        /////////////////
        // Comparators //
        /////////////////

        case GimmeGreater() => currentState.greater
        case GimmeGreaterEqual() => currentState.greaterEqual
        case GimmeLess() => currentState.less
        case GimmeLessEqual() => currentState.lessEqual
        case GimmeEqual() => currentState.equal

        case GimmeGreaterNum(num) => currentState greater num
        case GimmeGreaterEqualNum(num) => currentState greaterEqual num
        case GimmeLessNum(num) => currentState less num
        case GimmeLessEqualNum(num) => currentState lessEqual num
        case GimmeEqualNum(num) => currentState equal num

        //////////
        // Loop //
        //////////

        case GimmeLoopBegin(_) => // do nothing; just a marker

        case GimmeLoopEnd(lineLoopBeginning) => 
          // jump to beginning of loop
          runtimeLineNumber = lineLoopBeginning
          lineJump = true

        ///////////
        // Break //
        ///////////

        /* break stores where we need to jump, so we can just jump to the
         * correct location */
        case GimmeBreak(jumpLocation) => 
          runtimeLineNumber = jumpLocation
          lineJump = true

        case GimmeBreakTrue(jumpLocation) => 
          // only jump if true else do nothing
          if (currentState.getBool) {
            runtimeLineNumber = jumpLocation
            lineJump = true
          }

        case GimmeBreakFalse(jumpLocation) => 
          // only jump if false else do nothing
          if (!currentState.getBool) {
            runtimeLineNumber = jumpLocation
            lineJump = true
          }

        ////////////////
        // Binary Ops //
        ////////////////

        case GimmeAddition() => currentState.stackAddition

        case GimmeAdditionWith(num) => currentState stackAddition num

        case GimmeSubtraction() => currentState.stackSubtraction

        case GimmeSubtractionWith(num) => currentState stackSubtraction num

        case GimmeMultiplication() => currentState.stackMultiplication

        case GimmeMultiplicationWith(num) => currentState stackMultiplication num

        case GimmeDivision() => currentState.stackDivision
        
        case GimmeDivisionWith(num) => currentState stackDivision num

        //////////////
        // Negation //
        //////////////

        case GimmeNegation() => currentState.negation

        ////////////
        // Output //
        ////////////

        case GimmeOutput() => currentState.output

        case GimmeNone => 
          throw new RuntimeException("Shouldn't have added a None line to text")
      }

      if (currentLine.needToPrint) {
        currentState.output
      }
      
      // increment runtime line number in prep for next go around if we aren't
      // jumping somewhere
      if (!lineJump) {
        runtimeLineNumber += 1
      }
    }
  }
}
