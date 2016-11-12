package gimmehelpers

/* holds line types for storing lines in program text */
object GimmeOpLines {
  abstract sealed class GimmeOp

  case object GimmeNone extends GimmeOp

  case class GimmeNum(num: Int) extends GimmeOp
  case class GimmeNumRandom() extends GimmeOp
  case class GimmeNumRange(low: Int, high: Int) extends GimmeOp

  case class GimmeString(str: String) extends GimmeOp
  case class GimmeStringRandom() extends GimmeOp
  case class GimmeStringOutput(str: String) extends GimmeOp

  case class GimmeBool(bool: Boolean) extends GimmeOp
  case class GimmeBoolRandom() extends GimmeOp

  case class GimmeCondBegin(condLineEnd: Int, 
                            trueOrFalse: Boolean) extends GimmeOp
  case class GimmeCondEnd() extends GimmeOp

  case class GimmeLoopBegin(lineAfterLoop: Int) extends GimmeOp
  case class GimmeLoopEnd(loopLineBeginning: Int) extends GimmeOp

  case class GimmeBreak(breakLine: Int) extends GimmeOp

  case class GimmeAddition() extends GimmeOp
  case class GimmeAdditionWith(num: Int) extends GimmeOp
  case class GimmeSubtraction() extends GimmeOp
  case class GimmeSubtractionWith(num: Int) extends GimmeOp
  case class GimmeMultiplication() extends GimmeOp
  case class GimmeMultiplicationWith(num: Int) extends GimmeOp
  case class GimmeDivision() extends GimmeOp
  case class GimmeDivisionWith(num: Int) extends GimmeOp

  case class GimmeOutput() extends GimmeOp
}
