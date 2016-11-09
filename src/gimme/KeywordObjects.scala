package gimme

// class that represents the NUMBER keyword
abstract sealed class NumberWord
object NUMBER extends NumberWord

// class that represents the STRING keyword
abstract sealed class StringWord
object STRING extends StringWord

// class that represents the BOOL keyword
abstract sealed class BoolWord
object BOOL extends BoolWord

abstract sealed class AboveWord
object ABOVE extends AboveWord

abstract sealed class BelowWord
object BELOW extends BelowWord

