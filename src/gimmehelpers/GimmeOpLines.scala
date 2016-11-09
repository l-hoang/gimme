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

  case class GimmeBool(bool: Boolean) extends GimmeOp
  case class GimmeBoolRandom() extends GimmeOp

  case class GimmeOutput() extends GimmeOp
}
