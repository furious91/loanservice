package ge.itlab.loanservice.model.dto;

import ge.itlab.loanservice.model.anotation.DateTimeValid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplicationBaseDto {

    @NotNull
    private String personalId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @DateTimeValid
    private String birthDate;
    @NotNull
    private String employer;
    @NotNull
    private Double salary;
    @NotNull
    private Double monthlyLiability;
    @NotNull
    private Double requestedAmount;
    @NotNull
    private Integer requestedTerm;


}
