package tests

import gimme._
import scala.language.postfixOps

/* A test file for running certain commands to see if the work */
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


        GIMME A "testing apply booleans" AND OUTPUT;
        GIMME (100 > 100);
        GIMME OUTPUT;
        GIMME A "should be false" AND OUTPUT;

        GIMME A "testing bin ops" AND OUTPUT;

        GIMME A 30;
        GIMME A 20;
        GIMME ADDITION;
        GIMME OUTPUT;

        GIMME A "should have been 50" AND OUTPUT;

        GIMME ADDITION WITH NUMBER 1;
        GIMME OUTPUT;

        GIMME A "should have been 51" AND OUTPUT;

        // should be 1
        GIMME SUBTRACTION; 
        GIMME OUTPUT;

        GIMME A "should have been 1" AND OUTPUT;

        GIMME SUBTRACTION WITH NUMBER -8
        // -9
        GIMME OUTPUT;
        GIMME A "should have been -9" AND OUTPUT;

        GIMME MULTIPLICATION;
        // -9
        GIMME OUTPUT;
        GIMME A "should have been -9"
        GIMME OUTPUT;

        GIMME MULTIPLICATION WITH NUMBER 3;
        // -27
        GIMME OUTPUT;
        GIMME A "should have been -27"
        GIMME OUTPUT;

        GIMME DIVISION;
        // 3
        GIMME OUTPUT;
        GIMME A "should have been 3"
        GIMME OUTPUT;

        GIMME DIVISION WITH NUMBER 3;
        // 1
        GIMME OUTPUT;
        GIMME A "should have been 1"
        GIMME OUTPUT;

        GIMME A "testing bin ops complete";
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

        GIMME A 30;
        GIMME A 31;
        
        GIMME A "LOL TEST" AND OUTPUT;

        GIMME GREATER THAN;
        GIMME OUTPUT;
        GIMME GREATER THAN EQUAL TO;
        GIMME OUTPUT;

        GIMME A 31;

        GIMME LESS THAN;
        GIMME OUTPUT;
        GIMME LESS THAN EQUAL TO;
        GIMME OUTPUT;
        GIMME A 20;
        GIMME EQUAL TO;
        GIMME OUTPUT;

        // true
        GIMME GREATER THAN NUMBER 30;
        GIMME OUTPUT;
        //false
        GIMME GREATER THAN EQUAL TO NUMBER 19;
        GIMME OUTPUT;

        // false
        GIMME LESS THAN NUMBER 30;
        GIMME OUTPUT;

        // false
        GIMME LESS THAN EQUAL TO NUMBER 30;
        GIMME OUTPUT;

        // true
        GIMME EQUAL TO NUMBER 20;
        GIMME OUTPUT;

        //////////////////////////////////
        // Function declaration testing //
        //////////////////////////////////

        GIMME A "FUNCTION DECL TEST" AND OUTPUT;

        GIMME THE BELOW AS "lol";
        GIMME A "Shouldn't print this" AND OUTPUT;
        GIMME A "Shouldn't print this" AND OUTPUT;
        GIMME A "Shouldn't print this" AND OUTPUT;
        GIMME A "Shouldn't print this" AND OUTPUT;
        GIMME A "Shouldn't print this" AND OUTPUT;
        GIMME THE END OF "lol";
        
        GIMME A "FUNCTION DECL DONE" AND OUTPUT;

        //////////////////////
        // Negation testing //
        //////////////////////

        GIMME A "TESTING NEGATION" AND OUTPUT;

        GIMME A TRUE;
        GIMME NEGATION;
        GIMME OUTPUT; // FALSE
        GIMME NEGATION;
        GIMME OUTPUT; // TRUE

        GIMME A 20;
        GIMME OUTPUT; // 20
        GIMME NEGATION;
        GIMME OUTPUT; // -20
        GIMME NEGATION;
        GIMME OUTPUT; // 20

        //////////////////
        // Loop testing //
        //////////////////

        GIMME A 1;

        GIMME CONTINUOUSLY THE BELOW;
        GIMME A "lol here" AND OUTPUT;

        GIMME CONTINUOUSLY THE BELOW;
        GIMME A "print once" AND OUTPUT;
        GIMME AN EXIT;
        GIMME CONTINUOUSLY THE ABOVE;

        GIMME ADDITION WITH NUMBER 1;

        GIMME LESS THAN EQUAL TO NUMBER 5;
        GIMME AN EXIT IF TRUE;
        GIMME CONTINUOUSLY THE ABOVE;

        ///////////////////////////
        // Function call testing //
        ///////////////////////////

        GIMME THE RESULT OF "lol";
        GIMME A "done" AND OUTPUT;

        //////////////////////////////////
        // And output construct testing //
        //////////////////////////////////

        GIMME A "\n\n\n\n with output test \n\n\n" AND OUTPUT;

        GIMME A 123 AND OUTPUT;
        GIMME (1 + 1) AND OUTPUT;
        GIMME A TRUE AND OUTPUT;
        GIMME A FALSE AND OUTPUT;
        GIMME A NUMBER BETWEEN 20 AND 21 AND OUTPUT;

        GIMME A BOOL AND OUTPUT;
        GIMME (1 > 3) AND OUTPUT;
        GIMME A true AND OUTPUT;
        GIMME A false AND OUTPUT;

        GIMME A 1;
        GIMME A 1;

        // should be 2
        GIMME ADDITION AND OUTPUT;
        // should be 3
        (GIMME ADDITION WITH NUMBER 1) AND OUTPUT;

        // should be 1
        GIMME SUBTRACTION AND OUTPUT;
        // should be 2
        GIMME SUBTRACTION WITH NUMBER 3 AND OUTPUT;

        // should be 2
        GIMME MULTIPLICATION AND OUTPUT;
        // should be 6
        GIMME MULTIPLICATION WITH NUMBER 3 AND OUTPUT;

        // should be 3
        GIMME DIVISION AND OUTPUT;
        // should be 3
        GIMME DIVISION WITH NUMBER 9 AND OUTPUT;


        // FALSE
        GIMME GREATER THAN AND OUTPUT;
        // TRUE
        GIMME GREATER THAN EQUAL TO AND OUTPUT;
        // FALSE
        GIMME LESS THAN AND OUTPUT;
        // TRUE
        GIMME LESS THAN EQUAL TO AND OUTPUT;
        // TRUE
        GIMME EQUAL TO AND OUTPUT;

        // true
        GIMME GREATER THAN NUMBER 4 AND OUTPUT;
        // true
        GIMME GREATER THAN EQUAL TO NUMBER 4 AND OUTPUT;
        // FALSE
        GIMME LESS THAN NUMBER 4 AND OUTPUT;
        // false
        GIMME LESS THAN EQUAL TO NUMBER 4 AND OUTPUT;
        // true
        GIMME EQUAL TO NUMBER 3 AND OUTPUT;

        RUN;
    }
}
