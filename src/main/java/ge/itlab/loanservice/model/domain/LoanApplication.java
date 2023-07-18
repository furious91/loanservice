package ge.itlab.loanservice.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

    @Id
    private String personalId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Date birthDate;
    @Column
    private String employer;
    @Column
    private Double salary;
    @Column
    private Double monthlyLiability;
    @Column
    private Double requestedAmount;
    @Column
    private Integer requestedTerm;
    @Column
    private String decision;
    @Column
    private Double creditScore;


}
