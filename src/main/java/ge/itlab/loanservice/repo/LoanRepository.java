package ge.itlab.loanservice.repo;

import ge.itlab.loanservice.model.domain.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface LoanRepository extends JpaRepository<LoanApplication, String> {

    List<LoanApplication> findAllByDecision(String decision);

    @Modifying
    @Query("UPDATE LoanApplication SET creditScore=?1, decision=?2 where personalId=?3")
    void updateLoan(Double creditScore, String decision, String personalId);

}
