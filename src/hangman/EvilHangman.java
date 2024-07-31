package hangman;

import java.io.File;
import java.util.Scanner;
import java.util.SortedSet;

public class EvilHangman {
    public static void printUsedLetters(SortedSet<Character> used) {
        System.out.print("Used letters: ");
        for (Character c : used) {
            System.out.print(c);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        EvilHangmanGame game = new EvilHangmanGame();
        game.setGuessCount(Integer.parseInt(args[2]));
        Scanner SCANNER = new Scanner(System.in);

        try {
            game.startGame(new File(args[0]), Integer.parseInt(args[1]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        while(game.getGuessCount() > 0) {
            System.out.println("You have " + game.getGuessCount() + " guesses left");
            printUsedLetters(game.getGuessedLetters());
            System.out.println();
        }

        System.out.println("No more guesses left! GAME OVER!");
    }

}
