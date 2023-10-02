package com.lazovskyi.userservice.dto.validation.date;

import com.lazovskyi.userservice.config.bean.UserValidationProperties;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class DateValidator implements ConstraintValidator<ValidateDate, LocalDate> {

    private final UserValidationProperties props;
    private DateType type;

    @Override
    public void initialize(ValidateDate constraintAnnotation) {
        type = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }
        switch (type) {
            case BIRTHDATE:
                Period period = Period.between(date, LocalDate.now());
                return period.getYears() >= props.getPermittedAge();
            default:
                return false;
        }
    }
}