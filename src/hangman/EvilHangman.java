package hangman;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;

public class EvilHangman {
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
            System.out.println();
            System.out.println("You have " + game.getGuessCount() + " guesses left");
            printUsedLetters(game.getGuessedLetters());
            String curr = game.getCurrentPattern();
            System.out.println("Word: " + curr);
            System.out.print("Enter guess: ");
            char enteredLetter = SCANNER.next().charAt(0);

            try {
                game.makeGuess(enteredLetter);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            if(Objects.equals(curr, game.getCurrentPattern())) {
                System.out.println("Sorry, there are no " + enteredLetter + "'s");
                game.setGuessCount(game.getGuessCount()-1);
            } else {
                System.out.println("Yes, there is " + lettersFound(game.getCurrentPattern(), enteredLetter) + " " + enteredLetter);
            }
        }

        if(game.getGuessCount() == 0) {
            System.out.println("No more guesses left! GAME OVER!");
            System.out.println("The word was: " + randomWordLose(game.getWords()));
        }
    }

    public static void printUsedLetters(SortedSet<Character> used) {
        System.out.print("Used letters: ");
        for (Character c : used) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static int lettersFound (String pattern, char guess) {
        int count = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == guess) {
                count++;
            }
        }
        return count;
    }

    public static String randomWordLose (Set<String> words) {
        for(String string : words) {
            return string;
        }
        return "";
    }
}
