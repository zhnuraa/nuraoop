package exception;

/**
 * Custom exception for invalid user input / invalid model state.
 *
 * Week 6 requirement: do NOT print warnings inside setters.
 * Throw an exception and handle it in MenuManager (try/catch).
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
