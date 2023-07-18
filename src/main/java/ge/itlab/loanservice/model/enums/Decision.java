package ge.itlab.loanservice.model.enums;

public enum Decision {

    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    MANUAL("MANUAL");

    public final String decision;

    Decision(String decision) {
        this.decision = decision;
    }

}
