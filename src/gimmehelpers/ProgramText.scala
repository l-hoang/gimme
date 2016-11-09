package gimmehelpers


// import the case classes that represent gimme operations
// located in program line builder
import GimmeOpLines._
import OpEnums._

import scala.collection.mutable.HashMap
import scala.util.Random

/* This class holds the "text" of the program, i.e. the actual program split
 * into lines. It also has the runtime method for running the program. */
class ProgramText {
  // current line number
  var currentLineNumber = 1;

  val rng = new Random()
  val gimmeLines = new HashMap[Int, GimmeOp]

  /* add a line to our current lines, then increment the line counter by 1 */
  def addLine(line: GimmeOp) = {
    gimmeLines += Tuple2(currentLineNumber, line)
    currentLineNumber += 1
  }

  def finishLine(lineBuilder: ProgramLineBuilder) = {
    // grab the line from the builder
    val line = lineBuilder.returnLine

    if (line != GimmeNone) {
      addLine(line)
    }
  }

  /* runs the program starting from line 1 */
  def runProgram(currentState: ProgramState) = {
    var runtimeLineNumber = 1

    // loop as long as we are at a valid line number
    while (runtimeLineNumber < currentLineNumber) {
      // grab the line
      val currentLine = gimmeLines(runtimeLineNumber)

      currentLine match {
        case GimmeNum(num: Int) => currentState setNumber num

        case GimmeNumRandom() => currentState setNumber (rng nextInt 100)

        case GimmeNumRange(low: Int, high: Int) => 
          // create a range then select a random number from it
          val chosenRange = (low to high)
          currentState setNumber chosenRange(rng nextInt chosenRange.length)

        case GimmeString(str: String) => currentState setString str

        case GimmeStringRandom() => currentState setString 
          ((rng.nextPrintableChar.toString) + (rng.nextPrintableChar.toString) + 
          (rng.nextPrintableChar.toString) + (rng.nextPrintableChar.toString) + 
          (rng.nextPrintableChar.toString))

        case GimmeBool(bool: Boolean) => currentState setBool bool

        case GimmeBoolRandom() => currentState setBool rng.nextBoolean

        case GimmeOutput() => currentState.output

        case GimmeNone => 
          throw new RuntimeException("Shouldn't have added a None line to text")
      }
      
      // increment runtime line number in prep for next go around
      runtimeLineNumber += 1
    }
  }
}
