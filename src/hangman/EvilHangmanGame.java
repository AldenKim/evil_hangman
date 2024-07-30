package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private final Set<String> words = new HashSet<>();
    private final SortedSet<Character> guesses = new TreeSet<>();
    private final Map<String, Set<String>> patternsWithWords = new HashMap<>();
    public int guessCount;
    private String currentPattern;

    public void getGuess(int count) {
        guessCount = count;
    }

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        words.clear();

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
        patternsWithWords.clear();

        for(String word: words) {
            StringBuilder buildPattern = new StringBuilder(currentPattern);
            for(int i = 0; i < word.length(); i++) {
                if(guess == word.charAt(i)) {
                    buildPattern.setCharAt(i, guess);
                }
            }

            String pattern = buildPattern.toString();
            patternsWithWords.putIfAbsent(pattern, new HashSet<>());
            patternsWithWords.get(pattern).add(word);
        }

        int max = 0;
        Set<String> returnVal = new HashSet<>();

        for(Map.Entry<String, Set<String>> entry : patternsWithWords.entrySet()) {
            if(entry.getValue().size() > max) {
                returnVal = entry.getValue();
                max = entry.getValue().size();
                currentPattern = entry.getKey();
            } else if(entry.getValue().size() == max) {
                String tieBreak = tieBreak(entry.getKey(), currentPattern, guess);
                returnVal = patternsWithWords.get(tieBreak);
                currentPattern = tieBreak;
            }
        }
        words.clear();
        words.addAll(returnVal);
        guessCount--;

        return returnVal;
    }


    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guesses;
    }

    private String tieBreak(String newPattern, String oldPattern, Character guess) {
        boolean newHasGuess = newPattern.contains(Character.toString(guess));
        boolean oldHasGuess = oldPattern.contains(Character.toString(guess));

        if(newHasGuess && !oldHasGuess) {
            return oldPattern;
        }else if(!newHasGuess && oldHasGuess) {
            return newPattern;
        }

        if(countCharactersInPattern(newPattern) < countCharactersInPattern(oldPattern)) {
            return newPattern;
        } else if  (countCharactersInPattern(newPattern) > countCharactersInPattern(oldPattern)) {
            return oldPattern;
        }

        int newRight = findRightMostInstance(newPattern, guess);
        int oldRight = findRightMostInstance(oldPattern, guess);

        if(newRight > oldRight) {
            return newPattern;
        } else if (newRight < oldRight) {
            return oldPattern;
        }

        if(findNextRight(newPattern, oldPattern, guess) != -1) {
            return newPattern;
        }

        return oldPattern;
    }

    private int countCharactersInPattern(String pattern) {
        int count = 0;

        for(Character c : pattern.toCharArray()) {
            if(c != '-') {
                count++;
            }
        }

        return count;
    }

    private int findRightMostInstance(String pattern, Character guess) {
        for(int i = pattern.length() - 1; i >= 0; i--) {
            if(pattern.charAt(i) == guess) {
                return i;
            }
        }

        return -1;
    }

    private int findNextRight(String newPattern, String oldPattern, char guess) {
        for (int i = newPattern.length() - 1; i >= 0; i--) {
            if (newPattern.charAt(i) == guess && oldPattern.charAt(i) != guess) {
                return i;
            } else if (oldPattern.charAt(i) == guess && newPattern.charAt(i) != guess) {
                return -1;
            }
        }
        return -1;
    }
}
