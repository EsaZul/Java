package assignment2;
import assignment2.SecretCodeGenerator;
import assignment2.GameConfiguration;
import assignment2.Game;

import java.util.Scanner;

public class Driver {
    public static boolean testMode;

    public static void main(String[] args) {
        System.out.println("Welcome to Mastermind. Here are the rules.");
        System.out.println("This is a text version of the classic board game Mastermind.");
        System.out.println("The computer will think of a secret code. The code consists of 4 colored pegs.");
        System.out.println("The pegs MUST be one of six colors: blue, green, orange, purple, red, or yellow.");
        System.out.println("A color may appear more than once in the code.");
        System.out.println("You try to guess what colored pegs are in the code and what order they are in.");
        System.out.println("After you make a valid guess the result (feedback) will be displayed.");
        System.out.println("The result consists of a black peg for each peg you have guessed exactly correct (color and position) in your guess.");
        System.out.println("For each peg in the guess that is the correct color, but is out of position, you get a white peg.");
        System.out.println("For each peg, which is fully incorrect, you get no feedback.");
        System.out.println("Only the first letter of the color is displayed. B for Blue, R for Red, and so forth.");
        System.out.println("When entering guesses you only need to enter the first character of each color as a capital letter.");
        System.out.println("You have " + GameConfiguration.guessNumber + " guesses to figure out the secret code or you lose the game.");
        System.out.println("Are you ready to play? Enter 'N' for No, or press any other button to play");

        //TESTMODE BOOLEAN
        String test = args[0];
         if(test.equals("1")){
             testMode = true;
         }
         else {
             testMode = false;
         }

        Scanner scanner = new Scanner(System.in);
        String YN = scanner.next();

        if (YN.equals("N")) {
            System.out.println("Thank You! Goodbye!");
            System.exit(0);
        }

        //RUNS FIRST GAME
        Game.runGame();

        //REPEATS GAME UNTIL "N"
        while (Game.repeat == 1) {
            Game.runGame();
        }
    }
}
