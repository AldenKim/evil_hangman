package hangman;

public class EmptyDictionaryException extends Exception {
    public EmptyDictionaryException() {
        super();
    }

    public EmptyDictionaryException(String message) {
        super(message);
    }
}
