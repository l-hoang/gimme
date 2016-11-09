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

  case object G_OUTPUT extends GimmeOpEnum
}
