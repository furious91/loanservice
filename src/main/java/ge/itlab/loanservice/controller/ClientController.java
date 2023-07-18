package ge.itlab.loanservice.controller;

import ge.itlab.loanservice.model.dto.LoanApplicationBaseDto;
import ge.itlab.loanservice.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final LoanService loanService;

    @PostMapping(value = "/loan/register", consumes = "application/json")
    public ResponseEntity<String> createLoanApplication(@Valid @RequestBody LoanApplicationBaseDto loanApplicationDto) {
        String decision = loanService.createLoanApplciation(loanApplicationDto);
        return ResponseEntity.ok("Decision: " + decision);
    }

}
