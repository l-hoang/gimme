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

        RUN;
    }
}
