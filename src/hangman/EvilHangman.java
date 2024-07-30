package hangman;

import java.io.File;

public class EvilHangman {

    public static void main(String[] args) {
        EvilHangmanGame test = new EvilHangmanGame();
        try {
            test.startGame(new File(args[0]), Integer.parseInt(args[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
