package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private final Set<String> words = new HashSet<>();
    private final Set<String> guesses = new HashSet<>();
    private int guessCount;
    private String currentPattern;

    public void getGuess(int count) {
        guessCount = count;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        if (dictionary == null) {
            throw new IOException("Empty dictionary");
        }
        if(wordLength == 0) {
            throw new EmptyDictionaryException();
        }

        BufferedReader reader = new BufferedReader(new FileReader(dictionary));
        String line = reader.readLine();

        if(Objects.equals(line, null)) {
            throw new EmptyDictionaryException();
        }

        while (line != null) {
            if(line.length() == wordLength) {
                words.add(line);
            }
            line = reader.readLine();
        }
        reader.close();

        if(words.isEmpty()) {
            throw new EmptyDictionaryException();
        }

        guesses.clear();
        currentPattern = "";
        for(int i = 0; i < wordLength; i++) {
            currentPattern += "-";
        }
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
