package gimme

import scala.util.Random

/* The class that holds all of the keywords for Gimme */
class Gimme {
    /* The keyword GIMME which starts lines in this language */
    object GIMME {
        val rng = new Random()

        def NUMBER = {
            val result = rng nextInt 100
            println(result)
        }

        def BOOL = {
            val result = rng nextInt 1
            println(result)
        }

        def STRING = {
            val char1 = rng nextPrintableChar() toString
            val char2 = rng nextPrintableChar() toString
            val char3 = rng nextPrintableChar() toString
            val char4 = rng nextPrintableChar() toString
            val char5 = rng nextPrintableChar() toString
            
            val finalString = char1 + char2 + char3 + char4 + char5

            println(finalString)
        }
    }
}
