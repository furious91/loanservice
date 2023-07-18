package ge.itlab.loanservice.model.exception;

public class LoanNotFound extends RuntimeException {

    public LoanNotFound(String messge) {
        super(messge);
    }

}
