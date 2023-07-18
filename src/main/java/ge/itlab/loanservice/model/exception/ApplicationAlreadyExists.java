package ge.itlab.loanservice.model.exception;

public class ApplicationAlreadyExists extends RuntimeException {

    public ApplicationAlreadyExists(String messge) {
        super(messge);
    }

}
