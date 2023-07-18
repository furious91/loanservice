package ge.itlab.loanservice.model.anotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateTimeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeValid {

    String message() default "Invalid datetime!";

    String format() default "dd/MM/yyyy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}