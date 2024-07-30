package hangman;

import java.io.File;

public class EvilHangman {

    public static void main(String[] args) {
        EvilHangmanGame game = new EvilHangmanGame();
        game.getGuess(Integer.parseInt(args[2]));
        try {
            game.startGame(new File(args[0]), Integer.parseInt(args[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
