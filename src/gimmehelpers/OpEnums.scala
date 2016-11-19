package gimmehelpers

/* basically an enum class for my ops; these are set into the Line Builder
 * so that the Line builder knows what to return */
object OpEnums {
  abstract sealed class GimmeOpEnum

  case object G_NONE extends GimmeOpEnum

  case object G_NUMBER extends GimmeOpEnum
  case object G_NUMBER_RANDOM extends GimmeOpEnum
  case object G_NUMBER_RANGE extends GimmeOpEnum

  case object G_STRING extends GimmeOpEnum
  case object G_STRING_RANDOM extends GimmeOpEnum
  case object G_STRING_OUTPUT extends GimmeOpEnum

  case object G_BOOL extends GimmeOpEnum
  case object G_BOOL_RANDOM extends GimmeOpEnum

  case object G_COND_BEGIN extends GimmeOpEnum
  case object G_COND_END extends GimmeOpEnum

  case object G_FUNCTION_BEGIN extends GimmeOpEnum
  case object G_FUNCTION_END extends GimmeOpEnum

  case object G_GREATER extends GimmeOpEnum
  case object G_GREATER_EQUAL extends GimmeOpEnum
  case object G_LESS extends GimmeOpEnum
  case object G_LESS_EQUAL extends GimmeOpEnum
  case object G_EQUAL extends GimmeOpEnum

  case object G_GREATER_N extends GimmeOpEnum
  case object G_GREATER_EQUAL_N extends GimmeOpEnum
  case object G_LESS_N extends GimmeOpEnum
  case object G_LESS_EQUAL_N extends GimmeOpEnum
  case object G_EQUAL_N extends GimmeOpEnum

  case object G_LOOP_BEGIN extends GimmeOpEnum
  case object G_LOOP_END extends GimmeOpEnum

  case object G_BREAK extends GimmeOpEnum
  case object G_BREAK_TRUE extends GimmeOpEnum
  case object G_BREAK_FALSE extends GimmeOpEnum

  case object G_ADDITION extends GimmeOpEnum
  case object G_ADDITION_WITH extends GimmeOpEnum
  case object G_SUBTRACTION extends GimmeOpEnum
  case object G_SUBTRACTION_WITH extends GimmeOpEnum
  case object G_MULTIPLICATION extends GimmeOpEnum
  case object G_MULTIPLICATION_WITH extends GimmeOpEnum
  case object G_DIVISION extends GimmeOpEnum
  case object G_DIVISION_WITH extends GimmeOpEnum

  case object G_NEGATION extends GimmeOpEnum

  case object G_OUTPUT extends GimmeOpEnum
}
