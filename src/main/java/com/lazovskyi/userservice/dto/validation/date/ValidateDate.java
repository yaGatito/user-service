package com.lazovskyi.userservice.dto.validation.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Used to validate specified date in com.lazovskyi.dto.validation.date.DateType
 * When the date is null return true
 */
public @interface ValidateDate {
    DateType value();

    String message() default "invalid string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
