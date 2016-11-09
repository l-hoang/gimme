package gimmehelpers


// import the case classes that represent gimme operations
// located in program line builder
import GimmeOpLines._

import scala.collection.mutable.HashMap

class ProgramText {
  // current line number
  var currentLineNumber = 1;
  val gimmeLines = new HashMap[Int, GimmeOp]

  /* add a line to our current lines, then increment the line counter by 1 */
  def addLine(line: GimmeOp) = {
    gimmeLines += Tuple2(currentLineNumber, line)
    currentLineNumber += 1
  }
}
