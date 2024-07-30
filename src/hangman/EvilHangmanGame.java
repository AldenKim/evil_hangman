package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private final Set<String> words = new HashSet<>();
    private final SortedSet<Character> guesses = new TreeSet<>();
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
            throw new EmptyDictionaryException("No words with given length");
        }

        BufferedReader reader = new BufferedReader(new FileReader(dictionary));
        String line = reader.readLine();

        if(Objects.equals(line, null)) {
            throw new EmptyDictionaryException("No words in dictionary");
        }

        while (line != null) {
            if(line.length() == wordLength) {
                words.add(line);
            }
            line = reader.readLine();
        }
        reader.close();

        if(words.isEmpty()) {
            throw new EmptyDictionaryException("No words with given length");
        }

        guesses.clear();
        currentPattern = "";
        for(int i = 0; i < wordLength; i++) {
            currentPattern += "-";
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if(guesses.contains(guess)) {
            throw new GuessAlreadyMadeException("That letter has been used already");
        }

        guesses.add(guess);

        return Set.of();
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guesses;
    }
}
