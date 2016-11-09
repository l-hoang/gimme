package gimmehelpers

/* holds line types for storing lines in program text */
object GimmeOpLines {
  abstract sealed class GimmeOp

  case class GimmeNum(num: Int) extends GimmeOp
  case class GimmeString(str: String) extends GimmeOp
  case class GimmeBool(bool: Boolean) extends GimmeOp
}

