package ge.itlab.loanservice.model.anotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateTimeValidator implements ConstraintValidator<DateTimeValid, String> {

    private String dateFormat;

    @Override
    public void initialize(DateTimeValid constraintAnnotation) {
        dateFormat = constraintAnnotation.format();
    }

    @Override
    public boolean isValid(String strDate, ConstraintValidatorContext context) {
        try {
            DateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormat);
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(strDate);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
