package ge.itlab.loanservice.service.model;

import ge.itlab.loanservice.model.enums.Decision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecisionWithCreditScore {

    private Decision decision;
    private Double creditScore;

}
