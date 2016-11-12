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

  val rng = new Random()
  val gimmeLines = new HashMap[Int, GimmeOp]

  // holds current conditional beginning line numbers
  val conditionalStack = new ArrayDeque[Int]
  // holds current loop beginning line numbers
  val loopStack = new ArrayDeque[Int]
  // holds all breaks (line numbers) that exist in a loop (for later update)
  val breakStack = new HashMap[Int, ArrayDeque[Int]]

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
          gimmeLines.put(queueBreaks.next, GimmeBreak(jumpPoint))
        }

        // save loop end with beginning of loop 
        addLine(GimmeLoopEnd(loopBegin))

      ///////////
      // Break //
      ///////////

      case GimmeBreak(_) =>
        // push our line onto the "to handle" stack for this loop
        breakStack(loopStack.peek) push currentLineNumber
        // add line (will be changed later)
        addLine(line)

      /////////////////////
      // Everything else //
      /////////////////////

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

        case GimmeBreak(jumpLocation) => 
          runtimeLineNumber = jumpLocation
          lineJump = true

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

        ////////////
        // Output //
        ////////////

        case GimmeOutput() => currentState.output

        case GimmeNone => 
          throw new RuntimeException("Shouldn't have added a None line to text")
      }
      
      // increment runtime line number in prep for next go around if we aren't
      // jumping somewhere
      if (!lineJump) {
        runtimeLineNumber += 1
      }
    }
  }
}
