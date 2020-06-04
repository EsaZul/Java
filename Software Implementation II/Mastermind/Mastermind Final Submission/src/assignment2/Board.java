package assignment2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static assignment2.Game.secret;
    public class Board {

    private static String guess;
    private static Scanner input = new Scanner(System.in);
    public static ArrayList<String> history = new ArrayList<String>();


    public Board() {
        guess = input.nextLine();
    }

    static boolean checkCode() {
        int totalColors = GameConfiguration.colors.length;
        int valid = 0;

        System.out.println("You have " + Game.turns + " guesses remaining");
        System.out.println("Colors : " + Arrays.toString(GameConfiguration.colors));
        System.out.println("What is your next guess?");
        System.out.println("Type in the characters for your guess and press enter");
        System.out.println("Type 'HISTORY' to view your past guesses and results");
        System.out.println("Enter guess:");

        //Input for Guess
        Board board = new Board();

        //Qualifications for acceptable inputs (HISTORY, equal length, available colors
        if (guess.equals("HISTORY")) {
            for (int i = 0; i < history.size(); i++) {
                System.out.println(history.get(i));
            }
            return false;
        } else if (guess.length() != secret.length()) {
            System.out.println("INVALID GUESS, TRY AGAIN");
            return false;
        }

        for (int x = 0; x < GameConfiguration.pegNumber; x++) {
            for (int y = 0; y < totalColors; y++) {
                if (guess.charAt(x) == GameConfiguration.colors[y].charAt(0)) {
                    valid++;
                }
            }
        }
        if (valid != GameConfiguration.pegNumber) {
            System.out.println("INVALID GUESS,TRY AGAIN");
            return false;
        }
        return true;
    }

    public static void compareCode() {

        int black = 0;
        int white = 0;

        char[] tempGuess = guess.toCharArray();
        char[] tempColor = secret.toCharArray();
        int colorLength = GameConfiguration.pegNumber;

        //GIVES BLACK PEGS
        for (int a = 0; a < colorLength; a++) {
            if (tempGuess[a] == tempColor[a]) {
                tempGuess[a] = '-';
                tempColor[a] = '-';
                black++;
            }
        }

        //GIVES WHITE PEGS
        for (int x = 0; x < colorLength; x++) {
            for (int y = 0; y < colorLength; y++) {
                if ((tempGuess[x] == tempColor[y]) & ((tempGuess[x] != '-') | (tempColor[y] != '-'))) {
                    tempGuess[x] = '-';
                    tempColor[y] = '-';
                    white++;
                }
            }
        }

        String tempString = guess + "      " + black + "B_" + white + "W";

        //ADDS TO HISTORY
        history.add(tempString);

        System.out.println("RESULT : " + black + " Black Pegs & " + white + " White Pegs");

        if (black == GameConfiguration.pegNumber) {
            Game.winner = 1;
        }
    }
}
