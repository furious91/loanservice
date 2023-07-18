package ge.itlab.loanservice.controller;

import ge.itlab.loanservice.model.dto.LoanApplicationDto;
import ge.itlab.loanservice.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public ResponseEntity<List<LoanApplicationDto>> getAll(@RequestParam("field-code") String fieldCode) {
        return ResponseEntity.ok(loanService.getAllLoanApplications(fieldCode));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public ResponseEntity<String> deleteLoan(@PathVariable("id") String personalId) {
        loanService.deleteApplicationById(personalId);
        return ResponseEntity.ok("Loand deleted with ID: " + personalId);
    }

    @PatchMapping("/{id}/{credit-score}/{decision}")
    @PreAuthorize("hasAuthority('ROLE_OPERATOR')")
    public ResponseEntity<String> updateLoan(@PathVariable("id") String personalId, @PathVariable("credit-score") String creditScore, @PathVariable("decision") String decision) {
        loanService.updateByDecisionAndCreditScore(personalId, decision, creditScore);
        return ResponseEntity.ok("Loan updated");
    }


}
