package ge.itlab.loanservice.service;

import ge.itlab.loanservice.model.domain.LoanApplication;
import ge.itlab.loanservice.model.domain.UserInfo;
import ge.itlab.loanservice.model.dto.LoanApplicationBaseDto;
import ge.itlab.loanservice.model.dto.LoanApplicationDto;
import ge.itlab.loanservice.model.dto.UserInfoDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ObjectMapper {

    @SneakyThrows
    public LoanApplication dtoToDomain(LoanApplicationBaseDto dto) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setPersonalId(dto.getPersonalId());
        loanApplication.setFirstName(dto.getFirstName());
        loanApplication.setLastName(dto.getLastName());
        loanApplication.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getBirthDate()));
        loanApplication.setEmployer(dto.getEmployer());
        loanApplication.setSalary(dto.getSalary());
        loanApplication.setMonthlyLiability(dto.getMonthlyLiability());
        loanApplication.setRequestedAmount(dto.getRequestedAmount());
        loanApplication.setRequestedTerm(dto.getRequestedTerm());

        return loanApplication;
    }

    public LoanApplicationDto domainToDto(LoanApplication domain) {

        LoanApplicationDto dto = new LoanApplicationDto();
        dto.setPersonalId(domain.getPersonalId());
        dto.setFirstName(domain.getFirstName());
        dto.setLastName(domain.getLastName());
        dto.setBirthDate(domain.getBirthDate().toString());
        dto.setEmployer(domain.getEmployer());
        dto.setSalary(domain.getSalary());
        dto.setMonthlyLiability(domain.getMonthlyLiability());
        dto.setRequestedAmount(domain.getRequestedAmount());
        dto.setRequestedTerm(domain.getRequestedTerm());
        dto.setDecision(domain.getDecision());

        return dto;

    }

    public UserInfo userUnfoDtoToDomain(UserInfoDto dto) {
        UserInfo domain = new UserInfo();
        domain.setUsername(dto.getUsername());
        domain.setPassword(dto.getPassword());
        domain.setRoles("ROLE_OPERATOR");

        return domain;
    }

}
