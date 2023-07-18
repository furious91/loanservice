package ge.itlab.loanservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDto extends LoanApplicationBaseDto {

    private String decision;

}
