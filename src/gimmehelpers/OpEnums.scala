package gimmehelpers

/* basically an enum class for my ops */
object OpEnums {
  abstract sealed class GimmeOpEnum

  case object G_NONE extends GimmeOpEnum

  case object G_NUMBER extends GimmeOpEnum
  case object G_NUMBER_RANDOM extends GimmeOpEnum
  case object G_NUMBER_RANGE extends GimmeOpEnum

  case object G_STRING extends GimmeOpEnum
  case object G_STRING_RANDOM extends GimmeOpEnum

  case object G_BOOL extends GimmeOpEnum
  case object G_BOOL_RANDOM extends GimmeOpEnum

  case object G_COND_BEGIN extends GimmeOpEnum
  case object G_COND_END extends GimmeOpEnum

  case object G_ADDITION extends GimmeOpEnum
  case object G_ADDITION_WITH extends GimmeOpEnum
  case object G_SUBTRACTION extends GimmeOpEnum
  case object G_SUBTRACTION_WITH extends GimmeOpEnum
  case object G_MULTIPLICATION extends GimmeOpEnum
  case object G_MULTIPLICATION_WITH extends GimmeOpEnum
  case object G_DIVISION extends GimmeOpEnum
  case object G_DIVISION_WITH extends GimmeOpEnum

  case object G_OUTPUT extends GimmeOpEnum
}
