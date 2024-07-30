package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class EvilHangmanGame implements IEvilHangmanGame {
    Set<String> words = new HashSet<>();
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        if (dictionary == null) {
            throw new IOException("Empty dictionary");
        }

        BufferedReader reader = new BufferedReader(new FileReader(dictionary));
        String line = reader.readLine();

        while (line != null) {
            if(line.length() == wordLength) {
                System.out.println(line);
                words.add(line);
            }
            line = reader.readLine();
        }
        reader.close();

    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        return Set.of();
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }
}
