package gimme

/* The class that holds all of the keywords for Gimme */
class Gimme {
    /* The keyword GIMME which starts lines in this language */
    object GIMME {
        def NUMBER = {
            println(10)
        }

        def BOOL = {
            println(true)
        }

        def STRING = {
            println("test")
        }
    }
}
