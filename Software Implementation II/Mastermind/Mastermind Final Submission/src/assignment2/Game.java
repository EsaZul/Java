package assignment2;
import java.util.ArrayList;
import java.util.Scanner;


public class Game {
    static String secret;
    static int winner = 0;
    public static int repeat = 0;
    static int turns = GameConfiguration.guessNumber;

    public Game(String secret, int winner, int repeat, int turns) {
        Game.secret = SecretCodeGenerator.getInstance().getNewSecretCode();
        Game.winner = winner;
        Game.repeat = repeat;
        Game.turns = GameConfiguration.guessNumber;
    }


    public static void runGame() {

        System.out.println("Generating Secret Code...");

        Game first = new Game("any",0, 0, 1 );
        if(Driver.testMode) {
            System.out.println("Secret Code : " + Game.secret); //
        }

        //TURN AND WINNING LOGIC
        while ((Game.winner == 0) & (Game.turns > 0)) {
            while (!Board.checkCode());
            Game.turns--;
            Board.compareCode();
        }
        if (Game.turns == 0) {
            System.out.println("GAME OVER, YOU LOSE!");
        }
        else {
            System.out.println(("CONGRATULATIONS, YOU WIN!"));
        }

        System.out.println("SECRET CODE : " + secret);
        System.out.println("Wanna play again? Enter 'N' for No, or press any other button to play");

        Scanner scanner = new Scanner(System.in);
        String YN = scanner.next();

        //REPEAT LOGIC, REINITIALIZE HISTORY TABLE
        if (YN.equals("N")) {
            System.out.println("Thank You! Goodbye!");
            Game again = new Game("any",0, 0, 1 );
        }
        else {
            Game again = new Game("any",0, 1, 1 );
            Board.history = new ArrayList<String> ();
        }
    }
}


