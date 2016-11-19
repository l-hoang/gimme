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

  case class GimmeFunctionBegin(functionName: String, 
                                funcLineEnd: Int) extends GimmeOp
  case class GimmeFunctionEnd(functionName: String) extends GimmeOp

  case class GimmeGreater() extends GimmeOp
  case class GimmeGreaterEqual() extends GimmeOp
  case class GimmeLess() extends GimmeOp
  case class GimmeLessEqual() extends GimmeOp
  case class GimmeEqual() extends GimmeOp

  case class GimmeGreaterNum(num: Int) extends GimmeOp
  case class GimmeGreaterEqualNum(num: Int) extends GimmeOp
  case class GimmeLessNum(num: Int) extends GimmeOp
  case class GimmeLessEqualNum(num: Int) extends GimmeOp
  case class GimmeEqualNum(num: Int) extends GimmeOp

  case class GimmeLoopBegin(lineAfterLoop: Int) extends GimmeOp
  case class GimmeLoopEnd(loopLineBeginning: Int) extends GimmeOp

  case class GimmeBreak(breakLine: Int) extends GimmeOp
  case class GimmeBreakTrue(breakLine: Int) extends GimmeOp
  case class GimmeBreakFalse(breakLine: Int) extends GimmeOp

  case class GimmeAddition() extends GimmeOp
  case class GimmeAdditionWith(num: Int) extends GimmeOp
  case class GimmeSubtraction() extends GimmeOp
  case class GimmeSubtractionWith(num: Int) extends GimmeOp
  case class GimmeMultiplication() extends GimmeOp
  case class GimmeMultiplicationWith(num: Int) extends GimmeOp
  case class GimmeDivision() extends GimmeOp
  case class GimmeDivisionWith(num: Int) extends GimmeOp

  case class GimmeNegation() extends GimmeOp

  case class GimmeOutput() extends GimmeOp
}
