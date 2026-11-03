/**
 * Base class for banking-related checked exceptions.
 */
public class BankingException extends Exception {
    public BankingException(String message) {
        super(message);
    }
}
