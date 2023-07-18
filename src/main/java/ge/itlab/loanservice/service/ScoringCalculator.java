package ge.itlab.loanservice.service;

import ge.itlab.loanservice.model.dto.LoanApplicationBaseDto;
import ge.itlab.loanservice.model.enums.Decision;
import ge.itlab.loanservice.service.model.DecisionWithCreditScore;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class ScoringCalculator {

    public DecisionWithCreditScore calculateScore(LoanApplicationBaseDto loanApplicationDto) {

        int firstNameSum = convertCharToInt(loanApplicationDto.getFirstName().toLowerCase());
        double salary = loanApplicationDto.getSalary() * 1.5;
        double monthlyLiability = loanApplicationDto.getMonthlyLiability() * 3;
        String birthDateString = loanApplicationDto.getBirthDate();
        int day = Integer.parseInt(birthDateString.substring(0, 2));
        int month = Integer.parseInt(birthDateString.substring(3, 5));
        int year = Integer.parseInt(birthDateString.substring(6));
        int julianDay = calculateJulianDay(day, month, year);

        double creditScore = firstNameSum + salary - monthlyLiability + year - month - julianDay;

        if (creditScore < 2500) {
            return new DecisionWithCreditScore(Decision.REJECTED, creditScore);
        } else if (creditScore > 3500) {
            return new DecisionWithCreditScore(Decision.APPROVED, creditScore);
        } else {
            return new DecisionWithCreditScore(Decision.MANUAL, creditScore);
        }
    }

    private int calculateJulianDay(int day, int month, int year) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.DAY_OF_MONTH, day);
        gc.set(GregorianCalendar.MONTH, month - 1);
        gc.set(GregorianCalendar.YEAR, year);
        return gc.get(GregorianCalendar.DAY_OF_YEAR);
    }

    private int convertCharToInt(String firstname) {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Map<Character, Integer> characterInteger = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++) {
            characterInteger.put(alphabet.charAt(i), i + 1);
        }
        int result = 0;
        for (int i = 0; i < firstname.length(); i++) {
            if (characterInteger.containsKey(firstname.charAt(i))) {
                result += characterInteger.get(firstname.charAt(i));
            }
        }

        return result;
    }


}
