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

// represents TRUE
abstract sealed class TrueWord
object TRUE extends TrueWord

// represents FALSE
abstract sealed class FalseWord
object FALSE extends FalseWord

// represents ABOVE
abstract sealed class AboveWord
object ABOVE extends AboveWord

// represents BELOW
abstract sealed class BelowWord
object BELOW extends BelowWord

// represents BELOW
abstract sealed class WithWord
object WITH extends WithWord

// represents OUTPUT
abstract sealed class OutputWord
object OUTPUT extends OutputWord

