package ge.itlab.loanservice.model.exception;

public class PasswordMatchingException extends RuntimeException {

    public PasswordMatchingException(String message) {
        super(message);
    }

}
