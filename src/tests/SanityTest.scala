package tests

import gimme.Gimme
import scala.language.postfixOps

object SanityTest extends Gimme {
    def main(args: Array[String]) = {
        GIMME STRING;
        GIMME OUTPUT;

        GIMME NUMBER;
        GIMME OUTPUT;

        GIMME BOOL;
        GIMME OUTPUT;

        GIMME A NUMBER BETWEEN 100 AND 99
        GIMME OUTPUT;

    }
}
