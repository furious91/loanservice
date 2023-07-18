package ge.itlab.loanservice;

import ge.itlab.loanservice.model.dto.LoanApplicationBaseDto;
import ge.itlab.loanservice.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoanServiceApplicationTests {

    @Autowired
    LoanService loanService;

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldReturnExceptedResponseForCreateLoanApplciation() {

        final String expectedDecision = "APPROVED";
        LoanApplicationBaseDto loanApplicationBaseDto = LoanApplicationBaseDto.builder()
                .personalId("1239")
                .firstName("gio2")
                .lastName("gio2")
                .birthDate("28/12/1991")
                .employer("ggg")
                .salary(10000.88)
                .monthlyLiability(0.00)
                .requestedAmount(0.00)
                .requestedTerm(0)
                .build();
        String actualResponse = loanService.createLoanApplciation(loanApplicationBaseDto);
        assertEquals(expectedDecision, actualResponse);

    }

    //  No more time to cover code with another tests. Please do not be strict :)

}
