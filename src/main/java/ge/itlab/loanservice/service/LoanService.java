package ge.itlab.loanservice.service;

import ge.itlab.loanservice.model.domain.LoanApplication;
import ge.itlab.loanservice.model.dto.LoanApplicationBaseDto;
import ge.itlab.loanservice.model.dto.LoanApplicationDto;
import ge.itlab.loanservice.model.enums.Decision;
import ge.itlab.loanservice.model.exception.ApplicationAlreadyExists;
import ge.itlab.loanservice.model.exception.FieldNotFoundException;
import ge.itlab.loanservice.model.exception.LoanNotFound;
import ge.itlab.loanservice.model.exception.NoAuthorityException;
import ge.itlab.loanservice.repo.LoanRepository;
import ge.itlab.loanservice.service.model.DecisionWithCreditScore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final ScoringCalculator scoringCalculator;
    private final ObjectMapper objectMapper;
    private final LoanRepository loanRepository;

    public String createLoanApplciation(LoanApplicationBaseDto loanApplicationDto) {
        Optional<LoanApplication> byId = loanRepository.findById(loanApplicationDto.getPersonalId());
        if (byId.isPresent()) {
            throw new ApplicationAlreadyExists("Application Already Exists");
        }
        DecisionWithCreditScore decisionWithCreditScore = scoringCalculator.calculateScore(loanApplicationDto);
        LoanApplication loanApplication = objectMapper.dtoToDomain(loanApplicationDto);
        loanApplication.setDecision(decisionWithCreditScore.getDecision().decision);
        loanApplication.setCreditScore(decisionWithCreditScore.getCreditScore());
        loanRepository.save(loanApplication);
        return decisionWithCreditScore.getDecision().decision;
    }

    public List<LoanApplicationDto> getAllLoanApplications(String fieldCode) {
        List<LoanApplication> allApplicationsFromDB = loanRepository.findAll();
        List<LoanApplicationDto> list = allApplicationsFromDB.stream().map(objectMapper::domainToDto).toList();
        if (!fieldCode.isEmpty()) {
            return sortByFieldName(list, fieldCode);
        } else {
            return list;
        }
    }

    public List<LoanApplication> getManualLoans() {
        return loanRepository.findAllByDecision(Decision.MANUAL.decision);
    }

    public void updateByDecisionAndCreditScore(String personalId, String decision, String creditScore) {
        Optional<LoanApplication> loanFromDBOptional = loanRepository.findById(personalId);
        if (loanFromDBOptional.isEmpty()) {
            throw new LoanNotFound("Loan not found with personal ID: " + personalId);
        }
        LoanApplication loanFromDB = loanFromDBOptional.get();
        if (!loanFromDB.getDecision().equals(Decision.MANUAL.decision)) {
            throw new NoAuthorityException("Operator has no permission to modify this loan");
        }
        Decision decisionEnum = Decision.valueOf(decision);
        double creditScoreDouble = 0.0;
        try {
            creditScoreDouble = Double.parseDouble(creditScore);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Incorrect format for creditscore");
        }
        loanRepository.updateLoan(creditScoreDouble, decision, personalId);
    }

    public void deleteApplicationById(String personalId) {
        Optional<LoanApplication> loanById = loanRepository.findById(personalId);
        if (loanById.isPresent()) {
            loanRepository.deleteById(personalId);
        } else {
            throw new LoanNotFound("Loan not found by ID: " + personalId);
        }

    }


    private List<LoanApplicationDto> sortByFieldName(List<LoanApplicationDto> dto, String fieldCode) {

        String fieldName;
        Map<String, String> codeNames = new HashMap<>();
        Field[] fields = LoanApplicationBaseDto.class.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            i++;
            codeNames.put(String.valueOf(i), field.getName());
        }

        fieldName = codeNames.get(fieldCode);

        if (fieldName != null) {
            try {
                Field field = LoanApplicationBaseDto.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                return dto.stream()
                        .sorted((first, second) -> {
                            try {
                                String a = (String) field.get(first);
                                String b = (String) field.get(second);
                                return a.compareTo(b);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException("Error", e);
                            }
                        })
                        .collect(Collectors.toList());
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new FieldNotFoundException("Incorrect code. use from 1 till " + fields.length);
        }


    }


}
