package tests

import gimme._
import scala.language.postfixOps

object SanityTest extends Gimme {
    def main(args: Array[String]) = {
        GIMME A STRING;
        GIMME OUTPUT;

        GIMME A NUMBER;
        GIMME OUTPUT;

        GIMME A BOOL;
        GIMME OUTPUT;

        GIMME A NUMBER BETWEEN 100 AND 110;
        GIMME OUTPUT;

        GIMME A 30;
        GIMME OUTPUT;

        GIMME A "lol";
        GIMME OUTPUT;

        GIMME A true;
        GIMME OUTPUT;

        GIMME A (1000 + 3);
        GIMME OUTPUT;
        
        GIMME (100 - 100 + 2);
        GIMME OUTPUT;

        GIMME A FALSE;

        GIMME THE BELOW IF TRUE;
        GIMME A 3;
        GIMME OUTPUT;
        GIMME THE ABOVE;

        GIMME A FALSE;

        GIMME THE BELOW IF FALSE;
        GIMME A (4 + 3);
        GIMME OUTPUT;
        GIMME THE ABOVE;

        //GIMME CONTINUOUSLY THE BELOW;
        //GIMME AN EXIT IF FALSE;
        //GIMME AN EXIT IF TRUE;
        //GIMME AN EXIT;
        //GIMME CONINUOUSLY THE ABOVE;

        RUN;
    }
}
